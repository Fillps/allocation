package tcp_trb_final.allocator;

import java.util.ArrayList;
import java.util.List;

public class ToAllocate {
    private String id;

    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
