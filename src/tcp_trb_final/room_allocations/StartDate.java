package tcp_trb_final.room_allocations;

import tcp_trb_final.allocator.Requirement;

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
            return  0;
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
    public boolean isExclusive() {
        return true;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartDate startDate = (StartDate) o;

        if (!day.equals(startDate.day)) return false;
        return hour.equals(startDate.hour);
    }

    @Override
    public int hashCode() {
        int result = day.hashCode();
        result = 31 * result + hour.hashCode();
        return result;
    }
}
