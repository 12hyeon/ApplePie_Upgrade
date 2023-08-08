package capstone.ApplePie_Spring.Chat.domain;

import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "CHAT_JOIN")
public class ChatJoin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 연관 관계 매핑

    @ManyToOne
    private User user;

    @ManyToOne
    private Chatroom chatroom;

    @OneToMany(mappedBy = "chatJoin")
    private List<Message> messages = new ArrayList<>();
}