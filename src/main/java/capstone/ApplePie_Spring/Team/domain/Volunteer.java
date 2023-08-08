package capstone.ApplePie_Spring.Team.domain;

import capstone.ApplePie_Spring.Team.dto.TeamDto;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.config.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "VOLUNTEER")
public class Volunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Role {
        MANAGER(0,"기획자"),
        DESIGNER(1,"디자이너"),
        WEB(2,"웹 개발자"),
        FRONT(3,"프론트엔드 개발자"),
        BACKEND(4,"백엔드 개발자");

        private int id;
        private String name;

        Role(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Role getValue(int id) {
            return Arrays.stream(values())
                    .filter(value -> value.id == id)
                    .findAny()
                    .orElse(null);
        }

        public String getName() {
            return name;
        }
    }

    private Role role;
    private String application;

    // 연관 관계 매핑
    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Team team;

    public enum VolunteerStatus {
        APPLY(0,"지원 상태"),
        COMPLETE(1,"팀원 선정");

        private int id;
        private String name;

        VolunteerStatus(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static VolunteerStatus getValue(int id) {
            return Arrays.stream(values())
                    .filter(value -> value.id == id)
                    .findAny()
                    .orElse(null);
        }
    }

    @Column(name = "volunteer_status", nullable = false)
    private VolunteerStatus volunteerStatus;

    public void setVolunteerStatus(VolunteerStatus volunteerStatus) {
        this.volunteerStatus = volunteerStatus;
    }

    public void updateVolunteer(TeamDto teamDto) {
        this.role = teamDto.getRole();
        this.application = role.getName();
    }

    @Builder
    public Volunteer(User user, Team team, Volunteer.Role role, String application, VolunteerStatus volunteerStatus) {
        this.user = user;
        this.team = team;
        this.role = role;
        this.application = application;
        this.volunteerStatus = volunteerStatus;
    }

    public void delete() {
        super.delete();
    }
}
