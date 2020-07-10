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
public class Catalogo {
    private int idServicio;
    private String servicio;

    public Catalogo(int idServicio, String servicio) {
        this.idServicio = idServicio;
        this.servicio = servicio;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public String getServicio() {
        return servicio;
    }

    @Override
    public String toString() {
        return "Catalogo{" + "idServicio=" + idServicio + ", servicio=" + servicio + '}';
    }
    

    
    
}
