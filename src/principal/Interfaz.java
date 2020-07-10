/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import actores.Servicio;
import actores.Hotel;
import actores.Habitacion;
import actores.*;
import actores.Provincia;
import actores.Catalogo;
import ventanas.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jorge
 */
public class Interfaz extends Application {

    public static ArrayList<Provincia> provincias = new ArrayList<>();
    public static ArrayList<Ciudad> ciudades = new ArrayList<>();
    public static ArrayList<Catalogo> catalogos = new ArrayList<>();
    public static ArrayList<Hotel> hoteles = new ArrayList<>();
    public static ArrayList<Habitacion> habitaciones = new ArrayList<>();
    public static ArrayList<Servicio> servicios = new ArrayList<>();
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Reserva> reservas = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        VentanaPrincipal vp = new VentanaPrincipal();
        vp.start(new Stage());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        leerProvincias();
        leerCiudades();
        leerCatalogo();
        leerHoteles();
        leerHabitaciones();
        leerServicios();
        leerClientes();
        leerReservas();
//        System.out.println(clientes);
//        System.out.println(reservas);
        launch(args);
    }

    public static void leerClientes() {
        try (ObjectInputStream leerCl = new ObjectInputStream(new FileInputStream("src/archivos/clientes.dat"))) {
            clientes = (ArrayList<Cliente>) leerCl.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void leerReservas() {
        try (ObjectInputStream leerCl = new ObjectInputStream(new FileInputStream("src/archivos/reservas.dat"))) {
            reservas = (ArrayList<Reserva>) leerCl.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void leerProvincias() {
        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/provincias.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Provincia p = new Provincia(Integer.parseInt(sep[0]), sep[1], sep[2], sep[3], sep[4]);
                    provincias.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo provincias.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void leerCiudades() {

        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/ciudades.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Ciudad p = new Ciudad(Integer.parseInt(sep[0]), Integer.parseInt(sep[1]), sep[2]);
                    ciudades.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo ciudades.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void leerCatalogo() {
        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/catalogo.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Catalogo p = new Catalogo(Integer.parseInt(sep[0]), sep[1]);
                    catalogos.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo provincias.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void leerHoteles() {
        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/hoteles1.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Hotel p = new Hotel(Integer.parseInt(sep[0]), Integer.parseInt(sep[1]), sep[2], sep[3], sep[4], sep[5], sep[6], sep[7], sep[8], sep[9], sep[10].replace(",", "").replaceAll(" ", ""), sep[11].replace(",", "").replaceAll(" ", ""));
                    hoteles.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo provincias.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void leerHabitaciones() {
        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/habitaciones.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Habitacion p = new Habitacion(Integer.parseInt(sep[0]), Integer.parseInt(sep[1]), sep[2], Double.parseDouble(sep[3]), Double.parseDouble(sep[4]), Double.parseDouble(sep[5]));
                    habitaciones.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo provincias.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void leerServicios() {
        try (BufferedReader r = new BufferedReader(new FileReader("src/archivos/servicios.csv"))) {
            String l = r.readLine();
            while ((l = r.readLine()) != null) {
                try {
                    String[] sep = l.split("\\|"); //es solo una prueba
                    Servicio p;
                    if (sep[3].equals("activo")) {
                        p = new Servicio(Integer.parseInt(sep[0]), Integer.parseInt(sep[1]), Integer.parseInt(sep[2]), true);
                    } else {
                        p = new Servicio(Integer.parseInt(sep[0]), Integer.parseInt(sep[1]), Integer.parseInt(sep[2]), false);
                    }

                    servicios.add(p);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error en la linea " + l + " no se pudo procesar " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo provincias.csv no encontrado " + e);
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error en leerArchivoNotas() " + e);
        }
    }

    public static void alarma(String encabezado, String problema) {
        Alert a = new Alert(Alert.AlertType.ERROR, problema);
        a.setHeaderText(encabezado);
        a.show();

    }

}
