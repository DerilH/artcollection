package org.main.artcollection.models;

public class ArtPiece {
    private String title;
    private String artist;
    private int year;
    private String description;
    private String path;

    public ArtPiece(String title, String artist, int year, String description, String path) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.description = description;
        this.path = path;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }
    public String getPath() {
        return this.path;
    }

    // Setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title + "," + artist + "," + year + "," + description + "," + this.path;
    }

    public static ArtPiece fromString(String str) {
        String[] parts = str.split(",");
        return new ArtPiece(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]);
    }
}
