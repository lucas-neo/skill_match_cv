package skill.match.api.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import skill.match.api.model.Candidato;
import skill.match.api.service.CadastroService;
import skill.match.api.service.MatchingService;

import jakarta.inject.Inject;
import java.util.Optional;
import java.util.Collection;

@Controller("/candidatos")
public class CandidatoController {

    @Inject
    private CadastroService cadastroService;

    @Inject
    private MatchingService matchingService;

    @Post
    public HttpResponse<Candidato> addCandidato(@Body Candidato candidato) {
        Candidato novoCandidato = cadastroService.addCandidato(candidato);
        matchingService.atualizarScoresParaCandidato(novoCandidato); // Atualiza scores
        return HttpResponse.created(novoCandidato);
    }

    @Get
    public HttpResponse<Collection<Candidato>> getAllCandidatos() {
        Collection<Candidato> candidatos = cadastroService.getAllCandidatos().values();
        return HttpResponse.ok(candidatos);
    }

    @Get("/{id}")
    public HttpResponse<Candidato> getCandidato(@PathVariable String id) {
        Optional<Candidato> candidato = cadastroService.getCandidato(id);
        return candidato.map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    @Put("/{id}")
    public HttpResponse<Candidato> updateCandidato(@PathVariable String id, @Body Candidato candidatoAtualizado) {
        Candidato candidato = cadastroService.updateCandidato(id, candidatoAtualizado);
        if (candidato != null) {
            matchingService.atualizarScoresParaCandidato(candidato); // Re-calcula scores
            return HttpResponse.ok(candidato);
        }
        return HttpResponse.notFound();
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteCandidato(@PathVariable String id) {
        boolean deleted = cadastroService.deleteCandidato(id);
        if (deleted) {
            matchingService.removerScoresParaCandidato(id);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
