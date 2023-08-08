package capstone.ApplePie_Spring.AI.controller;

import capstone.ApplePie_Spring.AI.Response.ResponseAI;
import capstone.ApplePie_Spring.AI.dto.AIFindDto;
import capstone.ApplePie_Spring.AI.service.AIService;
import capstone.ApplePie_Spring.Board.resposne.ResponseBoardList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ai")
@RestController
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService){this.aiService=aiService;}

    //각 카테고리 별 postmapping이 필요해보임
    @PostMapping()
    public ResponseEntity<Object> getProjectAI(@RequestBody AIFindDto req){
        return new ResponseEntity<>(aiService.getProjectAI(req), HttpStatus.OK);
    }

    /*
    @PostMapping("/out")
    public ResponseEntity<Object> getOutAI(@RequestBody AIFindDto req){
        return aiService.getOutAI();
    }

    @PostMapping("/lesson")
    public ResponseEntity<Object> getLessonAI(@RequestBody AIFindDto req){
        return aiService.getLessonAI();
    }
    */

}