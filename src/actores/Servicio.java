/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actores;

/**
 *
 * @author jorge
 */
public class Servicio {
    private int secuencia;
    private int id_hotel;
    private int id_servicio;
    private boolean estado;

    public Servicio(int secuencia, int id_hotel, int id_servicio, boolean estado) {
        this.secuencia = secuencia;
        this.id_hotel = id_hotel;
        this.id_servicio = id_servicio;
        this.estado = estado;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "secuencia: " + secuencia + ", id_hotel: " + id_hotel + ", id_servicio: " + id_servicio + ", estado: " + estado;
    }
    
    
}
