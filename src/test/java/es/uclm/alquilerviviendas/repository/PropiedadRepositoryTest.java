package es.uclm.alquilerviviendas.repository;

import es.uclm.alquilerviviendas.entity.Propiedad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class PropiedadRepositoryTest {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Test
    void guardaYRecuperaPropiedad() {
        Propiedad propiedad = new Propiedad(
                "Apartamento de prueba",
                "Madrid",
                "Calle Test 1",
                "Vivienda completa",
                75.0,
                true,
                "Propiedad creada para pruebas."
        );

        propiedadRepository.save(propiedad);

        List<Propiedad> propiedades = propiedadRepository.findAll();

        assertEquals(1, propiedades.size());
        assertNotNull(propiedades.get(0).getId());
        assertEquals("Apartamento de prueba", propiedades.get(0).getTitulo());
        assertEquals("Madrid", propiedades.get(0).getCiudad());
    }
}