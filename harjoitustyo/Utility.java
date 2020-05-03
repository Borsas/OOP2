package harjoitustyo;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Utility {

    public LinkedList<String> lueTiedosto(String fileName){
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
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
