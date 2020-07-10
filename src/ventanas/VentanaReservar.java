/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import actores.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import principal.Interfaz;

/**
 *
 * @author John
 */
public class VentanaReservar extends Application{
    private final Hotel hotel;
    private final AnchorPane root;
    private final ScrollPane scrollTodaLaInfo;
    private final ArrayList<Habitacion> habitaciones;
    private final DatePicker fechaReserva;
    private final DatePicker fechaFin;
    private double valorPago;
    private Cliente cliente;
    private final TextField cedula;
    private final Button reservar;
    private final Button continuar;
    private final VBox panelInfo;
    private final Map<TextField,Double> cantHabita;
    private final Button calcular;
    private final Label lblDias;
    private final Label lbltotalPagar;
    private int cantDias;
    private final HBox cajaPago;
    
    
    public VentanaReservar(Hotel hotel,ArrayList<Habitacion> habitaciones) {
        this.hotel = hotel;
        cantDias = 1;
        cajaPago = new HBox (8);
        this.habitaciones=habitaciones;
        this.root = new AnchorPane();
        calcular = new Button("Calcular");
        fechaReserva = new DatePicker(LocalDate.now());
        fechaReserva.setEditable(false);
        lblDias = labelConFormatoTamano(String.valueOf(this.cantDias), "RED","WHITE",120,40,18);
        lbltotalPagar =  new Label("$0,00");
        lbltotalPagar.setAlignment(Pos.CENTER_RIGHT);
        fechaFin = new DatePicker();
        fechaFin.setEditable(false);
        cantHabita = new HashMap<>();
        valorPago = 0.0;
        scrollTodaLaInfo = new ScrollPane();
        cedula = new TextField();
        reservar = new Button("Hacer Reserva");
        continuar = new Button("Continuar");
        panelInfo = new VBox(3);
        organizarRoot();
    }

    private void organizarRoot(){
        
        lbltotalPagar.setMinSize(320, 40);
        lbltotalPagar.setMaxSize(320, 40);
        lbltotalPagar.setStyle("-fx-border-color: Black ; -fx-border-width: 1; -fx-border-radius: 5px;");
        
        ImageView img = new ImageView("/recursos/FondoReservas.png");
        img.setFitHeight(910);
        img.setFitWidth(710);
        root.getChildren().add(img);
        
        disenoBotones();
        
        HBox datosCliente = new HBox(1);
        datosCliente.setAlignment(Pos.CENTER_LEFT);
        datosCliente.setStyle("-fx-background-color: White");
        datosCliente.getChildren().addAll(labelConFormatoTamano("  "+hotel.getNombre_hotel()+" ", "WHITE","BLACK",250,40,14),labelConFormatoTamano("   Cliente: ","#000000","WHITE",100,40,16),cedula,new Label("     "),continuar);        
        
        AnchorPane.setTopAnchor(datosCliente, 116.0);
        AnchorPane.setLeftAnchor(datosCliente, 16.0);
        AnchorPane.setRightAnchor(datosCliente, 16.0);
        root.getChildren().addAll(datosCliente);
        panelInfo.setStyle("-fx-background-color: White");
        
        AnchorPane.setBottomAnchor(reservar, 30.0);
        AnchorPane.setLeftAnchor(reservar, 240.5);    
        reservar.setDisable(true);
        cajaPago.setDisable(true);
        this.scrollTodaLaInfo.setDisable(true);
        root.getChildren().add(reservar);
        
        AnchorPane.setLeftAnchor(scrollTodaLaInfo, 17.0);
        AnchorPane.setRightAnchor(scrollTodaLaInfo, 17.0);
        AnchorPane.setTopAnchor(scrollTodaLaInfo,163.5);
        AnchorPane.setBottomAnchor(scrollTodaLaInfo,140.5);
        root.getChildren().add(scrollTodaLaInfo);
        
        organizarScroll();
        
        AnchorPane.setLeftAnchor(cajaPago, 17.0);
        AnchorPane.setBottomAnchor(cajaPago,96.0);
        root.getChildren().add(cajaPago);

        this.cajaPago.getChildren().addAll(labelConFormatoTamano("  Total a pagar: ", "BLACK","#F0415B",200,40,18),lbltotalPagar,calcular);
    }
    
    public void disenoBotones(){
        continuar.setStyle("-fx-background-color: #353A42; -fx-border-width: 3");
        continuar.setMinSize(120.0, 30.0);
        continuar.setMaxSize(120.0, 30.0);
        continuar.setFont(Font.font("Georgia", 12));
        continuar.setOnMouseEntered(e->continuar.setEffect(new DropShadow()));
        continuar.setOnMouseExited(e->continuar.setEffect(null));        
        continuar.setTextFill(Color.WHITE);
        
        calcular.setStyle("-fx-background-color: #353A42; -fx-border-width: 3");
        calcular.setMinSize(140.0, 40.0);
        calcular.setMaxSize(140.0, 40.0);
        calcular.setFont(Font.font("Georgia", 14));
        calcular.setOnMouseEntered(e->calcular.setEffect(new DropShadow()));
        calcular.setOnMouseExited(e->calcular.setEffect(null));        
        calcular.setTextFill(Color.WHITE);
        
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
    }
    
