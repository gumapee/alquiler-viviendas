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
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class ReservaController {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    private static final String ESTADO_RECHAZADA_DEVOLUCION = "RECHAZADA - DEVOLUCIÓN REALIZADA";

    private static final String VISTA_NUEVA_RESERVA = "nueva-reserva";

    private static final String REDIRECT_RESERVAS = "redirect:/reservas";
    private static final String REDIRECT_PROPIEDADES = "redirect:/propiedades";

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
            return REDIRECT_PROPIEDADES;
        }

        model.addAttribute("propiedad", propiedad);
        return VISTA_NUEVA_RESERVA;
    }

    @PostMapping("/reservas")
    public String guardarReserva(
            @RequestParam Long propiedadId,
            @RequestParam String nombreInquilino,
            @RequestParam String emailInquilino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
            @RequestParam String metodoPago,
            Model model) {

        Propiedad propiedad = propiedadRepository.findById(propiedadId).orElse(null);

        if (propiedad == null) {
            return REDIRECT_PROPIEDADES;
        }

        if (!fechaSalida.isAfter(fechaEntrada)) {
            model.addAttribute("propiedad", propiedad);
            model.addAttribute("error", "La fecha de salida debe ser posterior a la fecha de entrada.");
            return VISTA_NUEVA_RESERVA;
        }

        if (!propiedadDisponible(propiedadId, fechaEntrada, fechaSalida)) {
            model.addAttribute("propiedad", propiedad);
            model.addAttribute("error", "La propiedad ya tiene una reserva confirmada para las fechas seleccionadas.");
            return VISTA_NUEVA_RESERVA;
        }

        long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        double importeTotal = noches * propiedad.getPrecioPorNoche();

        String estado = propiedad.isReservaInmediata() ? ESTADO_CONFIRMADA : ESTADO_PENDIENTE;

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

        return REDIRECT_RESERVAS;
    }

    @PostMapping("/reservas/{id}/confirmar")
    public String confirmarReserva(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if (reserva == null || !reserva.getEstado().equals(ESTADO_PENDIENTE)) {
            return REDIRECT_RESERVAS;
        }

        if (!propiedadDisponible(reserva.getPropiedad().getId(), reserva.getFechaEntrada(), reserva.getFechaSalida())) {
            reserva.setEstado(ESTADO_RECHAZADA_DEVOLUCION);
            reservaRepository.save(reserva);
            return REDIRECT_RESERVAS;
        }

        reserva.setEstado(ESTADO_CONFIRMADA);
        reservaRepository.save(reserva);

        rechazarSolicitudesSolapadas(reserva);

        return REDIRECT_RESERVAS;
    }

    @PostMapping("/reservas/{id}/rechazar")
    public String rechazarReserva(@PathVariable Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if (reserva != null && reserva.getEstado().equals(ESTADO_PENDIENTE)) {
            reserva.setEstado(ESTADO_RECHAZADA_DEVOLUCION);
            reservaRepository.save(reserva);
        }

        return REDIRECT_RESERVAS;
    }

    private boolean propiedadDisponible(Long propiedadId, LocalDate nuevaEntrada, LocalDate nuevaSalida) {
        List<Reserva> reservas = reservaRepository.findByPropiedadId(propiedadId);

        for (Reserva reserva : reservas) {
            boolean reservaConfirmada = reserva.getEstado().equals(ESTADO_CONFIRMADA);

            boolean fechasSolapadas = nuevaEntrada.isBefore(reserva.getFechaSalida())
                    && nuevaSalida.isAfter(reserva.getFechaEntrada());

            if (reservaConfirmada && fechasSolapadas) {
                return false;
            }
        }

        return true;
    }

    private void rechazarSolicitudesSolapadas(Reserva reservaConfirmada) {
        List<Reserva> reservas = reservaRepository.findByPropiedadId(reservaConfirmada.getPropiedad().getId());

        for (Reserva reserva : reservas) {
            boolean esOtraReserva = !reserva.getId().equals(reservaConfirmada.getId());
            boolean estaPendiente = reserva.getEstado().equals(ESTADO_PENDIENTE);

            boolean fechasSolapadas = reserva.getFechaEntrada().isBefore(reservaConfirmada.getFechaSalida())
                    && reserva.getFechaSalida().isAfter(reservaConfirmada.getFechaEntrada());

            if (esOtraReserva && estaPendiente && fechasSolapadas) {
                reserva.setEstado(ESTADO_RECHAZADA_DEVOLUCION);
                reservaRepository.save(reserva);
            }
        }
    }
}