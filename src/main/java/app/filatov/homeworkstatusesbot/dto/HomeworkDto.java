package app.filatov.homeworkstatusesbot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeworkDto {
    private Long id;

    private String status;

    @JsonProperty("homework_name")
    private String homeworkName;

    @JsonProperty("reviewer_comment")
    private String reviewerComment;

    @JsonProperty("date_updated")
    private LocalDateTime dateUpdated;

    @JsonProperty("lesson_name")
    private String lessonName;

    @JsonCreator
    public HomeworkDto() {
    }
    @Override
    public String toString() {
        return "\u25BA" + "  Домашняя работа: " + homeworkName + "\n" +
                "\u25B7" + "  статус: " + status + "\n" +
                "\u25B7" + "  комментарий ревьюера: " + reviewerComment + "\n" +
                "\u25B7" + "  обновлено: " + dateUpdated.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")) + "." + "\n";
    }
}
