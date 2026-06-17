package es.uclm.alquilerviviendas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;

    @ManyToOne
    private Propiedad propiedad;

    public ListaDeseos() {
    }

    public ListaDeseos(String emailUsuario, Propiedad propiedad) {
        this.emailUsuario = emailUsuario;
        this.propiedad = propiedad;
    }

    public Long getId() {
        return id;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }
}