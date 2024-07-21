package cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.dto.SucursalDTO;
import cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.services.SucursalService;

@Controller
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping("/add")
    public String addSucursalForm(Model model) {
        model.addAttribute("sucursal", new SucursalDTO());
        return "add-sucursal";
    }

    @PostMapping("/add")
    public String addSucursal(@ModelAttribute SucursalDTO sucursal) {
        sucursalService.saveSucursal(sucursal);
        return "redirect:/sucursal/getAll";
    }

    @GetMapping("/getAll")
    public String getAllSucursales(Model model) {
        List<SucursalDTO> sucursales = sucursalService.getAllSucursales();
        model.addAttribute("sucursales", sucursales);
        return "list-sucursales";
    }

    @GetMapping("/getOne/{id}")
    public String getSucursalById(@PathVariable int id, Model model) {
        SucursalDTO sucursal = sucursalService.getSucursalById(id);
        model.addAttribute("sucursal", sucursal);
        return "view-sucursal";
    }

    @GetMapping("/update/{id}")
    public String updateSucursalForm(@PathVariable int id, Model model) {
        SucursalDTO sucursal = sucursalService.getSucursalById(id);
        model.addAttribute("sucursal", sucursal);
        return "update-sucursal";
    }

    @PostMapping("/update")
    public String updateSucursal(@ModelAttribute SucursalDTO sucursal) {
        sucursalService.updateSucursal(sucursal);
        return "redirect:/sucursal/getAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteSucursal(@PathVariable int id) {
        sucursalService.deleteSucursal(id);
        return "redirect:/sucursal/getAll";
    }
}


