package harjoitustyo;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.kokoelma.Kokoelma;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Harjoitustyön Kayttoliittyma-luokka.
 * Tämä luokka ottaa vastaan käyttäjien komennot ja ohjaa ne oikealle Kokoelma lukokan methodille.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */
public class Kayttoliittyma {

    // Kokoelma
    public Kokoelma kokoelma;

    // Rakentaja
    public Kayttoliittyma() {
        kokoelma = new Kokoelma();
    }

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

    /**
     * Tarkistetaan, että ohjelmalle annetut parametrit ovat oikeat,
     * eli löytyvätkö halutut tiedostot ja onko parametrejä oikea määrä (2).
     * @param args
     */
    public void parseArguments(String[] args){
        System.out.println("Welcome to L.O.T.");

        if (args.length == 2){
            Utility util = new Utility();
            LinkedList<String> tekstiKokoelma = util.lueTiedosto(args[0]);
            LinkedList<String> sulkusanat = util.lueTiedosto(args[1]);

            if (tekstiKokoelma == null || sulkusanat == null){
                System.out.println("Missing file!");
            }else{
                mainLoop(tekstiKokoelma, sulkusanat);
            }

        }else{
            System.out.println("Wrong number of command-line arguments!");
        }

        System.out.println("Program terminated.");
    }

    /**
     * Tässä methodissa otetaan käyttäjältä vastaan kaikki komennot ja ohjataan ne oikeille methodeille
     *  Kokoelma luokassa
     * @param tekstiKokoelma tiedostosta luetut dokumentit
     * @param sulkusanat tiedostosta luetut sulkusanat
     */
    private void mainLoop(LinkedList<String> tekstiKokoelma, LinkedList<String> sulkusanat){
        Scanner scanner = new Scanner(System.in);

        boolean runLoop = true;
        boolean enableEcho = false;
        try {
            kokoelma.luoKokoelma(tekstiKokoelma);
        } catch (IllegalArgumentException e){
            System.out.println("Error!");
            runLoop = false;
        }

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
                        try {
                            kokoelma = new Kokoelma();
                            kokoelma.luoKokoelma(tekstiKokoelma);
                        } catch (IllegalArgumentException e){
                            System.out.println("Error!");
                        }
                        break;
                    case ECHO:
                        System.out.println("echo");
                        enableEcho = true;
                        break;
                    case QUIT:
                        runLoop = false;
                        break;
                    case FREQS:
                        TreeMap <String, Integer> hakemisto = kokoelma.haeKaikkiFrekvenssit();
                        laskeFrekvenssit(hakemisto);
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
                            kokoelma.poista(commandArg);
                            break;
                        case FREQS:
                            TreeMap <String, Integer> hakemisto = kokoelma.haeSananFrekvenssit(commandArg);
                            laskeFrekvenssit(hakemisto);
                            break;
                        case PPRINT:
                            // TODO
                            System.out.println("PPRINT");
                            break;
                        case SORT:
                            // TODO
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
                    poistoSanat.remove(0);

                    for (Dokumentti dokkari : kokoelma.dokumentit()){
                        if (dokkari.sanatTäsmäävät(poistoSanat)){
                            System.out.println(dokkari.tunniste());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error!");
                }
            } else if (command.get(0).equals(ADD)){
                String uusiDokumentti = inputCommand.replace(command.get(0) + " ", "");
                // Jos kokoelmasta löytyy samalla tunnisteella dokumentti, palauttaa method
                // IllegalArgumentException
                try {
                    kokoelma.lisääDokumenttiKokoelmaan(uusiDokumentti);
                } catch (IllegalArgumentException e){
                    System.out.println("Error!");
                }

            } else {
                System.out.println("Error!");
            }
        }
    }

    private void laskeFrekvenssit(TreeMap <String, Integer> hakemisto) {
        if (hakemisto != null){
            int count = 0;
            for (String sana: hakemisto.keySet()){
                System.out.printf("%s %d\n", sana, hakemisto.get(sana));
                count = count + hakemisto.get(sana);
            }
            System.out.printf("A total of %d words.\n", count);

        } else System.out.println("Error!");
    }
}