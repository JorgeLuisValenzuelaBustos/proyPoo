/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import actores.Cliente;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
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
public class IngresoClientes extends Application{
    /*El sistema debe permitir la creación de clientes a través de su cedula, nombre, direccion y correo electrónico*/
    
    private final AnchorPane root = new AnchorPane();
    private final Button registrar;
    private final Button cancelar;
    private final String[] etiquetas = {"Cedula: ","Nombre: ","Direccion: ","Correo electronico: "};
    private final ArrayList<TextField> cajasTexto;

    public IngresoClientes() {
        registrar = new Button ("Registrar cliente");
        cancelar = new Button ("Cancelar");
        this.cajasTexto = new ArrayList<>();
        organizarRoot();
    }
    
     private void organizarRoot(){
        ImageView fondo = new ImageView("/recursos/FondoRegistro.png");
        fondo.setFitHeight(560);
        fondo.setFitWidth(560);
        root.getChildren().add(fondo);
        
        cajaDatos();
        disenoBotones();
        
        cajasTexto.get(0).setOnKeyPressed(e->{});
        
        
      }
     
      private void cajaDatos(){
          /**El sistema debe permitir la creación de clientes a través de su cedula, nombre, direccion y correo electrónico*/
        VBox datos = new VBox(20);
        for (int i = 0; i < 4; i++) {
            HBox cajaDatos = new HBox(10);
            TextField campo = new TextField();
            campo.setMaxSize(180, 30);
            campo.setMinSize(180, 30);
            campo.setFont(Font.font("Felix Titling",12));
            cajasTexto.add(campo);
            
            Label lbl = new Label(etiquetas[i]);
            lbl.setMaxSize(120, 40);
            lbl.setMinSize(120, 25);
            lbl.setFont(Font.font("Felix Titling",13));
            lbl.setAlignment(Pos.CENTER_RIGHT);
            lbl.setTextAlignment(TextAlignment.RIGHT);
            lbl.setWrapText(true);
            lbl.setStyle("font-weight: bold");
            cajaDatos.getChildren().addAll(lbl,campo);
            datos.getChildren().add(cajaDatos);
            
        }
        AnchorPane.setTopAnchor(datos, 210.0);
        AnchorPane.setLeftAnchor(datos, 150.0);
        root.getChildren().add(datos);
    }
   
    @Override
        public void start(Stage primaryStage) {
        configBotones(primaryStage);
        Scene scene = new Scene(root, 550,550);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    private void configBotones(Stage actual) {
        cancelar.setOnAction(e->{
        actual.close();
        VentanaPrincipal vp = new VentanaPrincipal();
        vp.start(new Stage());});
        
        registrar.setOnAction(e->{
            validarDatos(actual);});
    }

    private void disenoBotones() {
        List<Button> botones = Arrays.asList(registrar,cancelar);
        HBox botons = new HBox(20);
        
        botones.forEach(b->{
            b.setMaxWidth(150);
            b.setMinWidth(150);
            b.setMaxHeight(50);
            b.setMinHeight(50);
            b.setTextFill(Color.WHITE);
            b.setWrapText(true);
            b.setTextAlignment(TextAlignment.CENTER);
            b.setFont(Font.font("Felix Titling",15));
        
        b.setStyle("-fx-background-color: #353A42");
        b.setAlignment(Pos.CENTER);
        b.setEffect(new DropShadow());
        
        b.setOnMouseEntered(e->{
            
            b.setStyle("-fx-background-color: #353A42; -fx-border-color: #E7EA02 ; -fx-border-width: 3");});
        b.setOnMouseExited(e->{b.setStyle("-fx-background-color: #353A42; -fx-border-width: 0");});
        botons.getChildren().add(b);
        });
        AnchorPane.setBottomAnchor(botons, 50.0);
        AnchorPane.setLeftAnchor(botons, 170.0);
        root.getChildren().add(botons);
    }
    
    private boolean validarDatos(Stage actual) {              
        ArrayList<String> datCliente = new ArrayList<>();
        for (TextField t: cajasTexto){
            if (t.getText().trim().isEmpty()){
                Interfaz.alarma("NO SE PUDO REGISTRAR","Debe llenar todos los campos");
                  return false;
            }else{
                datCliente.add(t.getText());
            }
        }
        if (datCliente.size()==4 & validarFormatoCedula()){
            Cliente cl = new Cliente(datCliente.get(0),datCliente.get(1),datCliente.get(2),datCliente.get(3));
            if(Interfaz.clientes.contains(cl)){
                Interfaz.alarma("NO SE PUDO REGISTRAR","Usuario ya registrado");
            }else{
                Interfaz.clientes.add(cl);
                Alert a = new Alert(AlertType.INFORMATION,"SE REGISTRO CON EXITO");
                a.setTitle("CLIENTE REGISTRADO");       
                a.setOnCloseRequest(f->{
                    actual.close();
                VentanaPrincipal vp = new VentanaPrincipal();
                vp.start(new Stage());});
                a.show();
                serializarClientes();
                return true;
  
            }
            
        }else if (!validarFormatoCedula()){
            Interfaz.alarma("Formato de cedula incorrecto","Solo debe ingresar valor numericos en la cedula\nLa cedula ingresada no tiene 10 digitos");
        }
    return false;
    }
    
    public boolean validarFormatoCedula(){
        for (int i = 0; i < cajasTexto.get(0).getText().length(); i++) {
            if(!Character.isDigit(cajasTexto.get(0).getText().charAt(i))){
                return false;
            }
            
        }
        return cajasTexto.get(0).getText().length() == 10;
    }
    
    public void serializarClientes(){
        try(ObjectOutputStream clientes = new ObjectOutputStream(new FileOutputStream("src/archivos/clientes.dat"))){
            clientes.writeObject(Interfaz.clientes);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        
    }
    
}
