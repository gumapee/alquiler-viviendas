package es.uclm.alquilerviviendas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String ciudad;
    private String direccion;
    private String tipo;
    private double precioPorNoche;
    private boolean reservaInmediata;
    private String descripcion;

    public Propiedad() {
    }

    public Propiedad(String titulo, String ciudad, String direccion, String tipo,
                     double precioPorNoche, boolean reservaInmediata, String descripcion) {
        this.titulo = titulo;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.reservaInmediata = reservaInmediata;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public boolean isReservaInmediata() {
        return reservaInmediata;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public void setReservaInmediata(boolean reservaInmediata) {
        this.reservaInmediata = reservaInmediata;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}