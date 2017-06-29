package tcp_trb_final.room_allocations;

import tcp_trb_final.allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class Teacher implements Requirement {


    private String name;
    private StartDate startDate;
    private String roomId;

    public Teacher(String name, StartDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    @Override
    public int verify(Requirement o) {
        if (!(o instanceof Teacher))
            return 0;
        Teacher n = (Teacher) o;
        if (name.equals(n.getName()) && startDate.verify(n.getStartDate())==-1){
            return -1;
        }
        else
            return  1;
    }

    @Override
    public int needsSaving() {
        return -1;
    }

    @Override
    public String answer() {
        return roomId;
    }

    @Override
    public boolean isExclusive() {
        return true;
    }

    @Override
    public boolean setAnswer(String answer) {
        this.roomId = answer;
        return true;
    }

    public String getName() {
        return name;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (!name.equals(teacher.name)) return false;
        return startDate.equals(teacher.startDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startDate.hashCode();
        return result;
    }
}
