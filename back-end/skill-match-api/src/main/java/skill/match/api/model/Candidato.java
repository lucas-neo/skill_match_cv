package skill.match.api.model;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Transient;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Serdeable
@MappedEntity // Anotação para Micronaut Data
public class Candidato {

    @Id // Chave primária no banco
    // Usaremos o ID da aplicação como chave primária no banco para simplificar,
    // assumindo que ele será único (ex: UUID gerado no serviço).
    private String id;

    private String nome;
    private String email;
    private String localizacao;
    private String habilidadesCsv; // Armazenar como CSV (Comma Separated Values)
    private int anosDeExperiencia;

    // Construtores, Getters e Setters

    public Candidato() {
    }

    public Candidato(String id, String nome, String email, String localizacao, List<String> habilidades, int anosDeExperiencia) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.localizacao = localizacao;
        setHabilidades(habilidades);
        this.anosDeExperiencia = anosDeExperiencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Transient // Esta propriedade não é persistida no banco
    public List<String> getHabilidades() {
        if (this.habilidadesCsv == null || this.habilidadesCsv.isBlank()) {
            return List.of();
        }
        return Arrays.stream(this.habilidadesCsv.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }

    @Transient // Esta propriedade não é persistida no banco
    public void setHabilidades(List<String> habilidades) {
        if (habilidades != null && !habilidades.isEmpty()) {
            this.habilidadesCsv = String.join(",", habilidades);
        } else {
            this.habilidadesCsv = null;
        }
    }

    // Getter/Setter para o campo CSV bruto, caso necessário para o Micronaut Data
    public String getHabilidadesCsv() {
        return habilidadesCsv;
    }

    public void setHabilidadesCsv(String habilidadesCsv) {
        this.habilidadesCsv = habilidadesCsv;
    }

    public int getAnosDeExperiencia() {
        return anosDeExperiencia;
    }

    public void setAnosDeExperiencia(int anosDeExperiencia) {
        this.anosDeExperiencia = anosDeExperiencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(id, candidato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "id='''" + id + "'''" +
                ", nome='''" + nome + "'''" +
                ", email='''" + email + "'''" +
                ", localizacao='''" + localizacao + "'''" +
                ", habilidadesCsv='''" + habilidadesCsv + "'''" +
                ", anosDeExperiencia=" + anosDeExperiencia +
                '}';
    }
}
