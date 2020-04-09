package harjoitustyo.dokumentit;

import harjoitustyo.apulaiset.Tietoinen;

import java.util.LinkedList;

public abstract class Dokumentti<T> implements Comparable<Dokumentti>, Tietoinen<Dokumentti> {

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

    @Override
    public void siivoa(LinkedList<String> sulkusanat, String välimerkit) throws IllegalArgumentException {

    }
}