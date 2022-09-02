package app.filatov.homeworkstatusesbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaderResponseDto {
    List<HomeworkDto> homeworks;
}
