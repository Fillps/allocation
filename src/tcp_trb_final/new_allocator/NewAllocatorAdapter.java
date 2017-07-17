package tcp_trb_final.new_allocator;

import tcp_trb_final.new_allocator.dominio.*;
import tcp_trb_final.new_allocator.serviços.*;
import tcp_trb_final.room_xml.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabja on 15/07/2017.
 */
public class NewAllocatorAdapter {
    private BANCO_DADOS bancoDados;
    private AllocationType allocationType;

    public NewAllocatorAdapter(BANCO_DADOS banco_dados, AllocationType allocationType){
        this.bancoDados = banco_dados;
        this.allocationType = allocationType;
    }

    public void allocate() {
        makeBancoDados();
        organizeData();

        ALOCACAO alocacao = new ALOCACAO(bancoDados);

        alocacao.Alocar_lista_turmas(false);
        alocacao.Alocar_lista_turmas(true);

        List<Turma> LISTA_ESPERA = bancoDados.getListaEspera();
        int numTurmasNaoAlocadas = LISTA_ESPERA.size();

        if(numTurmasNaoAlocadas == 0)
            System.out.printf("Todas as turmas foram alocadas com sucesso\n");
        else
        {
            System.out.printf("Nao foi possivel alocar %d turmas:\n", numTurmasNaoAlocadas);
            Turma.Imprimir_Lista_Turmas_nome(LISTA_ESPERA);
        }

        System.out.println("************************************************\n");
    }

    public void makeBancoDados() {
        makeCourses();
        makeFeatures();
        makeBuildings();
    }

    public void organizeData() {
        List<Dia> LISTA_DIAS = bancoDados.getListaDias();
        Collections.sort(LISTA_DIAS, Dia_Servico.DiaComparator);

        List<Horario> LISTA_HORARIOS = bancoDados.getListaHorarios();
        Collections.sort(LISTA_HORARIOS, Horario_Servico.HorarioNameComparator);

        List<Predio> LISTA_PREDIOS = bancoDados.getListaPredios();

        Sala_servico sala_servico = new Sala_servico(bancoDados); // para chamar o m�todo a seguir
        sala_servico.Preencher_LISTA_SALAS(LISTA_PREDIOS);

        Collections.sort(bancoDados.getListaSalas(), Sala_servico.SalaCapacidadeComparator);

        Turma_servico turma_servico = new Turma_servico(bancoDados);
        turma_servico.Preencher_LISTA_TURMAS();

        List<Professor> LISTA_PROFESSORES = bancoDados.getListaProfs();
        Professor_Servico prof_qualquer = new Professor_Servico(); // s� para chamar o m�todo a seguir
        prof_qualquer.Incluir_Professores_nas_suas_Turmas(LISTA_PROFESSORES);

        Incluir_ID_Disciplina_Turmas();
    }

    public AllocationType getAllocationType() {
        return allocationType;
    }

//---------------------Métodos auxiliares para a criação do banco de dados------------------------------------
    private void makeFeatures(){
        List<FeatureType> featureList = allocationType.getFeatures().getFeature();

        for(FeatureType featureType : featureList){

            bancoDados.set_TIPO_SALA_REGULAR(featureType.getName(), featureType.getId());

            TipoRecurso novo_tipo_sala = new TipoRecurso(featureType.getName(),featureType.getId(),featureType.getHidden());
            bancoDados.IncluirTipo_ListaTiposExistentes(novo_tipo_sala);
        }
    }

    private void makeBuildings(){
        List<BuildingType> buildingList = allocationType.getBuildings().getBuilding();
        List<Predio> lista_predios = bancoDados.getListaPredios();

        for(BuildingType buildingType : buildingList){
            List<RoomType> roomList = buildingType.getRoom();

            List<Sala> lista_salas = new ArrayList<>();
            for(RoomType roomType : roomList){
                Sala sala = new Sala(roomType.getAvailableForAllocation());

                sala.setID(roomType.getId());
                sala.setObservacao(roomType.getNote());
                sala.setListaTipos(roomType.getFeatureIds());
                sala.setNumLugares(roomType.getNumberOfPlaces());

                lista_salas.add(sala);
            }

            Predio predio = new Predio(buildingType.getId(), lista_salas);
            lista_predios.add(predio);
        }
    }

    private void makeCourses(){
        List<CourseType> courseList = allocationType.getCourses().getCourse();
        for(CourseType courseType : courseList){
            Disciplina disciplina = new Disciplina(courseType.getId(), courseType.getName());

            List<GroupType> groupList = courseType.getGroup();
            for(GroupType groupType : groupList){

                List<Professor> profAuxList = new ArrayList<>();
                makeProfessor(groupType, profAuxList);

                Turma turma = makeTurma(disciplina, groupType, profAuxList);

                List<SessionType> sessionList = groupType.getSession();
                for(SessionType sessionType : sessionList){
                    Aula aula = new Aula(turma.getIndicesProfessores());

                    aula.setDuracao(sessionType.getDuration());
                    aula.setDia(sessionType.getWeekday());
                    aula.setHoraInicio(sessionType.getStartTime());
                    aula.setTipoRecurso(sessionType.getFeatureIds());

                    turma.Incluir_Aula(aula);
                    aula.setIDTurma(turma.getID());
                    aula.setCodDisc(turma.getCodigoDisciplina());

                    bancoDados.IncluirDia_ListaDias(sessionType.getWeekday());
                    bancoDados.IncluirHorario_ListaHorarios(sessionType.getStartTime());
                    bancoDados.IncluirTipo_ListaTiposSolicitados(sessionType.getFeatureIds());
                }

                Incluir_Turma_na_Lista_Profs_Group(turma, profAuxList);

                Aula_Servico aula_qualquer = new Aula_Servico();
                aula_qualquer.Incluir_Numero_Alunos_Lista_Aulas(turma.getListaAulas(), turma.getNumAlunos());
            }
        }
    }

