package allocator;

import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

/**
 * Created by filip on 24/05/2017.
 */
public class Allocator {
    private List<ToAllocate> toAllocateList;
    private List<ToAllocate> availableList;
    private List<Requirement> outsideData = new ArrayList<>();


    public Allocator(List<ToAllocate> toAllocateList, List<ToAllocate> availableList) {
        this.toAllocateList = toAllocateList;
        this.availableList = availableList;
    }

    public List<ToAllocate> allocate(){

        for (ToAllocate toAllocate : toAllocateList){
            ToAllocate available = verifyAvailable(toAllocate);
            if (available==null){
                toAllocate.setAnswer("not found");
                continue;
            }
            toAllocate.setAnswer(available.getAnswer());
            for (Requirement toAllocateReq : toAllocate.getRequirements()){
                if (toAllocateReq.needsSaving()==-1){
                    toAllocateReq.setAnswer(toAllocate.getAnswer());
                    outsideData.add(toAllocateReq);
                }
                else if(toAllocateReq.needsSaving()==1){
                    available.addRequirement(toAllocateReq);
                }
            }

            }
        return toAllocateList;
    }

    private ToAllocate verifyAvailable(ToAllocate toAllocate){
         for (ToAllocate available : availableList){
             boolean found = true;
             for(Requirement requirement : toAllocate.getRequirements()){
                 Requirement out = verifyOutside(requirement);
                 if (out!=null){
                     return getAvailableFromAnswer(out.answer());
                 }
                 else if (!verifyRequeriment(requirement, available))
                    found = false;
            }
            if (found)
                return available;
        }
        return null;
    }

    private boolean verifyRequeriment(Requirement requirement, ToAllocate available){

        for (Requirement availableReq : available.getRequirements()){
            if(requirement.verify(availableReq)<0){
                return false;
            }
        }
        return true;
    }

    private Requirement verifyOutside(Requirement requirement){
        if(requirement.needsSaving()==-1)
            for (Requirement out : outsideData)
                if (requirement.verify(out)==-1 && out.answer()!=null)
                    return out;
        return null;
    }

    private ToAllocate getAvailableFromAnswer(String answer){
        for (ToAllocate available : availableList)
            if (available.getAnswer().equals(answer))
                return available;
        return null;
    }

}


