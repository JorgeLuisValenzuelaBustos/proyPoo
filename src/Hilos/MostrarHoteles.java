/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import actores.Hotel;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ventanas.InformacionHotel;
import ventanas.VentanaBusquedaHotel;

/**
 *
 * @author John
 */
public class MostrarHoteles {
        
        private final ScrollPane infoHotelesHilo;
        private final VBox panelHotelesHilo;
        private final Hotel h;
        private final VBox hotelEncontrado;
        private final AnchorPane ubicarResultados;
        private final Button informacion;
        private final Button mapa;


        public MostrarHoteles(ScrollPane infoHotelesHilo, VBox hotelesEncontrados, Hotel h) {
            this.hotelEncontrado = new VBox(20);
            this.infoHotelesHilo = infoHotelesHilo;
            this.panelHotelesHilo = hotelesEncontrados;
            ubicarResultados=new AnchorPane();
            this.h = h;
            informacion = new Button("+ Información");
            mapa = new Button("Ver Mapa");
            configBotones();
        }        
        
        public void crearPanelHotel(){
        ordenarInformacion();        
        panelHotelesHilo.getChildren().add(ubicarResultados);
        infoHotelesHilo.setContent(panelHotelesHilo);            
        }                        
        
        private void ordenarInformacion(){
            ImageView fotoHotel = new ImageView("/recursos/borde.png");
            
            AnchorPane.setTopAnchor(fotoHotel,20.0);
            AnchorPane.setLeftAnchor(fotoHotel, 20.0);
            AnchorPane.setBottomAnchor(fotoHotel, 20.0);
            fotoHotel.setFitWidth(150);
            fotoHotel.setFitHeight(150);
            fotoHotel.setEffect(new DropShadow());
                         
            hotelEncontrado.setMinWidth(540);
            Label nombre = new Label(h.getNombre_hotel());
            nombre.setStyle("-fx-text-color: BLACK; -fx-font-family: Georgia; -fx-font-size: 18; -fx-font-weight: bold");           
            
            Label direccion = new Label("Direccion: "+h.getDireccion_hotel());
            direccion.setStyle("-fx-text-color: BLACK; -fx-font-family: Georgia; -fx-font-size: 18");           
            direccion.setWrapText(true);
            
            Label estrellas = new Label();
            int clasificacion=Integer.parseInt(h.getClasificacion_hotel());
            String s="";
            for (int i = 1; i <= 5; i++) {
                if(i<=clasificacion){
                    s+="★";
                }else{
                    s+="☆";
                }
            }
            
            estrellas.setText(s);
            estrellas.setStyle("-fx-text-color: BLACK; -fx-font-family: Georgia; -fx-font-size: 20; -fx-font-weight: bold");          
            estrellas.setTextFill(Color.RED);
                        
            AnchorPane.setTopAnchor(hotelEncontrado,20.0);
            AnchorPane.setLeftAnchor(hotelEncontrado,200.0);
            AnchorPane.setRightAnchor(hotelEncontrado, 10.0);
            hotelEncontrado.getChildren().addAll(nombre,direccion,estrellas,cajaBotones());     
            
            ubicarResultados.getChildren().addAll(fotoHotel,hotelEncontrado);
            Thread tr = new Thread(new CargarImagenes(fotoHotel,h.getFoto_hotel()));
            tr.setDaemon(true);
            tr.start();
            

        }
        
        public HBox cajaBotones(){
            List<Button> botones = Arrays.asList(informacion, mapa);
            HBox botons = new HBox(20);
            botones.forEach(b -> {
            b.setMaxWidth(140);
            b.setMinWidth(140);
            b.setMaxHeight(30);
            b.setMinHeight(30);
            b.setTextFill(Color.WHITE);
            b.setWrapText(true);
            b.setTextAlignment(TextAlignment.CENTER);
            b.setFont(Font.font("Felix Titling", 12));
            b.setStyle("-fx-background-color: #353A42");
            b.setAlignment(Pos.CENTER);
            b.setEffect(new DropShadow());
            b.setOnMouseEntered(e -> {
                b.setStyle("-fx-background-color: #353A42; -fx-border-color: #E7EA02 ; -fx-border-width: 3");
            });
            b.setOnMouseExited(e -> {
                b.setStyle("-fx-background-color: #353A42; -fx-border-width: 0");
            });
            botons.getChildren().add(b);
        });
            
            return botons;
        }
        
        private void configBotones(){
            informacion.setOnAction(e->{
            InformacionHotel ih = new InformacionHotel(h);
            ih.start(new Stage());});
            
            mapa.setOnAction(e->verMapa());
        }
        
        public void verMapa() {
        URI myURI;
        try {
            String query = h.getNombre_hotel().replaceAll(" ", "%20") + "+Ecuador";
            myURI = new URI("https://www.google.com/maps/search/?api=1&query=" + query);
            Desktop.getDesktop().browse(myURI);
        } catch (URISyntaxException | IOException ex) {
            System.out.println(ex);
        }
    }
        
        class CargarImagenes implements Runnable{
            private final ImageView hotel;
            private final String url;

        public CargarImagenes(ImageView hotel, String url) {
            this.hotel = hotel;
            this.url = url;
        }
            
        @Override
        public void run() {
            hotel.setImage(new Image(url));
        }
        
        }
        
    }

