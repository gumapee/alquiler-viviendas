package es.uclm.alquilerviviendas.controller;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PropiedadController {

    private static final String PROPIEDADES = "propiedades";
    private static final String CIUDAD = "ciudad";
    private static final String TIPO = "tipo";
    private static final String RESERVA_INMEDIATA = "reservaInmediata";

    private final PropiedadRepository propiedadRepository;

    public PropiedadController(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    @GetMapping("/propiedades")
    public String listarPropiedades(Model model) {
        model.addAttribute(PROPIEDADES, propiedadRepository.findAll());
        model.addAttribute(CIUDAD, "");
        model.addAttribute(TIPO, "");
        model.addAttribute(RESERVA_INMEDIATA, "");
        return PROPIEDADES;
    }

    @GetMapping("/propiedades/buscar")
    public String buscarPropiedades(
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String reservaInmediata,
            Model model) {

        List<Propiedad> propiedades = propiedadRepository.findAll();

        if (ciudad != null && !ciudad.isBlank()) {
            propiedades = propiedades.stream()
                    .filter(propiedad -> propiedad.getCiudad().toLowerCase().contains(ciudad.toLowerCase()))
                    .toList();
        }

        if (tipo != null && !tipo.isBlank()) {
            propiedades = propiedades.stream()
                    .filter(propiedad -> propiedad.getTipo().equals(tipo))
                    .toList();
        }

        if (reservaInmediata != null && !reservaInmediata.isBlank()) {
            boolean filtroReservaInmediata = Boolean.parseBoolean(reservaInmediata);
            propiedades = propiedades.stream()
                    .filter(propiedad -> propiedad.isReservaInmediata() == filtroReservaInmediata)
                    .toList();
        }

        model.addAttribute(PROPIEDADES, propiedades);
        model.addAttribute(CIUDAD, ciudad);
        model.addAttribute(TIPO, tipo);
        model.addAttribute(RESERVA_INMEDIATA, reservaInmediata);

        return PROPIEDADES;
    }

    @GetMapping("/propiedades/nueva")
    public String mostrarFormularioNuevaPropiedad(Model model) {
        model.addAttribute("propiedad", new Propiedad());
        return "nueva-propiedad";
    }

    @PostMapping("/propiedades")
public String guardarPropiedad(Propiedad propiedad, Model model) {
    if (propiedad.getPrecioPorNoche() <= 0) {
        model.addAttribute("propiedad", propiedad);
        model.addAttribute("error", "El precio por noche debe ser mayor que 0.");
        return "nueva-propiedad";
    }

    propiedadRepository.save(propiedad);
    return "redirect:/propiedades";
}
}