package com.cavosh.cafebackend.global.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Componente encargado de la encriptacion de los datos sensibles de la aplicación, como contraseñas y tokens de autenticación.
 * Utiliza el algoritmo AES en modo GCM para garantizar la confidencialidad e integridad
 */
@Component
public class AesGcmEncryptionUtil {

    //Establecer constantes para la encriptación AES-GCM
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 126; // Longitud del tag de autenticación en bytes
    private static final int IV_LENGTH_BYTE = 12; // Longitud del vector de inicialización en bytes

    //Clave secreta para la encriptación AES-GCM, que se inyecta a través del constructor
    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    //Constructor
    public AesGcmEncryptionUtil(@Value("${app.security.card-secret-key:0123456789abcdef0123456789abcdef}") String secret) {
        //Asegurar 256 bits
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        byte[] validKey = new byte[32];
        System.arraycopy(keyBytes, 0, validKey, 0, Math.min(keyBytes.length, validKey.length));
        this.secretKey = new javax.crypto.spec.SecretKeySpec(validKey, "AES");
    }

    /**
     * Método para encriptar un número de tarjeta de crédito.
     * @param rawCardNumber el número de tarjeta sin encriptar.
     * @return el número de tarjeta encriptado en Base64.
     */
    public String encrypt(String rawCardNumber){
        try {
            byte[] iv = new byte[IV_LENGTH_BYTE];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new javax.crypto.spec.GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(rawCardNumber.replaceAll("\\s+", "").getBytes(StandardCharsets.UTF_8));


            //Empaquetar IV (12 bytes) + cipherText (variable) en un solo arreglo
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
            byteBuffer.put(iv);
            byteBuffer.put(cipherText);
            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando el número de la tarjeta", e);
        }
    }

    /**
     * Método para desencriptar un número de tarjeta de crédito.
     * @param encryptedPayload el número de tarjeta encriptado en Base64.
     * @return el número de tarjeta sin encriptar.
     */
    public String decrypt(String encryptedPayload) {
        try {
            //obtener el arreglo de bytes del payload encriptado
            byte[] decoded = Base64.getDecoder().decode(encryptedPayload);
            ByteBuffer byteBuffer = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH_BYTE];
            byteBuffer.get(iv);

            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la tarjeta", e);
        }
    }

    /**
     * Metodo para extraer los ultimos 4 digitos de un número de tarjeta, eliminando espacios y retornando los ultimos 4 digitos.
     * @param cardNumber - Número de la tarjeta
     * @return - Retorna los 4 digitos
     */
    public static String extractLastFour(String cardNumber) {
        String clean = cardNumber.replaceAll("\\s+", "");
        return clean.length() >= 4 ? clean.substring(clean.length() - 4) : clean;
    }

    /**
     * Genera la máscara para la vista del checkout (ej. "**** 2048")
     * @param lastFour - Últimos 4 dígitos de la tarjeta
     * @return - Retorna la máscara de la tarjeta
     */
    public static String maskCardNumber(String lastFour) {
        return "**** " + lastFour;
    }
}
