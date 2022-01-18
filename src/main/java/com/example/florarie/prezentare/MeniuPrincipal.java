package com.example.florarie.prezentare;

import com.example.florarie.logic.Utilitate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;

/**
 * Clasa pentru meniul principal
 */
public class MeniuPrincipal {
    private final Scene scena;

    public MeniuPrincipal() {
        Button butonFlori = Utilitate.CreazaButon("Flori", Utilitate.font, 80, 30);
        Button butonBuchete = Utilitate.CreazaButon("Buchete", Utilitate.font, 90, 30);
        Button butonIesire = Utilitate.CreazaButon("Iesire", Utilitate.font, 80, 30);

        butonFlori.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare(true).GetScena()));
        butonBuchete.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare(false).GetScena()));
        butonIesire.setOnAction(actionEvent -> Utilitate.stadiu.close());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.getChildren().addAll(butonFlori, butonBuchete, butonIesire);

        scena = new Scene(vBox, 250, 150);

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN), butonFlori::fire);
        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN), butonBuchete::fire);
        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), butonIesire::fire);
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
