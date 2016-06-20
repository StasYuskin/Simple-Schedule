package ua.pp.danielyuzek.simple_schedule.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Group implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return The schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * @param schedule The schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}