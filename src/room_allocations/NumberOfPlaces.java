package room_allocations;

import allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class NumberOfPlaces implements Requirement {
    private int seats;

    public NumberOfPlaces(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public int verify(Requirement o) {
        if (!(o instanceof NumberOfPlaces))
            return 0;
        int score;
        NumberOfPlaces n = (NumberOfPlaces) o;
        if (seats>= n.getSeats()){
            score = 100*n.getSeats()/seats;
        }
        else
            score = -1;
        return score;
    }

    @Override
    public int needsSaving() {
        return 0;
    }

    @Override
    public String answer() {
        return null;
    }

    @Override
    public boolean setAnswer(String answer) {
        return false;
    }
}
