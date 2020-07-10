/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import Hilos.MostrarHoteles;
import actores.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import principal.Interfaz;


/**
 *
 * @author John
 */
public class VentanaBusquedaHotel extends Application {
    private final AnchorPane root;
    private final ComboBox<Provincia> provincias;
    private final ComboBox<Ciudad> ciudades;
    private final Button regresar;
    private final ScrollPane scrollInfoHoteles;
    private final ScrollPane scrollInfoProvincia;
    private final ScrollPane scrollServicios;
    private final VBox panelHoteles;
    private final TextField hotel;
    private final Button buscarHotel;
    public static Set<Hotel> hotelesEnPantalla;
    private final Set<Servicio> filtroUsuario;   
    private boolean buscando;
    private final Button borrarBusqueda;
    private final Button borrarFiltros;
    
    public VentanaBusquedaHotel() {
        hotelesEnPantalla = new HashSet<>();
        filtroUsuario = new HashSet<>();
        scrollServicios = new ScrollPane();
        hotel = new TextField();
        this.root = new AnchorPane();
        scrollInfoHoteles = new ScrollPane();
        scrollInfoProvincia = new ScrollPane();
        provincias = new ComboBox<>();
        ciudades = new ComboBox<>();
        panelHoteles = new VBox();
        buscando = false;
        
        ImageView iconoborrarFiltros = new ImageView("/recursos/borrarFiltros.png");
        iconoborrarFiltros.setFitHeight(20);
        iconoborrarFiltros.setFitWidth(20);
        borrarFiltros = new Button ("Borrar Filtros",iconoborrarFiltros);
        
        ImageView icono = new ImageView("/recursos/Home.png");
        icono.setFitHeight(20);
        icono.setFitWidth(20);
        regresar = new Button("",icono);
        
        ImageView iconoBuscar = new ImageView("/recursos/lupa.png");
        iconoBuscar.setFitHeight(15);
        iconoBuscar.setFitWidth(15);
        buscarHotel = new Button("",iconoBuscar);
        
        ImageView iconoBorrar = new ImageView("/recursos/erase.png");
        iconoBorrar.setFitHeight(15);
        iconoBorrar.setFitWidth(15);
        borrarBusqueda = new Button("",iconoBorrar);
        
        
        organizarRoot();
    }
    
