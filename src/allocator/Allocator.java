package allocator;

import java.util.*;

/**
 * Created by filip on 24/05/2017.
 */
public class Allocator {
    private List<ToAllocate> toAllocateList;
    private List<Available> availableList;

    public static class Available extends ToAllocate{
        private Set<String> availableHours = new HashSet();

        public boolean addHour(String value) { return availableHours.add(value);}
    }


    public static class ToAllocate {
        private String id;
        private Map<String, String> requirements = new HashMap<>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Map<String, String> getRequirements() {
            return requirements;
        }

        public void addRequirement(String type, String value) {
            requirements.put(type, value);
        }
    }

    public static class Requirement {
        private String type;
        private String value;

        public Requirement(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}






