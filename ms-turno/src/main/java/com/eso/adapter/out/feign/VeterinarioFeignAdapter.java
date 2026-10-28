package com.eso.adapter.out.feign;

import com.eso.domain.exception.ServicioNoDisponibleException;
import com.eso.domain.port.out.VeterinarioServicePort;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeterinarioFeignAdapter implements VeterinarioServicePort {
    private final VeterinarioFeignClient veterinarioFeignClient;

    @CircuitBreaker(name = "ms-veterinario", fallbackMethod = "fallbackVeterinario")
    @Retry(name = "ms-veterinario", fallbackMethod = "fallbackVeterinario")
    @Override
    public boolean existsById(Long id) {
        try {
            veterinarioFeignClient.findById(id);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }

    private VeterinarioResponse fallbackVeterinario(Long veterinarioId, Throwable throwable) {
        throw new ServicioNoDisponibleException("El servicio de veterinarios no esta disponible: " + throwable.getMessage());
    }
}
