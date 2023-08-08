package capstone.ApplePie_Spring.Board.service;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {
    File save(Board board, int fileNumber, MultipartFile file);
    List<File> findByBoardId(Long boardId);
    Optional<File> findOne(Long boardId, int number);
}
