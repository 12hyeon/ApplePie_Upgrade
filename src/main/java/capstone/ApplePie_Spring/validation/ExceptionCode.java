package capstone.ApplePie_Spring.validation;

import java.util.Arrays;

import static capstone.ApplePie_Spring.validation.HttpStatus.*;

public enum ExceptionCode {
    /**
     * 회원가입 및 로그인
     */
    SIGNUP_CREATED_OK(SUCCESS, "A000", "회원가입 성공"),
    SIGNUP_DUPLICATED_EMAIL(NOT_FOUND_VALUE, "A002", "중복된 EMAIL"),
    SIGNUP_DUPLICATED_NICKNAME(NOT_FOUND_VALUE, "A003", "중복된 NICKNAME"),
    WITHDRAW_USER_OK(SUCCESS, "A004", "회원 탈퇴 성공"),

    LOGIN_OK(SUCCESS, "B000", "로그인 성공"),
    LOGIN_NOT_FOUND_EMAIL(NOT_FOUND_VALUE, "B001", "잘못된 EMAIL"),
    LOGIN_NOT_FOUND_PW(NOT_FOUND_VALUE, "B002", "잘못된 PASSWORD"),

    USER_ERROR(NOT_FOUND_VALUE, "C000", "처리 실패"),
    USER_PROFILE_FIND_OK(SUCCESS, "C001", "회원 조회 성공"),
    USER_FIND_NOT(INVALID_ACCESS, "C002", "회원 조회 실패"),
    USER_PROFILE_UPDATE_OK(SUCCESS, "C003", "회원정보 수정 성공"),
    USER_PROFILE_FIND_NOT(NOT_FOUND_VALUE, "C004", "PROFILE 없음"),

    PROFILES_CREATED_OK(SUCCESS, "D000", "세부 PROFILE 생성"),
    PROFILES_CREATED_ERROR(DUPLICATED_VALUE, "D001", "세부 PROFILES 이미 존재"),
    PROFILES_FIND_NOT(NOT_FOUND_VALUE, "D002", "세부 PROFILES 없음"),
    PROFILES_FIND_OK(SUCCESS, "D003", "세부 PROFILE 조회 성공"),
    PROFILES_OPEN_OK(SUCCESS, "D004", "세부 PROFILE 공개"),
    PROFILES_CLOSE_OK(SUCCESS, "D005", "세부 PROFILE 비공개"),

    BOARD_CREATED_OK(SUCCESS, "H00", "BOARD 생성"),
    BOARD_FIND_NOT(NOT_FOUND_VALUE, "H001", "BOARD 찾을 수 없음"),
    BOARD_FIND_OK(SUCCESS, "H002", "BOARD 조회 성공"),
    BOARD_UPDATE_OK(SUCCESS, "H003", "BOARD 수정 성공"),
    BOARD_DELETE_OK(SUCCESS, "H004", "BOARD 삭제 성공"),
    BOARD_DELETE_NOT(NOT_FOUND_VALUE, "H005", "BOARD의 작성자가 아님"),

    FILE_SAVE_NOT(INVALID_ACCESS, "I000", "FILE 저장 실패"),

    TEAM_CREATED_OK(SUCCESS, "J000", "TEAM 생성"),
    TEAM_CREATED_ERROR(DUPLICATED_VALUE, "J001", "TEAM 이미 존재"),
    TEAM_FIND_NOT(NOT_FOUND_VALUE, "J002", "TEAM 찾을 수 없음"),
    TEAM_FIND_OK(SUCCESS, "J003", "TEAM 조회 성공"),
    TEAM_COMPLETE(SUCCESS, "J004", "TEAM 마감"),
    TEAM_DELETE_OK(SUCCESS, "J005", "TEAM 삭제 성공"),
    TEAM_DELETE_NOT(NOT_FOUND_VALUE, "J006", "TEAM 생성자가 아님"),
    TEAM_UPDATE_OK(SUCCESS, "J007", "TEAM 업데이트"),

    VOLUNTEER_OK(SUCCESS, "K000", "지원 완료"),
    VOLUNTEER_CANCEL_OK(SUCCESS, "K001", "지원 취소"),
    VOLUNTEER_CREATED_ERROR(DUPLICATED_VALUE, "K002", "이미 지원한 TEAM"),
    VOLUNTEER_CANCEL_NOT(INVALID_ACCESS, "K003", "지원 취소 불가(팀원)"),
    VOLUNTEER_FIND_NOT(NOT_FOUND_VALUE, "K004", "지원자 조회 실패"),
    VOLUNTEER_FIND_OK(SUCCESS, "K005", "지원자 조회 성공"),

    MEMBER_OK(SUCCESS, "I000", "팀원으로 처리 성공"),
    MEMBER_FIND_OK(SUCCESS, "I001", "팀원 조회 성공"),

    /**
     * 잘못된 ExceptionCode
     */
    EMPTY(null, "Z000", "값이 없습니다."),
    WRONG_APPROACH(INVALID_ACCESS, "Z001", "잘못된 접근");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionCode findExceptionCodeByCode(String code) {
        return Arrays.stream(ExceptionCode.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElse(EMPTY);
    }
}