    private void makeProfessor(GroupType groupType, List<Professor> profAuxList){
        List<Professor> lista_professores = bancoDados.getListaProfs();

        List<String> nomes_profs = Lista_nomes_professores(groupType.getTeacher());

        for(String nome_professor : nomes_profs) {

            Professor professor = new Professor(nome_professor);

            if (lista_professores.indexOf(professor) == -1) {
                professor.setID((lista_professores.size()));
                lista_professores.add(professor);
            }
            else {
                professor.setID(lista_professores.indexOf(professor));
            }

            profAuxList.add(professor);
        }
    }

    private Turma makeTurma(Disciplina disciplina, GroupType groupType, List<Professor> profAuxList){
        Turma turma_qualquer = new Turma('x', 0);
        List<Object> lista_ids_turmas = turma_qualquer.Lista_ids_turmas(groupType.getId());

        List<Turma> lista_turmas_disciplina = disciplina.get_Lista_Turmas();
        for(Object id_turma : lista_ids_turmas){

            Turma turma = new Turma(id_turma, disciplina.getCodigo());

            int indice_turma = lista_turmas_disciplina.indexOf(turma);
            int numAlunos = Integer.parseInt(groupType.getNumberOfStudents());
            if(indice_turma == -1){
                lista_turmas_disciplina.add(turma);
                turma.setNumAlunos(numAlunos);
            }
            else{
                turma = lista_turmas_disciplina.get(indice_turma);
                if(turma.getNumAlunos() > numAlunos)
                    turma.setNumAlunos(numAlunos);
            }

            Professor_Servico prof_qualquer = new Professor_Servico();
            List<Integer> lista_indices_profs = prof_qualquer.Extrair_Indices_Lista_Professores(profAuxList);
            turma.Incluir_Professores(lista_indices_profs);

            return turma;
        }
    }

    private void Incluir_Turma_na_Lista_Profs_Group(Turma turma, List<Professor> profAuxList) {
        List<Professor> lista_professores = bancoDados.getListaProfs();

        int numProfs = profAuxList.size();


        for (int indice = 0; indice < numProfs; indice++)
        {
            Professor professor_group = profAuxList.get(indice);

            int indice_prof = lista_professores.indexOf(professor_group);

            // nao buscar por ID pois estes est�o sendo gerados
            Professor professor = lista_professores.get(indice_prof); // i-�simo professor da lista de profs do group corrente

            professor.Incluir_Turma(turma);
        }
    }

    private List<String> Lista_nomes_professores(String nomes_profs) {
        List<String> lista = new ArrayList<String>();

        int tamanho = nomes_profs.length();
        String nome;
        int indice = 0;

        while(indice >= 0)
        {
            indice = nomes_profs.indexOf(',');

            if (indice >= 0){
                nome = nomes_profs.substring(0, indice);
                lista.add(nome);

                nomes_profs = nomes_profs.substring(indice+2, tamanho-1); //indice + 2 : pula ',' e espaco
                tamanho = nomes_profs.length();
            }
            else{
                lista.add(nomes_profs); // a string ficou com o ultimo nome
                tamanho = 0; // for�ar parada
            }
        }

        return lista;
    }

//--------------------------------------------------------------------------------------------------------------
//----------------------Métodos auxiliares para alocação--------------------------------------------------------
    private void Incluir_ID_Disciplina_Turmas() {
        List<Disciplina> LISTA_DISCIPLINAS = bancoDados.getListaDisciplinas();
        int numDisciplinas = LISTA_DISCIPLINAS.size();

        for(int indiceDisc=0; indiceDisc < numDisciplinas; indiceDisc++)
        {
            Disciplina disciplina = LISTA_DISCIPLINAS.get(indiceDisc);		// i-�sima disiciplina da LISTA_DISCIPLINAS
            List<Turma> listas_turmas = disciplina.get_Lista_Turmas();		// lista de turmas da disciplina
            int numTurmas = listas_turmas.size();							// numero de turmas da disciplina

            // setar o ID da disciplina em cada turma
            for(int indiceTurma=0; indiceTurma < numTurmas; indiceTurma++)
            {
                Turma turma = listas_turmas.get(indiceTurma);				//j-�sima turma da discplina
                turma.setID_Disciplina(indiceDisc);							// setar a ID da disciplina

                List<Aula> lista_aulas = turma.getListaAulas();				// lista de aulas da turma j
                int numAulas = lista_aulas.size();							// numero de aulas da turma j

                // setar o ID da disciplina em cada aula da turma
                for(int indiceAula=0; indiceAula < numAulas; indiceAula++)
                {
                    Aula aula = lista_aulas.get(indiceAula);				// k-�sima aula da turma j
                    aula.setID_Disc(indiceDisc);							// setar a ID da disciplina
                }
            }
        }
    }
//--------------------------------------------------------------------------------------------------------------
}
