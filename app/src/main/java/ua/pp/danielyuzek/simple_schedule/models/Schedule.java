package ua.pp.danielyuzek.simple_schedule.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule implements Serializable {

    @SerializedName("weeks")
    @Expose
    private List<Week> weeks = new ArrayList<Week>();

    /**
     * @return The weeks
     */
    public List<Week> getWeeks() {
        return weeks;
    }

    /**
     * @param weeks The weeks
     */
    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

}