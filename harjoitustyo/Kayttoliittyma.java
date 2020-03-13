package harjoitustyo;

import java.util.Scanner;
import java.util.ArrayList;

public class Kayttoliittyma {

    // Määritellään halutut komennot
    private static final String PRINT = "print";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String FIND = "find";
    private static final String POLISH = "polish";
    private static final String RESET = "reset";
    private static final String ECHO = "echo";
    private static final String QUIT = "quit";
    private static final String FREQS = "freqs";
    private static final String SORT = "sort";
    private static final String PPRINT = "pprint";

    /* Tarkistetaan ovatko ohjelmalle annetut argumentit oikeat   
    */
    public void parseArguments(String[] args){
        if (args.length > 1){
            mainLoop();
        }else{
            System.out.println("Error!");
            mainLoop();
        }
    }

    /* Tässä methodissa otetaan käyttäjältä vastaan kaikki komennot ja ohjataan ne oikeille methodeille
     Kokoelma luokassa
     */
    private void mainLoop(){
        Scanner scanner = new Scanner(System.in);
        Kokoelma kokoelma = new Kokoelma();
        
        System.out.println("Welcome to L.O.T");

        Boolean runLoop = true;
        while(runLoop){
            System.out.println("Please, enter a command:");
            String[] command = scanner.nextLine().split(" ");

            // Komennot jotka eivät ota argumentteja vastaan
            if (command.length == 1){
                switch (command[0]) {
                    case RESET:
                        //TODO
                        System.out.println("RESET");
                        break;
                    case ECHO:
                        // TODO
                        System.out.println("ECHOING COMMANDS");
                        break;
                    case QUIT:
                        runLoop = false;
                        System.out.println("Program terminated.");
                        break;
                    default:
                        System.out.println("Error!");
                        break;
                }

            // Komennot jotka ottavat yhden argumentin vastaan, tästä on poistettu polish ja find,
            // ne käsitellään omilla alueillaan niiden laajuuden takia
            } else if (command.length == 2 && !command[0].equals(POLISH )&& !command[0].equals(FIND)) {
                try {
                    int commandArg = Integer.parseInt(command[1]);

                    switch (command[0]) {
                        case PRINT:
                            System.out.println("PRINT " + commandArg);
                            break;
                        case ADD:
                            System.out.println("ADDING");
                            break;
                        case REMOVE:
                            System.out.println("REMOVE");
                            break;
                        case FREQS:
                            System.out.println("FREQS");
                            break;
                        case PPRINT:
                            System.out.println("PPRINT");
                            break;
                        case SORT:
                            System.out.println("SORT");
                            break;
                        default:
                            System.out.println("Error!");
                            break;
                    }    
                } catch (Exception e) {
                    System.out.println("Error!");
                }

            } else if (command[0].equals(POLISH)){
                try {
                    String[] polishArgs = command[1].split("");
                    
                    System.out.println("POLISH");
                        for (String string : polishArgs) {
                            System.out.println(string);
                        }

                } catch (Exception e) {
                    System.out.println("Error!");
                }

            } else if (command[0].equals(FIND)){
                try {
                    // Lisätään find komennon kaikki arguementit listaan
                    ArrayList<String> findArgs = new ArrayList<String>();
                    for (int i = 1; i < command.length; i++) {
                        findArgs.add(command[i]);
                    }

                    System.out.println("FIND");
                    for (String string : findArgs) {
                        System.out.println(string);
                    }
                } catch (Exception e) {
                    System.out.println("Error!");    
                }
            } else{
                System.out.println("Error!");
            }
        }
    }
    
}