package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}