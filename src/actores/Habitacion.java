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
public class Habitacion {

    private int id_habitacion;
    private int id_hotel;
    private String tipo_habitacion;
    private double tarifa_sencilla;
    private double tarifa_doble;
    private double tarifa_triple;

    public Habitacion(int id_habitacion, int id_hotel, String tipo_habitacion, double tarifa_sencilla, double tarifa_doble, double tarifa_triple) {
        this.id_habitacion = id_habitacion;
        this.id_hotel = id_hotel;
        this.tipo_habitacion = tipo_habitacion;
        this.tarifa_sencilla = tarifa_sencilla;
        this.tarifa_doble = tarifa_doble;
        this.tarifa_triple = tarifa_triple;
    }

    @Override
    public String toString() {
        return "id_habitacion: " + id_habitacion + ", id_hotel: " + id_hotel + ", tipo_habitacion: " + tipo_habitacion + ", tarifa_sencilla: " + tarifa_sencilla + ", tarifa_doble: " + tarifa_doble + ", tarifa_triple: " + tarifa_triple;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public double getTarifa_sencilla() {
        return tarifa_sencilla;
    }

    public double getTarifa_doble() {
        return tarifa_doble;
    }

    public double getTarifa_triple() {
        return tarifa_triple;
    }

}
