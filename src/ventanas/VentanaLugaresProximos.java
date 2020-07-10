/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import Hilos.MostrarHoteles;
import actores.*;
import generadorhtmlmapbox.GeneradorHTMLMapBox;
import generadorhtmlmapbox.Ubicacion;
import geocoding.GeoCoding;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import principal.Interfaz;

/**
 *
 * @author John
 */
public class VentanaLugaresProximos extends Application {

    private final AnchorPane root;
    private final Button regresar;
    private final Button buscar;
    private final TextField lugarTur;
    private final ScrollPane scrollResultados;
    private final Map<Double, Hotel> mapDistancias;
    private final VBox panelResultados;
    private Ubicacion coorOrigen;

    public VentanaLugaresProximos() {
        mapDistancias = new TreeMap<>();
        coorOrigen = null;
        this.root = new AnchorPane();
        scrollResultados = new ScrollPane();
        panelResultados = new VBox(5);

        ImageView icono = new ImageView("/recursos/Home.png");
        icono.setFitHeight(15);
        icono.setFitWidth(15);
        regresar = new Button("", icono);

        ImageView iconoBuscar = new ImageView("/recursos/lupa.png");
        iconoBuscar.setFitHeight(15);
        iconoBuscar.setFitWidth(15);
        buscar = new Button("Buscar", iconoBuscar);

        lugarTur = new TextField();
        organizarRoot();
    }

    @Override
    public void start(Stage primaryStage) {
        configBotones(primaryStage);
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void organizarRoot() {
        ImageView img = new ImageView("/recursos/FondoProximos.png");
        img.setFitHeight(810);
        img.setFitWidth(810);
        root.getChildren().addAll(img);

        adornarBotones();

        AnchorPane.setTopAnchor(lugarTur, 165.6);
        AnchorPane.setRightAnchor(lugarTur, 200.0);
        AnchorPane.setLeftAnchor(lugarTur, 160.0);

        AnchorPane.setTopAnchor(buscar, 165.6);
        AnchorPane.setRightAnchor(buscar, 70.0);

        AnchorPane.setTopAnchor(regresar, 165.6);
        AnchorPane.setRightAnchor(regresar, 30.0);

        AnchorPane.setTopAnchor(scrollResultados, 202.0);
        AnchorPane.setRightAnchor(scrollResultados, 9.5);
        AnchorPane.setLeftAnchor(scrollResultados, 9.5);
        AnchorPane.setBottomAnchor(scrollResultados, 12.0);
        root.getChildren().addAll(lugarTur, buscar, regresar, scrollResultados);

    }

    private void adornarBotones() {
        lugarTur.setMinHeight(27);
        lugarTur.setMaxHeight(27);
        lugarTur.setStyle("-fx-background-color: WHITE; -fx-font-family: Georgia; -fx-font-size: 15");

        buscar.setMinWidth(90.0);
        buscar.setMaxWidth(90.0);
        buscar.setAlignment(Pos.CENTER);
        Button[] botones = {buscar, regresar};
        for (Button b : botones) {
            b.setStyle("-fx-background-color: WHITE; -fx-border-width: 3");
            b.setEffect(new DropShadow());
            b.setMaxHeight(27);
            b.setMinHeight(27);
            b.setOnMouseEntered(e -> {
                b.setStyle("-fx-background-color: white; -fx-border-color: RED ; -fx-border-width: 1");
            });
            b.setOnMouseExited(e -> {
                b.setStyle("-fx-background-color: white; -fx-border-width: 0");
            });
        }
    }

    public void configBotones(Stage actual) {
        regresar.setOnAction(r -> {
            VentanaPrincipal vp = new VentanaPrincipal();
            vp.start(new Stage());
            actual.close();
        });

        buscar.setOnAction(b -> {
            this.panelResultados.getChildren().clear();
            if (validarLugar()) {
                mapDistancias.clear();
                llenarMapa();
                System.out.print(mapDistancias);
            }
        });
    }

    public boolean validarLugar() {

        if (lugarTur.getText().isEmpty()) {
            Interfaz.alarma("DATOS INVALIDOS", "Por favor ingrese un lugar turistico");
        }

        try {
            GeoCoding a = new GeoCoding("pk.eyJ1IjoiYWxlbm9jb3QiLCJhIjoiY2s2NDM5dXNpMGloeDNlbzV5eThyamt2MiJ9.XzGYfbdCsRvW8etUGKG6Ew");
            coorOrigen = a.consultar(this.lugarTur.getText().trim());
            System.out.println(coorOrigen);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        if (coorOrigen == null) {
            Alert a = new Alert(AlertType.INFORMATION, "NO SE ENCONTRARON COINCIDENCIAS");
            a.setContentText("Verifique el lugar consultado, no se hallaron resultados");
            a.show();
        }
        return coorOrigen != null;
    }

    public static double calcularDistancia(Ubicacion origen, Ubicacion fin) {
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(fin.getLatitud() - origen.getLatitud());
        double dLng = Math.toRadians(fin.getLongitud() - origen.getLongitud());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(origen.getLatitud())) * Math.cos(Math.toRadians(fin.getLatitud()));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }

    public void llenarMapa() {
        Interfaz.hoteles.stream().forEach((h) -> {
            double ditancia;
            try {
                ditancia = calcularDistancia(coorOrigen, new Ubicacion(Double.valueOf(h.getLatitud()), Double.valueOf(h.getLongitud())));
                mapDistancias.put(ditancia, h);
            } catch (Exception ex) {
                System.err.println(ex.getMessage() + "  " + h.getId_hotel());
            }
        });
        if (!mapDistancias.isEmpty()) {
            llenarPanelResultados();
            generarMapa();
        }
    }

    public void llenarPanelResultados() {
        int contador = 0;
        for (Double d : mapDistancias.keySet()) {
            if (contador < 5) {
                MostrarHoteles mh = new MostrarHoteles(scrollResultados, this.panelResultados, this.mapDistancias.get(d));
                mh.crearPanelHotel();
            } else {
                break;
            }
            contador++;
        }
    }

    public void generarMapa() {
        try {
            //Prueba 1
            //Creando un html cuyo centro sea la latitud enviada por parametros 
            GeneradorHTMLMapBox generar = new GeneradorHTMLMapBox("pk.eyJ1IjoiYWxlbm9jb3QiLCJhIjoiY2s2NDM5dXNpMGloeDNlbzV5eThyamt2MiJ9.XzGYfbdCsRvW8etUGKG6Ew", this.coorOrigen.getLatitud(), this.coorOrigen.getLongitud());
            //Prueba 2
            //Creando un html poniendo varios marcadores
            generar.limpiarHTML();
            generar.establecerLocacionInicial(this.coorOrigen.getLongitud(), this.coorOrigen.getLatitud());
            int contador = 0;
            for (Double d : mapDistancias.keySet()) {
                if (contador < 5) {
                    generar.anadirMarcador(Double.parseDouble(mapDistancias.get(d).getLongitud()), Double.parseDouble(mapDistancias.get(d).getLatitud()), mapDistancias.get(d).getNombre_hotel());
                } else {
                    break;
                }
                contador++;
            }
            generar.grabarHTML("src/HTML/Cercanos a " + this.lugarTur.getText().toUpperCase() + ".html");
            Desktop.getDesktop().browse(Paths.get("src/HTML/Cercanos a " + this.lugarTur.getText().toUpperCase() + ".html").toAbsolutePath().toUri());

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
