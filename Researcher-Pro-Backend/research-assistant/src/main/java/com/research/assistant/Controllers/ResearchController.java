package com.research.assistant.Controllers;

import com.research.assistant.Config.RabbitMQConfig;
import com.research.assistant.Model.RabbitMQMessage;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Services.ResearchService;
import com.research.assistant.Services.StatsService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;
    private final StatsService statsService;

    @Autowired
    private RabbitTemplate template;

    //Adding a Rate Limiter
    private final Bucket getStatsBucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
            .build();

    private final Bucket processBucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
            .build();

    private final Bucket deleteStatsBucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(60))))
            .build();

    private final Bucket geminiRecommendationsBucket = Bucket.builder()
            .addLimit(Bandwidth.classic(1, Refill.intervally(1, Duration.ofMinutes(60))))
            .build();

    //Adding a Rate Limiter

    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest request){
        if(processBucket.tryConsume(1)) {
            String result = researchService.processContent(request);

            RabbitMQMessage rabbitMQMessage = new RabbitMQMessage();
            rabbitMQMessage.setMessageId(UUID.randomUUID().toString());
            rabbitMQMessage.setMessageDate(new Date());
            rabbitMQMessage.setMessage("New Request: " + request.getOperation().toUpperCase());
            template.convertAndSend(RabbitMQConfig.DATA_EXCHANGE, RabbitMQConfig.DATA_ROUTING_KEY, rabbitMQMessage);

            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests - please wait a minute before retrying.");
        }
    }

    @GetMapping("/getstats")
    public ResponseEntity<String> getStats(){
        if(getStatsBucket.tryConsume(1)) {
            String stats = statsService.getStats();
            return ResponseEntity.ok(stats);
        }
        else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests - please wait a minute before retrying.");
        }
    }

    @DeleteMapping("/getstats")
    public ResponseEntity<String> deleteStats(){
        if(deleteStatsBucket.tryConsume(1)) {
            statsService.deleteStats();
            return ResponseEntity.status(HttpStatus.OK).body("Http Status is OK");
        }
        else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests - please wait a minute before retrying.");
        }
    }

    @GetMapping("/getrecommendations")
    public ResponseEntity<String> getGeminiRecommendations(){
        if(geminiRecommendationsBucket.tryConsume(1)) {
            String response = statsService.getGeminiRecommendations();
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests - please wait a minute before retrying.");
        }

    }

}
