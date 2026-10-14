package com.eso.adapter.out.feign;

import com.eso.domain.port.out.VeterinarioServicePort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeterinarioFeignAdapter implements VeterinarioServicePort {
    private final VeterinarioFeignClient veterinarioFeignClient;

    @Override
    public boolean existsById(Long id) {
        try {
            veterinarioFeignClient.findById(id);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}
