package capstone.ApplePie_Spring.Board.service;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.domain.File;
import capstone.ApplePie_Spring.Board.dto.*;
import capstone.ApplePie_Spring.Board.repository.BoardRepository;
import capstone.ApplePie_Spring.Board.resposne.ResponseBoard;
import capstone.ApplePie_Spring.Board.resposne.ResponseBoardAndTeam;
import capstone.ApplePie_Spring.Board.resposne.ResponseBoardList;
import capstone.ApplePie_Spring.Board.resposne.ResponseNoBoard;
import capstone.ApplePie_Spring.Profiles.response.ResponseNoProfiles;
import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.Team.dto.FindTeamDto;
import capstone.ApplePie_Spring.Team.repository.TeamRepository;
import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.repository.ProfileRepository;
import capstone.ApplePie_Spring.User.repository.UserRepository;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BoardRepository boardRepository;
    private final TeamRepository teamRepository;
    private final FileService fileService;

    private static final Integer STATUS = 1;

    @Override
    public Object save(BoardSaveDto boardSaveDto, List<MultipartFile> files) throws Exception {

        Optional<User> findUser = userRepository.findByIdAndStatus(boardSaveDto.getUserId(), STATUS);
        if (findUser.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.USER_FIND_NOT);
        }

        Board board = Board.builder()
                .boardSaveDto(boardSaveDto)
                .user(findUser.get())
                .build();
        Board createBoard = boardRepository.save(board);
        board.setFiles(addFiles(board, files));

        List<String> urls = board.getFiles().stream()
                .map(File::getUrl)
                .collect(Collectors.toList());

        FindOneBoardDto findOneBoardDto = FindOneBoardDto.builder()
                .board(board)
                .urlList(urls)
                .build();
        return new ResponseBoard(ExceptionCode.BOARD_CREATED_OK, findOneBoardDto);
    }

    private List<File> addFiles(Board board, List<MultipartFile> files) {
        List<File> fileList = new ArrayList<>();
        if (files != null) { // 파일 크기는 front에서 처리
            for (int i = 0; i < files.size(); i++) {
                fileList.add(fileService.save(board, i+1, files.get(i)));
            }
        }
        return fileList;
    }

    @Override
    public Object findOne(long boardId) {
        Optional<Board> findBoard = boardRepository.findByIdAndStatus(boardId, STATUS);
        if (findBoard.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.BOARD_FIND_NOT);
        }

        Board board = findBoard.get();
        board.addViewCount();
        board.setFiles(fileService.findByBoardId(boardId));

        List<String> urls = board.getFiles().stream()
                .map(File::getUrl)
                .collect(Collectors.toList());

        FindOneBoardDto findOneBoardDto = FindOneBoardDto.builder()
                .board(findBoard.get())
                .urlList(urls)
                .build();

        Optional<Team> findTeam = teamRepository.findByBoardIdAndStatus(board.getId(), STATUS);
        FindTeamDto teamDto = null;
        if (findTeam.isPresent()) {
            teamDto = FindTeamDto.builder()
                    .team(findTeam.get())
                    .build();
        }

        return new ResponseBoardAndTeam(ExceptionCode.BOARD_FIND_OK, findOneBoardDto, teamDto);
    }

    @Override
    public Object BoardPagesBy(FindBoardDto findBoardDto) {
        List<Board> boardList = fetchPages(findBoardDto);

        List<FindBoardListDto> findBoardListDtoList = new ArrayList<>();
        for (Board board : boardList) {
            findBoardListDtoList.add(FindBoardListDto.builder()
                    .board(board)
                    .file(fileService.findOne(board.getId(), 1))
                    .build());
        }

        return new ResponseBoardList(ExceptionCode.BOARD_FIND_OK, findBoardListDtoList);
    }

    @Override
    public Object update(Long id, BoardUpdateDto boardUpdateDto, List<MultipartFile> files) throws Exception {
        Optional<Board> findBoard = boardRepository.findByIdAndStatus(id, STATUS);
        if (findBoard.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.BOARD_FIND_NOT);
        }

        Board board = findBoard.get();
        Optional<User> findUser = userRepository.findByIdAndStatus(boardUpdateDto.getUserId(), STATUS);
        if (findUser.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.USER_FIND_NOT);
        }

        User user = findUser.get();
        if (!user.getId().equals(boardUpdateDto.getUserId())) {
            return new ResponseNoBoard(ExceptionCode.WRONG_APPROACH);
        }

        board.update(boardUpdateDto);
        board.setFiles(addFiles(board, files));

        List<String> urls = board.getFiles().stream()
                .map(File::getUrl)
                .collect(Collectors.toList());

        FindOneBoardDto findOneBoardDto = FindOneBoardDto.builder()
                .board(board)
                .urlList(urls)
                .build();
        return new ResponseBoard(ExceptionCode.BOARD_UPDATE_OK, findOneBoardDto);
    }

    @Override
    public Object delete(Long bid, Long uid) {
        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.USER_FIND_NOT);
        }

        Optional<Board> findBoard = boardRepository.findByIdAndStatus(bid, STATUS);
        if (findBoard.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.BOARD_FIND_NOT);
        }
        Board board = findBoard.get();
        board.delete();
        return new ResponseNoBoard(ExceptionCode.BOARD_DELETE_OK);
    }

    @Override
    public Object myBoardPagesBy(Long uid) {
        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty()) {
            return new ResponseNoProfiles(ExceptionCode.USER_PROFILE_FIND_NOT);
        }

        List<Board> findBoard = boardRepository
                .findAllByUserIdAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(findUser.get().getId(), STATUS, LocalDate.now());
        List<FindBoardListDto> findBoardListDtoList = new ArrayList<>();
        for (Board board : findBoard) {
            findBoardListDtoList.add(FindBoardListDto.builder()
                    .board(board)
                    .file(fileService.findOne(board.getId(), 1))
                    .build());
        }
        return new ResponseBoardList(ExceptionCode.BOARD_FIND_OK, findBoardListDtoList);
    }

    private List<Board> fetchPages(FindBoardDto findBoardDto)  {
        PageRequest pageRequest = PageRequest.of(0, findBoardDto.getSize());
        Board.Category category = Board.Category.getValue(findBoardDto.getCategoryId());

        Long checkId = findBoardDto.getId();
        if (checkId == null) { // 처음 조회
            if (findBoardDto.getKeyword() == null)
                return boardRepository.findAllByCategoryAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(category, STATUS, LocalDate.now(), pageRequest);
            return boardRepository.findAllByCategoryAndStatusAndTitleContainingAndDeadlineGreaterThanEqualOrderByIdDesc(category, findBoardDto.getKeyword(), STATUS, LocalDate.now(), pageRequest);
        }
        else { // 불러오기
            if (findBoardDto.getKeyword() == null)
                return boardRepository.findAllByIdLessThanAndCategoryAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc
                        (findBoardDto.getId(), category, STATUS, LocalDate.now(), pageRequest);
            return boardRepository.findAllByIdLessThanAndCategoryAndStatusAndTitleContainingAndDeadlineGreaterThanEqualOrderByIdDesc
                    (findBoardDto.getId(), category, STATUS, findBoardDto.getKeyword(), LocalDate.now(), pageRequest);
        }
    }

}
