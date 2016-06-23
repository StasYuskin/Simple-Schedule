package ua.pp.danielyuzek.simple_schedule.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Week implements Serializable {

    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("week")
    @Expose
    private Integer week;
    @SerializedName("days")
    @Expose
    private List<Day> days = new ArrayList<Day>();

    /**
     * @return The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return The week
     */
    public Integer getWeek() {
        return week;
    }

    /**
     * @param week The week
     */
    public void setWeek(Integer week) {
        this.week = week;
    }

    /**
     * @return The days
     */
    public List<Day> getDays() {
        return days;
    }

    /**
     * @param days The days
     */
    public void setDays(List<Day> days) {
        this.days = days;
    }


}