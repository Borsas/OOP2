package harjoitustyo.kokoelma;

import harjoitustyo.Utility;
import harjoitustyo.dokumentit.Dokumentti;
import harjoitustyo.dokumentit.Uutinen;
import harjoitustyo.dokumentit.Vitsi;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Kokoelma-luokka. Tämä luokka pitää sisällään suurimmanosan harjoitustyön logiikasta.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class Kokoelma implements harjoitustyo.apulaiset.Kokoava<harjoitustyo.dokumentit.Dokumentti> {

    // Attribuutti
    private harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit;

    // Rakentaja
    public Kokoelma(){
        dokumentit = new harjoitustyo.omalista.OmaLista<>();
    }

    // Getter
    public harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit(){
        return dokumentit;
    }

    /**
     * Lisää dokumentin listalle.
     * @param uusi viite lisättävään dokumenttiin.
     * @throws IllegalArgumentException jos dokumentti löytyy jo
     */
    @Override
    public void lisää(harjoitustyo.dokumentit.Dokumentti uusi) throws IllegalArgumentException {
        // Jos dokumentit ei sisällä jo dokumenttiä, eikä se ole null, lisätään se dokumentteihin
        if (uusi == null || dokumentit().contains(uusi)) {
            throw new IllegalArgumentException();
        }
        dokumentit().lisää(uusi);
    }

    /**
     * Luo oikean dokumentti olion ja lisää sen kokoelmaan, jos sitä ei ole vielä olemassa
     * @param uusi dokumentti joka halutaan lisätä kokoelmaan
     * @throws IllegalArgumentException jos tunniste löytyy.
     */
    public void lisääDokumenttiKokoelmaan(String uusi) throws IllegalArgumentException {
        if (uusi == null){
            throw new IllegalArgumentException();
        }

        String[] uusiDokumentti = uusi.split("///");
        int uusiTunniste = Integer.parseInt(uusiDokumentti[0]);


        // Käydään kaikki dokumentit läpi ja tarkistetaan ettei tunnistetta löydy jo
        // Jos tunniste löytyy, heitetään virhe.
        for (Dokumentti tarkistaDokkari : dokumentit()){
            if (tarkistaDokkari.tunniste() == uusiTunniste) {
                throw new IllegalArgumentException();
            }
        }
        Dokumentti dokumentti;

        // Jos uusiDokumentti[1] sisältää pisteitä, on se päivämäärä, jolloin sen olio on Uutinen.
        if (uusiDokumentti[1].contains(".")){
            String[] katkaistuPvm = uusiDokumentti[1].split("\\.");

            int paiva = Integer.parseInt(katkaistuPvm[0]);
            int kuukausi = Integer.parseInt(katkaistuPvm[1]);
            int vuosi = Integer.parseInt(katkaistuPvm[2]);

            LocalDate pvm = LocalDate.of(vuosi, kuukausi, paiva);
            dokumentti = new Uutinen(uusiTunniste, pvm, uusiDokumentti[2]);
        } else {
            dokumentti = new Vitsi(uusiTunniste, uusiDokumentti[1], uusiDokumentti[2]);
        }
        // Tarkistetaan että se on oikeaa tyyppiä
        if(tarkistaDokumenttiTyyppi(dokumentti)){
            dokumentit().lisää(dokumentti);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Luo tiedoston sisällöstä dokumentteja ja lisää ne kokoelmaan.
     * @param kokoelma tiedoston nimi
     * @return palauttaa LinkedList<String> jos kokoelman luku onnistui, jos ei palauttaa null
     */
    public LinkedList<String> luoKokoelmaTiedostosta(String kokoelma){
        LinkedList<String> tekstiKokoelma = lueTiedosto(kokoelma);
        if (tekstiKokoelma == null){
            return null;
        }
        for (String uusiKokoelma : tekstiKokoelma){
            lisääDokumenttiKokoelmaan(uusiKokoelma);
        }
        return tekstiKokoelma;
    }

    /**
     * Luo kokoelman tiedoston sisällöstä ja lisää ne kokoelmaan.
     * Tämän avulla voidaan luoda kokoelma uudestaan.
     * @param tekstiKokoelma Tiedoston luettu sisältö
     * @throws IllegalArgumentException Jos parametri on null
     */
    public void luoKokoelma(LinkedList<String> tekstiKokoelma) throws IllegalArgumentException{
        if (tekstiKokoelma == null){
            throw new IllegalArgumentException();
        }
        for (String uusiKokoelma : tekstiKokoelma){
            lisääDokumenttiKokoelmaan(uusiKokoelma);
        }
    }

    /**
     * Tarkistetaan onko dokkari haluttua tyyppiä, eli Uutinen tai Vitsi.
     * Tapauksissa joissa dokumentit lista on tyhjä, palautetaan true.
     * @param dokkari Dokumentti jonka tyyppiä halutaan vertailla
     * @return true jos pitää paikkaansa, false jos eri tyyppiä.
     */
    public boolean tarkistaDokumenttiTyyppi(Dokumentti dokkari){
        if (dokumentit().size() == 0){
            return true;
        }
        if (dokumentit().get(0) instanceof Uutinen && dokkari instanceof Uutinen){
            return true;
        }
        if (dokumentit().get(0) instanceof Vitsi && dokkari instanceof Vitsi){
            return true;
        }
        return false;
    }

    /**
     * Palauttaa halutun dokumentin sen tunnisteen perusteella.
     * @param tunniste haettavan dokumentin tunniste.
     * @return palauttaa haetun dokumentin jos se on olemassa, muussa tapauksessa null
     */
    @Override
    public harjoitustyo.dokumentit.Dokumentti hae(int tunniste) {
        // Käy kaikki dokumentit läpi ja vertailee tunnisteita, palauttaa dokumentin, jos se löytyy, muuten palauttaa
        // null
        for (harjoitustyo.dokumentit.Dokumentti dokkari : dokumentit()){
            if (dokkari.tunniste() == tunniste){
                return dokkari;
            }
        }
        return null;
    }

    /**
     * Poistaa halutun dokumentin kokoelmasta.
     * @param tunniste dokumentin tunniste.
     * @throws IllegalArgumentException Jos dokumenttia ei löydy
     */
    public void poista(int tunniste) throws IllegalArgumentException{
        boolean onkoOlemassa = true;
        for (int i = 0; i < dokumentit().size(); i++) {
            if (dokumentit().get(i).tunniste() == tunniste){
                dokumentit().remove(i);
                onkoOlemassa = false;
            }
        }
        if (onkoOlemassa) throw new IllegalArgumentException();
    }

    /**
     * Hakee kokoelmasta yhden halutun dokumentin kaikki sanojen frekvenssit.
     * @param dokumentinTunniste haluttu dokumentti
     * @return Sanojen frekvenssit, tai null jos dokumenttia ei ole
     */
    public TreeMap<String, Integer> haeSananFrekvenssit(int dokumentinTunniste){
        Dokumentti tulostettava = hae(dokumentinTunniste);
        if (tulostettava == null) {
            return null;
        }
        return tulostettava.laskeFrekvenssit();
    }

    /**
     * Hakee kaikkien kokoelmassa olevien dokumenttia sanojen frekvenssit ja muodostaa niistä yhden
     * TreeMapin.
     * @return kaikki frekvenssit
     */
    public TreeMap<String, Integer> haeKaikkiFrekvenssit(){
        TreeMap<String, Integer> hakemisto = new TreeMap<>();

        for (Dokumentti dokkari: this.dokumentit()){
            TreeMap<String, Integer> freqs = dokkari.laskeFrekvenssit();
            for (String sanat: freqs.keySet()){
                if (!hakemisto.containsKey(sanat)){
                    hakemisto.put(sanat, freqs.get(sanat));
                } else {
                    hakemisto.replace(sanat, hakemisto.get(sanat) + freqs.get(sanat));
                }
            }
        }
        return hakemisto;
    }

    /**
     * Luo oikean tyyppisen Comparator<Dokumentti> variablen, joka annetaan OmaListan lajittele methodille.
     * @param laji Millä tavalla halutaan järjestää kokoelma, joko id, date tai type.
     * @throws IllegalArgumentException Jos käyttäjä antaa väärän lajin,
     * tai jos OmaListan elementit ovat väärää tyyppiä
     *
     */
    public void mitenLajitellaanKokoelma(String laji) throws IllegalArgumentException{
        Comparator<Dokumentti> vertailija;
        try {
            switch (laji) {
                case "id":
                    // Otetaan suoraan Dokumentin tunniste ja luodaan siitä Comparator
                    vertailija = Comparator.comparing(Dokumentti::tunniste);
                    break;
                case "date":
                    // Castataan Dokumentti Uutiseksi, että saamme sen päivämäärän
                    vertailija = Comparator.comparing(dokumentti -> ((Uutinen) dokumentti).päivämäärä());
                    break;
                case "type":
                    // Castataan Dokumentti Vitsiksi, että saamme sen lajin
                    vertailija = Comparator.comparing(dokumentti -> ((Vitsi) dokumentti).laji());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            dokumentit.lajittele(vertailija);
        } catch (ClassCastException e){
            throw new IllegalArgumentException();
        }
    }

    /**
     * Lukee halutun tiedoston sisällön käyttäen Utilityn lueTiedosto.
     * @param tiedosto tiedoston sijainti.
     * @return palauttaa LinkedListin, joka sisältää tiedoston sisällön, tai null jos tapahtuu virhe
     */
    public LinkedList<String> lueTiedosto(String tiedosto){
        Utility utility = new Utility();
        return utility.lueTiedosto(tiedosto);
    }

}