package ua.pp.danielyuzek.simple_schedule.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lesson implements Serializable {

    @SerializedName("ordering")
    @Expose
    private Integer ordering;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("teacher")
    @Expose
    private String teacher;

    /**
     * @return The ordering
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * @param ordering The ordering
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place The place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}