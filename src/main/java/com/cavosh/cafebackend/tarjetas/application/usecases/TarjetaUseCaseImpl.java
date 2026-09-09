package com.cavosh.cafebackend.tarjetas.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.global.infrastructure.security.AesGcmEncryptionUtil;
import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;
import com.cavosh.cafebackend.tarjetas.domain.ports.in.TarjetaUseCase;
import com.cavosh.cafebackend.tarjetas.domain.ports.out.TarjetaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarjetaUseCaseImpl implements TarjetaUseCase {

    private final TarjetaRepositoryPort tarjetaRepository;
    private final AesGcmEncryptionUtil encryptionUtil;

    @Transactional
    public TarjetaUsuario registerNewTarjeta(RegisterTarjetaCommand command) {
        String numero = command.numeroTarjeta().replaceAll("\\s+","");

        if(numero.length() <13 || numero.length() > 19)
            throw new BusinessRuleException("El número de tarjeta no es válido");

        String ultimosCuatro = numero.substring(numero.length() - 4);
        String numeroEncriptado = encryptionUtil.encrypt(numero);

        long cantidadTarjetas = tarjetaRepository.countByUsuarioId(command.usuarioId());
        boolean predeterminado = command.predeterminado() || cantidadTarjetas == 0;

        if(predeterminado)
            tarjetaRepository.uncheckDefaults(command.usuarioId());

        TarjetaUsuario tarjeta = new TarjetaUsuario(
                null,
                command.usuarioId(),
                command.marca().trim(),
                ultimosCuatro,
                numeroEncriptado,
                command.titular().trim(),
                predeterminado,
                Instant.now()
        );

        return tarjetaRepository.save(tarjeta);
    }

    @Transactional(readOnly = true)
    public List<TarjetaUsuario> listTarjetas(Integer usuarioId) {
        return tarjetaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public void checkDefaults(Integer usuarioId, Integer tarjetaId){
        TarjetaUsuario tarjeta = tarjetaRepository.findByIdAndUsuarioId(tarjetaId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("La tarjeta no existe para el usuario"));

        tarjetaRepository.uncheckDefaults(usuarioId);

        TarjetaUsuario actualizada = new TarjetaUsuario(
                tarjeta.id(),
                tarjeta.usuarioId(),
                tarjeta.marca(),
                tarjeta.ultimosCuatro(),
                tarjeta.numeroEncriptado(),
                tarjeta.titular(),
                true,
                tarjeta.createdAt()
        );

        tarjetaRepository.save(actualizada);
    }

    @Transactional
    public void deleteTarjeta(Integer usuarioId, Integer tarjetaId) {
        TarjetaUsuario tarjeta = tarjetaRepository.findByIdAndUsuarioId(tarjetaId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarjeta no encontrada"));

        tarjetaRepository.deleteByIdAndUsuarioId(tarjetaId, usuarioId);

        //Si se eliminó la tarjeta predeterminada y quedan otras, marcar la más reciente
        if (tarjeta.predeterminado()) {
            List<TarjetaUsuario> restantes = tarjetaRepository.findByUsuarioId(usuarioId);
            if (!restantes.isEmpty()) {
                TarjetaUsuario siguiente = restantes.getFirst();
                tarjetaRepository.save(new TarjetaUsuario(
                        siguiente.id(),
                        siguiente.usuarioId(),
                        siguiente.marca(),
                        siguiente.ultimosCuatro(),
                        siguiente.numeroEncriptado(),
                        siguiente.titular(),
                        true,
                        siguiente.createdAt()
                ));
            }
        }
    }
}
