package com.example.florarie.prezentare;

import com.example.florarie.logic.Utilitate;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCodeCombination;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

/**
 * Clasa pentru meniul de autentificare
 */
public class MeniuAutentificare {
    public final Scene scena;

    /**
     * Constructorul pentru initializarea variabilelor si a creatii interfetei grafice
     */
    public MeniuAutentificare() {
        GridPane grila = new GridPane();
        grila.setAlignment(Pos.CENTER);
        grila.setHgap(10);
        grila.setVgap(10);

        Text titlulScenei = new Text("Conectati-va in florarie");
        titlulScenei.setFont(Utilitate.font);

        Label etichetaNumeDeUtilizator = new Label("Nume:");
        TextField nume = new TextField();

        Label etichetaParola = new Label("Parola:");
        PasswordField parola = new PasswordField();

        Button butonAutentificare = new Button("Autentificare");

        grila.add(titlulScenei, 0, 0, 2, 1);
        grila.add(etichetaNumeDeUtilizator, 0, 1);
        grila.add(nume, 1, 1);
        grila.add(etichetaParola, 0, 2);
        grila.add(parola, 1, 2);
        grila.add(butonAutentificare, 1, 4);

        butonAutentificare.setOnAction(evenimentDeActiune -> {
            if (Objects.equals(nume.getText(), "nume") && Objects.equals(parola.getText(), "parola")) {
                Utilitate.SchimbaScena(evenimentDeActiune, new MeniuPrincipal().GetScena());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare");
                alert.setHeaderText("Nume sau parola incorecte!");
                alert.showAndWait();
            }
        });

        scena = new Scene(grila, 250, 150);

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), butonAutentificare::fire);
        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN), butonAutentificare::fire);

        ContextMenu meniuDeContext = new ContextMenu();
        MenuItem menuItemGolireCampuri = new MenuItem("Golire campuri");
        menuItemGolireCampuri.setOnAction(actionEvent -> {
            nume.setText("");
            parola.setText("");
        });
        meniuDeContext.getItems().add(menuItemGolireCampuri);
        scena.setOnContextMenuRequested(contextMenuEvent -> meniuDeContext.show(scena.getWindow(), contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));
    }

    /**
     * <p> Metoda pentru intoarcerea scenei
     * </p>
     * @return o scena
     */
    public Scene GetScena() {
        return scena;
    }
}
