const candidatos = [
  {
    nome: "Lucas Bufalo Neo",
    experiencia: 5,
    habilidades: ["JavaScript", "React", "CSS"],
    formacao: "Engenharia da Computação"
  },
  {
    nome: "Cristiano Ronaldo",
    experiencia: 2,
    habilidades: ["HTML", "CSS"],
    formacao: "Sistemas de Informação"
  },
  {
    nome: "Andressa Urach",
    experiencia: 4,
    habilidades: ["Python", "Django", "SQL"],
    formacao: "Ciência da Computação"
  }
];

const requisitos = {
  experienciaMinima: 3,
  habilidadesDesejadas: ["JavaScript", "React", "CSS"],
  formacaoObrigatoria: "Engenharia da Computação"
};

function calcularPontuacao(candidato) {
  let pontos = 0;
  if (candidato.experiencia >= requisitos.experienciaMinima) pontos += 30;
  candidato.habilidades.forEach(h => {
    if (requisitos.habilidadesDesejadas.includes(h)) pontos += 10;
  });
  if (candidato.formacao === requisitos.formacaoObrigatoria) pontos += 20;
  return pontos;
}

function classificarStatus(pontos) {
  if (pontos >= 60) return 'Alta compatibilidade';
  if (pontos >= 40) return 'Média compatibilidade';
  return 'Baixa compatibilidade';
}

function classNameStatus(pontos) {
  if (pontos >= 60) return 'status-alta';
  if (pontos >= 40) return 'status-media';
  return 'status-baixa';
}

const tbody = document.getElementById("tabela-candidatos");
candidatos.forEach(c => {
  const pontos = calcularPontuacao(c);
  const status = classificarStatus(pontos);
  const statusClass = classNameStatus(pontos);

  const row = document.createElement("tr");
  row.innerHTML = `
    <td>${c.nome}</td>
    <td>${c.experiencia} anos</td>
    <td>${c.habilidades.join(", ")}</td>
    <td>${c.formacao}</td>
    <td><strong>${pontos}</strong></td>
    <td><span class="${statusClass}">${status}</span></td>
    <td><button onclick='verPerfil(${JSON.stringify(c)})'>Ver Perfil</button></td>
  `;
  tbody.appendChild(row);
});

function verPerfil(candidato) {
  localStorage.setItem("candidatoSelecionado", JSON.stringify(candidato));
  window.location.href = "perfil.html";
}