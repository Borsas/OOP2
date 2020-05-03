package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Abstrakti Dokumentti-luokka. Kokoelmissa käytetyt oliot periytyvät tästä.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public abstract class Dokumentti implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {

    // Attribuutit
    private int tunniste;
    private String teksti;

    // Rakentaja
    public Dokumentti(int uusiTunniste, String uusiTeksti) throws IllegalArgumentException{
        tunniste(uusiTunniste);
        teksti(uusiTeksti);
    }

    // Tunniste setteri ja getteri
    public void tunniste(int uusiTunniste) throws IllegalArgumentException{
        if (uusiTunniste > 0){
            tunniste = uusiTunniste;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public int tunniste(){
        return tunniste;
    }

    // Teksti setteri ja getteri
    public void teksti(String uusiTeksti) throws IllegalArgumentException{
        if(uusiTeksti != null && uusiTeksti.length() > 0){
            teksti = uusiTeksti;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public String teksti(){
        return teksti;
    }

    // Overridet vaadituille methodeille
    @Override
    public int compareTo(Dokumentti o){
        if (this.tunniste() > o.tunniste()){
            return 1;
        } else if (this.tunniste() == o.tunniste()){
            return 0;
        }else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()){
            return false;
        }
        Dokumentti other = (Dokumentti) o;
        if (other.tunniste() != this.tunniste()){
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return tunniste() + "///" + teksti();
    }

    /**
     * Tarkistaa täsmäävätkö käyttäjän antamat sanat dokumentin tekstin sanoja.
     * @param hakusanat lista dokumentin tekstistä haettavia sanoja.
     * @return palauttaa true jos hakusanat täsmäävät dokumentin tekstin sanoja.
     * @throws IllegalArgumentException
     */
    @Override
    public boolean sanatTäsmäävät(LinkedList<String> hakusanat) throws IllegalArgumentException {
        if (hakusanat == null || hakusanat.size() == 0){
            throw new IllegalArgumentException();
        }

        String[] sanat = teksti().split(" ");
        int hakuPituus = hakusanat.size();
        int onkoSamaPituus = 0;

        for(int i = 0; i < hakuPituus; i++){
            for (String sana : sanat){
                if (sana.equals(hakusanat.get(hakuPituus - 1))){
                    onkoSamaPituus++;
                    break;
                }
            }
        }

        if (onkoSamaPituus == hakuPituus){
            return true;
        }

        return false;
    }

    // Ei valmis
    @Override
    public TreeMap<String, Integer> laskeFrekvenssit() {
        throw new UnsupportedOperationException();
    }

    /**
     * Poistaa dokumentin tekstistä sulkusanat ja käyttäjän antamat välimerkit.
     * @param sulkusanat lista dokumentin tekstistä poistettavia sanoja.
     * @param välimerkit dokumentin tekstistä poistettavat välimerkit merkkijonona.
     * @throws IllegalArgumentException
     */
    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {
        if (sulkusanat == null || sulkusanat.size() == 0 || välimerkit == null || välimerkit.equals("")){
            throw new IllegalArgumentException();
        }

        // Luodaan teksti variablesta uusi LinkedList erottamalla sanat välilyönnillä
        LinkedList<String> uudetSanat = new LinkedList<String>(Arrays.asList(teksti().split(" ")));
        cleanVälimerkit(uudetSanat, välimerkit);

        for (String sana : sulkusanat){
            for(int i = 0; i < uudetSanat.size(); i++) {
                if (uudetSanat.get(i).toLowerCase().equals(sana)){
                    uudetSanat.remove(i);
                    i--;
                }
            }
        }
        teksti(combineToStringWithSpace(uudetSanat));

    }

    /**
     * Poistetaan dokumentin tekstistä kaikki annetut välimerkit
     * ja asetetaan sen listassa muokatun elementin päälle.
     * @param lista sisältää dokumentin tekstin
     * @param välimerkit käyttäjän antamat välimerkit
     */
    private void cleanVälimerkit(LinkedList<String> lista, String välimerkit){
        for (int i = 0; i < lista.size(); i++) {
             lista.set(i, lista.get(i).replaceAll(String.format("[%s]*", välimerkit), ""));
        }
    }

    /**
     *
     * @param lista lista joka sisältää dokumentin tekstin.
     * @return
     */
    private String combineToStringWithSpace(LinkedList<String> lista) {
        StringBuilder combinedString = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            if (i != lista.size() - 1){
                combinedString.append(lista.get(i).toLowerCase()).append(" ");
            }else{
                combinedString.append(lista.get(i).toLowerCase());
            }
        }
        return combinedString.toString();
    }
}