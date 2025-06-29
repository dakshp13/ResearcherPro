document.addEventListener('DOMContentLoaded', () => {
    chrome.storage.local.get(['researchNotes'], function(result){
        if(result.researchNotes){
            document.getElementById('notes').value = result.researchNotes;
        }
    });
    //document.getElementById('suggestionsBtn').addEventListener('click', suggest);
   // document.getElementById('summarizeDetailedBtn').addEventListener('click', summarizeDetailedText);
    //document.getElementById('summarizeBriefBtn').addEventListener('click', summarizeBriefText);
   // document.getElementById('mlaBtn').addEventListener('click', mlaCiteText);
   // document.getElementById('apaBtn').addEventListener('click', apaCiteText);
   // document.getElementById('ieeeBtn').addEventListener('click', ieeeCiteText);
   // document.getElementById('chicagoBtn').addEventListener('click', chicagoCiteText);
   // document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
   
   //TESTING NEW LOGIC BELOW
    const suggestionsBtn = document.getElementById('suggestionsBtn');
    const summarizeDetailedBtn = document.getElementById('summarizeDetailedBtn');
    const summarizeBriefBtn = document.getElementById('summarizeBriefBtn');
    const mlaBtn = document.getElementById('mlaBtn');
    const apaBtn = document.getElementById('apaBtn');
    const ieeeBtn = document.getElementById('ieeeBtn');
    const chicagoBtn = document.getElementById('chicagoBtn');
    const saveNotesBtn = document.getElementById('saveNotesBtn');
    const openStatsBtn = document.getElementById("openStatsBtn");
    const goBackBtn = document.getElementById("goBackBtn");

    if (suggestionsBtn) {
        suggestionsBtn.addEventListener('click', suggest);
    }

    if(summarizeDetailedBtn) {
        summarizeDetailedBtn.addEventListener('click', summarizeDetailedText);
    }

    if(summarizeBriefBtn) {
        summarizeBriefBtn.addEventListener('click', summarizeBriefText);
    }

    if(mlaBtn) {
        mlaBtn.addEventListener('click', mlaCiteText);
    }

    if(apaBtn) {
        apaBtn.addEventListener('click', apaCiteText);
    }

    if(ieeeBtn) {
        ieeeBtn.addEventListener('click', ieeeCiteText);
    }

    if(chicagoBtn) {
        chicagoBtn.addEventListener('click', chicagoCiteText);
    }

    if(saveNotesBtn) {
        saveNotesBtn.addEventListener('click', saveNotes);
    }

    if (openStatsBtn) {
        openStatsBtn.addEventListener("click", () => {
        window.location.href = "stats.html";
        });
    }

    if (goBackBtn) {
        goBackBtn.addEventListener("click", () => {
         window.location.href = "sidepanel.html";
        });
    }





   //TESTING NEW LOGIC ABOVE

});

async function mlaCiteText(){
    geminiCommunicator('MLA Citation');
}

async function apaCiteText(){
    geminiCommunicator('APA Citation');
}

async function ieeeCiteText(){
    geminiCommunicator('IEEE Citation');
}

async function chicagoCiteText(){
    geminiCommunicator('Chicago-Style Citation');
}

async function summarizeDetailedText(){
   geminiCommunicator('summarize:detailed');
}

async function summarizeBriefText(){
    geminiCommunicator('summarize:brief');
}

async function suggest() {
    geminiCommunicator('suggest');
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
        else{
            showResult('Thinking ...');
        }

        const response = await fetch('http://localhost:8080/api/research/process', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify({ content: result, operation: operation})
        });

        if(!response.ok){
            throw new Error(`Hold on your going to fast for me to think: Wait a bit and then try again please ...`);
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
