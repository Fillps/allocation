package tcp_trb_final.room_allocations;

import tcp_trb_final.allocator.Requirement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by filip on 24/06/2017.
 */
class FeaturesTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verify() {
        Requirement features1 = new Features("1");
        Requirement features2 = new Features("2");

        assertTrue(features1.verify(features1)==1);
        System.out.println(features1.verify(features2));
        assertTrue(features1.verify(features2)==0);
    }

}