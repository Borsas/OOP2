package harjoitustyo;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import harjoitustyo.kokoelma.Kokoelma;
import harjoitustyo.omalista.OmaLista;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

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

    /** Tarkistetaan ovatko ohjelmalle annetut argumentit oikeat
    */
    public void parseArguments(String[] args){
        if (args.length > 1){
            mainLoop();
        }else{
            System.out.println("Error!");
            mainLoop();
        }
    }

    /** Tässä methodissa otetaan käyttäjältä vastaan kaikki komennot ja ohjataan ne oikeille methodeille
     Kokoelma luokassa
     */
    private void mainLoop(){
        Scanner scanner = new Scanner(System.in);
        Kokoelma kokoelma = new Kokoelma();

        LinkedList<String> sulkusanat = new LinkedList<>();
        sulkusanat.add("cat");
        Vitsi v1 = new Vitsi(1, "joo", "asd");
        Vitsi v3 = new Vitsi(3, "joo", "cat dog.?@ asd");
        Vitsi v4 = new Vitsi(4, "ei", "cat dog");
        kokoelma.lisää(v1);
        kokoelma.lisää(v4);
        kokoelma.lisää(v3);

        System.out.println("Welcome to L.O.T");

        boolean runLoop = true;
        boolean enableEcho = false;

        while(runLoop){
            System.out.println("Please, enter a command:");
            String inputCommand = scanner.nextLine();
            LinkedList<String> command = new LinkedList<>(Arrays.asList(inputCommand.split(" ")));

            if (enableEcho){
                System.out.println(inputCommand);
            }

            // Komennot jotka eivät ota argumentteja vastaan
            if (command.size() == 1){
                switch (command.get(0)) {
                    case PRINT:
                        // Tulostaa kaikki dokumentit jos "print" ei saa argumenttia
                        for(Dokumentti dokkari: kokoelma.dokumentit()){
                            System.out.println(dokkari);
                        }
                        break;
                    case RESET:
                        kokoelma = new Kokoelma();
                        Vitsi v2 = new Vitsi(2, "asdgf", "etrhweqr");
                        kokoelma.lisää(v2);
                        //kokoelma.lisääTiedostosta(tiedosto);
                        break;
                    case ECHO:
                        enableEcho = true;
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
            } else if (command.size() == 2
                && !command.get(0).equals(POLISH) && !command.get(0).equals(FIND) && !command.get(0).equals(ADD)) {
                try {
                    int commandArg = Integer.parseInt(command.get(1));

                    switch (command.get(0)) {
                        case PRINT:
                            Dokumentti dokkari = kokoelma.hae(commandArg);
                            if (dokkari != null){
                                System.out.println(dokkari);
                            }else{
                                System.out.println("Error!");
                            }
                            break;
                        case REMOVE:
                            for (int i = 0; i < kokoelma.dokumentit().size(); i++) {
                                if(kokoelma.dokumentit().get(i).tunniste() == commandArg){
                                    kokoelma.dokumentit().remove(i);
                                }
                            }
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

            } else if (command.get(0).equals(POLISH) && command.size() == 2){
                    for(Dokumentti dokkari : kokoelma.dokumentit()){
                        dokkari.siivoa(sulkusanat, command.get(1));
                    }

            } else if (command.get(0).equals(FIND)){
                try {
                    // Lisätään find komennon kaikki arguementit listaan ja poistetaan eka, koska se on komento "find"
                    LinkedList<String> poistoSanat = new LinkedList<>(command);
                    poistoSanat.remove(1);

                    for (Dokumentti dokkari : kokoelma.dokumentit()){
                        if (dokkari.sanatTäsmäävät(poistoSanat)){
                            System.out.println(dokkari.tunniste());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error!");
                }
            } else if (command.get(0).equals(ADD)){
                    try {
                        String[] uusiDokumentti = command.get(1).split("///");
                        int uusiTunniste = Integer.parseInt(uusiDokumentti[0]);

                        // Käydään kaikki dokumentit läpi ja tarkistetaan ettei tunnistetta löydy jo
                        // Jos tunnistetta ei löydä, tarkisteaan onko kokoelmassa Vitsejä vai Uutisia ja luodaan
                        // Uusi olio sen perusteella
                        for (Dokumentti tarkistaDokkari : kokoelma.dokumentit()){
                            if (tarkistaDokkari.tunniste() != uusiTunniste){
                                if (tarkistaDokkari instanceof Vitsi){
                                    Vitsi uusiVitsi = new Vitsi(uusiTunniste, uusiDokumentti[1], uusiDokumentti[2]);
                                    kokoelma.lisää(uusiVitsi);
                                } else{
                                    LocalDate pvm = LocalDate.parse(uusiDokumentti[1]);
                                    Uutinen uusiUutinen = new Uutinen(uusiTunniste, pvm, uusiDokumentti[2]);
                                    kokoelma.lisää(uusiUutinen);
                                }
                                break;
                            } else{
                                System.out.println("Error!");
                                break;
                            }
                        }
                    } catch (Exception e){
                        System.out.println("Error!");
                    }
            } else {
                System.out.println("Error!");
            }
        }
    }
}