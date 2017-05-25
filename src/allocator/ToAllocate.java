package allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToAllocate {
    private String id;
    private List<Requirement> requirements = new ArrayList<>();

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void addRequirement(Requirement requirement) { requirements.add(requirement); }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
