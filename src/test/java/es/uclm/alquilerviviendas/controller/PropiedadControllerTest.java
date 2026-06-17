package es.uclm.alquilerviviendas.controller;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class PropiedadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Test
    void listarPropiedadesMuestraVistaPropiedades() throws Exception {
        mockMvc.perform(get("/propiedades"))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedades"))
                .andExpect(model().attributeExists("propiedades"));
    }

    @Test
    void mostrarFormularioNuevaPropiedad() throws Exception {
        mockMvc.perform(get("/propiedades/nueva"))
                .andExpect(status().isOk())
                .andExpect(view().name("nueva-propiedad"))
                .andExpect(model().attributeExists("propiedad"));
    }

    @Test
    void guardarPropiedadRedirigeAListado() throws Exception {
        mockMvc.perform(post("/propiedades")
                        .param("titulo", "Piso de prueba MVC")
                        .param("ciudad", "Madrid")
                        .param("direccion", "Calle MVC 1")
                        .param("tipo", "Vivienda completa")
                        .param("precioPorNoche", "80")
                        .param("reservaInmediata", "true")
                        .param("descripcion", "Propiedad creada desde test MVC."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedades"));
    }

    @Test
    void buscarPropiedadesPorCiudad() throws Exception {
        Propiedad propiedad = new Propiedad(
                "Ático para búsqueda",
                "Valencia",
                "Calle Búsqueda 5",
                "Vivienda completa",
                95.0,
                true,
                "Propiedad usada para probar búsqueda."
        );

        propiedadRepository.save(propiedad);

        mockMvc.perform(get("/propiedades/buscar")
                        .param("ciudad", "Valencia")
                        .param("tipo", "")
                        .param("reservaInmediata", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedades"))
                .andExpect(model().attributeExists("propiedades"))
                .andExpect(model().attribute("ciudad", "Valencia"));
    }
}