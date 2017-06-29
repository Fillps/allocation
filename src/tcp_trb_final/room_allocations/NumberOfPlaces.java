package tcp_trb_final.room_allocations;

import tcp_trb_final.allocator.Requirement;

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
        if (seats<= n.getSeats()){
            score = n.getSeats() - seats + 1;
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
    public boolean isExclusive() {
        return true;
    }

    @Override
    public boolean setAnswer(String answer) {
        return false;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