    @Override
    public void start(Stage primaryStage) { 
        configBotones(primaryStage);
        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setTitle("Consulta tu hotel");
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    private void organizarRoot() {
        ImageView fondo = new ImageView("/recursos/FondoBusquedaProvincia.png");
        fondo.setFitHeight(910);
        fondo.setFitWidth(1010);
        root.getChildren().add(fondo);
        
        AnchorPane.setTopAnchor(provincias, 177.6);
        AnchorPane.setRightAnchor(provincias, 270.0);
        AnchorPane.setLeftAnchor(provincias, 140.0);
        root.getChildren().add(provincias);
        llenarCboProvincias();
        
        ciudades.setStyle("-fx-background-color: WHITE; -fx-font-family: Georgia; -fx-font-size: 15");
        AnchorPane.setTopAnchor(ciudades, 335.0);
        AnchorPane.setLeftAnchor(ciudades, 120.0);        
        root.getChildren().add(ciudades);
        
        AnchorPane.setTopAnchor(borrarFiltros, 177.6);
        AnchorPane.setRightAnchor(borrarFiltros, 110.0);
        root.getChildren().add(borrarFiltros);
        
        AnchorPane.setTopAnchor(buscarHotel, 335.0);
        AnchorPane.setRightAnchor(buscarHotel, 100.0);     
        root.getChildren().add(buscarHotel);
        
        AnchorPane.setTopAnchor(borrarBusqueda, 335.0);
        AnchorPane.setRightAnchor(borrarBusqueda, 60.0);     
        root.getChildren().add(borrarBusqueda);
        
        AnchorPane.setTopAnchor(hotel, 335.0);
        AnchorPane.setRightAnchor(hotel, 150.0);      
        hotel.setMinSize(260.0, 20.0);
        root.getChildren().add(hotel);
        
        adornarScrolls();
        
        mostrarServicios();
        
        AnchorPane.setTopAnchor(regresar, 177.6);
        AnchorPane.setRightAnchor(regresar, 60.0);
        root.getChildren().add(regresar);               
        
    }
    
    private void llenarCboProvincias(){
        provincias.getItems().addAll(Interfaz.provincias);
        provincias.setStyle("-fx-background-color: WHITE; -fx-font-family: Georgia; -fx-font-size: 15");
      
        provincias.setOnAction(e->{
            try{
           llenarCboCiudades();           
           mostrarInfoProvincia();
           }catch(Exception ex){
               System.err.println(ex.getMessage());
           }
            });
        
    }
    
    private void llenarCboCiudades() {
        ciudades.getItems().clear();        
        Interfaz.ciudades.forEach(c->{
        if(c.getIdProvincia()==provincias.getValue().getIdProvincia()){
            ciudades.getItems().add(c);
        }});    
        
        ciudades.setOnAction(e->{
            hotel.clear();
            buscando = false;
            mostrarHoteles();
            mostrarServicios();
        });
        
    }

    private void adornarScrolls(){
        
        AnchorPane.setTopAnchor(scrollInfoHoteles, 372.2);
        AnchorPane.setLeftAnchor(scrollInfoHoteles,10.6);
        AnchorPane.setRightAnchor(scrollInfoHoteles,254.5);
        AnchorPane.setBottomAnchor(scrollInfoHoteles,17.0);
        scrollInfoHoteles.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(scrollInfoHoteles);
 
        AnchorPane.setTopAnchor(scrollInfoProvincia, 214.0);
        AnchorPane.setLeftAnchor(scrollInfoProvincia,10.6);
        AnchorPane.setRightAnchor(scrollInfoProvincia,10.6);
        AnchorPane.setBottomAnchor(scrollInfoProvincia,585.0);
        scrollInfoProvincia.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(scrollInfoProvincia);
        
        AnchorPane.setTopAnchor(scrollServicios, 407.0);
        AnchorPane.setLeftAnchor(scrollServicios,762.2);
        AnchorPane.setRightAnchor(scrollServicios,10.5);
        AnchorPane.setBottomAnchor(scrollServicios,17.0);
        scrollServicios.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(scrollServicios);
  
        
        
    }
    
    private void configBotones(Stage actual) {
        regresar.setOnAction(e->{
        VentanaPrincipal vp = new VentanaPrincipal();
        actual.close();
        vp.start(new Stage());
        });
        
        buscarHotel.setOnAction(b->{     
        if(hotel.getText().trim().isEmpty()){
            Interfaz.alarma("No se puede realizar la busqueda", "Debe ingresar el nombre del hotel");
        }else{
            panelHoteles.getChildren().clear();   
            if(ciudades.getValue()==null &provincias.getValue()==null){
                hotelesEnPantalla.clear();
            }
            mostrarServicios();
            buscarPorNombre();
            buscando = true;
        }});
        
       this.borrarBusqueda.setOnAction(e->{
            buscando = false;
            hotel.clear();
            mostrarServicios();
            panelHoteles.getChildren().clear();
            hotelesEnPantalla.forEach(hp ->{
                if(ciudades.getValue()!=null &provincias.getValue()!=null){
                 MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,hp);
                mh.crearPanelHotel();   
                }
            });
       });

       borrarFiltros.setOnAction(b->{
           buscando = false;
           hotel.clear();
           hotelesEnPantalla.clear();
           mostrarServicios();
           panelHoteles.getChildren().clear();
           scrollInfoHoteles.setContent(panelHoteles);
           scrollInfoProvincia.setContent(new Label(""));
           ciudades.getItems().clear();
           provincias.getItems().clear();
           llenarCboProvincias();    
           
       });
       
       Button [] botones = {borrarFiltros,borrarBusqueda,regresar,buscarHotel};
       for (Button botone : botones) {
            botone.setFont(Font.font("Felix Titling", 11));
            botone.setStyle("-fx-background-color: WHITE; -fx-border-width: 3");
            botone.setEffect(new DropShadow());
        }

    }

