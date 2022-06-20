package com.wiley.c242.connorhs.Model.DTO;

public class DVD
{
    private String title;
    private int releaseDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private String userNote;

    public DVD()
    {

    }

    public DVD(String title, String release, String mpaaRating, String director, String studio, String userNote) {
        this.title = title;
        setStringReleaseDate(release);
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.studio = studio;
        this.userNote = userNote;
    }

    public String ToString()
    {
        return title + ", " + getStringReleaseDate() + ", " + mpaaRating + ", " + director + ", " + studio + ", " + userNote;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseDate() { return releaseDate; }
    public void setReleaseDate(int releaseDate) { this.releaseDate = releaseDate; }
    public String getStringReleaseDate()
    {
        int day = this.releaseDate % 100;
        int month = (this.releaseDate/ 100) % 100;
        int year = this.releaseDate / 10000;
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }
    public void setStringReleaseDate(String releaseDate)
    {
        String[] ymd = releaseDate.split("-");
        this.releaseDate = Integer.parseInt(ymd[0] + ymd[1] + ymd[2]);
    }

    public String getMpaaRating() { return mpaaRating; }
    public void setMpaaRating(String mpaaRating) { this.mpaaRating = mpaaRating; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getStudio() { return studio; }
    public void setStudio(String studio) { this.studio = studio; }

    public String getUserNote() { return userNote; }
    public void setUserNote(String userNote) { this.userNote = userNote; }
}
