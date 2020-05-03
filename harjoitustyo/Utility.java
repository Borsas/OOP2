package harjoitustyo;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Utility-luokka. Utility-luokka luo LinkedList<String> olion lukemalla käyttäjän antaman tiedoston sisällön.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet II, kevät 2020
 * <p>
 * @author Oskari Kuikka 430988 (oskari.kuikka@tuni.fi)
 */

public class Utility {

    /**
     * Lukee halutun tiedoston sisällän ja asettaa sen LinkedListiin.
     * @param tiedosto tiedoston sijainti.
     * @return palauttaa LinkedListin, joka sisältää tiedoston sisällön
     */
    public LinkedList<String> lueTiedosto(String tiedosto){
        if (tiedosto == null) {
            return null;
        }

        File file = new File(tiedosto);
        LinkedList<String> fileContents = new LinkedList<>();

        if (!file.exists()){
            return null;
        }

        try {
            Scanner fileRead = new Scanner(file);
            while(fileRead.hasNextLine()){
                fileContents.add(fileRead.nextLine());
            }
        }catch (Exception e){
            return null;
        }

        return fileContents;
    }
}
