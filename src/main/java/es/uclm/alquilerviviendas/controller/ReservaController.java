package es.uclm.alquilerviviendas.controller;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.entity.Reserva;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import es.uclm.alquilerviviendas.repository.ReservaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final PropiedadRepository propiedadRepository;

    public ReservaController(ReservaRepository reservaRepository, PropiedadRepository propiedadRepository) {
        this.reservaRepository = reservaRepository;
        this.propiedadRepository = propiedadRepository;
    }

    @GetMapping("/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "reservas";
    }

    @GetMapping("/reservas/nueva")
    public String mostrarFormularioReserva(@RequestParam Long propiedadId, Model model) {
        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElse(null);

        if (propiedad == null) {
            return "redirect:/propiedades";
        }

        model.addAttribute("propiedad", propiedad);
        return "nueva-reserva";
    }

    @PostMapping("/reservas")
    public String guardarReserva(
            @RequestParam Long propiedadId,
            @RequestParam String nombreInquilino,
            @RequestParam String emailInquilino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
            @RequestParam String metodoPago) {

        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElse(null);

        if (propiedad == null) {
            return "redirect:/propiedades";
        }

        long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);

        if (noches <= 0) {
            noches = 1;
        }

        double importeTotal = noches * propiedad.getPrecioPorNoche();

        String estado = propiedad.isReservaInmediata() ? "CONFIRMADA" : "PENDIENTE";

        Reserva reserva = new Reserva(
                nombreInquilino,
                emailInquilino,
                fechaEntrada,
                fechaSalida,
                metodoPago,
                estado,
                importeTotal,
                propiedad
        );

        reservaRepository.save(reserva);

        return "redirect:/reservas";
    }
}