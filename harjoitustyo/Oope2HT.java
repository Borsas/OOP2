// OOP2 Harjoitustyö Oskari Kuikka
package harjoitustyo;

public class Oope2HT {

    public static void main(String[] args) {
        // Luo uuden Käyttöliittymä olion ja käynnistää sen.

        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.parseArguments(args);
    }
}