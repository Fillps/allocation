package allocator;

import java.util.*;

/**
 * Created by filip on 24/05/2017.
 */
public class Allocator {
    protected List<ToAllocate> toAllocateList; //"saida", só falta inserir as salas
    protected List<ToAllocate> availableList;
    private List<Requirement> globalRequirements = new ArrayList<>(); //professor e turma já estão alocados


    public Allocator(List<ToAllocate> toAllocateList, List<ToAllocate> availableList) {
        this.toAllocateList = toAllocateList;
        this.availableList = availableList;
    }

    public List<ToAllocate> allocate(){

        for (ToAllocate toAllocate : toAllocateList){
            ToAllocate available = verifyAvailable(toAllocate);
            if (available==null){
                toAllocate.setAnswer("not found#not found");
                continue;
            }
            toAllocate.setAnswer(available.getAnswer());
            for (Requirement toAllocateReq : toAllocate.getRequirements()){ //V caso professor de a mesma aula em duas turmas
                if (toAllocateReq.needsSaving()==-1){ // -1 significa que precisa salvar nos requerimentos globais
                    toAllocateReq.setAnswer(toAllocate.getAnswer()); // coloca a resposta no requerimento a ser salvo
                    globalRequirements.add(toAllocateReq); //não soma o numero total de alunos da 2 turmas
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
     * @param toAllocate -> um objeto a ser alocado (do toAllocateList)
     * @return available -> retorna um objeto que satisfaz todos os requerimentos (da availableList)
     */
    private ToAllocate verifyAvailable(ToAllocate toAllocate){
        int best_score = 0;
        ToAllocate best_fit = null;
        for (ToAllocate available : availableList){
             int current_score = 0;
             for(Requirement requirement : toAllocate.getRequirements()){
                 Requirement out = verifyGlobalRequirements(requirement); //verifica se o requerimento existe nos requerimentos globais
                 if (out!=null)                                          // se ele existe, entao ja temos a resposta, que esta contida nesse requerimento
                     return getAvailableFromAnswer(out.answer());
                 else if(verifyRequeriment(requirement, available)<0){
                     current_score = -1;
                     break;
                 }
                 current_score += verifyRequeriment(requirement, available);
             }
             if (current_score > best_score){
                 best_score = current_score;
                 best_fit = available;
             }
        }
        return best_fit;
    }

    private int verifyRequeriment(Requirement requirement, ToAllocate available){ //compara um requirement de toAllocate com todos os requiments de available

        int score = 0;
        for (Requirement availableReq : available.getRequirements()){
            int ver = requirement.verify(availableReq);
            if (requirement.isExclusive() && ver < 0)
                return -1;
            score += ver;
        }
        if (!requirement.isExclusive() && score == 0)
            score = -1;
        return score;
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


