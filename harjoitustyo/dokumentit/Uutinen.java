package harjoitustyo.dokumentit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Uutinen-luokka. Pitää sisällään Uutinen olioon tarvittavat tiedot. Periytyy Dokumentti-luokasta.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class Uutinen extends Dokumentti{
    
    // Attribuutti
    private LocalDate päivämäärä;

    // Rakentajat
    public Uutinen(int tunniste, LocalDate pvm, String teksti) throws IllegalArgumentException{
        super(tunniste, teksti);
        päivämäärä(pvm);
    }

    // Päivämäärä getteri ja setteri
    public void päivämäärä(LocalDate uusiPvm) throws IllegalArgumentException{
        if (uusiPvm != null){
            päivämäärä = uusiPvm;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public LocalDate päivämäärä(){

        return päivämäärä;
    }

    // Overridetään toString
    @Override
    public String toString(){
        DateTimeFormatter form = DateTimeFormatter.ofPattern("d.M.YYYY");
        return tunniste() + "///" + form.format(päivämäärä()) + "///" + teksti();
    }
}