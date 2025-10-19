// ==================== CONFIGURATION ====================
const CONFIG = {
    API_BASE: 'http://localhost:8080/dhvani',
    TOKEN_KEY: 'token',
    USERNAME_KEY: 'username'
};

// ==================== STATE MANAGEMENT (In-Memory Storage) ====================
const state = {
    token: null,
    currentUser: null,
    allSongs: [],
    allArtists: [],
    allAlbums: [],
    isInitialized: false
};

// ==================== INITIALIZATION ====================
window.onload = () => {
    console.log('Application loaded');
    // Note: In-memory storage means user will need to login each time
    // For persistent sessions, backend should implement refresh tokens
};

// ==================== AUTH FUNCTIONS ====================
function showAuthTab(tab) {
    const tabs = document.querySelectorAll('.auth-tab');
    tabs.forEach(t => t.classList.remove('active'));
    event.target.classList.add('active');

    document.getElementById('loginForm').style.display = tab === 'login' ? 'block' : 'none';
    document.getElementById('signupForm').style.display = tab === 'signup' ? 'block' : 'none';
}

async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${CONFIG.API_BASE}/user/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (data.status === 'SUCCESS') {
            state.token = data.data.token;
            state.currentUser = data.data.username;
            showMainContent();
            showAlert('authAlert', 'Login successful!', 'success');
        } else {
            showAlert('authAlert', data.message, 'error');
        }
    } catch (error) {
        console.error('Login error:', error);
        showAlert('authAlert', 'Login failed. Please check if the backend is running on port 8080.', 'error');
    }
}

async function handleSignup(e) {
    e.preventDefault();
    const password = document.getElementById('signupPassword').value;
    const confirmPassword = document.getElementById('signupConfirmPassword').value;

    if (password !== confirmPassword) {
        showAlert('authAlert', 'Passwords do not match', 'error');
        return;
    }

    const signupData = {
        username: document.getElementById('signupUsername').value,
        fullName: document.getElementById('signupFullName').value,
        phoneNumber: document.getElementById('signupPhone').value,
        dateOfBirth: document.getElementById('signupDob').value,
        gender: document.getElementById('signupGender').value,
        country: document.getElementById('signupCountry').value,
        password: password,
        confirmPassword: confirmPassword
    };

    try {
        const response = await fetch(`${CONFIG.API_BASE}/user/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(signupData)
        });

        const data = await response.json();

        if (data.status === 'SUCCESS') {
            showAlert('authAlert', 'Signup successful! Please login.', 'success');
            setTimeout(() => {
                document.querySelector('.auth-tab').click();
            }, 2000);
        } else {
            showAlert('authAlert', data.message, 'error');
        }
    } catch (error) {
        console.error('Signup error:', error);
        showAlert('authAlert', 'Signup failed. Please check if the backend is running.', 'error');
    }
}

function logout() {
    state.token = null;
    state.currentUser = null;
    state.allSongs = [];
    state.allArtists = [];
    state.allAlbums = [];
    document.getElementById('authSection').classList.add('active');
    document.getElementById('mainContent').classList.remove('active');
    document.getElementById('header').style.display = 'none';
}

function showMainContent() {
    document.getElementById('authSection').classList.remove('active');
    document.getElementById('mainContent').classList.add('active');
    document.getElementById('header').style.display = 'flex';
    document.getElementById('welcomeText').textContent = `Welcome, ${state.currentUser}`;
    if (!state.isInitialized) {
        loadAllData();
        state.isInitialized = true;
    }
}

// ==================== NAVIGATION ====================
function showSection(section) {
    const tabs = document.querySelectorAll('.nav-tab');
    tabs.forEach(t => t.classList.remove('active'));
    event.target.classList.add('active');

    const sections = document.querySelectorAll('.content-section');
    sections.forEach(s => s.classList.remove('active'));
    document.getElementById(`${section}Section`).classList.add('active');

    switch(section) {
        case 'songs': loadSongs(); break;
        case 'artists': loadArtists(); break;
        case 'albums': loadAlbums(); break;
        case 'playlists': loadPlaylists(); break;
        case 'recommendations': loadRecommendations(); break;
        case 'favorites': loadFavorites(); break;
    }
}

// ==================== DATA LOADING ====================
async function loadAllData() {
    await Promise.all([loadSongs(), loadArtists(), loadAlbums()]);
}

async function loadSongs() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/song`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        state.allSongs = data.data || [];
        displaySongs(state.allSongs);
    } catch (error) {
        console.error('Error loading songs:', error);
        showGridError('songsGrid', 'Failed to load songs');
    }
}

async function loadArtists() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/artist`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        state.allArtists = data.data || [];
        displayArtists(state.allArtists);
        populateArtistSelects();
    } catch (error) {
        console.error('Error loading artists:', error);
        showGridError('artistsGrid', 'Failed to load artists');
    }
}

async function loadAlbums() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/album`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        state.allAlbums = data.data || [];
        displayAlbums(state.allAlbums);
        populateAlbumSelects();
    } catch (error) {
        console.error('Error loading albums:', error);
        showGridError('albumsGrid', 'Failed to load albums');
    }
}

