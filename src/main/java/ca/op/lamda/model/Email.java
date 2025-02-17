package ca.op.lamda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value
@AllArgsConstructor(staticName = "of")
public class Email {
    Long id;
    String toAddress;
    String subject;
    String messageType;
    String domain;
    String fromAddress;
    String mailDateTime;
    String snsPublishTime;
    String sesMessageId;
}
