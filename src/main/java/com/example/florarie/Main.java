package com.example.florarie;

import com.example.florarie.logic.IncarcatorDate;
import com.example.florarie.logic.Utilitate;
import com.example.florarie.prezentare.MeniuAutentificare;
import com.example.florarie.prezentare.MeniuPrincipal;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    public static IncarcatorDate incarcatorDate;
    public static MeniuAutentificare meniuAutentificare;

    @Override
    public void start(@NotNull Stage stadiu) {
        Utilitate.stadiu = stadiu;

        incarcatorDate = new IncarcatorDate(null);
        meniuAutentificare = new MeniuAutentificare();

        stadiu.setTitle("Florarie");
        stadiu.setScene(meniuAutentificare.GetScena());
        stadiu.setResizable(false);
        stadiu.show();

        Rectangle2D marimeEcran = Screen.getPrimary().getVisualBounds();
        stadiu.setX((marimeEcran.getWidth() - stadiu.getWidth()) / 2);
        stadiu.setY((marimeEcran.getHeight() - stadiu.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch();
    }
}