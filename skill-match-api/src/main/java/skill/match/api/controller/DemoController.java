package skill.match.api.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import skill.match.api.model.Candidato;
import skill.match.api.model.PerfilScore;
import skill.match.api.model.Vaga;
import skill.match.api.service.ScoreService;

import jakarta.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Controller("/demo")
public class DemoController {

    @Inject
    private ScoreService scoreService;

    @Get("/test-matching/{vagaId}")
    public HttpResponse<Map<String, Object>> testMatching(@PathVariable String vagaId) {
        // Criar dados de teste em memória
        List<Candidato> candidatos = Arrays.asList(
            new Candidato("cand-1", "João Silva", "joao@email.com", "São Paulo", 
                Arrays.asList("Java", "Spring", "MySQL", "Docker"), 5),
            new Candidato("cand-2", "Maria Santos", "maria@email.com", "São Paulo", 
                Arrays.asList("Java", "Spring Boot", "PostgreSQL", "Kubernetes"), 7), 
            new Candidato("cand-3", "Pedro Oliveira", "pedro@email.com", "Rio de Janeiro", 
                Arrays.asList("Python", "Django", "MongoDB"), 3),
            new Candidato("cand-4", "Ana Costa", "ana@email.com", "São Paulo", 
                Arrays.asList("Java", "Spring", "MySQL", "React"), 6)
        );

        Vaga vaga = new Vaga("vaga-1", "Desenvolvedor Java Senior", "São Paulo", 
            Arrays.asList("Java", "Spring", "MySQL"), 4);

        // Calcular scores para todos os candidatos
        List<PerfilScore> scores = candidatos.stream()
            .map(candidato -> {
                int score = scoreService.calcularScore(candidato, vaga);
                return new PerfilScore(candidato.getId(), vaga.getId(), score);
            })
            .sorted((a, b) -> Integer.compare(b.getScore(), a.getScore())) // Ordenar por score decrescente
            .collect(Collectors.toList());

        // Pegar os melhores candidatos (top 3)
        List<Map<String, Object>> melhoresCandidatos = scores.stream()
            .limit(3)
            .map(perfilScore -> {
                Candidato candidato = candidatos.stream()
                    .filter(c -> c.getId().equals(perfilScore.getCandidatoId()))
                    .findFirst().orElse(null);
                
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("candidato", candidato);
                resultado.put("score", perfilScore.getScore());
                return resultado;
            })
            .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("vaga", vaga);
        response.put("melhoresCandidatos", melhoresCandidatos);
        response.put("totalCandidatos", candidatos.size());
        
        return HttpResponse.ok(response);
    }
}
