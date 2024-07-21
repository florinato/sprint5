package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.domain.FlorEntity;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.dto.FlorDTO;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.repository.FlorRepository;

@Service
public class FlorService {

    @Autowired
    private FlorRepository florRepository;

    public FlorDTO addFlor(FlorDTO florDTO) {
        FlorEntity florEntity = new FlorEntity();
        florEntity.setNomFlor(florDTO.getNomFlor());
        florEntity.setPaisFlor(florDTO.getPaisFlor());
        florRepository.save(florEntity);
        return new FlorDTO(florEntity.getPk_FlorID(), florEntity.getNomFlor(), florEntity.getPaisFlor());
    }

    public FlorDTO updateFlor(Integer id, FlorDTO florDTO) {
        FlorEntity florEntity = florRepository.findById(id).orElseThrow();
        florEntity.setNomFlor(florDTO.getNomFlor());
        florEntity.setPaisFlor(florDTO.getPaisFlor());
        florRepository.save(florEntity);
        return new FlorDTO(florEntity.getPk_FlorID(), florEntity.getNomFlor(), florEntity.getPaisFlor());
    }

    public void deleteFlor(Integer id) {
        florRepository.deleteById(id);
    }

    public FlorDTO getFlorById(Integer id) {
        FlorEntity florEntity = florRepository.findById(id).orElseThrow();
        return new FlorDTO(florEntity.getPk_FlorID(), florEntity.getNomFlor(), florEntity.getPaisFlor());
    }

    public List<FlorDTO> getAllFlors() {
        return florRepository.findAll().stream()
                .map(florEntity -> new FlorDTO(florEntity.getPk_FlorID(), florEntity.getNomFlor(), florEntity.getPaisFlor()))
                .collect(Collectors.toList());
    }
}
