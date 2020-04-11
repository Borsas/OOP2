package harjoitustyo.omalista;

import harjoitustyo.apulaiset.Ooperoiva;
import java.util.LinkedList;

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E>{

    // Toteutetaan pakollinen method void lisää Ooperoiva luokasta
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
                    this.addLast( uusi);
                    break;
                }

            }
        }
    }
}