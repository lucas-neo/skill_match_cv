package skill.match.api.repository;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import skill.match.api.model.Candidato;

// Especifica o dialeto SQL para SQLite
@JdbcRepository(dialect = Dialect.H2)
public interface CandidatoRepository extends CrudRepository<Candidato, String> {
    // Micronaut Data irá implementar os métodos CRUD básicos automaticamente.
    // Você pode adicionar métodos de consulta personalizados aqui se necessário.
    // Ex: Optional<Candidato> findByEmail(String email);
}
