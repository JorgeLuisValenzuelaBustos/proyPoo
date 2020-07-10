/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actores;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author John
 */
public class Reserva implements Serializable {

    private Hotel hotel;
    private LocalDate fechaReserva;
    private LocalDate fechaFin;
    private Cliente cliente;
    private String cedula;
    private double totalPago;
    private double total;
    private double comision;

    public Reserva(Hotel hotel, LocalDate fechaReserva, LocalDate fechaFin, Cliente cliente, double totalPago) {
        this.hotel = hotel;
        this.fechaReserva = fechaReserva;
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        this.cedula = cliente.getCedula();
        this.totalPago = totalPago;
        this.comision = totalPago * 0.10;
        this.total = totalPago * 1.10;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public String getCedula() {
        return cedula;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public double getComision() {
        return comision;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.hotel);
        hash = 97 * hash + Objects.hashCode(this.fechaReserva);
        hash = 97 * hash + Objects.hashCode(this.fechaFin);
        hash = 97 * hash + Objects.hashCode(this.cliente);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.totalPago) ^ (Double.doubleToLongBits(this.totalPago) >>> 32));
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
        final Reserva other = (Reserva) obj;
        if (!Objects.equals(this.hotel, other.hotel)) {
            return false;
        }
        if (!Objects.equals(this.fechaReserva, other.fechaReserva)) {
            return false;
        }
        if (!Objects.equals(this.fechaFin, other.fechaFin)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return Double.doubleToLongBits(this.totalPago) == Double.doubleToLongBits(other.totalPago);
    }

    @Override
    public String toString() {
        return "Reserva{" + "hotel=" + hotel + ", fechaReserva=" + fechaReserva + ", fechaFin=" + fechaFin + ", cliente=" + cliente + ", cedula=" + cedula + ", totalPago=" + totalPago + ", total=" + total + ", comision=" + comision + '}';
    }


}
