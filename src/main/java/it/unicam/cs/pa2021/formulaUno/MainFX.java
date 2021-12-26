package it.unicam.cs.pa2021.formulaUno;

import javafx.application.Application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainFX {

    public static String gameBuilderName;

    public static String totNumOfPlayers;

    public static void main(String[] args) {

        while (true) {
            try {

                Scanner filePathScanner = new Scanner(System.in);

                Logger.getGlobal().info("Digitare il path del file di testo che continene il builder del Circuito" +
                        " e il numero di giocatori desiderato");

                String filePath = filePathScanner.nextLine();

                File textfile = new File(filePath);

                Scanner fileScanner = new Scanner(textfile);

                for (int i = 0; i < 2; ++i) {
                    if (i == 0) {
                        gameBuilderName = fileScanner.nextLine();
                    }
                    if (i == 1)
                        totNumOfPlayers = fileScanner.nextLine();
                }

                fileScanner.close();

                Application.launch(fxmlView.class, args);

                break;

            } catch (NoSuchElementException | FileNotFoundException exception) {
                Logger.getGlobal().info("File path is not valid");
                exception.printStackTrace();
            }
        }
    }
}
