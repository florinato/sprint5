package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n03.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n03.model.dto.FlorDTO;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n03.model.services.FlorService;

@RestController
@RequestMapping("/flor")
public class FlorController {

    @Autowired
    private FlorService florService;

    @PostMapping("/clientFlorsAdd")
    public FlorDTO addFlor(@RequestBody FlorDTO florDTO) {
        return florService.addFlor(florDTO);
    }

    @PutMapping("/clientFlorsUpdate/{id}")
    public void updateFlor(@PathVariable int id, @RequestBody FlorDTO florDTO) {
        florService.updateFlor(id, florDTO);
    }

    @DeleteMapping("/clientFlorsDelete/{id}")
    public void deleteFlor(@PathVariable int id) {
        florService.deleteFlor(id);
    }

    @GetMapping("/clientFlorsGetOne/{id}")
    public FlorDTO getFlorById(@PathVariable int id) {
        return florService.getFlorById(id);
    }

    @GetMapping("/clientFlorsAll")
    public List<FlorDTO> getAllFlors() {
        return florService.getAllFlors();
    }
}
