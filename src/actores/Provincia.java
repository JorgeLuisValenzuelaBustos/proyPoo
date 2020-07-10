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
public class Provincia {
    private int idProvincia;
    private String provincia;
    private String descripcion;
    private String region;
    private String web;
    
    public Provincia(int id,String p, String desc, String region, String web){
        this.idProvincia = id;
        this.provincia = p;
        this.descripcion = desc;
        this.region = region;
        this.web = web;
    }

    @Override
    public String toString() {
        return provincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdProvincia() {
        return idProvincia;
    }
    
    
}
