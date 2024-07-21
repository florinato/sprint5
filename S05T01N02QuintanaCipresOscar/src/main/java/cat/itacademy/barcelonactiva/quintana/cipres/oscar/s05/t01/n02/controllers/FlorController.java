package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.dto.FlorDTO;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.services.FlorService;

@Controller
@RequestMapping("/flor")
public class FlorController {

    @Autowired
    private FlorService florService;

    // Métodos para las vistas Thymeleaf

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("flor", new FlorDTO());
        return "addFlor";
    }

    @PostMapping("/add")
    public String addFlor(@ModelAttribute FlorDTO florDTO) {
        florService.addFlor(florDTO);
        return "redirect:/flor/getAll";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        FlorDTO florDTO = florService.getFlorById(id);
        model.addAttribute("flor", florDTO);
        return "updateFlor";
    }

    @PostMapping("/update/{id}")
    public String updateFlor(@PathVariable Integer id, @ModelAttribute FlorDTO florDTO) {
        florService.updateFlor(id, florDTO);
        return "redirect:/flor/getAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteFlor(@PathVariable Integer id) {
        florService.deleteFlor(id);
        return "redirect:/flor/getAll";
    }

    @GetMapping("/getOne/{id}")
    public String getFlorById(@PathVariable Integer id, Model model) {
        FlorDTO florDTO = florService.getFlorById(id);
        model.addAttribute("flor", florDTO);
        return "viewFlor";
    }

    @GetMapping("/getAll")
    public String getAllFlors(Model model) {
        List<FlorDTO> flors = florService.getAllFlors();
        model.addAttribute("flors", flors);
        return "listFlors";
    }

    // Métodos adicionales para manejar las solicitudes REST (API)

    @PostMapping("/api/add")
    @ResponseBody
    public ResponseEntity<FlorDTO> addFlorApi(@RequestBody FlorDTO florDTO) {
        florService.addFlor(florDTO);
        return ResponseEntity.ok(florDTO);
    }

    @PutMapping("/api/update/{id}")
    @ResponseBody
    public ResponseEntity<FlorDTO> updateFlorApi(@PathVariable Integer id, @RequestBody FlorDTO florDTO) {
        florService.updateFlor(id, florDTO);
        return ResponseEntity.ok(florDTO);
    }

    @DeleteMapping("/api/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteFlorApi(@PathVariable Integer id) {
        florService.deleteFlor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/getOne/{id}")
    @ResponseBody
    public ResponseEntity<FlorDTO> getFlorByIdApi(@PathVariable Integer id) {
        FlorDTO florDTO = florService.getFlorById(id);
        return ResponseEntity.ok(florDTO);
    }

    @GetMapping("/api/getAll")
    @ResponseBody
    public ResponseEntity<List<FlorDTO>> getAllFlorsApi() {
        List<FlorDTO> flors = florService.getAllFlors();
        return ResponseEntity.ok(flors);
    }
}

