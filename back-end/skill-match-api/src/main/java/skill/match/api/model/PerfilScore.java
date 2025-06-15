package skill.match.api.model;

import java.util.Objects;

public class PerfilScore implements Comparable<PerfilScore> {
    private String candidatoId;
    private int score;
    private String vagaId; // Adicionado para saber a qual vaga o score se refere

    public PerfilScore(String candidatoId, String vagaId, int score) {
        this.candidatoId = candidatoId;
        this.vagaId = vagaId;
        this.score = score;
    }

    public String getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(String candidatoId) {
        this.candidatoId = candidatoId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getVagaId() {
        return vagaId;
    }

    public void setVagaId(String vagaId) {
        this.vagaId = vagaId;
    }

    @Override
    public int compareTo(PerfilScore other) {
        // Ordena por score (maior primeiro)
        int scoreCompare = Integer.compare(other.score, this.score);
        if (scoreCompare != 0) {
            return scoreCompare;
        }
        // Se o score for igual, desempata pelo ID do candidato (ordem alfabética)
        return this.candidatoId.compareTo(other.candidatoId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilScore that = (PerfilScore) o;
        return score == that.score &&
                Objects.equals(candidatoId, that.candidatoId) &&
                Objects.equals(vagaId, that.vagaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidatoId, score, vagaId);
    }

    @Override
    public String toString() {
        return "PerfilScore{" +
                "candidatoId='''" + candidatoId + "'''" +
                ", score=" + score +
                ", vagaId='''" + vagaId + "'''" +
                '}';
    }
}
