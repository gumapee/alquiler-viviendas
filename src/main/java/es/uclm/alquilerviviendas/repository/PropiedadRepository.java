package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}