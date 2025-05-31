document.addEventListener('DOMContentLoaded', () => {
    chrome.storage.local.get(['researchNotes'], function(result){
        if(result.researchNotes){
            document.getElementById('notes').value = result.researchNotes;
        }
    });
    document.getElementById('summarizeBtn').addEventListener('click', summarizeText);
    document.getElementById('mlaBtn').addEventListener('click', mlaCiteText);
    document.getElementById('apaBtn').addEventListener('click', apaCiteText);
    document.getElementById('ieeeBtn').addEventListener('click', ieeeCiteText);
    document.getElementById('chicagoBtn').addEventListener('click', chicagoCiteText);
    document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
});

async function mlaCiteText(){

}

async function apaCiteText(){

}

async function ieeeCiteText(){

}

async function chicagoCiteText(){
 
}

async function summarizeText(){
   geminiCommunicator('summarize');
}

async function geminiCommunicator(operation){
    try{
        //const [tab] = await chrome.tabs.query({ active:true, currentWindow: true});
        //const  [{result}] = await chrome.scripting.executeScript({
        //    target: {tabId: tab.id},
        //    function: () => window.getSelection().toString()
        // });

        const result = document.getElementById('contents').value; //Allow to Copy and Paste Text

        if(!result){
            showResult('Please select some text first');
            return;
        }

        const response = await fetch('http://localhost:8080/api/research/process', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify({ content: result, operation: operation})
        });

        if(!response.ok){
            throw new Error(`API Error: ${response.status}`);
        }

        const text = await response.text();
        showResult(text.replace(/\n/g,'<br>'));

    } catch (error) {
        showResult('Error: ' + error.message);
    }

}

async function saveNotes(){
    const notes = document.getElementById('notes').value;
    chrome.storage.local.set({ 'researchNotes': notes}, function() {
        alert('Notes saved successfully');
    });
}

function showResult(content){
    document.getElementById('results').innerHTML = `<div class="result-item"><div class="result-content">${content}</div></div>`;
}
