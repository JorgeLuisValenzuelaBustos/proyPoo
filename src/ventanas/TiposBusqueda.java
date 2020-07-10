/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.util.*;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author John
 */
public class TiposBusqueda extends Application{

    private final Button hoteles;
    private final Button prox;
    private final Button regresar;
    private final AnchorPane root;

    public TiposBusqueda() {
        hoteles = new Button ("Hoteles");
        prox = new Button ("Proximidad a lugar turístico");
        regresar = new Button("Regresar");
        root = new AnchorPane();
        organizarRoot();
    }
    
    @Override
    public void start(Stage primaryStage) {
        configBotones(primaryStage);
        Scene scene = new Scene(root, 600,600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    private void configBotones(Stage actual) {
        hoteles.setOnAction(e->{
            VentanaBusquedaHotel vpc = new VentanaBusquedaHotel();
            actual.close();
            vpc.start(new Stage());
        });
        
        prox.setOnAction(e->{
            VentanaLugaresProximos vlp = new VentanaLugaresProximos();
            actual.close();
            vlp.start(new Stage());
        });
        
        regresar.setOnAction(e->{
        actual.close();
        VentanaPrincipal vp = new VentanaPrincipal();
        vp.start(new Stage());
        });
        
        
        
    }

    private void organizarRoot() {
        ImageView fondo = new ImageView("/recursos/FondoBusquedasTipo.png");
        fondo.setFitHeight(610);
        fondo.setFitWidth(610);
        root.getChildren().add(fondo);
        
        disenoBotones();
        
    }

    private void disenoBotones() {
        List<Button> botones = Arrays.asList(hoteles,prox,regresar);
        VBox botons = new VBox(20);
        
        botones.forEach(b->{
            b.setMaxWidth(270);
            b.setMinWidth(270);
            b.setMaxHeight(90);
            b.setMinHeight(90);
            b.setTextFill(Color.WHITE);
            b.setWrapText(true);
            b.setTextAlignment(TextAlignment.CENTER);
            b.setFont(Font.font("Felix Titling",25));
        
        b.setStyle("-fx-background-color: #353A42");
        b.setAlignment(Pos.CENTER);
        b.setEffect(new DropShadow());
        
        b.setOnMouseEntered(e->{b.setStyle("-fx-background-color: #353A42; -fx-border-color: #E7EA02 ; -fx-border-width: 3");});
        b.setOnMouseExited(e->{b.setStyle("-fx-background-color: #353A42; -fx-border-width: 0");});
        botons.getChildren().add(b);
        });
        AnchorPane.setBottomAnchor(botons, 80.0);
        AnchorPane.setLeftAnchor(botons, 170.0);
        root.getChildren().add(botons);
        
    }
    
}
