package es.uclm.alquilerviviendas.config;

import es.uclm.alquilerviviendas.entity.Propiedad;
import es.uclm.alquilerviviendas.repository.PropiedadRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PropiedadRepository propiedadRepository;

    public DataInitializer(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    @Override
    public void run(String... args) {
        if (propiedadRepository.count() == 0) {
            propiedadRepository.save(new Propiedad(
                    "Apartamento céntrico",
                    "Madrid",
                    "Calle Mayor n/10",
                    "Vivienda completa",
                    85.0,
                    true,
                    "Apartamento luminoso en el centro de Madrid."
            ));

            propiedadRepository.save(new Propiedad(
                    "Habitación privada cerca de la universidad",
                    "Toledo",
                    "Avenida Universidad n/5",
                    "Habitación individual",
                    35.0,
                    false,
                    "Habitación para estudiantes con escritorio y wifi."
            ));

            propiedadRepository.save(new Propiedad(
                    "Casa rural con piscina",
                    "Cuenca",
                    "Camino del Río n/22",
                    "Vivienda completa",
                    120.0,
                    true,
                    "Casa rural ideal para fines de semana."
            ));
        }
    }
}