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
public class Ciudad {
    private int idCiudad;
    private int idProvincia;
    private String Ciuduad;

    public Ciudad(int idCiudad, int idProvincia, String Ciuduad) {
        this.idCiudad = idCiudad;
        this.idProvincia = idProvincia;
        this.Ciuduad = Ciuduad;
    }

    @Override
    public String toString() {
        return Ciuduad;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public int getIdCiudad() {
        return idCiudad;
    }
    
    
    
}
