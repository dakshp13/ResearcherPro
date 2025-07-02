document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("goBackBtn").addEventListener("click", () => {
         window.location.href = "sidepanel.html";
        });
    document.getElementById('getStatsBtn').addEventListener('click', getStats);
    document.getElementById('geminiRecommendsBtn').addEventListener('click', getGeminiRecommendations);
    document.getElementById('deleteBtn').addEventListener('click', deleteData);
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

async function getGeminiRecommendations() {
    try {

        showResult2('Analyzing Data ...');

        const response = await fetch('http://localhost:8080/api/research/getrecommendations', {
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
        showResult2(text.replace(/\n/g,'<br>'));
        

    } catch (error) {
        showResult2('Error: ' + error.message);
    }
}

async function deleteData() {

    try {

        const response = await fetch('http://localhost:8080/api/research/getstats', {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json'},
        });

        if(response.status === 429){
            alert(`Too Many Requests, Please Try Again Later ...`);
        }

        if(!response.ok){
            alert(`Hold on your going to fast for me to think: Wait a bit and then try again please ...`);
        }

        if(response.ok){
            alert('Data Deleted Successfully');
        }
        

    } catch (error) {
        alert('Error in Deleting Data');
    }

}

function showResult(content){
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}

function showResult2(content){
    document.getElementById('results2').innerHTML = `<div class="result2-item"><div class="result2-content">${content}</div></div>`;
}