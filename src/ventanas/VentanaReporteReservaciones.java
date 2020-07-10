/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import actores.Reserva;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import principal.Interfaz;

/**
 *
 * @author John
 */
public class VentanaReporteReservaciones extends Application {

    private final VBox root = new VBox();
    private final HBox header;
    private final Label reservaTitle = new Label("RESERVAS");
    private final ScrollPane content;
    private final TableView tablaReservas;
    private final ObservableList<Reserva> data;
    private final Button volver;
    Scene scene = new Scene(root, 700, 500);

    public VentanaReporteReservaciones() {
        this.data = FXCollections.observableArrayList();
        data.addAll(Interfaz.reservas);
        this.content = new ScrollPane();
        this.tablaReservas = new TableView();
        tablaReservas.setItems(data);
        this.header = new HBox();
        this.volver = new Button("◄");
        organizarRoot();
    }

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().addAll(header, tablaReservas);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/styles.css").toExternalForm());
        configBotones(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/recursos/ico.png"));
        primaryStage.setTitle("Reservas Existentes");
        primaryStage.setResizable(false);
        llenaTablaReservas();
        primaryStage.show();
    }

    private void organizarRoot() {
        header.getChildren().addAll(volver, reservaTitle);
        header.setId("header");
        reservaTitle.setId("titulo");
        header.setMaxSize(root.getWidth() + 10, 100);
        header.setMinSize(root.getWidth() + 10, 100);
        header.setAlignment(Pos.CENTER);
        content.setContent(tablaReservas);
        volver.setId("record-sales");
        reservaTitle.setFont(Font.font("Georgia"));
        volver.setTextFill(Color.WHITE);
        volver.setWrapText(true);
        volver.setAlignment(Pos.CENTER_LEFT);
        volver.setTextAlignment(TextAlignment.CENTER);
        volver.setFont(Font.font("Georgia", 25));
        tablaReservas.setMinWidth(scene.getWidth() + 9);
        tablaReservas.setMaxWidth(scene.getWidth() + 9);
        tablaReservas.setMaxHeight(scene.getHeight() - header.getHeight());
        tablaReservas.setMinHeight(scene.getHeight() - header.getHeight());
    }

    private void llenaTablaReservas() {
        TableColumn cedulaCol = new TableColumn("No. Cédula");
        TableColumn clienteCol = new TableColumn("Cliente");
        TableColumn hotelCol = new TableColumn("Hotel");
        TableColumn fechaCol = new TableColumn("Fecha");
        TableColumn inicioCol = new TableColumn("Inicio");
        TableColumn finCol = new TableColumn("Fin");
        fechaCol.getColumns().addAll(inicioCol, finCol);
        TableColumn costoHotelCol = new TableColumn("Costo Hotel");
        TableColumn comisionCol = new TableColumn("Comisión");
        TableColumn costoTotalCol = new TableColumn("Costo Total");
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        cedulaCol.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        hotelCol.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        inicioCol.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        finCol.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        costoHotelCol.setCellValueFactory(new PropertyValueFactory<>("totalPago"));
        comisionCol.setCellValueFactory(new PropertyValueFactory<>("comision"));
        costoTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        tablaReservas.getColumns().addAll(cedulaCol, clienteCol, hotelCol, fechaCol, costoHotelCol, comisionCol, costoTotalCol);

    }

    private void configBotones(Stage actual) {
        volver.setOnAction(e -> {
            VentanaPrincipal principal = new VentanaPrincipal();
            principal.start(new Stage());
            actual.close();
        });
    }
}
