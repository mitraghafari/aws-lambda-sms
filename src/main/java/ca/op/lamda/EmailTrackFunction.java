package ca.op.lamda;

import ca.op.lamda.dao.DefaultEmailDao;
import ca.op.lamda.dao.EmailDao;
import ca.op.lamda.dto.Response;
import ca.op.lamda.model.Email;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.util.Optional;


public class EmailTrackFunction implements RequestHandler<SNSEvent,Object> {

    private final EmailDao emailDao = DefaultEmailDao.INSTANCE;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Object handleRequest(SNSEvent event, Context context) {
        try{
            Optional<SNSEvent.SNSRecord> snsRecord = event.getRecords().stream().findAny();
            if(snsRecord.isPresent()){
                final SNSEvent.SNS sns = snsRecord.get().getSNS();
                final String jsonMessage = sns.getMessage();
                final Email email = gson.fromJson(jsonMessage, Email.class);
                context.getLogger().log("insert email for client: " + email.getToAddress());
                emailDao.insert(email);
            }
            return Response.builder()
                    .httpCode(HttpURLConnection.HTTP_OK)
                    .message("OK.")
                    .build();
        } catch (Exception e){
            return Response.builder()
                    .httpCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    .message("Something went wrong.")
                    .build();
        }
    }
}
