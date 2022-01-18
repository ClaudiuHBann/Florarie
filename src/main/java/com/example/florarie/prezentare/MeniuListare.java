package com.example.florarie.prezentare;

import com.example.florarie.Main;
import com.example.florarie.date.Buchet;
import com.example.florarie.date.Floare;
import com.example.florarie.logic.Utilitate;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Clasa pentru listarea datelor (florilor/buchetelor)
 */
public class MeniuListare {
    private final Scene scena;

    private Button butonDeAplicare;
    private TextField filtruNume;
    private ComboBox<String> filtruCuloare;
    private Slider filtruPret;
    ComboBox<String> filtruDesign;

    private Map<String, Spinner> listaSpinnere;

    /**
     * Constructor pentru initializarea variabilelor si crearea de interfata pentru listarea florilor sau buchetelor
     */
    public MeniuListare(boolean esteFloare) {
        GridPane grila = new GridPane();
        grila.setMinSize(600, 300);
        grila.setMaxSize(600, 300);

        List<Floare> floriLista = Main.incarcatorDate.IncarcaToateFlorile();
        List<Buchet> bucheteLista = Main.incarcatorDate.IncarcaToateBuchetele();

        List<String> listaDeFoldereInX;
        if (esteFloare) {
            listaDeFoldereInX = Main.incarcatorDate.ListaDeFoldereInX("flori");
        } else {
            listaDeFoldereInX = Main.incarcatorDate.ListaDeFoldereInX("buchete");
        }

        ListView<String> listaDeVizualizare = new ListView<>(FXCollections.observableArrayList(listaDeFoldereInX));
        listaDeVizualizare.setMinSize(800, 300);
        listaDeVizualizare.setMaxSize(800, 300);

        listaDeVizualizare.setCellFactory(celula -> new ListCell<>() {
            @Override
            protected void updateItem(String element, boolean gol) {
                super.updateItem(element, gol);
                if (element != null) {
                    setText(element);
                    setFont(Utilitate.font);
                } else {
                    setText("");
                }
            }
        });

        listaDeVizualizare.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (esteFloare) {
                    Utilitate.SchimbaScena(keyEvent.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaFloare(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
                } else {
                    Utilitate.SchimbaScena(keyEvent.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaBuchet(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
                }
            }
        });
        listaDeVizualizare.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2 && click.getButton().equals(MouseButton.PRIMARY)) {
                if (esteFloare) {
                    Utilitate.SchimbaScena(click.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaFloare(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
                } else {
                    Utilitate.SchimbaScena(click.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaBuchet(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
                }
            }
        });

        HBox hBox = new HBox();
        hBox.setMinSize(800, 100);
        hBox.setMaxSize(800, 100);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0, 10, 0, 10));

        Button butonInSpate = Utilitate.CreazaButon("Back", Utilitate.font, 80, 40);
        butonInSpate.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuPrincipal().GetScena()));

        if (esteFloare) {
            StackPane panouFiltruNume = new StackPane();
            Label filtruNumeEticheta = new Label("Nume:");
            filtruNumeEticheta.setFont(Utilitate.font);
            filtruNume = new TextField();
            StackPane.setAlignment(filtruNumeEticheta, Pos.TOP_CENTER);
            StackPane.setAlignment(filtruNume, Pos.CENTER);
            panouFiltruNume.getChildren().addAll(filtruNumeEticheta, filtruNume);

            StackPane panouFiltruCuloare = new StackPane();
            Label filtruCuloareEticheta = new Label("Culoare:");
            filtruCuloareEticheta.setFont(Utilitate.font);
            filtruCuloare = new ComboBox<>();
            StackPane.setAlignment(filtruCuloareEticheta, Pos.TOP_CENTER);
            StackPane.setAlignment(filtruCuloare, Pos.CENTER);
            floriLista.forEach(floare -> {
                if (!filtruCuloare.getItems().contains(floare.culoare)) {
                    filtruCuloare.getItems().add(floare.culoare);
                }
            });
            filtruCuloare.getItems().add("Toate");
            filtruCuloare.setValue("Toate");
            panouFiltruCuloare.getChildren().addAll(filtruCuloareEticheta, filtruCuloare);

            StackPane panouFiltruPret = new StackPane();
            Label filtruPretEticheta = new Label("Pret:");
            filtruPretEticheta.setFont(Utilitate.font);
            filtruPret = new Slider();
            filtruPret.setMin(0);
            filtruPret.setMax(100);
            filtruPret.setValue(0);
            filtruPret.setShowTickLabels(true);
            filtruPret.setShowTickMarks(true);
            filtruPret.setBlockIncrement(1);
            StackPane.setAlignment(filtruPretEticheta, Pos.TOP_CENTER);
            StackPane.setAlignment(filtruPret, Pos.CENTER);
            panouFiltruPret.getChildren().addAll(filtruPretEticheta, filtruPret);

            butonDeAplicare = Utilitate.CreazaButon("Aplicare", Utilitate.font, 100, 40);
            butonDeAplicare.setOnAction(actionEvent -> {
                List<String> restulDeFlori = new ArrayList<>(listaDeFoldereInX);
                restulDeFlori.removeAll(listaDeVizualizare.getItems());
                listaDeVizualizare.getItems().addAll(restulDeFlori);

                if (filtruNume.getText().length() > 0) {
                    listaDeVizualizare.getItems().removeIf(nume -> !nume.toLowerCase().contains(filtruNume.getText().toLowerCase()));
                }

                if (filtruCuloare.getSelectionModel().getSelectedItem() != null && !Objects.equals(filtruCuloare.getSelectionModel().getSelectedItem(), "Toate")) {
                    floriLista.forEach(floare -> {
                        if (!Objects.equals(floare.culoare, filtruCuloare.getSelectionModel().getSelectedItem())) {
                            listaDeVizualizare.getItems().remove(floare.denumire);
                        }
                    });
                }

                if (filtruPret.getValue() != 0.0) {
                    floriLista.forEach(floare -> {
                        if (filtruPret.getValue() > floare.pret) {
                            listaDeVizualizare.getItems().remove(floare.denumire);
                        }
                    });
                }
            });

            hBox.getChildren().addAll(butonInSpate, panouFiltruNume, panouFiltruCuloare, panouFiltruPret, butonDeAplicare);
        } else {
            StackPane panouDesign = new StackPane();
            Label filtruDesignEticheta = new Label("Design:");
            filtruDesignEticheta.setFont(Utilitate.font);
            filtruDesign = new ComboBox<>();
            StackPane.setAlignment(filtruDesignEticheta, Pos.TOP_CENTER);
            StackPane.setAlignment(filtruDesign, Pos.CENTER);
            bucheteLista.forEach(buchet -> {
                if (!filtruDesign.getItems().contains(buchet.design)) {
                    filtruDesign.getItems().add(buchet.design);
                }
            });
            filtruDesign.getItems().add("Toate");
            filtruDesign.setValue("Toate");
            panouDesign.getChildren().addAll(filtruDesignEticheta, filtruDesign);

            butonDeAplicare = Utilitate.CreazaButon("Aplicare", Utilitate.font, 100, 40);
            butonDeAplicare.setOnAction(actionEvent -> {
                List<String> restulDeBuchete = new ArrayList<>(listaDeFoldereInX);
                restulDeBuchete.removeAll(listaDeVizualizare.getItems());
                listaDeVizualizare.getItems().addAll(restulDeBuchete);

                if (filtruDesign.getSelectionModel().getSelectedItem() != null && !Objects.equals(filtruDesign.getSelectionModel().getSelectedItem(), "Toate")) {
                    bucheteLista.forEach(buchet -> {
                        if (filtruDesign.getSelectionModel().getSelectedItem().contains(buchet.design)) {
                            listaDeVizualizare.getItems().remove(buchet.denumire);
                        }
                    });
                }
            });

            Button butonCreare = Utilitate.CreazaButon("Creaza Buchet", Utilitate.font, 150, 40);
            butonCreare.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare().GetScena()));

            hBox.getChildren().addAll(butonInSpate, panouDesign, butonDeAplicare, butonCreare);
        }

        grila.add(listaDeVizualizare, 0, 0);
        grila.add(hBox, 0, 1);

        scena = new Scene(grila, 800, 400);

        if (esteFloare) {
            CreareMeniuDeContext(true);
        } else {
            CreareMeniuDeContext(false);
        }

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN), butonDeAplicare::fire);
    }

    /**
     * Constructor pentru initializarea variabilelor si crearea de interfata pentru listarea florilor pentru crearea unui buchet
     */
    public MeniuListare() {
        GridPane grila = new GridPane();
        grila.setMinSize(600, 300);
        grila.setMaxSize(600, 300);

        listaSpinnere = new HashMap<>();

        List<Floare> floriLista = Main.incarcatorDate.IncarcaToateFlorile();
        List<String> listaDeFoldereInX = Main.incarcatorDate.ListaDeFoldereInX("flori");

        ListView<Floare> listaDeVizualizare = new ListView<>(FXCollections.observableArrayList(floriLista));
        listaDeVizualizare.setMinSize(800, 300);
        listaDeVizualizare.setMaxSize(800, 300);

        listaDeVizualizare.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Floare> call(ListView<Floare> listaFlori) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Floare floare, boolean gol) {
                        super.updateItem(floare, gol);
                        if (gol || floare == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(null);

                            BorderPane panouDeMargine = new BorderPane();

                            Label numeFloare = new Label(floare.denumire);
                            numeFloare.setFont(Utilitate.font);

                            Spinner spinner;
                            if (!listaSpinnere.containsKey(floare.denumire)) {
                                spinner = new Spinner<>(0, 100, 0, 1);
                                listaSpinnere.put(floare.denumire, spinner);
                            } else {
                                spinner = listaSpinnere.get(floare.denumire);
                            }

                            panouDeMargine.setLeft(numeFloare);
                            panouDeMargine.setRight(spinner);

                            setGraphic(panouDeMargine);
                        }
                    }
                };
            }
        });

        listaDeVizualizare.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                Utilitate.SchimbaScena(keyEvent.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaFloare(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
            }
        });
        listaDeVizualizare.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2 && click.getButton().equals(MouseButton.PRIMARY)) {
                Utilitate.SchimbaScena(click.getSource(), new MeniuPrezentare(Main.incarcatorDate.IncarcaFloare(listaDeFoldereInX.get(listaDeVizualizare.getSelectionModel().getSelectedIndex()))).GetScena());
            }
        });

        HBox hBox = new HBox();
        hBox.setMinSize(800, 100);
        hBox.setMaxSize(800, 100);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0, 10, 0, 10));

        Button butonInSpate = Utilitate.CreazaButon("Back", Utilitate.font, 80, 40);
        butonInSpate.setOnAction(actionEvent -> Utilitate.SchimbaScena(actionEvent, new MeniuListare(false).GetScena()));

        StackPane panouFiltruNume = new StackPane();
        Label filtruNumeEticheta = new Label("Nume:");
        filtruNumeEticheta.setFont(Utilitate.font);
        filtruNume = new TextField();
        StackPane.setAlignment(filtruNumeEticheta, Pos.TOP_CENTER);
        StackPane.setAlignment(filtruNume, Pos.CENTER);
        panouFiltruNume.getChildren().addAll(filtruNumeEticheta, filtruNume);

        StackPane panouFiltruCuloare = new StackPane();
        Label filtruCuloareEticheta = new Label("Culoare:");
        filtruCuloareEticheta.setFont(Utilitate.font);
        filtruCuloare = new ComboBox<>();
        StackPane.setAlignment(filtruCuloareEticheta, Pos.TOP_CENTER);
        StackPane.setAlignment(filtruCuloare, Pos.CENTER);
        floriLista.forEach(floare -> {
            if (!filtruCuloare.getItems().contains(floare.culoare)) {
                filtruCuloare.getItems().add(floare.culoare);
            }
        });
        filtruCuloare.getItems().add("Toate");
        filtruCuloare.setValue("Toate");
        panouFiltruCuloare.getChildren().addAll(filtruCuloareEticheta, filtruCuloare);

        StackPane panouFiltruPret = new StackPane();
        Label filtruPretEticheta = new Label("Pret:");
        filtruPretEticheta.setFont(Utilitate.font);
        filtruPret = new Slider();
        filtruPret.setMin(0);
        filtruPret.setMax(100);
        filtruPret.setValue(0);
        filtruPret.setShowTickLabels(true);
        filtruPret.setShowTickMarks(true);
        filtruPret.setBlockIncrement(1);
        StackPane.setAlignment(filtruPretEticheta, Pos.TOP_CENTER);
        StackPane.setAlignment(filtruPret, Pos.CENTER);
        panouFiltruPret.getChildren().addAll(filtruPretEticheta, filtruPret);

        butonDeAplicare = Utilitate.CreazaButon("Aplicare", Utilitate.font, 100, 40);
        butonDeAplicare.setOnAction(actionEvent -> {
            List<Floare> restulDeFlori = new ArrayList<>(floriLista);
            restulDeFlori.removeAll(listaDeVizualizare.getItems());
            listaDeVizualizare.getItems().addAll(restulDeFlori);

            if (filtruNume.getText().length() > 0) {
                listaDeVizualizare.getItems().removeIf(floare -> !floare.denumire.toLowerCase().contains(filtruNume.getText().toLowerCase()));
            }

            if (filtruCuloare.getSelectionModel().getSelectedItem() != null && !Objects.equals(filtruCuloare.getSelectionModel().getSelectedItem(), "Toate")) {
                floriLista.forEach(floare -> {
                    if (!Objects.equals(floare.culoare, filtruCuloare.getSelectionModel().getSelectedItem())) {
                        listaDeVizualizare.getItems().removeIf(floare1 -> floare1.denumire.contains(floare.denumire));
                    }
                });
            }

            if (filtruPret.getValue() != 0.0) {
                floriLista.forEach(floare -> {
                    if (filtruPret.getValue() > floare.pret) {
                        listaDeVizualizare.getItems().removeIf(floareV -> floareV.denumire.contains(floare.denumire));
                    }
                });
            }
        });

        Button butonFinalizare = Utilitate.CreazaButon("Creaza Buchet", Utilitate.font, 150, 40);
        butonFinalizare.setOnAction(actionEvent -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Finalizare comanda buchet");
            alerta.setHeaderText("Informatii in plus pentru comanda:");

            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Spinner> entry : listaSpinnere.entrySet()) {
                stringBuilder.append(entry.getValue().getValue()).append(' ').append(entry.getKey()).append("\n");
            }
            alerta.setContentText(stringBuilder.toString());

            TextArea zonaMesaj = new TextArea();
            zonaMesaj.setWrapText(true);
            zonaMesaj.setMaxWidth(Double.MAX_VALUE);
            zonaMesaj.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(zonaMesaj, Priority.ALWAYS);
            GridPane.setHgrow(zonaMesaj, Priority.ALWAYS);
            alerta.getDialogPane().setExpandableContent(zonaMesaj);

            Optional<ButtonType> rezultat = alerta.showAndWait();
            if (rezultat.isPresent() && rezultat.get() == ButtonType.OK) {
                PrintWriter printWriter = null;
                try {
                    printWriter = new PrintWriter(System.getProperty("user.dir") + "\\comenzi.txt", StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                printWriter.println(stringBuilder + "\n" + zonaMesaj.getText() + "\n\n");
                printWriter.close();
            }
        });

        hBox.getChildren().addAll(butonInSpate, panouFiltruNume, panouFiltruCuloare, panouFiltruPret, butonDeAplicare, butonFinalizare);

        grila.add(listaDeVizualizare, 0, 0);
        grila.add(hBox, 0, 1);

        scena = new Scene(grila, 800, 400);

        CreareMeniuDeContext(true);

        scena.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN), butonDeAplicare::fire);
    }

    /**
     * Metoda pentru crearea meniului de context
     */
    private void CreareMeniuDeContext(boolean esteFloare) {
        ContextMenu meniuDeContext = new ContextMenu();

        MenuItem elementDinMeniuAplicare = new MenuItem("Aplicare");
        elementDinMeniuAplicare.setOnAction(actionEvent -> butonDeAplicare.fire());
        meniuDeContext.getItems().add(elementDinMeniuAplicare);

        MenuItem elementDinMeniuStergereFiltre = new MenuItem("Stergere Filtre");
        elementDinMeniuStergereFiltre.setOnAction(actionEvent -> {
            if (esteFloare) {
                filtruNume.setText("");
                filtruCuloare.setValue("Toate");
                filtruPret.setValue(0);
            } else {
                filtruDesign.setValue("Toate");
            }

            butonDeAplicare.fire();
        });
        meniuDeContext.getItems().add(elementDinMeniuStergereFiltre);

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