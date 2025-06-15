package skill.match.api.service;

import jakarta.inject.Singleton;
import skill.match.api.model.Candidato;
import skill.match.api.model.Vaga;

@Singleton
public class ScoreService {

    private static final int PONTOS_POR_HABILIDADE_COMPATIVEL = 10;
    private static final int PONTOS_POR_EXPERIENCIA_COMPATIVEL = 20;
    private static final int PONTOS_POR_LOCALIZACAO_REMOTA_OU_COMPATIVEL = 15;

    public int calcularScore(Candidato candidato, Vaga vaga) {
        if (candidato == null || vaga == null) {
            return 0;
        }

        int scoreHabilidades = 0;
        if (candidato.getHabilidades() != null && vaga.getHabilidadesRequeridas() != null) {
            for (String habilidadeCandidato : candidato.getHabilidades()) {
                if (vaga.getHabilidadesRequeridas().contains(habilidadeCandidato)) {
                    scoreHabilidades += PONTOS_POR_HABILIDADE_COMPATIVEL;
                }
            }
        }

        int scoreExperiencia = 0;
        if (candidato.getAnosDeExperiencia() >= vaga.getAnosDeExperienciaMinimos()) {
            scoreExperiencia = PONTOS_POR_EXPERIENCIA_COMPATIVEL;
        }

        int scoreLocalizacao = 0;
        if (vaga.getLocalizacao() != null) {
            if ("Remoto".equalsIgnoreCase(vaga.getLocalizacao())) {
                scoreLocalizacao = PONTOS_POR_LOCALIZACAO_REMOTA_OU_COMPATIVEL;
            } else if (candidato.getLocalizacao() != null && vaga.getLocalizacao().equalsIgnoreCase(candidato.getLocalizacao())) {
                scoreLocalizacao = PONTOS_POR_LOCALIZACAO_REMOTA_OU_COMPATIVEL;
            }
        }

        return scoreHabilidades + scoreExperiencia + scoreLocalizacao;
    }
}
