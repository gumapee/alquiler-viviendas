package es.uclm.alquilerviviendas.controller;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PropiedadController {

    private final PropiedadRepository propiedadRepository;

    public PropiedadController(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    @GetMapping("/propiedades")
    public String listarPropiedades(Model model) {
        model.addAttribute("propiedades", propiedadRepository.findAll());
        return "propiedades";
    }

    @GetMapping("/propiedades/nueva")
    public String mostrarFormularioNuevaPropiedad(Model model) {
        model.addAttribute("propiedad", new Propiedad());
        return "nueva-propiedad";
    }

    @PostMapping("/propiedades")
    public String guardarPropiedad(Propiedad propiedad) {
        propiedadRepository.save(propiedad);
        return "redirect:/propiedades";
    }
}