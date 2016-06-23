package ua.pp.danielyuzek.simple_schedule.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Week getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        return getWeek(calendar.get(Calendar.WEEK_OF_YEAR));
    }

    public Week getWeek(int weekNumber) {
        int weekPair = (weekNumber % 2 == 0) ? 2 : 1;

        if (weeks != null) {
            for (Week week : weeks) {
                if (week.getWeek().equals(weekPair)) {
                    return week;
                }
            }
        }

        return null;
    }




}