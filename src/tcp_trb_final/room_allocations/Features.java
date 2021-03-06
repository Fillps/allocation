package tcp_trb_final.room_allocations;

import tcp_trb_final.allocator.Requirement;

/**
 * Created by filip on 24/05/2017.
 */
public class Features implements Requirement {

    private String id;

    public Features(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    @Override
    public int verify(Requirement o) {
        if (!(o instanceof Features))
            return 0;
        Features n = (Features) o;
        if (id.equals(n.getId())){
            return 1;
        }
        else
            return 30;
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
        return false;
    }

    @Override
    public boolean setAnswer(String answer) {
        return false;
    }
}
