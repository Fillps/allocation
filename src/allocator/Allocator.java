package allocator;

import java.util.*;

/**
 * Created by filip on 24/05/2017.
 */
public class Allocator {
    protected List<ToAllocate> toAllocateList;
    protected List<ToAllocate> availableList;
    private List<Requirement> globalRequirements = new ArrayList<>();


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
                if (toAllocateReq.needsSaving()==-1){ // -1 significa que precisa salvar nos requerimentos globais
                    toAllocateReq.setAnswer(toAllocate.getAnswer()); // coloca a resposta no requerimento a ser salvo
                    globalRequirements.add(toAllocateReq);
                }
                else if(toAllocateReq.needsSaving()==1){ // 1 significa que precisa atualizar o objeto que foi alocado (reservar o horario da sala)
                    available.addRequirement(toAllocateReq);  
                }
            }
        }
        return toAllocateList;
    }

    /***
     *
     * @param toAllocate -> um objeto a ser alocado
     * @return available -> retorna um objeto que satisfaz todos os requerimentos
     */
    private ToAllocate verifyAvailable(ToAllocate toAllocate){
         for (ToAllocate available : availableList){
             boolean found = true;
             for(Requirement requirement : toAllocate.getRequirements()){
                 Requirement out = verifyGlobalRequirements(requirement); //verifica se o requerimento existe nos requerimentos globais
                 if (out!=null){                                          // se ele existe, entao ja temos a resposta, que esta contida nesse requerimento
                     return getAvailableFromAnswer(out.answer());
                 }
                 else if (!verifyRequeriment(requirement, available))     // se ele nao existe, continua testando os outros requerimentos
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

    private Requirement verifyGlobalRequirements(Requirement requirement){
        if(requirement.needsSaving()==-1)
            for (Requirement out : globalRequirements)
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


