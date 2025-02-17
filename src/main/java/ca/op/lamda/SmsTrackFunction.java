package ca.op.lamda;

import ca.op.lamda.dao.DefaultSmsDao;
import ca.op.lamda.dao.SmsDao;
import ca.op.lamda.dto.Response;
import ca.op.lamda.model.Sms;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Optional;


public class SmsTrackFunction implements RequestHandler<SNSEvent, Object> {

    private final SmsDao smsDao = DefaultSmsDao.INSTANCE;

    @Override
    public Object handleRequest(SNSEvent event, Context context) {
        try {
            Optional<SNSEvent.SNSRecord> snsRecord = event.getRecords().stream().findAny();
            if (snsRecord.isPresent()) {
                final SNSEvent.SNS sns = snsRecord.get().getSNS();

                Sms sms = Sms.of(
                        null,
                        sns.getSubject(),
                        sns.getMessage(),
                        new Date());

                context.getLogger().log("insert sms for client: " + sms.getClientOpttId());
                smsDao.insert(sms);
            }
            return Response.builder()
                    .httpCode(HttpURLConnection.HTTP_OK)
                    .message("OK.")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .httpCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    .message("Something went wrong.")
                    .build();
        }
    }
}
