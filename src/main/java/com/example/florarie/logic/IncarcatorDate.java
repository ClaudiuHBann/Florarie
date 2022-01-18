package com.example.florarie.logic;

import com.example.florarie.date.*;

import javafx.scene.image.Image;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa pentru incarcarea de date din fisiere
 */
public class IncarcatorDate {
    private String folderRadacina;

    /**
     * Constructor de initializare a folderului radacina unde se afla informatiile
     */
    public IncarcatorDate(String folderRadacina) {
        if (folderRadacina == null) {
            this.folderRadacina = System.getProperty("user.dir");
        } else {
            this.folderRadacina = folderRadacina;
        }

        this.folderRadacina += "\\";
    }

    /**
     * <p> Metoda pentru incarcarea de flori/buchete
     * </p>
     * @param x "flori"/"buchete"
     * @return o lista de stringuri ce contine toate denumirile folderelor din "flori"/"buchete"
     */
    public List<String> ListaDeFoldereInX(String x) {
        List<String> listaDeFoldereInCale = new ArrayList<>();
        File[] listaDeFoldere = new File(folderRadacina + x + "\\").listFiles(File::isDirectory);

        for (File file : listaDeFoldere) {
            listaDeFoldereInCale.add(file.getName());
        }

        return listaDeFoldereInCale;
    }

    /**
     * <p> Metoda pentru incarcarea unui buchet
     * </p>
     * @param numeBuchet numele buchetul care se va incarca
     * @return un buchet cu informatiile din fisier
     */
    public Buchet IncarcaBuchet(String numeBuchet) {
        Buchet buchet = new Buchet();
        buchet.poza = IncarcaPoza(folderRadacina + "buchete\\" + numeBuchet + "\\" + numeBuchet + ".jpg");

        Scanner scanner;
        try {
            scanner = new Scanner(new File(folderRadacina + "buchete\\" + numeBuchet + "\\info.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        buchet.denumire = scanner.nextLine();
        buchet.componente = scanner.nextLine();
        buchet.design = scanner.nextLine();
        buchet.pret = Integer.parseInt(scanner.nextLine());
        buchet.modDeIngrijire = "";
        while (scanner.hasNextLine()) {
            buchet.modDeIngrijire += scanner.nextLine() + "\n";
        }

        return buchet;
    }

    /**
     * <p> Metoda pentru incarcarea unui buchet
     * </p>
     * @param numeFloare numele florii care se va incarca
     * @return o floare cu informatiile din fisier
     */
    public Floare IncarcaFloare(String numeFloare) {
        Floare floare = new Floare();
        floare.poza = IncarcaPoza(folderRadacina + "flori\\" + numeFloare + "\\" + numeFloare + ".jpg");

        Scanner scanner;
        try {
            scanner = new Scanner(new File(folderRadacina + "flori\\" + numeFloare + "\\info.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        floare.denumire = scanner.nextLine();
        floare.specie = scanner.nextLine();
        floare.culoare = scanner.nextLine();
        floare.pret = Integer.parseInt(scanner.nextLine());
        floare.modDeIngrijire = "";
        while (scanner.hasNextLine()) {
            floare.modDeIngrijire += scanner.nextLine() + "\n";
        }

        return floare;
    }

    /**
     * <p> Metoda pentru incarcarea tuturor buchetelor
     * </p>
     * @return o lista de buchete
     */
    public List<Buchet> IncarcaToateBuchetele() {
        List<Buchet> listaDeBuchete = new ArrayList<>();
        ListaDeFoldereInX("buchete").forEach(numeBuchet -> listaDeBuchete.add(IncarcaBuchet(numeBuchet)));
        return listaDeBuchete;
    }

    /**
     * <p> Metoda pentru incarcarea tuturor florilor
     * </p>
     * @return o lista de flori
     */
    public List<Floare> IncarcaToateFlorile() {
        List<Floare> listaDeFlori = new ArrayList<>();
        ListaDeFoldereInX("flori").forEach(numeFloare -> listaDeFlori.add(IncarcaFloare(numeFloare)));
        return listaDeFlori;
    }

    /**
     * <p> Metoda pentru incarcarea unei poze dintr-un fisier
     * </p>
     * @return o imagine
     */
    private @NotNull Image IncarcaPoza(String caleaIntreaga) {
        InputStream curentDeIntrare = null;

        try {
            curentDeIntrare = new FileInputStream(caleaIntreaga);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Image(curentDeIntrare);
    }
}
