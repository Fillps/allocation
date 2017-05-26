package room_allocations;

import allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class StartDate implements Requirement {
    private String day;
    private String hour;

    public StartDate(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }


    @Override
    public int verify(Requirement o) {
        if (!(o instanceof StartDate))
            return 0;
        StartDate n = (StartDate) o;
        if (day.equals(n.getDay()) && hour.equals(n.getHour())){
            return -1;
        }
        else
            return  1;
    }

    @Override
    public int needsSaving() {
        return 1;
    }

    @Override
    public String answer() {
        return null;
    }

    @Override
    public boolean setAnswer(String answer) {
        return false;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }
}
