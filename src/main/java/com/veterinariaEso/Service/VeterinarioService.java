package com.veterinariaEso.Service;

import com.veterinariaEso.DTO.VeterinarioDTO;
import com.veterinariaEso.Exception.ResourceNotFoundException;
import com.veterinariaEso.Mapper.VeterinarioMapper;
import com.veterinariaEso.Model.Veterinario;
import com.veterinariaEso.Repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final VeterinarioMapper veterinarioMapper;

    public List<VeterinarioDTO> getAllVeterinarios() {
        return veterinarioRepository.findAll().stream().map(veterinarioMapper::toVeterinarioDTO).collect(Collectors.toList());
    }

    public VeterinarioDTO getVeterinarioById(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario", id));
        return veterinarioMapper.toVeterinarioDTO(veterinario);
    }

    @Transactional
    public VeterinarioDTO createVeterinario(VeterinarioDTO veterinarioDTO) {
        if (veterinarioRepository.existsByMatricula(veterinarioDTO.getMatricula())) {
            throw new RuntimeException("Matrícula ya registrada: " + veterinarioDTO.getMatricula());
        }
        Veterinario veterinario = veterinarioMapper.toVeterinario(veterinarioDTO);
        return veterinarioMapper.toVeterinarioDTO(veterinarioRepository.save(veterinario));
    }

    public VeterinarioDTO updateVeterinario(Long id, VeterinarioDTO veterinarioDTO) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario", id));
        veterinario.setNombre(veterinarioDTO.getNombre());
        veterinario.setApellido(veterinarioDTO.getApellido());
        veterinario.setMatricula(veterinarioDTO.getMatricula());
        veterinario.setEspecialidad(veterinarioDTO.getEspecialidad());
        return veterinarioMapper.toVeterinarioDTO(veterinarioRepository.save(veterinario));
    }

    public void deleteVeterinario(Long id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veterinario", id);
        }
        veterinarioRepository.deleteById(id);
    }
}
