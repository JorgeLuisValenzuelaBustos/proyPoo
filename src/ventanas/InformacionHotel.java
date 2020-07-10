/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import actores.*;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import principal.Interfaz;

/**
 *
 * @author John
 */
public final class InformacionHotel extends Application{
    private final Hotel hotel;
    private final AnchorPane root;
    private final ScrollPane tablaInfo;
    private final Button reservar;

    public InformacionHotel(Hotel hotel) {
        this.hotel = hotel;
        this.root= new AnchorPane();
        this.tablaInfo = new ScrollPane();
        this.reservar = new Button("Reservar");
        organizarRoot();
    }
       
    @Override
    public void start(Stage primaryStage)  {
     configBotones(primaryStage);
     Scene scene =  new Scene(root,600,700);   
     primaryStage.setScene(scene);
     primaryStage.getIcons().add(new Image("/recursos/ico.png"));
     primaryStage.setResizable(false);
     primaryStage.initModality(Modality.APPLICATION_MODAL);
     primaryStage.show();
     
    }
    
    public void organizarRoot(){
    ImageView fondo = new ImageView("/recursos/FondoInformacion.png");
    fondo.setFitHeight(710);
    fondo.setFitWidth(610);
    root.getChildren().add(fondo);
    
    AnchorPane.setLeftAnchor(tablaInfo, 15.2);
    AnchorPane.setRightAnchor(tablaInfo, 15.2);
    AnchorPane.setTopAnchor(tablaInfo,121.5);
    AnchorPane.setBottomAnchor(tablaInfo,93.0);
    
    AnchorPane.setBottomAnchor(reservar, 30.0);
    AnchorPane.setLeftAnchor(reservar, 189.5);    
    root.getChildren().addAll(tablaInfo,reservar);
    
    mostrarInformacion();
    
    }
    
    private void configBotones(Stage actual){ 
        reservar.setMaxWidth(250);
        reservar.setMinWidth(250);
        reservar.setMaxHeight(40);
        reservar.setMinHeight(40);
        reservar.setTextFill(Color.WHITE);
        reservar.setWrapText(true);
        reservar.setTextAlignment(TextAlignment.CENTER);
        reservar.setFont(Font.font("Felix Titling", 15));
        reservar.setStyle("-fx-background-color: #353A42");
        reservar.setAlignment(Pos.CENTER);
        reservar.setEffect(new DropShadow());
        reservar.setOnMouseEntered(e -> {
                reservar.setStyle("-fx-background-color: #353A42; -fx-border-color: #E7EA02 ; -fx-border-width: 3");});
        reservar.setOnMouseExited(e -> {
                reservar.setStyle("-fx-background-color: #353A42; -fx-border-width: 0");});
        reservar.setOnAction(e->{
        VentanaReservar vr = new VentanaReservar(hotel,buscarHabitaciones());
        actual.close();
        vr.start(new Stage());});
        
    }