    private void mostrarInfoProvincia() {
        Label lblProvincia = new Label(provincias.getValue().getDescripcion());
        lblProvincia.setWrapText(true);
        lblProvincia.setMinHeight(108.4);      
        lblProvincia.setMinWidth(root.getWidth()-24.3);
        lblProvincia.setMaxWidth(scrollInfoProvincia.getWidth()-8);
        lblProvincia.setTextAlignment(TextAlignment.JUSTIFY);
        lblProvincia.setStyle("-fx-background-color: #DEEBF7;-fx-font-family: Georgia; -fx-font-size: 15");        
        scrollInfoProvincia.setContent(lblProvincia);
        
        
    }
    
    private void mostrarHoteles(){        
        filtroUsuario.clear();
        panelHoteles.getChildren().clear();
        hotelesEnPantalla.clear();
        panelHoteles.setStyle("-fx-background-color: WHITE;");
        try{                 
            Interfaz.hoteles.forEach(h->{
                if(h.getId_ciudad()==ciudades.getValue().getIdCiudad()){
                    hotelesEnPantalla.add(h);
                    MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,h);
                    mh.crearPanelHotel();
                }
            });  
        }catch(Exception ex){
        System.err.println(ex.getMessage());}
              
    }
    
    private void mostrarServicios(){
        VBox listaServicios = new VBox(10);
        listaServicios.setStyle("-fx-background-color: WHITE; -fx-font-family: Georgia; -fx-font-size: 15");
        listaServicios.setMinWidth(220.0);
        Interfaz.catalogos.forEach(c->{
        CheckBox tipoServicio = new CheckBox(c.getServicio());
        tipoServicio.setOnAction(e->{        
            seleccionarHotelServicio(tipoServicio,c);
            if(filtroUsuario.isEmpty() & !hotelesEnPantalla.isEmpty() & buscando){
            buscarPorNombre();
                }
            else if(filtroUsuario.isEmpty() &ciudades.getValue()!=null &provincias.getValue()!=null){                                
                mostrarHoteles();
        } });
        HBox espacio = new HBox();
        espacio.getChildren().addAll(new Label("   "),tipoServicio);
        listaServicios.getChildren().addAll(espacio);});        
        scrollServicios.setContent(listaServicios);
    }
    
    private void seleccionarHotelServicio(CheckBox c, Catalogo cata){ 
        panelHoteles.getChildren().clear();
        Interfaz.servicios.forEach(s->{
            if(s.getId_servicio()==cata.getIdServicio()){
                if(c.isSelected()){            
                    filtroUsuario.add(s);
                }else if(!c.isSelected()){
                    filtroUsuario.remove(s);
                }
        }});
                 if(hotelesEnPantalla.isEmpty()){
            Interfaz.hoteles.forEach(h->{
                hotelesEnPantalla.add(h);
            });
        }
        ArrayList<Hotel> temporalesEnPantalla = new ArrayList<>();
        filtroUsuario.stream().forEach((s) -> {
            hotelesEnPantalla.stream().forEach((h) -> {
                if(buscando & h.getNombre_hotel().toUpperCase().contains(hotel.getText().trim().toUpperCase()) & h.getId_hotel()==s.getId_hotel() & s.isEstado() & !temporalesEnPantalla.contains(h)){
                    MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,h);
                    mh.crearPanelHotel();
                    temporalesEnPantalla.add(h);
                }
                else if(!buscando &h.getId_hotel()==s.getId_hotel() & s.isEstado() & !temporalesEnPantalla.contains(h)){
                    MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,h);
                    mh.crearPanelHotel();
                    temporalesEnPantalla.add(h);
                }
            });
        });
    
    }

    public void buscarPorNombre(){
        panelHoteles.getChildren().clear();
        if (hotelesEnPantalla.isEmpty()){
            Interfaz.hoteles.forEach(h->{
            if(h.getNombre_hotel().toUpperCase().contains(hotel.getText().trim().toUpperCase())){
            hotelesEnPantalla.add(h);
            MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,h);
                    mh.crearPanelHotel();
            }
            
            });
        } else{
            hotelesEnPantalla.forEach(hp ->{
                if(hp.getNombre_hotel().toUpperCase().contains(hotel.getText().trim().toUpperCase())){
                MostrarHoteles mh = new MostrarHoteles(scrollInfoHoteles,panelHoteles,hp);
                mh.crearPanelHotel();
                }
                });
        }
        
        
    }
}
