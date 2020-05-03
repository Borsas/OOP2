package harjoitustyo.dokumentit;

/**
 * Vitsi-luokka. Pitää sisällään Vitsi olioon tarvittavat tiedot. Periytyy Dokumentti-luokasta.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class Vitsi extends Dokumentti {

    // Attribuutit
    private String laji;

    // Rakentaja
    public Vitsi(int tunniste,  String uusiLaji, String teksti) throws IllegalArgumentException{
        super(tunniste, teksti);
        laji(uusiLaji);
    }

    // Laji getteri ja setteri
    public void laji(String uusiLaji) throws IllegalArgumentException{
        if (uusiLaji != null && uusiLaji.length() > 0){
            laji = uusiLaji;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public String laji(){
        return laji;
    }

    // Overridetään toString
    @Override
    public String toString(){
        return tunniste() + "///" + laji() + "///" + teksti();
    }
}