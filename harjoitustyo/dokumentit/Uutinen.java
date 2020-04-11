package harjoitustyo.dokumentit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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