async function loadPlaylists() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/playlist/user`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        displayPlaylists(data.data || []);
    } catch (error) {
        console.error('Error loading playlists:', error);
        showGridError('playlistsGrid', 'Failed to load playlists');
    }
}

async function loadRecommendations() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/recommendation`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        displayRecommendations(data.data || []);
    } catch (error) {
        console.error('Error loading recommendations:', error);
        showGridError('recommendationsGrid', 'Failed to load recommendations');
    }
}

async function loadFavorites() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/user-song/favourite`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        displayFavorites(data.data || []);
    } catch (error) {
        console.error('Error loading favorites:', error);
        showGridError('favoritesGrid', 'Failed to load favorites');
    }
}

// ==================== DISPLAY FUNCTIONS ====================
function displaySongs(songs) {
    const grid = document.getElementById('songsGrid');
    if (!songs || songs.length === 0) {
        grid.innerHTML = '<div class="empty-state">No songs available</div>';
        return;
    }

    grid.innerHTML = songs.map(song => `
        <div class="card">
            <div class="card-title">${escapeHtml(song.title)}</div>
            <div class="card-info">Genre: ${song.genre}</div>
            <div class="card-info">Language: ${song.language}</div>
            <div class="card-info">Energy: ${song.energy}</div>
            <div class="card-info">Mood: ${song.mood}</div>
            <div class="card-actions">
                <button class="btn btn-primary btn-small" onclick="setPreference(${song.id}, 'LIKED')">❤️ Like</button>
                <button class="btn btn-primary btn-small" onclick="setPreference(${song.id}, 'FAVOURITE')">⭐ Favorite</button>
                <button class="btn btn-secondary btn-small" onclick="editSong(${song.id})">✏️ Edit</button>
                <button class="btn btn-danger btn-small" onclick="deleteSong(${song.id})">🗑️ Delete</button>
            </div>
        </div>
    `).join('');
}

function displayArtists(artists) {
    const grid = document.getElementById('artistsGrid');
    if (!artists || artists.length === 0) {
        grid.innerHTML = '<div class="empty-state">No artists available</div>';
        return;
    }

    grid.innerHTML = artists.map(artist => `
        <div class="card">
            <div class="card-title">${escapeHtml(artist.name)}</div>
            <div class="card-info">Country: ${artist.country}</div>
            <div class="card-info">Gender: ${artist.gender}</div>
            <div class="card-info">DOB: ${artist.dateOfBirth || 'N/A'}</div>
            <div class="card-actions">
                <button class="btn btn-secondary btn-small" onclick="viewArtistSongs(${artist.id})">🎵 View Songs</button>
                <button class="btn btn-danger btn-small" onclick="deleteArtist(${artist.id})">🗑️ Delete</button>
            </div>
        </div>
    `).join('');
}

function displayAlbums(albums) {
    const grid = document.getElementById('albumsGrid');
    if (!albums || albums.length === 0) {
        grid.innerHTML = '<div class="empty-state">No albums available</div>';
        return;
    }

    grid.innerHTML = albums.map(album => `
        <div class="card">
            <div class="card-title">${escapeHtml(album.title)}</div>
            <div class="card-info">Artist ID: ${album.artistId}</div>
            <div class="card-info">Release Date: ${album.releaseDate || 'N/A'}</div>
            <div class="card-actions">
                <button class="btn btn-secondary btn-small" onclick="viewAlbumSongs(${album.id})">🎵 View Songs</button>
                <button class="btn btn-danger btn-small" onclick="deleteAlbum(${album.id})">🗑️ Delete</button>
            </div>
        </div>
    `).join('');
}

function displayPlaylists(playlists) {
    const grid = document.getElementById('playlistsGrid');
    if (!playlists || playlists.length === 0) {
        grid.innerHTML = '<div class="empty-state">No playlists created yet</div>';
        return;
    }

    grid.innerHTML = playlists.map(playlist => `
        <div class="card">
            <div class="card-title">${escapeHtml(playlist.name)}</div>
            <div class="card-info">Created: ${new Date(playlist.creationTime).toLocaleDateString()}</div>
            <div class="card-actions">
                <button class="btn btn-primary btn-small" onclick="viewPlaylistSongs(${playlist.id})">🎵 View Songs</button>
                <button class="btn btn-danger btn-small" onclick="deletePlaylist(${playlist.id})">🗑️ Delete</button>
            </div>
        </div>
    `).join('');
}

function displayRecommendations(recommendations) {
    const grid = document.getElementById('recommendationsGrid');
    if (!recommendations || recommendations.length === 0) {
        grid.innerHTML = '<div class="empty-state">No recommendations available. Click "Generate New" to get recommendations!</div>';
        return;
    }

    grid.innerHTML = recommendations.map(rec => `
        <div class="card">
            <div class="card-title">${escapeHtml(rec.title || 'Song')}</div>
            <div class="card-info">Genre: ${rec.genre || 'N/A'}</div>
            <div class="card-info">Language: ${rec.language || 'N/A'}</div>
            <div class="card-actions">
                <button class="btn btn-primary btn-small" onclick="setPreference(${rec.id}, 'LIKED')">❤️ Like</button>
            </div>
        </div>
    `).join('');
}

function displayFavorites(favorites) {
    const grid = document.getElementById('favoritesGrid');
    if (!favorites || favorites.length === 0) {
        grid.innerHTML = '<div class="empty-state">No favorite songs yet</div>';
        return;
    }

    grid.innerHTML = favorites.map(fav => `
        <div class="card">
            <div class="card-title">${escapeHtml(fav.title || 'Song')}</div>
            <div class="card-info">Genre: ${fav.genre || 'N/A'}</div>
            <div class="card-info">Language: ${fav.language || 'N/A'}</div>
            <div class="card-actions">
                <button class="btn btn-danger btn-small" onclick="removePreference(${fav.id})">Remove</button>
            </div>
        </div>
    `).join('');
}

// ==================== FILTERING ====================
function filterSongs(type, value) {
    if (!value) {
        displaySongs(state.allSongs);
        return;
    }
    const filtered = state.allSongs.filter(song => song[type] === value);
    displaySongs(filtered);
}

// ==================== MODAL FUNCTIONS ====================
function openSongModal() {
    document.getElementById('songModal').classList.add('active');
    document.getElementById('songForm').reset();
    document.getElementById('songId').value = '';
    document.getElementById('songModalTitle').textContent = 'Add Song';
}

function openArtistModal() {
    document.getElementById('artistModal').classList.add('active');
    document.getElementById('artistForm').reset();
}

function openAlbumModal() {
    document.getElementById('albumModal').classList.add('active');
    document.getElementById('albumForm').reset();
}

function openPlaylistModal() {
    document.getElementById('playlistModal').classList.add('active');
    document.getElementById('playlistForm').reset();
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// ==================== FORM SUBMISSIONS ====================
async function handleSongSubmit(e) {
    e.preventDefault();

    const songData = {
        title: document.getElementById('songTitle').value,
        genre: document.getElementById('songGenre').value,
        energy: document.getElementById('songEnergy').value,
        mood: document.getElementById('songMood').value,
        language: document.getElementById('songLanguage').value,
        artistId: parseInt(document.getElementById('songArtist').value),
        albumId: parseInt(document.getElementById('songAlbum').value) || 0,
        tempo: parseFloat(document.getElementById('songTempo').value)
    };

    const songId = document.getElementById('songId').value;
    const url = songId ? `${CONFIG.API_BASE}/song/${songId}` : `${CONFIG.API_BASE}/song`;
    const method = songId ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}`
            },
            body: JSON.stringify(songData)
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            closeModal('songModal');
            loadSongs();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to save song');
    }
}

