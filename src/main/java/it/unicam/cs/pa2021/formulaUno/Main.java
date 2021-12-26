package it.unicam.cs.pa2021.formulaUno;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static int keyboardInput(Scanner keyboardScanner) throws IllegalArgumentException{
        int keyboardInput = keyboardScanner.nextInt();
        if (keyboardInput < 0 || keyboardInput >= 3)
            throw new IllegalArgumentException("Valore in input non valido");
        return keyboardInput;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException,
            FileNotFoundException, NoSuchMethodException {


        Scanner filePathScanner = new Scanner(System.in);

        Logger.getGlobal().info("Digitare il path del file di testo che continene il builder del Circuito" +
                " e il numero di giocatori desiderato");

        String filePath = filePathScanner.nextLine();

        File textfile = new File(filePath);

        Scanner fileScanner = new Scanner(textfile);

        String gameBuilderName = null;

        int totNumOfPlayers = 0;

        for (int i = 0; i < 2; ++i){
            if (i == 0) {
                gameBuilderName = fileScanner.nextLine();
            }
            if (i == 1)
                totNumOfPlayers = fileScanner.nextInt();
        }

        fileScanner.close();

        Class<?> gameBuilderClass = Main.class.getClassLoader().loadClass(gameBuilderName);

        Constructor<?> gameBuilderConstructor = gameBuilderClass.getConstructor();

        Object gameBuilder = gameBuilderConstructor.newInstance();

        Controller<GameBuilder<GraphCircuit_DS, Car<Node>>, Node> controller = new DefaultController();

        controller.newGame((GameBuilder<GraphCircuit_DS, Car<Node>>) gameBuilder, totNumOfPlayers);

        Scanner keyboardScanner = new Scanner(System.in);

        while (!controller.isGameOver()){
            Logger.getGlobal().info("\nDigitare 0 per il prossimo turno\n"+
                                    "Digitare 1 per vedere la classifica\n"+
                    "Digitare 2 per sapere quali Giocatori sono ancora in partira");
            try {
                int keyboardInput = Main.keyboardInput(keyboardScanner);
                if (keyboardInput == 0){
                    controller.nextTurn();
                } else if (keyboardInput == 1){
                    controller.Classfication();
                } else {
                    controller.getDriversStatus();
                }
            } catch (IllegalArgumentException | InputMismatchException illegalArgumentException) {
                Logger.getGlobal().info("Inserire un numero compreso tra 0 e 2");
                illegalArgumentException.printStackTrace();
            }

        }
        keyboardScanner.close();
    }

}
