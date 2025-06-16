package skill.match.api.service;

import jakarta.inject.Singleton;
import jakarta.annotation.PostConstruct;
import skill.match.api.model.Candidato;
import skill.match.api.model.Vaga;
import skill.match.api.repository.CandidatoRepository;
import skill.match.api.repository.VagaRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class CadastroService {

    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;

    // Cache em memória para acesso rápido. Sincronizado com o banco.
    private final Map<String, Candidato> candidatosCache = new ConcurrentHashMap<>();
    private final Map<String, Vaga> vagasCache = new ConcurrentHashMap<>();

    public CadastroService(CandidatoRepository candidatoRepository, VagaRepository vagaRepository) {
        this.candidatoRepository = candidatoRepository;
        this.vagaRepository = vagaRepository;
    }

    @PostConstruct
    public void loadCache() {
        try {
            // Carrega todos os candidatos do banco para o cache
            StreamSupport.stream(candidatoRepository.findAll().spliterator(), false)
                    .forEach(candidato -> candidatosCache.put(candidato.getId(), candidato));

            // Carrega todas as vagas do banco para o cache
            StreamSupport.stream(vagaRepository.findAll().spliterator(), false)
                    .forEach(vaga -> vagasCache.put(vaga.getId(), vaga));
        } catch (Exception e) {
            // Se as tabelas não existirem ainda, apenas log o erro mas não falha
            System.out.println("Warning: Could not load cache from database - tables may not exist yet: " + e.getMessage());
        }
    }

    public Candidato addCandidato(Candidato candidato) {
        if (candidato.getId() == null || candidato.getId().isBlank()) {
            candidato.setId(UUID.randomUUID().toString());
        }
        Candidato savedCandidato = candidatoRepository.save(candidato);
        candidatosCache.put(savedCandidato.getId(), savedCandidato); // Atualiza cache
        return savedCandidato;
    }

    public Optional<Candidato> getCandidato(String id) {
        // Tenta buscar do cache primeiro
        Candidato cachedCandidato = candidatosCache.get(id);
        if (cachedCandidato != null) {
            return Optional.of(cachedCandidato);
        }
        // Se não estiver no cache, busca no banco e atualiza o cache
        Optional<Candidato> candidatoFromDb = candidatoRepository.findById(id);
        candidatoFromDb.ifPresent(c -> candidatosCache.put(c.getId(), c));
        return candidatoFromDb;
    }

    public Map<String, Candidato> getAllCandidatos() {
        // Retorna o cache. Para garantir dados mais recentes, poderia recarregar do DB.
        // Para esta implementação, o cache é atualizado nas operações de escrita.
        return candidatosCache;
    }

    public Candidato updateCandidato(String id, Candidato candidatoAtualizado) {
        if (!candidatoRepository.existsById(id)) {
            // Ou lançar uma exceção de "não encontrado"
            return null;
        }
        candidatoAtualizado.setId(id); // Garante que o ID não seja alterado
        Candidato updatedCandidato = candidatoRepository.update(candidatoAtualizado);
        candidatosCache.put(updatedCandidato.getId(), updatedCandidato); // Atualiza cache
        return updatedCandidato;
    }

    public boolean deleteCandidato(String id) {
        if (!candidatoRepository.existsById(id)) {
            return false;
        }
        candidatoRepository.deleteById(id);
        candidatosCache.remove(id); // Remove do cache
        return true;
    }

    public Vaga addVaga(Vaga vaga) {
        if (vaga.getId() == null || vaga.getId().isBlank()) {
            vaga.setId(UUID.randomUUID().toString());
        }
        Vaga savedVaga = vagaRepository.save(vaga);
        vagasCache.put(savedVaga.getId(), savedVaga); // Atualiza cache
        return savedVaga;
    }

    public Optional<Vaga> getVaga(String id) {
        Vaga cachedVaga = vagasCache.get(id);
        if (cachedVaga != null) {
            return Optional.of(cachedVaga);
        }
        Optional<Vaga> vagaFromDb = vagaRepository.findById(id);
        vagaFromDb.ifPresent(v -> vagasCache.put(v.getId(), v));
        return vagaFromDb;
    }

    public Map<String, Vaga> getAllVagas() {
        return vagasCache;
    }

    public Vaga updateVaga(String id, Vaga vagaAtualizada) {
        if (!vagaRepository.existsById(id)) {
            return null;
        }
        vagaAtualizada.setId(id);
        Vaga updatedVaga = vagaRepository.update(vagaAtualizada);
        vagasCache.put(updatedVaga.getId(), updatedVaga); // Atualiza cache
        return updatedVaga;
    }

    public boolean deleteVaga(String id) {
        if (!vagaRepository.existsById(id)) {
            return false;
        }
        vagaRepository.deleteById(id);
        vagasCache.remove(id); // Remove do cache
        return true;
    }
}