async function handleArtistSubmit(e) {
    e.preventDefault();

    const artistData = {
        name: document.getElementById('artistName').value,
        country: document.getElementById('artistCountry').value,
        gender: document.getElementById('artistGender').value,
        dateOfBirth: document.getElementById('artistDob').value
    };

    try {
        const response = await fetch(`${CONFIG.API_BASE}/artist`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}`
            },
            body: JSON.stringify(artistData)
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            closeModal('artistModal');
            loadArtists();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to create artist');
    }
}

async function handleAlbumSubmit(e) {
    e.preventDefault();

    const albumData = {
        title: document.getElementById('albumTitle').value,
        artistId: parseInt(document.getElementById('albumArtist').value),
        releaseDate: document.getElementById('albumReleaseDate').value
    };

    try {
        const response = await fetch(`${CONFIG.API_BASE}/album`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}`
            },
            body: JSON.stringify(albumData)
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            closeModal('albumModal');
            loadAlbums();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to create album');
    }
}

async function handlePlaylistSubmit(e) {
    e.preventDefault();

    const playlistData = {
        name: document.getElementById('playlistName').value
    };

    try {
        const response = await fetch(`${CONFIG.API_BASE}/playlist`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}`
            },
            body: JSON.stringify(playlistData)
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            closeModal('playlistModal');
            loadPlaylists();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to create playlist');
    }
}

// ==================== DELETE FUNCTIONS ====================
async function deleteSong(id) {
    if (!confirm('Are you sure you want to delete this song?')) return;

    try {
        const response = await fetch(`${CONFIG.API_BASE}/song/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadSongs();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to delete song');
    }
}

