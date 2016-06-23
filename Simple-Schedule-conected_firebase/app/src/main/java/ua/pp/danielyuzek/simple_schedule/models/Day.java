package ua.pp.danielyuzek.simple_schedule.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day implements Serializable {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lessons")
    @Expose
    private List<Lesson> lessons = new ArrayList<Lesson>();

    /**
     * @return The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number The day_number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The day_name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The lessons
     */
    public List<Lesson> getLessons() {
        return lessons;
    }

    /**
     * @param lessons The lessons
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}