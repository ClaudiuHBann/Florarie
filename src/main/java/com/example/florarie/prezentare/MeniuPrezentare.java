package com.example.florarie.prezentare;

import com.example.florarie.date.*;
import com.example.florarie.logic.Utilitate;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import org.jetbrains.annotations.NotNull;

/**
 * Clasa pentru meniul de prezentare a unei flori sau buchet
 */
public class MeniuPrezentare {
    private final Scene scena;

    /**
     * Constructorul pentru initializarea variabilelor si a crearii interfetei pentru o floare
     */
    public MeniuPrezentare(@NotNull Floare floare) {
        GridPane grila = new GridPane();
        grila.setMinSize(600, 400);
        grila.setMaxSize(600, 400);

        ImageView vedereImagine = new ImageView();
        vedereImagine.setFitWidth(250);
        vedereImagine.setFitHeight(150);
        vedereImagine.setImage(floare.poza);
        vedereImagine.setPreserveRatio(true);

        double raportDeAspect = floare.poza.getWidth() / floare.poza.getHeight();
        double latimeImagine = Math.min(vedereImagine.getFitWidth(), vedereImagine.getFitHeight() * raportDeAspect);
        double inaltimeImagine = Math.min(vedereImagine.getFitHeight(), vedereImagine.getFitWidth() / raportDeAspect);

        Button butonInapoi = Utilitate.CreazaButon("Back", Utilitate.font, 80, 30);
        butonInapoi.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare(true).GetScena()));
        GridPane.setHalignment(butonInapoi, HPos.CENTER);
        GridPane.setValignment(butonInapoi, VPos.CENTER);

        ScrollPane panouDeInformatii = new ScrollPane();
        panouDeInformatii.setContent(new Text("Denumire: " + floare.denumire + '\n' + "Culoare: " + floare.culoare + '\n' + "Specie: " + floare.specie + '\n' + "Pret: " + floare.pret));
        panouDeInformatii.setMinSize(400 - latimeImagine, 30 + inaltimeImagine);
        panouDeInformatii.setMaxSize(400 - latimeImagine, 30 + inaltimeImagine);

        ScrollPane panouDeInformatiiPlus = new ScrollPane();
        panouDeInformatiiPlus.setMinSize(400, 400 - 30 - inaltimeImagine);
        panouDeInformatiiPlus.setMaxSize(400, 400 - 30 - inaltimeImagine);
        panouDeInformatiiPlus.setContent(new Text(floare.modDeIngrijire));

        grila.add(butonInapoi, 0, 0);
        grila.add(vedereImagine, 0, 1);
        grila.add(panouDeInformatii, 1, 0, 1, 2);
        grila.add(panouDeInformatiiPlus, 0, 2, 2, 1);

        scena = new Scene(grila, 400, 400);

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN), butonInapoi::fire);
    }

    /**
     * Constructorul pentru initializarea variabilelor si a crearii interfetei pentru un buchet
     */
    public MeniuPrezentare(@NotNull Buchet buchet) {
        GridPane grila = new GridPane();
        grila.setMinSize(600, 400);
        grila.setMaxSize(600, 400);

        ImageView vedereImagine = new ImageView();
        vedereImagine.setFitWidth(250);
        vedereImagine.setFitHeight(150);
        vedereImagine.setImage(buchet.poza);
        vedereImagine.setPreserveRatio(true);

        double raportDeAspect = buchet.poza.getWidth() / buchet.poza.getHeight();
        double latimeImagine = Math.min(vedereImagine.getFitWidth(), vedereImagine.getFitHeight() * raportDeAspect);
        double inaltimeImagine = Math.min(vedereImagine.getFitHeight(), vedereImagine.getFitWidth() / raportDeAspect);

        Button butonInapoi = Utilitate.CreazaButon("Back", Utilitate.font, 80, 30);
        butonInapoi.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare(false).GetScena()));
        GridPane.setHalignment(butonInapoi, HPos.CENTER);
        GridPane.setValignment(butonInapoi, VPos.CENTER);

        ScrollPane panouDeInformatii = new ScrollPane();
        panouDeInformatii.setContent(new Text("Denumire: " + buchet.denumire + '\n' + "Contine: " + buchet.componente + '\n' + "Design: " + buchet.design + '\n' + "Pret: " + buchet.pret));
        panouDeInformatii.setMinSize(400 - latimeImagine, 30 + inaltimeImagine);
        panouDeInformatii.setMaxSize(400 - latimeImagine, 30 + inaltimeImagine);

        ScrollPane panouDeInformatiiPlus = new ScrollPane();
        panouDeInformatiiPlus.setMinSize(400, 400 - 30 - inaltimeImagine);
        panouDeInformatiiPlus.setMaxSize(400, 400 - 30 - inaltimeImagine);
        panouDeInformatiiPlus.setContent(new Text(buchet.modDeIngrijire));

        grila.add(butonInapoi, 0, 0);
        grila.add(vedereImagine, 0, 1);
        grila.add(panouDeInformatii, 1, 0, 1, 2);
        grila.add(panouDeInformatiiPlus, 0, 2, 2, 1);

        scena = new Scene(grila, 400, 400);

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN), butonInapoi::fire);
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
