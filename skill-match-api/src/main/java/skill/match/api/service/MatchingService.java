package skill.match.api.service;

import jakarta.inject.Singleton;
import skill.match.api.model.Candidato;
import skill.match.api.model.PerfilScore;
import skill.match.api.model.Vaga;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class MatchingService {

    private final CadastroService cadastroService;
    private final ScoreService scoreService;

    // Estrutura para armazenar scores: Map<idVaga, PriorityQueue<PerfilScore>>
    // A PriorityQueue manterá os candidatos ordenados pelo maior score para aquela vaga.
    private final Map<String, PriorityQueue<PerfilScore>> scoresPorVaga = new ConcurrentHashMap<>();

    public MatchingService(CadastroService cadastroService, ScoreService scoreService) {
        this.cadastroService = cadastroService;
        this.scoreService = scoreService;
        // Inicializar scores para vagas existentes (se houver alguma ao iniciar)
        // Em um sistema real, isso poderia ser carregado de um banco de dados
        this.cadastroService.getAllVagas().values().forEach(this::recalcularScoresParaVaga);
    }

    // Chamado quando um novo candidato é adicionado/atualizado
    public void atualizarScoresParaCandidato(Candidato candidato) {
        if (candidato == null) return;
        for (Vaga vaga : cadastroService.getAllVagas().values()) {
            int score = scoreService.calcularScore(candidato, vaga);
            PerfilScore perfilScore = new PerfilScore(candidato.getId(), vaga.getId(), score);
            
            scoresPorVaga.computeIfAbsent(vaga.getId(), k -> new PriorityQueue<>()).removeIf(ps -> ps.getCandidatoId().equals(candidato.getId()));
            scoresPorVaga.get(vaga.getId()).add(perfilScore);
        }
    }

    // Chamado quando uma nova vaga é adicionada/atualizada
    public void recalcularScoresParaVaga(Vaga vaga) {
        if (vaga == null) return;
        PriorityQueue<PerfilScore> scoresDaVaga = scoresPorVaga.computeIfAbsent(vaga.getId(), k -> new PriorityQueue<>());
        scoresDaVaga.clear();

        for (Candidato candidato : cadastroService.getAllCandidatos().values()) {
            int score = scoreService.calcularScore(candidato, vaga);
            scoresDaVaga.add(new PerfilScore(candidato.getId(), vaga.getId(), score));
        }
    }
    
    // Chamado quando uma vaga é removida
    public void removerScoresParaVaga(String vagaId) {
        scoresPorVaga.remove(vagaId);
    }

    // Chamado quando um candidato é removido
    public void removerScoresParaCandidato(String candidatoId) {
        scoresPorVaga.values().forEach(pq -> pq.removeIf(ps -> ps.getCandidatoId().equals(candidatoId)));
    }

    public List<PerfilScore> getMelhoresCandidatosParaVaga(String vagaId, int limite) {
        PriorityQueue<PerfilScore> scores = scoresPorVaga.get(vagaId);
        if (scores == null) {
            return Collections.emptyList();
        }
        // A PriorityQueue já está ordenada. Apenas pegamos os primeiros 'limite' elementos.
        // Como a PriorityQueue não oferece acesso direto por índice de forma eficiente e ordenada,
        // uma forma é converter para lista e pegar o subconjunto.
        return scores.stream().limit(limite).collect(Collectors.toList());
    }

    public List<PerfilScore> getVagasCompativeisParaCandidato(String candidatoId, int limite) {
        Candidato candidato = cadastroService.getCandidato(candidatoId).orElse(null);
        if (candidato == null) {
            return Collections.emptyList();
        }

        List<PerfilScore> vagasCompativeis = new ArrayList<>();
        for (Vaga vaga : cadastroService.getAllVagas().values()) {
            int score = scoreService.calcularScore(candidato, vaga);
            if (score > 0) { // Considerar apenas se houver alguma compatibilidade
                vagasCompativeis.add(new PerfilScore(candidatoId, vaga.getId(), score));
            }
        }

        // Ordena pela maior pontuação
        vagasCompativeis.sort(Collections.reverseOrder()); // Usa o compareTo de PerfilScore

        return vagasCompativeis.stream().limit(limite).collect(Collectors.toList());
    }
}
