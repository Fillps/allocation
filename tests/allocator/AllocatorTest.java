package allocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import room_allocations.Features;
import room_allocations.NumberOfPlaces;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by filip on 21/06/2017.
 */
class AllocatorTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verifyRequirementTest() {
        ToAllocate toAllocate = new ToAllocate();
        toAllocate.addRequirement(new Features("2"));
        toAllocate.addRequirement(new NumberOfPlaces(20));


    }

}