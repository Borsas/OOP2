import harjoitustyo.Utility;
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

    /**
     * Kokoelma asetetaan tähän variableen, jotta sitä voidaan käyttää luokan sisällä
     */
    private Kokoelma kokoelma;

    // Rakentaja
    public Kayttoliittyma() {
        kokoelma = new Kokoelma();
    }


    /**
     * Tarkistetaan, että ohjelmalle annetut parametrit ovat oikeat,
     * eli löytyvätkö halutut tiedostot ja onko parametrejä oikea määrä (2).
     * @param args mainista saadut argumentit.
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
        boolean komentoOnNumero;

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

            // Tarkistetaan että komennon ensimmäinen parametri on numero
            komentoOnNumero = command.size() == 2 && onkoNumero(command.get(1));

            if (enableEcho){
                System.out.println(inputCommand);
            }

            // Komennot jotka eivät ota argumentteja vastaan
            if (command.size() == 1){
                switch (command.get(0)) {
                    case "print":
                        // Tulostaa kaikki dokumentit jos "print" ei saa argumenttia
                        for(Dokumentti dokkari: kokoelma.dokumentit()){
                            System.out.println(dokkari);
                        }
                        break;
                    case "reset":
                        try {
                            kokoelma = new Kokoelma();
                            kokoelma.luoKokoelma(tekstiKokoelma);
                        } catch (IllegalArgumentException e){
                            System.out.println("Error!");
                        }
                        break;
                    case "echo":
                        System.out.println("echo");
                        enableEcho = true;
                        break;
                    case "quit":
                        runLoop = false;
                        break;
                    case "freqs":
                        TreeMap <String, Integer> hakemisto = kokoelma.haeKaikkiFrekvenssit();
                        laskeFrekvenssit(hakemisto);
                        break;
                    default:
                        System.out.println("Error!");
                        break;
                }

            // Komennot jotka ottavat yhden argumentin vastaan, joka on Integer
            } else if (komentoOnNumero && command.size() == 2 ) {
                try {
                    int commandArg = Integer.parseInt(command.get(1));

                    switch (command.get(0)) {
                        case "print":
                            Dokumentti dokkari = kokoelma.hae(commandArg);
                            if (dokkari != null){
                                System.out.println(dokkari);
                            }else{
                                System.out.println("Error!");
                            }
                            break;
                        case "remove":
                            // Virheen sattuessa catch nappaa sen.
                            kokoelma.poista(commandArg);
                            break;
                        case "freqs":
                            TreeMap <String, Integer> hakemisto = kokoelma.haeSananFrekvenssit(commandArg);
                            laskeFrekvenssit(hakemisto);
                            break;
                        case "pprint":
                            for (Dokumentti tuloste: kokoelma.dokumentit()){
                                prettyPrint(tuloste, commandArg);
                            }
                            break;
                        default:
                            System.out.println("Error!");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error!");
                }

            // Komennot jotka ottavat vastaan joko yhden argumentin joka ei ole integer, tai komennot
            // jotka ottavat vastaan enemmän kuin yhden integerin.
            } else if ( !komentoOnNumero && command.size() >= 2 ){

                switch (command.get(0)){
                    case "polish":
                        if (command.size() == 2) {
                            for(Dokumentti dokkari : kokoelma.dokumentit()){
                                dokkari.siivoa(sulkusanat, command.get(1));
                            }
                        } else {
                            System.out.println("Error!");
                        }
                        break;
                    case "find":
                        try {
                            // Lisätään find komennon kaikki arguementit listaan ja poistetaan eka,
                            // koska se on komento "find"
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
                        break;
                    case "add":
                        String uusiDokumentti = inputCommand.replace(command.get(0) + " ", "");
                        // Jos kokoelmasta löytyy samalla tunnisteella dokumentti, palauttaa method
                        // IllegalArgumentException
                        try {
                            kokoelma.lisääDokumenttiKokoelmaan(uusiDokumentti);
                        } catch (IllegalArgumentException e){
                            System.out.println("Error!");
                        }
                        break;
                    case "pprint":
                        try {
                            int leveys = Integer.parseInt(command.get(1));
                            int dokumenttiTunnus = Integer.parseInt(command.get(2));
                            Dokumentti tulostettava = kokoelma.hae(dokumenttiTunnus);

                            if (tulostettava == null){
                                System.out.println("Error!");
                            } else {
                                prettyPrint(tulostettava, leveys);
                            }
                        } catch (Exception e){
                            System.out.println("Error!");
                        }
                        break;
                    case "sort":
                        try {
                            kokoelma.mitenLajitellaanKokoelma(command.get(1));
                        } catch (IllegalArgumentException e){
                            System.out.println("Error!");
                        }
                        break;

                default:
                        System.out.println("Error!");
                        break;
                }
            } else {
                System.out.println("Error!");
            }
        }
    }

    /**
     * Tarkistaa onko sana integer
     * @param sana sana mikä halutaan tarkistaa
     * @return true jos sana on integer, false jos ei
     */
    private boolean onkoNumero(String sana){
        try{
            int x = Integer.parseInt(sana);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Tulostaa hakemiston sanat ja niiden lukumäärät riveittäin järjestettynä sanan mukaan.
     * Lopuksi tulostaa sanojen yhteis lukumäärän
     * @param hakemisto TreeMap joka sisältää sanat ja niiden lukumäärät
     */
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

    /**
     * Tulostaa dokumentin siten, että rivin leveys on pienempi tai yhtäsuuri kuin "leveys" Integer.
     * @param dokkari Dokumentti joka halutaan tulostaa
     * @param leveys Rivin leveys
     * @throws IllegalArgumentException Jos Dokumentti on null tai leveys on negatiivinen
     */
    private void prettyPrint(Dokumentti dokkari, int leveys) throws IllegalArgumentException{
        if (dokkari == null || leveys  < 0) {
            throw new IllegalArgumentException();
        }
        // Luodaan aloitus tuloste ja jaetaan dokumentin teksti arrayksi.
        String[] dokumentinTiedot = dokkari.toString().split("///");
        String[] teksti = dokkari.teksti().split(" ");
        String alku = dokumentinTiedot[0] + "///" + dokumentinTiedot[1] + "///" + teksti[0];

        int sarakkeenLeveys = alku.length();
        System.out.print(alku);

        for (int i = 1; i < teksti.length; i++) {
            int pituus = teksti[i].length();

            // Jos sanan pituus ja sarakkeen sen hetkinen pituus ovat alle halutun leveyden
            // tulostetaan sana ja asetetaan välilyönti sen eteen
            if (pituus + sarakkeenLeveys < leveys) {
                System.out.print(" " + teksti[i]);
                sarakkeenLeveys = sarakkeenLeveys + pituus + 1;
            // Jos taas leveys on sama tai suurempi, tulostetaan rivinvaihto ja sen perään uusi sana
            // Aloittaen uuden rivin.
            } else if (pituus + sarakkeenLeveys >= leveys){
                System.out.print("\n" + teksti[i]);
                sarakkeenLeveys = pituus;
            }
        }
        System.out.print("\n");
    }
}