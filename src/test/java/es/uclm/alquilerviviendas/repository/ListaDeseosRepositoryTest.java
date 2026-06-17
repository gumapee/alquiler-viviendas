package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.ListaDeseos;
import es.uclm.alquilerviviendas.entity.Propiedad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ListaDeseosRepositoryTest {

    @Autowired
    private ListaDeseosRepository listaDeseosRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Test
    void buscaListaDeseosPorUsuarioYEvitaDuplicados() {
        Propiedad propiedad = new Propiedad(
                "Apartamento favorito",
                "Toledo",
                "Calle Favorita 3",
                "Habitación individual",
                45.0,
                true,
                "Propiedad favorita para pruebas."
        );

        propiedadRepository.save(propiedad);

        ListaDeseos deseo = new ListaDeseos("usuario@test.com", propiedad);
        listaDeseosRepository.save(deseo);

        List<ListaDeseos> deseos = listaDeseosRepository.findByEmailUsuario("usuario@test.com");

        assertEquals(1, deseos.size());
        assertEquals("Apartamento favorito", deseos.get(0).getPropiedad().getTitulo());
        assertTrue(listaDeseosRepository.existsByEmailUsuarioAndPropiedadId("usuario@test.com", propiedad.getId()));
    }
}