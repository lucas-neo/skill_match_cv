package skill.match.api.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import skill.match.api.model.Vaga;
import skill.match.api.service.CadastroService;
import skill.match.api.service.MatchingService;

import jakarta.inject.Inject;
import java.util.Optional;
import java.util.Collection;

@Controller("/vagas")
public class VagaController {

    @Inject
    private CadastroService cadastroService;

    @Inject
    private MatchingService matchingService;

    @Post
    public HttpResponse<Vaga> addVaga(@Body Vaga vaga) {
        Vaga novaVaga = cadastroService.addVaga(vaga);
        matchingService.recalcularScoresParaVaga(novaVaga); // Calcula scores para esta nova vaga
        return HttpResponse.created(novaVaga);
    }

    @Get
    public HttpResponse<Collection<Vaga>> getAllVagas() {
        Collection<Vaga> vagas = cadastroService.getAllVagas().values();
        return HttpResponse.ok(vagas);
    }

    @Get("/{id}")
    public HttpResponse<Vaga> getVaga(@PathVariable String id) {
        Optional<Vaga> vaga = cadastroService.getVaga(id);
        return vaga.map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    @Put("/{id}")
    public HttpResponse<Vaga> updateVaga(@PathVariable String id, @Body Vaga vagaAtualizada) {
        Vaga vaga = cadastroService.updateVaga(id, vagaAtualizada);
        if (vaga != null) {
            matchingService.recalcularScoresParaVaga(vaga); // Re-calcula scores para esta vaga
            return HttpResponse.ok(vaga);
        }
        return HttpResponse.notFound();
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteVaga(@PathVariable String id) {
        boolean deleted = cadastroService.deleteVaga(id);
        if (deleted) {
            matchingService.removerScoresParaVaga(id);
            return HttpResponse.noContent();
        }
        return HttpResponse.notFound();
    }
}
