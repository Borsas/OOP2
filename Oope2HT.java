import harjoitustyo.Kayttoliittyma;

/**
 * Harjoitustyön pääsuoritus luokka.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class Oope2HT {

    public static void main(String[] args) {
        //Luo uuden Käyttöliittymä olion ja käynnistää sen.

        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.parseArguments(args);
    }
}