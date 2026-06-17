package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {

    List<ListaDeseos> findByEmailUsuario(String emailUsuario);

    boolean existsByEmailUsuarioAndPropiedadId(String emailUsuario, Long propiedadId);
}