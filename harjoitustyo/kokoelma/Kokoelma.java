// OOP2 Harjoitustyö - Oskari Kuikka - 430988
package harjoitustyo.kokoelma;

import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import java.time.LocalDate;
import java.util.LinkedList;

public class Kokoelma implements harjoitustyo.apulaiset.Kokoava<harjoitustyo.dokumentit.Dokumentti> {

    // Attribuutti
    private harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit;

    // Rakentaja
    public Kokoelma(){
        dokumentit = new harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti>();
    }

    // Getter
    public harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit(){
        return dokumentit;
    }


    @Override
    public void lisää(harjoitustyo.dokumentit.Dokumentti uusi) throws IllegalArgumentException {
        // Jos dokumentit ei sisällä jo dokumenttiä, eikä se ole null, lisätään se dokumentteihin
        if (uusi == null || dokumentit.contains(uusi)) {
            throw new IllegalArgumentException();
        }
        dokumentit.lisää(uusi);
    }

    /**
     * Lisää käyttäjän antaman dokumentin kokoelmaan, jos sitä ei löydy sieltä vielä.
     * @param uusi dokumentti joka halutaan lisätä kokoelmaan
     * @throws IllegalArgumentException
     */
    public void lisääDokumenttiKokoelmaan(String uusi) throws IllegalArgumentException {
        if (uusi == null){
            throw new IllegalArgumentException();
        }

        String[] uusiDokumentti = uusi.split("///");
        int uusiTunniste = Integer.parseInt(uusiDokumentti[0]);

        // Käydään kaikki dokumentit läpi ja tarkistetaan ettei tunnistetta löydy jo
        // Jos tunniste löytyy, heitetään virhe.
        for (Dokumentti tarkistaDokkari : dokumentit){
            if (tarkistaDokkari.tunniste() == uusiTunniste) {
                throw new IllegalArgumentException();
            }
        }
        // Jos uusiDokumentti[1] sisältää pisteitä, on se päivämäärä, jolloin sen olio on Uutinen.
        if (uusiDokumentti[1].contains(".")){
            String[] katkaistuPvm = uusiDokumentti[1].split("\\.");

            int paiva = Integer.parseInt(katkaistuPvm[0]);
            int kuukausi = Integer.parseInt(katkaistuPvm[1]);
            int vuosi = Integer.parseInt(katkaistuPvm[2]);

            LocalDate pvm = LocalDate.of(vuosi, kuukausi, paiva);
            Uutinen uusiUutinen = new Uutinen(uusiTunniste, pvm, uusiDokumentti[2]);
            this.lisää(uusiUutinen);
        } else {
            Vitsi uusiVitsi = new Vitsi(uusiTunniste, uusiDokumentti[1], uusiDokumentti[2]);
            this.lisää(uusiVitsi);
        }
    }

    public void luoKokoelma(LinkedList<String> tekstiKokoelma) throws IllegalArgumentException{
        if (tekstiKokoelma == null){
            throw new IllegalArgumentException();
        }
        for (String uusiKokoelma : tekstiKokoelma){
            lisääDokumenttiKokoelmaan(uusiKokoelma);
        }
    }

    @Override
    public harjoitustyo.dokumentit.Dokumentti hae(int tunniste) {
        // Käy kaikki dokumentit läpi ja vertailee tunnisteita, palauttaa dokumentin, jos se löytyy, muuten palauttaa
        // null
        for (harjoitustyo.dokumentit.Dokumentti dokkari : dokumentit){
            if (dokkari.tunniste() == tunniste){
                return dokkari;
            }
        }
        return null;
    }

    public void poista(int tunniste){
        for (int i = 0; i < dokumentit.size(); i++) {
            if (dokumentit.get(i).tunniste() == tunniste){
                dokumentit.remove(i);
            }
        }
    }

}