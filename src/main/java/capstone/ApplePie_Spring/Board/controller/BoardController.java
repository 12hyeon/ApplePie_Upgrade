package capstone.ApplePie_Spring.Board.controller;

import capstone.ApplePie_Spring.Board.dto.BoardUserDto;
import capstone.ApplePie_Spring.Board.dto.FindBoardDto;
import capstone.ApplePie_Spring.Board.dto.BoardSaveDto;
import capstone.ApplePie_Spring.Board.dto.BoardUpdateDto;
import capstone.ApplePie_Spring.Board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 글 생성
    @PostMapping
    public ResponseEntity<Object> createBoard(
            @RequestPart(value = "board") BoardSaveDto boardSaveDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files) throws Exception {
        return new ResponseEntity<>(boardService.save(boardSaveDto, files), HttpStatus.OK);
    }

    // 단일 글 조회
    @GetMapping("/{bid}")
    public ResponseEntity<Object> getBoardOne(@PathVariable long bid) {
        return new ResponseEntity<>(boardService.findOne(bid), HttpStatus.OK);
    }


    // 전체 글 조회
    @PostMapping("/all") // get
    public ResponseEntity<Object> getBoards(@RequestBody FindBoardDto findBoardDto) {
        return new ResponseEntity<>(boardService.BoardPagesBy(findBoardDto), HttpStatus.OK);
    }


    // 수정
    @PutMapping("/{bid}")
    public ResponseEntity<Object> getBoardOne(@PathVariable long bid,
                                              @RequestPart(value = "board") BoardUpdateDto boardUpdateDto,@RequestPart(value = "file", required = false) List<MultipartFile> files) throws Exception {
        return new ResponseEntity<>(boardService.update(bid, boardUpdateDto, files), HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{bid}")
    public ResponseEntity<Object> delete(@PathVariable long bid, @RequestBody BoardUserDto boardUserDto) {
        return new ResponseEntity<>(boardService.delete(bid, boardUserDto.getUserId()), HttpStatus.OK);
    }
}
