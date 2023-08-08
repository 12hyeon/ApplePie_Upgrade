package capstone.ApplePie_Spring.AI.service;

import capstone.ApplePie_Spring.AI.Response.ResponseAI;
import capstone.ApplePie_Spring.AI.domain.AI;
import capstone.ApplePie_Spring.AI.dto.AIFindDto;
import capstone.ApplePie_Spring.AI.dto.categoryFind;
import capstone.ApplePie_Spring.AI.repository.AIRepository;
import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.dto.FindBoardCategoryDto;
import capstone.ApplePie_Spring.Board.dto.FindBoardListDto;
import capstone.ApplePie_Spring.Board.dto.FindOneBoardDto;
import capstone.ApplePie_Spring.Board.repository.BoardRepository;
import capstone.ApplePie_Spring.Board.resposne.ResponseBoardList;
import capstone.ApplePie_Spring.Board.resposne.ResponseNoBoard;
import capstone.ApplePie_Spring.Board.service.FileService;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AIService {
    private final AIRepository aiRepository;

    private static final Integer STATUS = 1;

    private final FileService fileService;

    private final BoardRepository boardRepository;

    //각 카테고리 별 서비스 존재해야 할 듯?
    public Object getProjectAI(AIFindDto req){
        AI.Category category = AI.Category.getValue(req.getCategoryId());
        Board.Category category1 = Board.Category.getValue(req.getCategoryId());

        System.out.println(category);

        List<AI> ai = aiRepository.findAllByUserIdAndCategory(req.getUser_id(), category);

        System.out.println(ai);

        //최신거 하나만 긁어오기. 자료형 달라질 듯
        //만약 세부 카테고리 별 API 만드려면 위 sql이 Id와 category로 찾아오기 -> 존재하는지만? 찾을 필요는 없을 듯
        // 팀 카테고리에 지원한게 존재하는지? 외주 카테고리에 지원한게 존재하는지 등 => 존재 안하면 조회수 많은거 추천

        List<FindBoardListDto> findBoardListDtoList = new ArrayList<>();

        //if문으로 지원 내역 존재하는지 체크
        if(ai.isEmpty()){
            //List<Board> team_all_board = boardRepository.ViewCount(category1, STATUS);
            List<Board> team_all_board = boardRepository.findAllByCategoryAndStatusAndDeadlineGreaterThanEqualOrderByViewCountDesc(category1, LocalDate.now(), STATUS);


            System.out.println(team_all_board);


            List<Board> team_rating_board = new ArrayList<>();

            team_rating_board.add(team_all_board.get(0));
            team_rating_board.add(team_all_board.get(1));
            team_rating_board.add(team_all_board.get(2));


            for (Board board111 : team_rating_board) {
                findBoardListDtoList.add(FindBoardListDto.builder()
                        .board(board111)
                        .file(fileService.findOne(board111.getId(), 1))
                        .build());
            }

        }
        else{
            List<Board> board_1 = boardRepository.findAllByCategoryAndStatusAndDeadlineGreaterThanEqual(category1, LocalDate.now(), STATUS);

            System.out.println(category1);
            System.out.println(board_1);

            if (board_1.isEmpty()) {
                return new ResponseNoBoard(ExceptionCode.USER_FIND_NOT);
            }
            //모든 글 가져오기. 자료형 달라질 듯 + status

        /*
        List<FindBoardCategoryDto> board_1 = new ArrayList<>();

        for(int i =0; i<board.size(); i++){
            if(board.get(i).getRole().get(ai.get(0).getRole())>0) //ai 테이블에 저장되는것이 사용자가 지원한 역할의 숫자랑 맵핑?
                //ai 테이블에 존재하는 role이 정확히 어떤값인지 알아야 함
                board_1.add(board.get(i));
        }
        */

            //DB 저장 내용 중 List에서 int 확인하는거니까 mysql list 중 해당하는 숫자 있는 컬럼만 select 해오는 방법 찾기
            // 전체 board의 role 컬럼을 가져오고 role 컬럼과 role int를 비교하기
            double arr[][] = new double[board_1.size()][2];

            System.out.println(ai.get(0).getContent());
            System.out.println(board_1.size());

            for(int i =0; i<board_1.size()-1; i++){
                double similarity = findSimilarity(ai.get(0).getContent() + ai.get(0).getTitle(),
                        board_1.get(i).getContent() + board_1.get(i).getTitle());
                arr[i][0] = board_1.get(i).getId();
                arr[i][1] = similarity;
                //2차원 배열로 유사도 + board_id 저장
            }

            Arrays.sort(arr, new Comparator<double[]>() {
                public int compare(double[] o1, double[] o2) {
                    return Double.compare(o1[1], o2[1]);
                }
            });

            Long id1 = (long)arr[1][0];
            Long id2 = (long)arr[2][0];
            Long id3 = (long)arr[3][0];

            Optional<Board> findBoard1 = boardRepository.findById(id1);
            Optional<Board> findBoard2 = boardRepository.findById(id2);
            Optional<Board> findBoard3 = boardRepository.findById(id3);

            List<Board> boardList = new ArrayList<>();
            boardList.add(findBoard1.get());
            boardList.add(findBoard2.get());
            boardList.add(findBoard3.get());

            //List<FindBoardListDto> findBoardListDtoList = new ArrayList<>();
            for (Board board111 : boardList) {
                findBoardListDtoList.add(FindBoardListDto.builder()
                        .board(board111)
                        .file(fileService.findOne(board111.getId(), 1))
                        .build());
            }


            //2차원 배열의 유사도 값을 sort -> sort한 거 중 큰 거 3개의 board_id를 sql 후 return

            // Y에 Board 내용들 중 content + title 한 값 비교. 그 후, 해당 값이 일정 이상 넘기면 board_id list에 추가
            //-> 일정 이상 넘겼다는 것을 확인 하기 위해 유사도 분석 후 유사도 값과 board_id 추가. 이후 상위 5개에 대한 board_id 긁어오고 sql한 다음 return



            // 전체 글 중 AI 테이블에 저장된 카테고리로 sql (카테고리값과 role값은 해당 유저의 가장 최근 생성된 AI 테이블의 카테고리 별 테이블 값)
            // -> 이 사람이 지원한 카테고리가 3개라면 3개, 2개라면 2개가 나옴
            // 만약 팀원 모집이라면 팀원 모집 글 중 role로 sql
            // 총 3개의 카테고리 sql
        }

        return new ResponseBoardList(ExceptionCode.BOARD_FIND_OK, findBoardListDtoList);

    }

    public static int getLevenshteinDistance(String X, String Y)
    {
        int m = X.length();
        int n = Y.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public static double findSimilarity(String x, String y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            // 필요한 경우 선택적으로 대소문자를 무시합니다.
            return (maxLength - getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }


    /*
    public List<ResponseAI> getOutAI(AIFindDto req){
        List<categoryFind> ai = aiRepository.findAllById(req.getUser_id()); //최신거 하나만 긁어오기. 자료형 달라질 듯
        //만약 세부 카테고리 별 API 만드려면 위 sql이 Id와 category로 찾아오기 -> 존재하는지만? 찾을 필요는 없을 듯
        // 팀 카테고리에 지원한게 존재하는지? 외주 카테고리에 지원한게 존재하는지 등 => 존재 안하면 조회수 많은거 추천

        List<FindBoardCategoryDto> board = boardRepository.findAllByCategory(ai.get(0).getCategory()); //모든 글 가져오기. 자료형 달라질 듯
        //ai를 리스트 형태로 받아오고, Board sql에 인자로 주려고 하면 어떻게 써야하지????? -> 인자 저렇게 쓰면 될 듯??
        if(req.getCategory() == "team"){

            //DB 저장 내용 중 List에서 int 확인하는거니까 mysql list 중 해당하는 숫자 있는 컬럼만 select 해오는 방법 찾기
            // 전체 board의 role 컬럼을 가져오고 role 컬럼과 role int를 비교하기

        }

        public List<ResponseAI> getLessonAI(AIFindDto req){
            List<categoryFind> ai = aiRepository.findAllById(req.getUser_id()); //최신거 하나만 긁어오기. 자료형 달라질 듯
            //만약 세부 카테고리 별 API 만드려면 위 sql이 Id와 category로 찾아오기 -> 존재하는지만? 찾을 필요는 없을 듯
            // 팀 카테고리에 지원한게 존재하는지? 외주 카테고리에 지원한게 존재하는지 등 => 존재 안하면 조회수 많은거 추천

            List<FindBoardCategoryDto> board = boardRepository.findAllByCategory(ai.get(0).getCategory()); //모든 글 가져오기. 자료형 달라질 듯
            //ai를 리스트 형태로 받아오고, Board sql에 인자로 주려고 하면 어떻게 써야하지????? -> 인자 저렇게 쓰면 될 듯??
            if(req.getCategory() == "team"){

                //DB 저장 내용 중 List에서 int 확인하는거니까 mysql list 중 해당하는 숫자 있는 컬럼만 select 해오는 방법 찾기
                // 전체 board의 role 컬럼을 가져오고 role 컬럼과 role int를 비교하기

            }

    */
}