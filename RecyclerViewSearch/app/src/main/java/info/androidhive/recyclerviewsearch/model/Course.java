package info.androidhive.recyclerviewsearch.model;

import java.util.ArrayList;

public class Course
{
    private ArrayList<Subjects> Subjects;

    private String image;

    private String CourseType;

    public ArrayList<Subjects> getSubjects ()
    {
        return Subjects;
    }

    public void setSubjects (ArrayList<Subjects> Subjects)
    {
        this.Subjects = Subjects;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getCourseType ()
    {
        return CourseType;
    }

    public void setCourseType (String CourseType)
    {
        this.CourseType = CourseType;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Subjects=" + Subjects +
                ", image='" + image + '\'' +
                ", CourseType='" + CourseType + '\'' +
                '}';
    }
}
