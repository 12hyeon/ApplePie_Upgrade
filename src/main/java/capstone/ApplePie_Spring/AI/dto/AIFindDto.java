package capstone.ApplePie_Spring.AI.dto;

import lombok.Getter;

@Getter
public class AIFindDto {
    private long user_id;

    private int categoryId; // 카테고리값에 해당하는 글들만 긁어오기 위함. 만약 해당 카테고리에 해당하는 AI 테이블 존재하지 않으면
    // 추천이 존재하지 않는다 or 뷰 카운트 높은 순으로 주기
}