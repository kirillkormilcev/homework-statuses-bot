package app.filatov.homeworkstatusesbot.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "homeworks")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Homework {
    @Id
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "homework_name")
    private String homeworkName;

    @Column(name = "reviewer_comment")
    private String reviewerComment;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "lesson_name")
    private String lessonName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Homework homework = (Homework) o;

        return Objects.equals(id, homework.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
