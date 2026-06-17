package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByPropiedadId(Long propiedadId);
}