package room_allocations;

import allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class Teacher implements Requirement{


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

}
