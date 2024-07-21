package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n03.model.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n03.model.dto.FlorDTO;

@Service
public class FlorService {
    private final RestTemplate restTemplate;

    public FlorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FlorDTO> getAllFlors() {
        String url = "http://localhost:9001/flor/api/getAll";
        FlorDTO[] flors = restTemplate.getForObject(url, FlorDTO[].class);
        return Arrays.asList(flors);
    }

    public FlorDTO getFlorById(int id) {
        String url = "http://localhost:9001/flor/api/getOne/" + id;
        return restTemplate.getForObject(url, FlorDTO.class);
    }

    public FlorDTO addFlor(FlorDTO florDTO) {
        String url = "http://localhost:9001/flor/api/add";
        return restTemplate.postForObject(url, florDTO, FlorDTO.class);
    }

    public void updateFlor(int id, FlorDTO florDTO) {
        String url = "http://localhost:9001/flor/api/update/" + id;
        restTemplate.put(url, florDTO);
    }

    public void deleteFlor(int id) {
        String url = "http://localhost:9001/flor/api/delete/" + id;
        restTemplate.delete(url);
    }
}
