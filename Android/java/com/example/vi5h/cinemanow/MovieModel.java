package com.example.vi5h.cinemanow;

public class MovieModel
{
    int id;
    String name;
    String plot;
    String duration;

    public MovieModel()
    {

    }

    public int getId() {
        return id;
    }

    public String getName() {return name;}

    public String getPlot() {
        return plot;
    }

    public String getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
