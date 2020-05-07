package harjoitustyo.omalista;

import harjoitustyo.apulaiset.Ooperoiva;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * OmaLista-luokka. OmaLista-luokka periytyy LinkedList-luokasta. OmaListaa käytetään harjoitustyössä
 * käytettävän datan varastoimiseen.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E>{

    /**
     * Asettaa uuden elementin oikealle paikalle listalla.
     * Method vertailee uutta elementtiä ja vanhoja elementtejä käyttäen Comparable-rajapintaa.
     * Jos "uusi" on pienempi kuin seuraava elementti, asetetaan "uusi" elementti seuraavan elementin taakse.
     * @param uusi viite olioon, jonka luokan tai luokan esivanhemman oletetaan
     * toteuttaneen Comparable-rajapinnan.
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unchecked")
    @Override
    public void lisää(E uusi) throws IllegalArgumentException {
        // Tarkistetaan että uusi on Comparable instanssi
        if (!(uusi instanceof Comparable)) {
            throw new IllegalArgumentException();
        }

        if (this.size() == 0) {
            this.add(uusi);
        } else {
            // Käydään lista läpi ja pakotetaan variableille Comparable
            for (int i = 0; i < this.size(); i++) {
                if (((Comparable) this.get(i)).compareTo(uusi) > 0) {
                    this.add(i, uusi);
                    break;
                } else if(i == this.size() - 1){
                    this.addLast(uusi);
                    break;
                }
            }
        }
    }

    /**
     * Järjestää listan käyttäen apunaan Comparator vertailijaa.
     * @param vertailija viite vertailijaan, joka on Comparator-rajapinnan toteuttava
     * metodi eli lambda.
     * @throws IllegalArgumentException jos on null tai lajittelun aikana tapahtuu jokin virhe
     */
    @Override
    public void lajittele(Comparator<? super E> vertailija) throws IllegalArgumentException {
        if (vertailija == null ) {
            throw new IllegalArgumentException();
        }
        try {
            this.sort(vertailija);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}