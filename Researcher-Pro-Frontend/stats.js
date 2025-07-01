document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("goBackBtn").addEventListener("click", () => {
         window.location.href = "sidepanel.html";
        });
    document.getElementById('getStatsBtn').addEventListener('click', getStats);
});

async function getStats() {
    try{

        showResult('Fetching Data ...');

        const response = await fetch('http://localhost:8080/api/research/getstats', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json'},
        });

        if(response.status === 429){
            throw new Error(`Too Many Requests, Please Try Again Later ...`);
        }

        if(!response.ok){
            throw new Error(`Hold on your going to fast for me to think: Wait a bit and then try again please ...`);
        }

        const text = await response.text();
        showResult(text.replace(/\n/g,'<br>'));

    } catch (error) {
        showResult('Error: ' + error.message);
    }
}

function showResult(content){
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}