    private void configBotones(Stage actual) {
        continuar.setOnAction(b->{
        if(cedula.getText().isEmpty()){
            Interfaz.alarma("DATOS INCORRECTOS", "Por favor, ingrese el numero de cedula del cliente");
        }else if(!validarCliente()){
            Interfaz.alarma("CLIENTE NO ENCONTRADO", "El cliente que busca no se encuentra registrado");
        }else{
            cedula.setDisable(true);
            cajaPago.setDisable(false);
        this.scrollTodaLaInfo.setDisable(false);
        }});  
        
        calcular.setOnAction(e->calcularTotal());
        
        fechaReserva.setOnAction(ff->{
        try{
        cantDias=numeroDiasEntreDosFechas(fechaReserva.getValue(),fechaFin.getValue());
        this.lblDias.setText(String.valueOf(cantDias));    
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }});
        
        fechaFin.setOnAction(f->{
        cantDias=numeroDiasEntreDosFechas(fechaReserva.getValue(),fechaFin.getValue());
        this.lblDias.setText(String.valueOf(cantDias));});
        
        this.reservar.setOnAction(e->{
            Alert a = new Alert(AlertType.CONFIRMATION,"¿Desea continuar con la reserva?");
            double total = this.valorPago +(this.valorPago*0.1);
            a.setContentText("La agencia comisiona el 10% de esta transacción\nComision: $"+String.format("%.2f",this.valorPago*0.1)+"\nValor Final (+10%): $"+String.format("%.2f", total));
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK){
                Interfaz.reservas.add(new Reserva(hotel, fechaReserva.getValue(),fechaFin.getValue(), cliente, this.valorPago));
                this.serializarReservas();
                Alert al = new Alert(AlertType.INFORMATION,"SE REGISTRO CON EXITO");
                al.setTitle("SE HA GENERADO SU RESERVA");       
                al.show();
                al.setOnCloseRequest(f->actual.close());
            }
        });
        
    }

    private void organizarScroll(){
        HBox fechaInicio = new HBox(10);
        fechaInicio.setAlignment(Pos.CENTER_LEFT);
        fechaInicio.getChildren().addAll(labelConFormatoTamano("  Fecha llegada: ", "BLACK","WHITE",150,40,15),fechaReserva);
        HBox fechaLlegada = new HBox(10);
        fechaLlegada.setAlignment(Pos.CENTER_LEFT);
        fechaLlegada.getChildren().addAll(labelConFormatoTamano("  Fecha final: ", "BLACK","WHITE",150,40,15),fechaFin);      
        
        panelInfo.getChildren().addAll(fechaInicio,fechaLlegada);
        panelInfo.getChildren().addAll(new HBox(labelConFormatoTamano("  N° de Dias: ", "BLACK","WHITE",120,40,14),this.lblDias));
        
        validarFechas();

        HBox cajaInfoHabitaciones = new HBox(12);
        VBox tipoHab = new VBox(5);
        for (Habitacion hab: habitaciones){
            HBox precio;
            tipoHab.getChildren().add(labelConFormatoTamano("  "+hab.getTipo_habitacion()+" ", "WHITE","#061B5D",650,30,14));
            
            if (hab.getTarifa_sencilla()>0){
                agregarPrecios("\tSencilla:  $"+hab.getTarifa_sencilla(), tipoHab,hab.getTarifa_sencilla());
            
            }if(hab.getTarifa_doble()>0){
                agregarPrecios("\tDoble:  $"+hab.getTarifa_doble(), tipoHab,hab.getTarifa_doble());                
            }if(hab.getTarifa_triple()>0){
                agregarPrecios("\tTriple:  $"+hab.getTarifa_triple(), tipoHab,hab.getTarifa_triple());                                
            }
        }
        cajaInfoHabitaciones.getChildren().addAll(labelConFormatoTamano("  Seleccione habitacion(es): ", "BLACK","WHITE",200,40,15),tipoHab);
        panelInfo.getChildren().addAll(cajaInfoHabitaciones,labelConFormatoTamano(" ", "BLACK","WHITE",80,15,14));
        scrollTodaLaInfo.setContent(panelInfo);
        
    }
    
    public void agregarPrecios(String texto, VBox checkPrecios, double tipoTarifa){
        TextField cant = new TextField();
        cant.setStyle("-fx-border-color: Black ; -fx-border-width: 1");
        cant.setFont(Font.font("Georgia", 12));
        cant.setDisable(true);
        cant.setMinSize(100, 30);
        cant.setMaxSize(100, 30);
        
        CheckBox ch  = new CheckBox(texto);
        ch.setOnAction(e->{
            if(ch.isSelected()){
                cant.setDisable(false);
                
                this.cantHabita.put(cant,tipoTarifa);
            }else{
                cant.setDisable(true);
                cant.clear();
                this.cantHabita.remove(cant);
            }
            if(cantHabita.isEmpty()){
                reservar.setDisable(true);
            }
            System.out.println(this.cantHabita.size());});
        ch.setMinSize(180, 30);
        ch.setMaxSize(180, 30);
        HBox precio = new HBox(8);
        precio.getChildren().addAll(ch,labelConFormatoTamano("  # de hab: ", "BLACK","WHITE",80,30,14), cant);
        precio.setStyle("-fx-background-color: WHITE; -fx-font-family: Georgia; -fx-font-size: 13");
        checkPrecios.getChildren().add(precio);
    }

    
    public boolean validarCliente(){
        for (Cliente c: Interfaz.clientes){
            if(c.getCedula().equals(cedula.getText().trim())){
                cliente=c;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void start(Stage primaryStage) {
        configBotones(primaryStage);
        Scene scene = new Scene(root,700,900);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public Label labelConFormato(String texto, String colorLetra, String colorFondo){
        Label lbl = new Label(texto);
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER_LEFT);
        lbl.setTextFill(Color.web(colorLetra));
        lbl.setMinSize(160, 40);
        lbl.setMaxWidth(160);
        lbl.setFont(Font.font("Georgia", 16));
        lbl.setStyle("-fx-background-color: "+colorFondo);
        return lbl;
    }
    
    public final Label labelConFormatoTamano(String texto, String colorLetra, String colorFondo,double ancho, double alto,double tamanoLetra){
        Label lbl = labelConFormato(texto,colorLetra,colorFondo);
        lbl.setMinSize(ancho, alto);
        lbl.setMaxWidth(ancho);
        lbl.setFont(Font.font("Georgia", tamanoLetra));
        lbl.setStyle("-fx-background-color: "+colorFondo);
        return lbl;
    }
    
    public void validarFechas(){
       Callback<DatePicker, DateCell> dayCellFactory = date -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {

                super.updateItem(item, empty);
                
                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.BLACK);

                // deshabilitar las fechas antiguas
                if (item.isBefore(fechaReserva.getValue())) {
                    this.setDisable(true);
                }
                
                // fines de semana en color verde
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                     this.setTextFill(Color.GREEN);
                }
            }
        };
       
       fechaFin.setDayCellFactory(dayCellFactory);
       
       Callback<DatePicker, DateCell> dayNowCellFactory = date -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {

                super.updateItem(item, empty);
                
                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.BLACK);

                // deshabilitar las fechas futuras
                if (item.isBefore(LocalDate.now())) {
                    this.setDisable(true);
                }
                try{
                if(item.isAfter(fechaFin.getValue())){
                    this.setDisable(true);
                }    
                }catch(Exception ex){ }
                
                
                // fines de semana en color verde
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.GREEN);
                }
            }
        };
       
       fechaReserva.setDayCellFactory(dayNowCellFactory);

    }
    
    public static int numeroDiasEntreDosFechas(LocalDate fecha1, LocalDate fecha2){
        SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        //fecha inicio
        Calendar fechaInicio = new GregorianCalendar();
        fechaInicio.set(fecha1.getYear(), fecha1.getMonthValue(),fecha1.getDayOfMonth());
 
        //fecha fin
        Calendar fechaFin = new GregorianCalendar();
        fechaFin.set( fecha2.getYear(),fecha2.getMonthValue(),fecha2.getDayOfMonth());
        
        //restamos las fechas como se puede ver son de tipo Calendar,        
        //debemos obtener el valor long con getTime.getTime.
        if(fechaFin.equals(fechaInicio)){
            return 1;
        }
        
        c.setTimeInMillis(fechaFin.getTime().getTime() - fechaInicio.getTime().getTime());
        //la resta provoca que guardamos este valor en c,
        //los milisegundos corresponde al tiempo en dias
        //asi sabemos cuantos dias
 
        //System.out.println("N. dias" + c.get(Calendar.DAY_OF_YEAR));       
        return c.get(Calendar.DAY_OF_YEAR);
        
}
    public void calcularTotal (){
        double temporal=0.0;         
          if(fechaFin.getValue()==null){
              Interfaz.alarma("FECHA INCORRECTA", "Por favor seleccione la fecha en la que finalizara su estancia");
          }else if(cantHabita.isEmpty()){
              Interfaz.alarma("No ha seleccionado habitaciones", "Por favor seleccione el tipo de habitacion e ingrese la cantidad");
          }else{
         try{
         for (Map.Entry<TextField, Double> entry : cantHabita.entrySet()) {
            temporal+=(Integer.valueOf(entry.getKey().getText().trim())*entry.getValue());
          }    
          this.valorPago=temporal*this.cantDias;
          this.lbltotalPagar.setText("\t $"+String.format("%.2f", valorPago));
          
          reservar.setDisable(false);
         }
         catch(Exception ex){
            System.err.println(ex.getMessage());
            Interfaz.alarma("CANT DE HABITACIONES INCORRECTA", "Por favor, solo ingrese valores numericos en la cantidad de habitaciones");
          }     
          }       
    }
    
      public void serializarReservas(){
        try(ObjectOutputStream reservas = new ObjectOutputStream(new FileOutputStream("src/archivos/reservas.dat"))){
            reservas.writeObject(Interfaz.reservas);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        
    }
        
}
