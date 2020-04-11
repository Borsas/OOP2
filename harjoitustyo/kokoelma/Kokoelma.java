// OOP2 Harjoitustyö - Oskari Kuikka - 430988
package harjoitustyo.kokoelma;

import harjoitustyo.apulaiset.Kokoava;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.omalista.OmaLista;

public class Kokoelma<Dokumentti> implements harjoitustyo.apulaiset.Kokoava<harjoitustyo.dokumentit.Dokumentti> {

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

    @Override
    public harjoitustyo.dokumentit.Dokumentti hae(int tunniste) {
        // Käy kaikki dokumentit läpi ja vertailee tunnisteita, palauttaa dokumentin, jos se löytyy, muuten palauttaa
        // null
        for(harjoitustyo.dokumentit.Dokumentti dokkari : dokumentit){
            if (dokkari.tunniste() == tunniste){
                return dokkari;
            }
        }
        return null;
    }
}