package skill.match.api.repository;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import skill.match.api.model.Vaga;

@JdbcRepository(dialect = Dialect.H2)
public interface VagaRepository extends CrudRepository<Vaga, String> {
    // Métodos CRUD básicos são fornecidos automaticamente.
}
