package app.filatov.homeworkstatusesbot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

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
        return "\u279F" + "  Домашняя работа: " + homeworkName + "\n" +
                "\u02D9" + "  статус: " + status + "\n" +
                "\u02D9" + "  комментарий ревьюера: " + reviewerComment + "\n" +
                "\u02D9" + "  обновлено: " + dateUpdated + "." + "\n";
    }
}
