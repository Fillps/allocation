package room_allocations;

import allocator.Requirement;

/**
 * Created by filip on 24/06/2017.
 */
public class RequiresRoom implements Requirement {
    String room_id;

    public RequiresRoom(String room_id) {
        this.room_id = room_id;
    }

    @Override
    public int verify(Requirement o) {
        if (o instanceof RequiresRoom)
            return 0;
        return -1;
    }

    @Override
    public int needsSaving() {
        return 0;
    }

    @Override
    public String answer() {
        return room_id;
    }

    @Override
    public boolean isExclusive() {
        return true;
    }

    @Override
    public boolean setAnswer(String answer) {
        room_id = answer;
        return true;
    }
}
