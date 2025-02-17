package ca.op.lamda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Date;

@Data
@Builder
@Value
@AllArgsConstructor(staticName = "of")
public class Sms {
    Long id;
    String clientOpttId;
    String message;
    Date createDate;
}
