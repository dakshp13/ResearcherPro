package com.research.assistant.Controllers;

import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Services.ResearchService;
import com.research.assistant.Services.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;
    private final StatsService statsService;

    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest request){
        String result = researchService.processContent(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getstats")
    public ResponseEntity<String> getStats(){
        String stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }

}
