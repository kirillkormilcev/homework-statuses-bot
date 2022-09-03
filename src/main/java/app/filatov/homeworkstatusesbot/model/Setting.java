package app.filatov.homeworkstatusesbot.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "settings")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Setting {
    @EmbeddedId
    private SettingCompositeKey settingCompositeKey;

    @Column
    private String value;
}
