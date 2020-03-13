package harjoitustyo.dokumentit;

public abstract class Dokumentti implements Comparable<Dokumentti>{

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

    public boolean equals(Dokumentti o){
        if (o.tunniste() == this.tunniste()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return tunniste() + "///" + teksti();
    }

    
}