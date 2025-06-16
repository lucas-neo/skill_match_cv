// API Configuration
const API_BASE_URL = 'http://localhost:8080';

// Application State
let candidates = [];
let jobs = [];
let currentMatches = [];

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    checkApiStatus();
    loadDashboard();
    loadCandidates();
    loadJobs();
});

// API Status Check
async function checkApiStatus() {
    const statusIndicator = document.getElementById('api-status-indicator');
    const statusText = document.getElementById('api-status-text');
    
    try {
        const response = await fetch(`${API_BASE_URL}/hello`);
        if (response.ok) {
            statusIndicator.className = 'status-online';
            statusText.textContent = 'API Online';
        } else {
            throw new Error('API não disponível');
        }
    } catch (error) {
        statusIndicator.className = 'status-offline';
        statusText.textContent = 'API Offline';
        showMessage('Erro ao conectar com a API. Verifique se o back-end está rodando.', 'error');
    }
}

// Navigation
function showSection(sectionName) {
    // Hide all sections
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Show selected section
    document.getElementById(`${sectionName}-section`).classList.add('active');
    
    // Update navigation
    document.querySelectorAll('.sidebar a').forEach(link => {
        link.classList.remove('nav-active');
    });
    event.target.classList.add('nav-active');
    
    // Load section data
    if (sectionName === 'candidates') {
        loadCandidates();
    } else if (sectionName === 'jobs') {
        loadJobs();
        updateJobSelect();
    } else if (sectionName === 'matching') {
        updateJobSelect();
    } else if (sectionName === 'dashboard') {
        loadDashboard();
    }
}

// Dashboard Functions
async function loadDashboard() {
    try {
        const [candidatesResponse, jobsResponse] = await Promise.all([
            fetch(`${API_BASE_URL}/candidatos`),
            fetch(`${API_BASE_URL}/vagas`)
        ]);
        
        if (candidatesResponse.ok && jobsResponse.ok) {
            const candidatesData = await candidatesResponse.json();
            const jobsData = await jobsResponse.json();
            
            document.getElementById('total-candidates').textContent = candidatesData.length;
            document.getElementById('total-jobs').textContent = jobsData.length;
        }
    } catch (error) {
        console.error('Erro ao carregar dashboard:', error);
    }
}

// Candidates Functions
async function loadCandidates() {
    try {
        showLoading('candidates-table');
        const response = await fetch(`${API_BASE_URL}/candidatos`);
        
        if (response.ok) {
            candidates = await response.json();
            renderCandidatesTable();
        } else {
            throw new Error('Erro ao carregar candidatos');
        }
    } catch (error) {
        console.error('Erro ao carregar candidatos:', error);
        showMessage('Erro ao carregar candidatos', 'error');
        document.getElementById('candidates-table').innerHTML = '<tr><td colspan="6">Erro ao carregar dados</td></tr>';
    }
}

function renderCandidatesTable() {
    const tbody = document.getElementById('candidates-table');
    
    if (candidates.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Nenhum candidato cadastrado</td></tr>';
        return;
    }
    
    tbody.innerHTML = candidates.map(candidate => `
        <tr>
            <td>${candidate.nome}</td>
            <td>${candidate.email}</td>
            <td>${candidate.localizacao}</td>
            <td>${candidate.anosDeExperiencia} anos</td>
            <td>
                <div class="skills-list">
                    ${candidate.habilidades.map(skill => 
                        `<span class="skill-tag">${skill}</span>`
                    ).join('')}
                </div>
            </td>
            <td>
                <button class="btn-danger" onclick="deleteCandidate('${candidate.id}')">Excluir</button>
            </td>
        </tr>
    `).join('');
}

async function addCandidate(event) {
    event.preventDefault();
    
    const formData = {
        nome: document.getElementById('candidate-name').value,
        email: document.getElementById('candidate-email').value,
        localizacao: document.getElementById('candidate-location').value,
        anosDeExperiencia: parseInt(document.getElementById('candidate-experience').value),
        habilidades: document.getElementById('candidate-skills').value.split(',').map(s => s.trim())
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/candidatos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            showMessage('Candidato adicionado com sucesso!', 'success');
            closeModal('add-candidate-modal');
            document.getElementById('candidate-form').reset();
            loadCandidates();
            loadDashboard();
        } else {
            throw new Error('Erro ao adicionar candidato');
        }
    } catch (error) {
        console.error('Erro ao adicionar candidato:', error);
        showMessage('Erro ao adicionar candidato', 'error');
    }
}

async function deleteCandidate(id) {
    if (!confirm('Tem certeza que deseja excluir este candidato?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/candidatos/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showMessage('Candidato excluído com sucesso!', 'success');
            loadCandidates();
            loadDashboard();
        } else {
            throw new Error('Erro ao excluir candidato');
        }
    } catch (error) {
        console.error('Erro ao excluir candidato:', error);
        showMessage('Erro ao excluir candidato', 'error');
    }
}

// Jobs Functions
async function loadJobs() {
    try {
        showLoading('jobs-table');
        const response = await fetch(`${API_BASE_URL}/vagas`);
        
        if (response.ok) {
            jobs = await response.json();
            renderJobsTable();
        } else {
            throw new Error('Erro ao carregar vagas');
        }
    } catch (error) {
        console.error('Erro ao carregar vagas:', error);
        showMessage('Erro ao carregar vagas', 'error');
        document.getElementById('jobs-table').innerHTML = '<tr><td colspan="5">Erro ao carregar dados</td></tr>';
    }
}

