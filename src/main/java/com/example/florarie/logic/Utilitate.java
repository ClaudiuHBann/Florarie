package com.example.florarie.logic;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

/**
 * Clasa utilitara unde se afla metode si variabile folosite uzual
 */
public class Utilitate {
    public static Stage stadiu;
    public static final Font font = Font.font("Calibri", 18);
    public static final Rectangle2D marimeEcran = Screen.getPrimary().getVisualBounds();

    /**
     * <p> Metoda pentru crearea de butoane
     * </p>
     * @param text textul butonului
     * @param font fontul butonului
     * @param latime latimea butonului
     * @param inaltime inaltimea butonului
     * @return butonul cu specificatiile date
     */
    public static @NotNull Button CreazaButon(@NotNull String text, @NotNull Font font, int latime, int inaltime) {
        Button butonNou = new Button(text);
        butonNou.setMinSize(latime, inaltime);
        butonNou.setMaxSize(latime, inaltime);
        butonNou.setFont(font);
        return butonNou;
    }

    /**
     * <p> Metoda pentru schimbarea scenei
     * </p>
     * @param evenimentDeActiune evenimentul de actiune al unui widget
     * @param scena scena cu care se va schimba
     */
    public static void SchimbaScena(@NotNull ActionEvent evenimentDeActiune, Scene scena) {
        stadiu = (Stage) ((Node) evenimentDeActiune.getSource()).getScene().getWindow();
        stadiu.setScene(scena);
        stadiu.setX((marimeEcran.getWidth() - stadiu.getWidth()) / 2);
        stadiu.setY((marimeEcran.getHeight() - stadiu.getHeight()) / 2);
        stadiu.show();
    }

    /**
     * <p> Metoda pentru schimbarea scenei
     * </p>
     * @param obiect sursa unui eveniment
     * @param scena scena cu care se va schimba
     */
    public static void SchimbaScena(@NotNull Object obiect, Scene scena) {
        stadiu = (Stage) ((Node) obiect).getScene().getWindow();
        stadiu.setScene(scena);
        stadiu.setX((marimeEcran.getWidth() - stadiu.getWidth()) / 2);
        stadiu.setY((marimeEcran.getHeight() - stadiu.getHeight()) / 2);
        stadiu.show();
    }
}
