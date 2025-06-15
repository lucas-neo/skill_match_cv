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
public class Vaga {

    @Id // Chave primária no banco, usando o ID da aplicação
    private String id;

    private String titulo;
    private String localizacao;
    private String habilidadesRequeridasCsv; // Armazenar como CSV
    private int anosDeExperienciaMinimos;

    // Construtores, Getters e Setters

    public Vaga() {
    }

    public Vaga(String id, String titulo, String localizacao, List<String> habilidadesRequeridas, int anosDeExperienciaMinimos) {
        this.id = id;
        this.titulo = titulo;
        this.localizacao = localizacao;
        setHabilidadesRequeridas(habilidadesRequeridas);
        this.anosDeExperienciaMinimos = anosDeExperienciaMinimos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Transient // Esta propriedade não é persistida no banco
    public List<String> getHabilidadesRequeridas() {
        if (this.habilidadesRequeridasCsv == null || this.habilidadesRequeridasCsv.isBlank()) {
            return List.of();
        }
        return Arrays.stream(this.habilidadesRequeridasCsv.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }

    @Transient // Esta propriedade não é persistida no banco
    public void setHabilidadesRequeridas(List<String> habilidadesRequeridas) {
        if (habilidadesRequeridas != null && !habilidadesRequeridas.isEmpty()) {
            this.habilidadesRequeridasCsv = String.join(",", habilidadesRequeridas);
        } else {
            this.habilidadesRequeridasCsv = null;
        }
    }

    // Getter/Setter para o campo CSV bruto
    public String getHabilidadesRequeridasCsv() {
        return habilidadesRequeridasCsv;
    }

    public void setHabilidadesRequeridasCsv(String habilidadesRequeridasCsv) {
        this.habilidadesRequeridasCsv = habilidadesRequeridasCsv;
    }

    public int getAnosDeExperienciaMinimos() {
        return anosDeExperienciaMinimos;
    }

    public void setAnosDeExperienciaMinimos(int anosDeExperienciaMinimos) {
        this.anosDeExperienciaMinimos = anosDeExperienciaMinimos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaga vaga = (Vaga) o;
        return Objects.equals(id, vaga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "id='''" + id + "'''" +
                ", titulo='''" + titulo + "'''" +
                ", localizacao='''" + localizacao + "'''" +
                ", habilidadesRequeridasCsv='''" + habilidadesRequeridasCsv + "'''" +
                ", anosDeExperienciaMinimos=" + anosDeExperienciaMinimos +
                '}';
    }
}