    private void mostrarInformacion() {
        VBox datos = new VBox(1);   
        HBox cajaInfo = new HBox(2);
        Label nombre = labelConFormato("  "+hotel.getNombre_hotel(),"#4472C4","FFFFFF");
        nombre.setMinWidth(346.0);
        cajaInfo.getChildren().addAll(labelConFormatoTitulo("  Hotel ","#4472C4","FFFFFF"),nombre);
        AnchorPane.setTopAnchor(cajaInfo, 90.5);
        AnchorPane.setLeftAnchor(cajaInfo, 15.5);
        root.getChildren().add(cajaInfo);
        datos.getChildren().addAll(new Line(15.2,2,605,2));
        
        cajaInfo = new HBox(2); 
        Label etiqueta = labelConFormatoTitulo("  Habitaciones ","#D9E2F3","000000");
        double lineas = 0.0;
        String habitaciones = "";
        for(Habitacion h: buscarHabitaciones()){    
            habitaciones+="  "+h.getTipo_habitacion();
            
            if (h.getTarifa_sencilla()>0){
                habitaciones+="\n\t-Precio sencillo: $"+h.getTarifa_sencilla();
                lineas++;
            }if(h.getTarifa_doble()>0){
                habitaciones+="\n\t-Precio doble: $"+h.getTarifa_doble();
                lineas++;
            }if(h.getTarifa_triple()>0){
                habitaciones+="\n\t-Precio triple: $"+h.getTarifa_triple();
                lineas++;
            }
            lineas+=2;
            habitaciones+="\n\n";
           
        }
        etiqueta.setMinHeight(lineas*19);
        cajaInfo.getChildren().addAll(etiqueta,labelConFormato(habitaciones,"#D9E2F3","#000000"));        
        datos.getChildren().addAll(cajaInfo,new Line(15.2,2,605,2));
        
        String servicios = "\n";
        lineas=2;
        for (Catalogo s: buscarServicios()){
           lineas++;
           servicios+="  - "+s.getServicio()+"\n";
        }
        servicios+="\n";
        cajaInfo = new HBox(2); 
        etiqueta = labelConFormatoTitulo("  Servicios ","#FFFFFF","#000000");
        etiqueta.setMinHeight(lineas*19);
        cajaInfo.getChildren().addAll(etiqueta,labelConFormato(servicios,"#FFFFFF","#000000"));        
        datos.getChildren().addAll(cajaInfo,new Line(15.2,2,605,2));
        
        cajaInfo = new HBox(2);      
        cajaInfo.setAlignment(Pos.CENTER);
        cajaInfo.setStyle("-fx-background-color: #D9E2F3");
        cajaInfo.getChildren().addAll(labelConFormatoTitulo("  Descripción ","#D9E2F3","#000000"),labelConFormato("  "+hotel.getDescripcion_hotel(),"#D9E2F3","#000000"));
        datos.getChildren().addAll(cajaInfo,new Line(15.2,2,605,2));
        
        
        tablaInfo.setContent(datos);
        cajaInfo = new HBox(2);      
        cajaInfo.setAlignment(Pos.CENTER);
        cajaInfo.setStyle("-fx-background-color: #FFFFFF");
        cajaInfo.getChildren().addAll(labelConFormatoTitulo("  Dirección Web ","#FFFFFF","#000000"),labelConFormato("  "+hotel.getWeb_hotel(),"#FFFFFF","#000000"));
        datos.getChildren().addAll(cajaInfo);
        
        
    }
    
    public ArrayList<Habitacion> buscarHabitaciones(){
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        Interfaz.habitaciones.forEach(h->{
        if(h.getId_hotel()==hotel.getId_hotel()){
            habitaciones.add(h);
        }});
        return habitaciones;
    }
    
    public Label labelConFormato(String texto, String colorFondo,String colorLetra){
        Label lbl = new Label(texto);
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setTextFill(Color.web(colorLetra));
        lbl.setMinSize(360, 30);
        lbl.setMaxWidth(360);
        lbl.setFont(Font.font("Georgia", 15));
        lbl.setStyle("-fx-background-color: "+colorFondo);
        return lbl;
    }       
    
    public Label labelConFormatoTitulo(String texto, String colorFondo,String colorLetra){
        Label lbl = new Label(texto);
        lbl.setWrapText(true);
        lbl.setTextFill(Color.web(colorLetra));
        lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setMinSize(230, 30);
        lbl.setMaxWidth(230);        
        lbl.setFont(Font.font("Georgia", 16));
        lbl.setStyle("-fx-background-color: "+colorFondo+";-fx-font-weight: bold");
        return lbl;
    }
    
    public ArrayList<Catalogo> buscarServicios(){
        ArrayList<Catalogo> servicios = new ArrayList<>();
        Interfaz.servicios.forEach(s->{
        if(s.getId_hotel()==hotel.getId_hotel()){
            Interfaz.catalogos.forEach(c->{
                if(c.getIdServicio()==s.getId_servicio()){
                    servicios.add(c);
                }
            });
            
        }});
    return servicios;
    }
            
}
