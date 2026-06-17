package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.entity.Reserva;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Test
    void buscaReservasPorPropiedad() {
        Propiedad propiedad = new Propiedad(
                "Casa rural de prueba",
                "Cuenca",
                "Camino Test 2",
                "Vivienda completa",
                120.0,
                false,
                "Casa creada para pruebas."
        );

        propiedadRepository.save(propiedad);

        Reserva reserva = new Reserva();
        reserva.setNombreInquilino("Usuario Test");
        reserva.setEmailInquilino("usuario@test.com");
        reserva.setFechaEntrada(LocalDate.of(2026, Month.JULY, 1));
        reserva.setFechaSalida(LocalDate.of(2026, Month.JULY, 5));
        reserva.setMetodoPago("PayPal");
        reserva.setEstado("PENDIENTE");
        reserva.setImporteTotal(480.0);
        reserva.setPropiedad(propiedad);

        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByPropiedadId(propiedad.getId());

        assertEquals(1, reservas.size());
        assertEquals("Usuario Test", reservas.get(0).getNombreInquilino());
        assertEquals("PENDIENTE", reservas.get(0).getEstado());
        assertEquals(propiedad.getId(), reservas.get(0).getPropiedad().getId());
    }
}