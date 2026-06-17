package es.uclm.alquilerviviendas.controller;

import es.uclm.alquilerviviendas.entity.ListaDeseos;
import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.repository.ListaDeseosRepository;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ListaDeseosController {

    private static final String EMAIL_USUARIO_DEMO = "usuario@demo.com";

    private final ListaDeseosRepository listaDeseosRepository;
    private final PropiedadRepository propiedadRepository;

    public ListaDeseosController(ListaDeseosRepository listaDeseosRepository,
                                 PropiedadRepository propiedadRepository) {
        this.listaDeseosRepository = listaDeseosRepository;
        this.propiedadRepository = propiedadRepository;
    }

    @GetMapping("/lista-deseos")
    public String verListaDeseos(Model model) {
        model.addAttribute("deseos", listaDeseosRepository.findByEmailUsuario(EMAIL_USUARIO_DEMO));
        model.addAttribute("emailUsuario", EMAIL_USUARIO_DEMO);
        return "lista-deseos";
    }

    @PostMapping("/lista-deseos/{propiedadId}/anadir")
    public String anadirAListaDeseos(@PathVariable Long propiedadId) {
        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElse(null);

        if (propiedad != null &&
                !listaDeseosRepository.existsByEmailUsuarioAndPropiedadId(EMAIL_USUARIO_DEMO, propiedadId)) {
            ListaDeseos deseo = new ListaDeseos(EMAIL_USUARIO_DEMO, propiedad);
            listaDeseosRepository.save(deseo);
        }

        return "redirect:/lista-deseos";
    }

    @PostMapping("/lista-deseos/{id}/eliminar")
    public String eliminarDeListaDeseos(@PathVariable Long id) {
        listaDeseosRepository.deleteById(id);
        return "redirect:/lista-deseos";
    }
}