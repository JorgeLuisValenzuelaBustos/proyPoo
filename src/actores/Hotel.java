/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actores;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class Hotel implements Serializable{

    private int id_hotel;
    private int id_ciudad;
    private String nombre_hotel;
    private String descripcion_hotel;
    private String tarjeta_hotel;
    private String ubicacion_hotel;
    private String direccion_hotel;
    private String web_hotel;
    private String clasificacion_hotel;
    private String foto_hotel;
    private String latitud;
    private String longitud;
    private static final long serialVersionUID = 771223652324025287L;

    public Hotel(int id_hotel, int id_ciudad, String nombre_hotel, String descripcion_hotel, String tarjeta_hotel, String ubicacion_hotel, String direccion_hotel, String web_hotel, String clasificacion_hotel, String foto_hotel, String latitud, String longitud) {
        this.id_hotel = id_hotel;
        this.id_ciudad = id_ciudad;
        this.nombre_hotel = nombre_hotel;
        this.descripcion_hotel = descripcion_hotel;
        this.tarjeta_hotel = tarjeta_hotel;
        this.ubicacion_hotel = ubicacion_hotel;
        this.direccion_hotel = direccion_hotel;
        this.web_hotel = web_hotel;
        this.clasificacion_hotel = clasificacion_hotel;
        this.foto_hotel = foto_hotel;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getTarjeta_hotel() {
        return tarjeta_hotel;
    }

    public void setTarjeta_hotel(String tarjeta_hotel) {
        this.tarjeta_hotel = tarjeta_hotel;
    }

    public String getUbicacion_hotel() {
        return ubicacion_hotel;
    }

    public void setUbicacion_hotel(String ubicacion_hotel) {
        this.ubicacion_hotel = ubicacion_hotel;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getClasificacion_hotel() {
        return clasificacion_hotel;
    }

    public String getFoto_hotel() {
        return foto_hotel;
    }

    @Override
    public String toString() {
        return this.nombre_hotel;
    }


    public int getId_ciudad() {
        return id_ciudad;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public String getNombre_hotel() {
        return nombre_hotel;
    }

    public String getDireccion_hotel() {
        return direccion_hotel;
    }

    public String getWeb_hotel() {
        return web_hotel;
    }

    public String getDescripcion_hotel() {
        return descripcion_hotel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id_hotel;
        hash = 97 * hash + this.id_ciudad;
        hash = 97 * hash + Objects.hashCode(this.direccion_hotel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hotel other = (Hotel) obj;
        if (this.id_hotel != other.id_hotel) {
            return false;
        }
        if (this.id_ciudad != other.id_ciudad) {
            return false;
        }
        if (!Objects.equals(this.direccion_hotel, other.direccion_hotel)) {
            return false;
        }
        return true;
    }

}
