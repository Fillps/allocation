package allocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import room_allocations.Features;
import room_allocations.NumberOfPlaces;
import room_allocations.StartDate;
import room_allocations.Teacher;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by filip on 21/06/2017.
 */
class AllocatorTest {


    @Test
    void verifyRequirementTest() {
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());
        ToAllocate available = new ToAllocate();
        available.addRequirement(new Features("9"));
        available.addRequirement(new NumberOfPlaces(40));

        Requirement requirement = new Features("2");

        assertTrue(allocator.verifyRequeriment(requirement, available)==-1);
    }

    @Test
    void noSpaceAllocateTest(){
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());

        ToAllocate toAllocate = new ToAllocate();
        StartDate startDate = new StartDate("1", "08:30");
        toAllocate.addRequirement(startDate);
        toAllocate.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate.addRequirement(new NumberOfPlaces(40));
        allocator.toAllocateList.add(toAllocate);

        ToAllocate available = new ToAllocate();
        available.setAnswer("PREDIO_1#SALA_1");
        available.setId("PREDIO_1#SALA_1");
        available.addRequirement(new NumberOfPlaces(39));
        allocator.availableList.add(available);


        allocator.allocate();
        ToAllocate result = allocator.toAllocateList.get(0);
        assertEquals(result.getAnswer(), "not found#not found");
    }

    @Test
    void featureAllocateTest(){
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());

        ToAllocate toAllocate = new ToAllocate();
        StartDate startDate = new StartDate("1", "08:30");
        toAllocate.addRequirement(startDate);
        toAllocate.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate.addRequirement(new NumberOfPlaces(50));
        toAllocate.addRequirement(new Features("FEATURE"));
        toAllocate.addRequirement(new Features("ANOTHER FEATURE"));
        allocator.toAllocateList.add(toAllocate);

        ToAllocate available1 = new ToAllocate();
        available1.setAnswer("PREDIO_1#SALA_1");
        available1.setId("PREDIO_1#SALA_1");
        available1.addRequirement(new NumberOfPlaces(70));
        available1.addRequirement(new Features("FEATURE"));
        allocator.availableList.add(available1);

        ToAllocate available2 = new ToAllocate();
        available2.setAnswer("PREDIO_2#SALA_2");
        available2.setId("PREDIO_2#SALA_2");
        available2.addRequirement(new NumberOfPlaces(50));
        allocator.availableList.add(available2);

        ToAllocate available3 = new ToAllocate();
        available3.setAnswer("PREDIO_3#SALA_3");
        available3.setId("PREDIO_3#SALA_3");
        available3.addRequirement(new NumberOfPlaces(70));
        available3.addRequirement(new Features(" ANOTHER FEATURE"));
        allocator.availableList.add(available3);

        ToAllocate available4 = new ToAllocate();
        available4.setAnswer("PREDIO_4#SALA_4");
        available4.setId("PREDIO_4#SALA_4");
        available4.addRequirement(new NumberOfPlaces(100));
        available4.addRequirement(new Features("FEATURE"));
        available4.addRequirement(new Features("ANOTHER FEATURE"));
        allocator.availableList.add(available4);

        allocator.allocate();
        ToAllocate result = allocator.toAllocateList.get(0);
        assertEquals(available4.getAnswer(), result.getAnswer());
    }

    @Test
    void dateAllocateTest() {
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());

        ToAllocate toAllocate = new ToAllocate();
        StartDate startDate = new StartDate("1", "08:30");
        toAllocate.addRequirement(startDate);
        toAllocate.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate.addRequirement(new NumberOfPlaces(20));
        allocator.toAllocateList.add(toAllocate);

        ToAllocate available1 = new ToAllocate();
        available1.setAnswer("PREDIO_1#SALA_1");
        available1.setId("PREDIO_1#SALA_1");
        available1.addRequirement(new NumberOfPlaces(20));
        available1.addRequirement(new StartDate("1", "08:30"));
        allocator.availableList.add(available1);

        ToAllocate available2 = new ToAllocate();
        available2.setAnswer("PREDIO_2#SALA_2");
        available2.setId("PREDIO_2#SALA_2");
        available2.addRequirement(new NumberOfPlaces(40));
        allocator.availableList.add(available2);

        allocator.allocate();
        ToAllocate result = allocator.toAllocateList.get(0);
        assertEquals(available2.getAnswer(), result.getAnswer());
    }


    @Test
    void generalAllocateTest(){
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());

        ToAllocate toAllocate = new ToAllocate();
        StartDate startDate = new StartDate("1", "10:30");
        toAllocate.addRequirement(startDate);
        toAllocate.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate.addRequirement(new NumberOfPlaces(43));
        toAllocate.addRequirement(new Features("FEATURE"));
        allocator.toAllocateList.add(toAllocate);

        ToAllocate available1 = new ToAllocate();
        available1.setAnswer("PREDIO_1#SALA_1");
        available1.setId("PREDIO_1#SALA_1");
        available1.addRequirement(new NumberOfPlaces(50));
        available1.addRequirement(new Features("FEATURE"));
        allocator.availableList.add(available1);

        ToAllocate available2 = new ToAllocate();
        available2.setAnswer("PREDIO_2#SALA_2");
        available2.setId("PREDIO_2#SALA_2");
        available2.addRequirement(new NumberOfPlaces(45));
        available2.addRequirement(new StartDate("1", "10:30"));
        available2.addRequirement(new Features("FEATURE"));
        allocator.availableList.add(available2);

        ToAllocate available3 = new ToAllocate();
        available3.setAnswer("PREDIO_3#SALA_3");
        available3.setId("PREDIO_3#SALA_3");
        available3.addRequirement(new NumberOfPlaces(40));
        available3.addRequirement(new Features("FEATURE"));
        allocator.availableList.add(available3);

        ToAllocate available4 = new ToAllocate();
        available4.setAnswer("PREDIO_4#SALA_4");
        available4.setId("PREDIO_4#SALA_4");
        available4.addRequirement(new NumberOfPlaces(43));
        allocator.availableList.add(available4);


        allocator.allocate();
        ToAllocate result = allocator.toAllocateList.get(0);
        assertEquals(available1.getAnswer(), result.getAnswer());
    }

    @Test
    void globalRequirementAllocateTest(){
        Allocator allocator = new Allocator(new ArrayList<ToAllocate>(), new ArrayList<ToAllocate>());

        ToAllocate toAllocate1 = new ToAllocate();
        StartDate startDate = new StartDate("1", "10:30");
        toAllocate1.addRequirement(startDate);
        toAllocate1.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate1.addRequirement(new NumberOfPlaces(50));
        allocator.toAllocateList.add(toAllocate1);

        ToAllocate toAllocate2 = new ToAllocate();
        toAllocate2.addRequirement(startDate);
        toAllocate2.addRequirement(new Teacher("THE PROFESSOR", startDate));
        toAllocate2.addRequirement(new NumberOfPlaces(45));
        allocator.toAllocateList.add(toAllocate2);

        ToAllocate available1 = new ToAllocate();
        available1.setAnswer("PREDIO_1#SALA_1");
        available1.setId("PREDIO_1#SALA_1");
        available1.addRequirement(new NumberOfPlaces(50));
        allocator.availableList.add(available1);

        ToAllocate available2 = new ToAllocate();
        available2.setAnswer("PREDIO_2#SALA_2");
        available2.setId("PREDIO_2#SALA_2");
        available2.addRequirement(new NumberOfPlaces(45));
        allocator.availableList.add(available2);

        allocator.allocate();
        ToAllocate result1 = allocator.toAllocateList.get(0);
        ToAllocate result2 = allocator.toAllocateList.get(0);

        assertEquals(result1.getAnswer(), result2.getAnswer());

    }

}