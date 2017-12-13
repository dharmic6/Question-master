package info.androidhive.recyclerviewsearch.model;

import java.util.ArrayList;

/**
 * Created by ksk648 on 11/25/17.
 */


public class Courses{

    private ArrayList<Course> courses;



    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courses=" + courses +
                '}';
    }
}


