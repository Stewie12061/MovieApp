package com.example.movie_app.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Movie {
    private String Fuid;
    private String Fthumb;
    private String Ftrailer;
    private String Fname;
    private String Fsynopsis;
    private String FvideoTrailer;
    private String Ffavorite;
    private int Fmmpa;
    private int Fyear;
    private int Fseason;
    private ArrayList<String> Fcategory;
    private List<Episode> Eepisode;
    private List<CastCrew> Ccast;

    public Movie() {
    }

    public Movie(String fuid, String fthumb, String ftrailer, String fname, String fsynopsis, String fvideoTrailer, int fmmpa, int fyear, int fseason, ArrayList<String> fcategory) {
        Fuid = fuid;
        Fthumb = fthumb;
        Ftrailer = ftrailer;
        Fname = fname;
        Fsynopsis = fsynopsis;
        FvideoTrailer = fvideoTrailer;
        Fmmpa = fmmpa;
        Fyear = fyear;
        Fseason = fseason;
        Fcategory = fcategory;
    }

    public Movie(String fuid, String fthumb, String ftrailer, String fname, String fsynopsis, String fvideoTrailer, int fmmpa, int fyear, int fseason, ArrayList<String> fcategory, List<Episode> eepisode, List<CastCrew> ccast) {
        Fuid = fuid;
        Fthumb = fthumb;
        Ftrailer = ftrailer;
        Fname = fname;
        Fsynopsis = fsynopsis;
        FvideoTrailer = fvideoTrailer;
        Fmmpa = fmmpa;
        Fyear = fyear;
        Fseason = fseason;
        Fcategory = fcategory;
        Eepisode = eepisode;
        Ccast = ccast;
    }

    public String toStringIn4(){
        return Fyear + " " + Fmmpa + " Season " + Fseason;
    }

    public String toStringSeasonEpisode(){
        return "S"+ Fseason + " - " + Fname;
    }

    public String toStringCategory(){
        String res = "";
        for(int i = 0;i < Fcategory.size() && i < 3; ++i){
            res+= Fcategory.get(i) +" ";

        }
        return res;
    }

    @Override
    public String toString() {
        return "Movie2{" +
                "Fuid='" + Fuid + '\'' +
                ", Fthumb='" + Fthumb + '\'' +
                ", Ftrailer='" + Ftrailer + '\'' +
                ", Fname='" + Fname + '\'' +
                ", Fsynopsis='" + Fsynopsis + '\'' +
                ", FvideoTrailer='" + FvideoTrailer + '\'' +
                ", Fmmpa=" + Fmmpa +
                ", Fyear=" + Fyear +
                ", Fseason=" + Fseason +
                '}';
    }

    public String getFuid() {
        return Fuid;
    }

    public void setFuid(String fuid) {
        Fuid = fuid;
    }

    public String getFthumb() {
        return Fthumb;
    }

    public void setFthumb(String fthumb) {
        Fthumb = fthumb;
    }

    public String getFtrailer() {
        return Ftrailer;
    }

    public void setFtrailer(String ftrailer) {
        Ftrailer = ftrailer;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getFsynopsis() {
        return Fsynopsis;
    }

    public void setFsynopsis(String fsynopsis) {
        Fsynopsis = fsynopsis;
    }

    public String getFvideoTrailer() {
        return FvideoTrailer;
    }

    public void setFvideoTrailer(String fvideoTrailer) {
        FvideoTrailer = fvideoTrailer;
    }

    public int getFmmpa() {
        return Fmmpa;
    }

    public void setFmmpa(int fmmpa) {
        Fmmpa = fmmpa;
    }

    public int getFyear() {
        return Fyear;
    }

    public void setFyear(int fyear) {
        Fyear = fyear;
    }

    public int getFseason() {
        return Fseason;
    }

    public void setFseason(int fseason) {
        Fseason = fseason;
    }

    public ArrayList<String> getFcategory() {
        return Fcategory;
    }

    public void setFcategory(ArrayList<String> fcategory) {
        Fcategory = fcategory;
    }

    public List<Episode> getEepisode() {
        return Eepisode;
    }

    public void setEepisode(List<Episode> eepisode) {
        Eepisode = eepisode;
    }

    public List<CastCrew> getCcast() {
        return Ccast;
    }

    public void setCcast(List<CastCrew> ccast) {
        Ccast = ccast;
    }

    public String getFfavorite() {
        return Ffavorite;
    }

    public void setFfavorite(String ffavorite) {
        Ffavorite = ffavorite;
    }
}
