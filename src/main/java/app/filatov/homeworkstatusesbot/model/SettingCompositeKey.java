package app.filatov.homeworkstatusesbot.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SettingCompositeKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String key;
}
