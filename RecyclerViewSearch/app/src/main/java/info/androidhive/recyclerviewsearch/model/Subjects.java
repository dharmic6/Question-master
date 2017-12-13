package info.androidhive.recyclerviewsearch.model;

import java.util.ArrayList;

public class Subjects
{
    private String name;

    private String image;

    private ArrayList<Chapters> Chapters;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public ArrayList<Chapters> getChapters ()
    {
        return Chapters;
    }

    public void setChapters (ArrayList<Chapters> Chapters)
    {
        this.Chapters = Chapters;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", Chapters=" + Chapters +
                '}';
    }
}
