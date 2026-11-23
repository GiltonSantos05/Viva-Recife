const CIDADE_PADRAO = 'Recife';

const map = L.map('map').setView([-8.01341, -34.9682], 6);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors'
}).addTo(map);

const barraBusca = document.getElementById('searchInput').closest('.position-absolute');
const input = document.getElementById('searchInput');

// Botão para voltar a pesquisar
const btnNova = document.createElement('button');
btnNova.className = 'btn btn-primary position-absolute d-none';
btnNova.style.cssText = 'top: 12px; left: 12px; z-index: 1000;';
btnNova.textContent = 'Nova busca';
document.body.appendChild(btnNova);

let marcacoes = [];

function limparMarcacoes() {
    marcacoes.forEach(m => map.removeLayer(m));
    marcacoes = [];
    document.getElementById('listaResultados')?.remove();
}

function separarEndereco(texto) {
    // "Rua X, 249" | "Rua X 249"
    let m = texto.match(/^(.+?)[,\s]+(\d+)\s*$/);
    if (m) return { rua: m[1].trim(), numero: m[2] };
    // "249 Rua X"
    m = texto.match(/^(\d+)[,\s]+(.+)$/);
    if (m) return { rua: m[2].trim(), numero: m[1] };
    return { rua: texto, numero: null };
}

const LOCATIONIQ_KEY = 'pk.9b6ccb50202890ffa38a2a0a5a57dbf4';

async function geocodificar(endereco) {
    const b = map.getBounds();

    const params = new URLSearchParams({
        key: LOCATIONIQ_KEY,
        q: endereco,
        format: 'json',
        addressdetails: 1,
        countrycodes: 'br',
        limit: 8,
        // área visível: oeste,norte,leste,sul
        viewbox: [b.getWest(), b.getNorth(), b.getEast(), b.getSouth()].join(','),
        'accept-language': 'pt-BR'
    });

    const response = await fetch(`https://us1.locationiq.com/v1/search?${params}`);

    if (response.status === 404) return [];   // LocationIQ devolve 404 quando não acha nada
    if (!response.ok) throw new Error(`Erro ${response.status}`);

    const data = await response.json();

    return data.map(r => ({
        lat: r.lat,
        lon: r.lon,
        display_name: r.display_name,
        temNumero: Boolean(r.address?.house_number)
    }));
}

function criarPopup(texto, aviso) {
    const div = document.createElement('div');
    div.textContent = texto;
    if (aviso) {
        const p = document.createElement('p');
        p.className = 'text-danger small mb-0 mt-2';
        p.textContent = aviso;
        div.appendChild(p);
    }
    return div;
}

function mostrarLista(resultados) {
    const lista = document.createElement('div');
    lista.id = 'listaResultados';
    lista.className = 'list-group position-absolute start-50 translate-middle-x w-100';
    lista.style.cssText = 'top: 60px; max-width: 600px; z-index: 1000; max-height: 50vh; overflow-y: auto;';

    resultados.forEach((r, i) => {
        const item = document.createElement('button');
        item.className = 'list-group-item list-group-item-action';
        item.textContent = r.display_name;
        item.addEventListener('click', () => {
            map.setView([r.lat, r.lon], 17);
            marcacoes[i].openPopup();
        });
        lista.appendChild(item);
    });

    document.body.appendChild(lista);
}

document.getElementById('searchBtn').addEventListener('click', async () => {
    const pesquisa = input.value.trim();
    if (!pesquisa) return;

    const { rua, numero } = separarEndereco(pesquisa);

    try {
        const textoBusca = numero ? `${rua}, ${numero}, ${CIDADE_PADRAO}` : pesquisa;
        let resultados = await geocodificar(textoBusca);
        let numeroNaoEncontrado = false;

        if (numero) {
            const comNumero = resultados.filter(r => r.temNumero);
            if (comNumero.length > 0) {
                resultados = comNumero;
            } else {
                numeroNaoEncontrado = true;
            }
        }

        if (resultados.length === 0) {
            alert('Nenhum local com este nome encontrado.');
            return;
        }

        limparMarcacoes();

        const aviso = numeroNaoEncontrado
            ? `O número ${numero} não está no OpenStreetMap. Mostrando a rua.`
            : null;

        resultados.forEach(r => {
            const m = L.marker([r.lat, r.lon]).addTo(map)
                .bindPopup(criarPopup(r.display_name, aviso));
            marcacoes.push(m);
        });

        if (marcacoes.length === 1) {
            const r = resultados[0];
            map.setView([r.lat, r.lon], numeroNaoEncontrado ? 17 : 19);
            marcacoes[0].openPopup();
        } else {
            mostrarLista(resultados);
            map.fitBounds(L.featureGroup(marcacoes).getBounds(), { padding: [50, 50], maxZoom: 16 });
        }

        // Esconde a barra e mostra o botão "Nova busca"
        barraBusca.classList.add('d-none');
        btnNova.classList.remove('d-none');

    } catch (error) {
        console.log('Ocorreu um erro ao buscar o local', error);
        alert('Ocorreu um erro ao buscar o local: ' + error.message);
    }
});

btnNova.addEventListener('click', () => {
    limparMarcacoes();
    input.value = '';
    barraBusca.classList.remove('d-none');
    btnNova.classList.add('d-none');
    input.focus();
});

input.addEventListener('keydown', e => {
    if (e.key === 'Enter') document.getElementById('searchBtn').click();
});