function renderJobsTable() {
    const tbody = document.getElementById('jobs-table');
    
    if (jobs.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Nenhuma vaga cadastrada</td></tr>';
        return;
    }
    
    tbody.innerHTML = jobs.map(job => `
        <tr>
            <td>${job.titulo}</td>
            <td>${job.localizacao}</td>
            <td>${job.anosDeExperienciaMinimos} anos</td>
            <td>
                <div class="skills-list">
                    ${job.habilidadesRequeridas.map(skill => 
                        `<span class="skill-tag">${skill}</span>`
                    ).join('')}
                </div>
            </td>
            <td>
                <button class="btn-danger" onclick="deleteJob('${job.id}')">Excluir</button>
            </td>
        </tr>
    `).join('');
}

async function addJob(event) {
    event.preventDefault();
    
    const formData = {
        titulo: document.getElementById('job-title').value,
        localizacao: document.getElementById('job-location').value,
        anosDeExperienciaMinimos: parseInt(document.getElementById('job-min-experience').value),
        habilidadesRequeridas: document.getElementById('job-skills').value.split(',').map(s => s.trim())
    };
    
    try {
        const response = await fetch(`${API_BASE_URL}/vagas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });
        
        if (response.ok) {
            showMessage('Vaga adicionada com sucesso!', 'success');
            closeModal('add-job-modal');
            document.getElementById('job-form').reset();
            loadJobs();
            loadDashboard();
        } else {
            throw new Error('Erro ao adicionar vaga');
        }
    } catch (error) {
        console.error('Erro ao adicionar vaga:', error);
        showMessage('Erro ao adicionar vaga', 'error');
    }
}

async function deleteJob(id) {
    if (!confirm('Tem certeza que deseja excluir esta vaga?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/vagas/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showMessage('Vaga excluída com sucesso!', 'success');
            loadJobs();
            loadDashboard();
        } else {
            throw new Error('Erro ao excluir vaga');
        }
    } catch (error) {
        console.error('Erro ao excluir vaga:', error);
        showMessage('Erro ao excluir vaga', 'error');
    }
}

// Matching Functions
function updateJobSelect() {
    const select = document.getElementById('job-select');
    select.innerHTML = '<option value="">Selecione uma vaga...</option>';
    
    jobs.forEach(job => {
        const option = document.createElement('option');
        option.value = job.id;
        option.textContent = job.titulo;
        select.appendChild(option);
    });
}

async function findMatches() {
    const jobId = document.getElementById('job-select').value;
    if (!jobId) {
        showMessage('Selecione uma vaga primeiro', 'info');
        return;
    }
    
    try {
        const resultsContainer = document.getElementById('matches-results');
        resultsContainer.innerHTML = '<div class="loading"><div class="spinner"></div></div>';
        
        const response = await fetch(`${API_BASE_URL}/matching/vaga/${jobId}`);
        
        if (response.ok) {
            currentMatches = await response.json();
            renderMatches();
            document.getElementById('total-matches').textContent = currentMatches.length;
        } else {
            throw new Error('Erro ao buscar matches');
        }
    } catch (error) {
        console.error('Erro ao buscar matches:', error);
        showMessage('Erro ao buscar matches', 'error');
        document.getElementById('matches-results').innerHTML = '<p>Erro ao carregar matches</p>';
    }
}

function renderMatches() {
    const container = document.getElementById('matches-results');
    
    if (currentMatches.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #6c757d;">Nenhum match encontrado para esta vaga.</p>';
        return;
    }
    
    container.innerHTML = currentMatches.map(match => {
        const scoreClass = getScoreClass(match.score);
        const scoreText = getScoreText(match.score);
        
        return `
            <div class="match-card ${scoreClass}">
                <div class="match-score ${scoreClass.replace('-score', '')}">${match.score.toFixed(1)}%</div>
                <div class="candidate-info">
                    <h4>${match.candidato.nome}</h4>
                    <p><strong>Email:</strong> ${match.candidato.email}</p>
                    <p><strong>Localização:</strong> ${match.candidato.localizacao}</p>
                    <p><strong>Experiência:</strong> ${match.candidato.anosDeExperiencia} anos</p>
                    <p><strong>Status:</strong> ${scoreText}</p>
                    <div class="skills-list">
                        ${match.candidato.habilidades.map(skill => {
                            const selectedJob = jobs.find(j => j.id === document.getElementById('job-select').value);
                            const isMatched = selectedJob && selectedJob.habilidadesRequeridas.includes(skill);
                            return `<span class="skill-tag ${isMatched ? 'matched' : ''}">${skill}</span>`;
                        }).join('')}
                    </div>
                </div>
            </div>
        `;
    }).join('');
}

function getScoreClass(score) {
    if (score >= 70) return 'high-score';
    if (score >= 40) return 'medium-score';
    return 'low-score';
}

function getScoreText(score) {
    if (score >= 70) return 'Alta compatibilidade';
    if (score >= 40) return 'Média compatibilidade';
    return 'Baixa compatibilidade';
}

// Modal Functions
function showAddCandidateModal() {
    document.getElementById('add-candidate-modal').style.display = 'block';
}

function showAddJobModal() {
    document.getElementById('add-job-modal').style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Utility Functions
function showLoading(elementId) {
    document.getElementById(elementId).innerHTML = '<tr><td colspan="6" style="text-align: center;"><div class="spinner" style="margin: 20px auto;"></div></td></tr>';
}

function showMessage(message, type) {
    // Create message element
    const messageEl = document.createElement('div');
    messageEl.className = `message ${type}`;
    messageEl.textContent = message;
    
    // Insert at top of main content
    const main = document.querySelector('.main');
    main.insertBefore(messageEl, main.firstChild);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (messageEl.parentNode) {
            messageEl.parentNode.removeChild(messageEl);
        }
    }, 5000);
}

// Close modals when clicking outside
window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// Periodic API status check
setInterval(checkApiStatus, 30000); // Check every 30 seconds