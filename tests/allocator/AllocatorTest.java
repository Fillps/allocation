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
    Allocator allocator;

    @BeforeEach
    void setUp() {
        allocator = new Allocator();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void verifyRequirementTest() {
        ToAllocate available = new ToAllocate();
        available.addRequirement(new Features("9"));
        available.addRequirement(new NumberOfPlaces(40));

        Requirement requirement = new Features("2");

        assertTrue(allocator.verifyRequeriment(requirement, available)==-1);


    }

}