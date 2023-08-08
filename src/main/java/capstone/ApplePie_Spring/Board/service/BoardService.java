package capstone.ApplePie_Spring.Board.service;

import capstone.ApplePie_Spring.Board.dto.FindBoardDto;
import capstone.ApplePie_Spring.Board.dto.BoardSaveDto;
import capstone.ApplePie_Spring.Board.dto.BoardUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    Object save(BoardSaveDto boardSaveDto, List<MultipartFile> files) throws Exception;
    Object findOne(long boardId);
    Object BoardPagesBy(FindBoardDto findBoardDto);
    Object update(Long id, BoardUpdateDto boardUpdateDto, List<MultipartFile> files) throws Exception;
    Object delete(Long bid, Long uid);

    Object myBoardPagesBy(Long uid);
}
