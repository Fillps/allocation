package room_allocations;

import allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class NumberOfPlaces implements Requirement {
    private int seats;

    public int getSeats() {
        return seats;
    }

    @Override
    public int compare(Object o) {
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
}
