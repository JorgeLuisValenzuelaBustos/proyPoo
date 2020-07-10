/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Alejandra Cotrina, Jorge Valenzuela, Ramon Macias
 */
public class VentanaPrincipal extends Application {

    private final Button crearCliente;
    private final AnchorPane root;
    private final Button busquedas;
    private final Button reservas;

    public VentanaPrincipal() {
        crearCliente = new Button("Registrar Cliente");
        root = new AnchorPane();
        busquedas = new Button("Busquedas");
        reservas = new Button("Reservas");
        organizarRoot();
    }

    @Override
    public void start(Stage primaryStage) {
        configBotones(primaryStage);
        Scene scene = new Scene(root, 750.0, 450.0);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        primaryStage.setTitle("CLICK TOURS");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void organizarRoot() {
        ImageView fondo = new ImageView("/recursos/FondoPrincipal.png");
        fondo.setFitHeight(460);
        fondo.setFitWidth(760);
        root.getChildren().add(fondo);

        disenoBotones();
    }

    private void disenoBotones() {
        List<Button> botones = Arrays.asList(crearCliente, busquedas,reservas);
        HBox botons = new HBox(20);
        botones.forEach(b -> {
            b.setMaxWidth(200);
            b.setMinWidth(200);
            b.setMaxHeight(80);
            b.setMinHeight(80);
            b.setTextFill(Color.WHITE);
            b.setWrapText(true);
            b.setTextAlignment(TextAlignment.CENTER);
            b.setFont(Font.font("Felix Titling", 25));
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

        AnchorPane.setBottomAnchor(botons, 100.0);
        AnchorPane.setLeftAnchor(botons, 65.0);
        root.getChildren().add(botons);

    }

    private void configBotones(Stage actual) {
        busquedas.setOnAction(e -> {
            TiposBusqueda tb = new TiposBusqueda();
            actual.close();
            tb.start(new Stage());
        });

        crearCliente.setOnAction(e -> {
            IngresoClientes ic = new IngresoClientes();
            actual.close();
            ic.start(new Stage());
        });
        
        reservas.setOnAction(e->{
            VentanaReporteReservaciones vrr = new VentanaReporteReservaciones();
            vrr.start(new Stage());
            actual.close();});
    }

}
