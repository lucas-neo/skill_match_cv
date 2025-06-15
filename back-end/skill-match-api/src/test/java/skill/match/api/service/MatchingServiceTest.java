package skill.match.api.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import skill.match.api.model.Candidato;
import skill.match.api.model.Vaga;

import jakarta.inject.Inject;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class MatchingServiceTest {

    @Inject
    private ScoreService scoreService;

    @Test
    public void testCalculoScoreHabilidades() {
        // Criando candidato com habilidades específicas
        Candidato candidato = new Candidato(
            "candidato-1",
            "João Silva", 
            "joao@email.com",
            "São Paulo",
            Arrays.asList("Java", "Spring", "MySQL"),
            5
        );

        // Criando vaga que busca as mesmas habilidades
        Vaga vaga = new Vaga(
            "vaga-1",
            "Desenvolvedor Java",
            "São Paulo", 
            Arrays.asList("Java", "Spring", "MySQL"),
            3
        );

        // Calculando score
        int score = scoreService.calcularScore(candidato, vaga);

        // Deve ter score alto pois:
        // - Todas as habilidades batem
        // - Experiência suficiente (5 >= 3)
        // - Mesma localização
        assertTrue(score > 50, "Score deveria ser alto para candidato compatível");
    }

    @Test
    public void testCalculoScoreIncompativel() {
        // Candidato com habilidades diferentes
        Candidato candidato = new Candidato(
            "candidato-2",
            "Maria Santos",
            "maria@email.com", 
            "Rio de Janeiro",
            Arrays.asList("Python", "Django", "PostgreSQL"),
            2
        );

        // Vaga buscando habilidades diferentes
        Vaga vaga = new Vaga(
            "vaga-1",
            "Desenvolvedor Java",
            "São Paulo",
            Arrays.asList("Java", "Spring", "MySQL"), 
            5
        );

        int score = scoreService.calcularScore(candidato, vaga);

        // Deve ter score baixo pois:
        // - Nenhuma habilidade bate
        // - Experiência insuficiente (2 < 5)
        // - Localização diferente
        assertTrue(score < 20, "Score deveria ser baixo para candidato incompatível");
    }

    @Test
    public void testCalculoScoreParcial() {
        // Candidato com algumas habilidades compatíveis
        Candidato candidato = new Candidato(
            "candidato-3", 
            "Carlos Oliveira",
            "carlos@email.com",
            "São Paulo",
            Arrays.asList("Java", "React", "MongoDB"), // Só Java bate
            6
        );

        Vaga vaga = new Vaga(
            "vaga-1",
            "Desenvolvedor Java",
            "São Paulo",
            Arrays.asList("Java", "Spring", "MySQL"),
            4
        );

        int score = scoreService.calcularScore(candidato, vaga);

        // Deve ter score médio pois:
        // - Uma habilidade bate (Java)
        // - Experiência suficiente (6 >= 4)  
        // - Mesma localização
        assertTrue(score > 20 && score < 60, "Score deveria ser médio para compatibilidade parcial");
    }
}
