package room_allocations;

import allocator.Requirement;

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
    public int compare(Object o) {
        if (!(o instanceof Features))
            return 0;
        Features n = (Features) o;
        if (id.equals(n.getId())){
            return 1;
        }
        else
            return  -1;
    }
}
