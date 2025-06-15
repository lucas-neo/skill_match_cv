package skill.match.api.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import skill.match.api.model.Candidato;
import skill.match.api.model.PerfilScore;
import skill.match.api.service.CadastroService;
import skill.match.api.service.MatchingService;

import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/matching")
public class MatchingController {

    @Inject
    private MatchingService matchingService;
    @Inject
    private CadastroService cadastroService; // Para buscar detalhes do candidato

    @Get("/vagas/{vagaId}/melhores-candidatos")
    public HttpResponse<List<Candidato>> getMelhoresCandidatosParaVaga(
            @PathVariable String vagaId,
            @QueryValue(defaultValue = "10") int limite) {

        List<PerfilScore> melhoresPerfis = matchingService.getMelhoresCandidatosParaVaga(vagaId, limite);
        if (melhoresPerfis.isEmpty() && !cadastroService.getVaga(vagaId).isPresent()){
            return HttpResponse.notFound();
        }

        List<Candidato> candidatos = melhoresPerfis.stream()
                .map(perfilScore -> cadastroService.getCandidato(perfilScore.getCandidatoId()).orElse(null))
                .filter(candidato -> candidato != null)
                .collect(Collectors.toList());

        return HttpResponse.ok(candidatos);
    }

    @Get("/candidatos/{candidatoId}/vagas-compativeis")
    public HttpResponse<List<PerfilScore>> getVagasCompativeisParaCandidato(
            @PathVariable String candidatoId,
            @QueryValue(defaultValue = "5") int limite) {
        if (!cadastroService.getCandidato(candidatoId).isPresent()){
            return HttpResponse.notFound();
        }
        List<PerfilScore> vagasCompativeis = matchingService.getVagasCompativeisParaCandidato(candidatoId, limite);
        return HttpResponse.ok(vagasCompativeis);
    }

}