async function deleteArtist(id) {
    if (!confirm('Are you sure you want to delete this artist?')) return;

    try {
        const response = await fetch(`${CONFIG.API_BASE}/artist/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadArtists();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to delete artist');
    }
}

async function deleteAlbum(id) {
    if (!confirm('Are you sure you want to delete this album?')) return;

    try {
        const response = await fetch(`${CONFIG.API_BASE}/album/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadAlbums();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to delete album');
    }
}

async function deletePlaylist(id) {
    if (!confirm('Are you sure you want to delete this playlist?')) return;

    try {
        const response = await fetch(`${CONFIG.API_BASE}/playlist/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadPlaylists();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to delete playlist');
    }
}

// ==================== PREFERENCE FUNCTIONS ====================
async function setPreference(songId, preference) {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/user-song/${songId}/preference?preference=${preference}`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            alert(`Song ${preference.toLowerCase()}!`);
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to set preference');
    }
}

async function removePreference(songId) {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/user-song/${songId}/preference`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadFavorites();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to remove preference');
    }
}

// ==================== RECOMMENDATION FUNCTIONS ====================
async function generateRecommendations() {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/recommendation/generate`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });

        const data = await response.json();
        if (data.status === 'SUCCESS') {
            loadRecommendations();
        } else {
            alert(data.message);
        }
    } catch (error) {
        alert('Failed to generate recommendations');
    }
}

// ==================== HELPER FUNCTIONS ====================
function populateArtistSelects() {
    const selects = [
        document.getElementById('songArtist'),
        document.getElementById('albumArtist')
    ];

    selects.forEach(select => {
        if (select) {
            select.innerHTML = '<option value="">Select Artist</option>' +
                state.allArtists.map(artist =>
                    `<option value="${artist.id}">${escapeHtml(artist.name)}</option>`
                ).join('');
        }
    });
}

function populateAlbumSelects() {
    const select = document.getElementById('songAlbum');
    if (select) {
        select.innerHTML = '<option value="0">No Album</option>' +
            state.allAlbums.map(album =>
                `<option value="${album.id}">${escapeHtml(album.title)}</option>`
            ).join('');
    }
}

function editSong(id) {
    const song = state.allSongs.find(s => s.id === id);
    if (!song) return;

    document.getElementById('songId').value = song.id;
    document.getElementById('songTitle').value = song.title;
    document.getElementById('songGenre').value = song.genre;
    document.getElementById('songEnergy').value = song.energy;
    document.getElementById('songMood').value = song.mood;
    document.getElementById('songLanguage').value = song.language;
    document.getElementById('songArtist').value = song.artistId;
    document.getElementById('songAlbum').value = song.albumId || 0;
    document.getElementById('songTempo').value = song.tempo;
    document.getElementById('songModalTitle').textContent = 'Edit Song';
    document.getElementById('songModal').classList.add('active');
}

function viewArtistSongs(artistId) {
    showSection('songs');
    const filtered = state.allSongs.filter(song => song.artistId === artistId);
    displaySongs(filtered);
}

function viewAlbumSongs(albumId) {
    showSection('songs');
    const filtered = state.allSongs.filter(song => song.albumId === albumId);
    displaySongs(filtered);
}

async function viewPlaylistSongs(playlistId) {
    try {
        const response = await fetch(`${CONFIG.API_BASE}/playlist/${playlistId}/songs`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();

        if (data.status === 'SUCCESS') {
            showSection('songs');
            displaySongs(data.data || []);
        }
    } catch (error) {
        alert('Failed to load playlist songs');
    }
}

function showAlert(elementId, message, type) {
    const alertDiv = document.getElementById(elementId);
    alertDiv.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
    setTimeout(() => {
        alertDiv.innerHTML = '';
    }, 5000);
}

function showGridError(gridId, message) {
    const grid = document.getElementById(gridId);
    grid.innerHTML = `<div class="empty-state">${message}</div>`;
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.classList.remove('active');
    }
}