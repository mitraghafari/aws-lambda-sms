package ca.op.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsDeliveryHandler implements RequestHandler<SQSEvent, Void> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DB_URL = "jdbc:mysql://appoplive.amazonaws.com:3306/email_track";
    private static final String DB_USER = "op";
    private static final String DB_PASSWORD = "BWcYT5YNDY8YQsYN";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO sms_delivery_table (message_id, phone_number, status, provider_response, sent_timestamp, delivered_timestamp, price, error_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                JsonNode messageBody = OBJECT_MAPPER.readTree(msg.getBody());

                String messageId = messageBody.get("MessageId").asText();
                String phoneNumber = messageBody.get("PhoneNumber").asText();
                String status = messageBody.get("Status").asText();
                String providerResponse = messageBody.get("ProviderResponse").asText();

                String sentTimestampStr = messageBody.get("SentTimestamp").asText(null);
                String deliveredTimestampStr = messageBody.get("DeliveredTimestamp").asText(null);
                Double price = messageBody.get("Price").asDouble(0.0);
                String errorCode = messageBody.get("ErrorCode").asText(null);

                Timestamp sentTimestamp = parseTimestamp(sentTimestampStr);
                Timestamp deliveredTimestamp = parseTimestamp(deliveredTimestampStr);

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, messageId);
                    preparedStatement.setString(2, phoneNumber);
                    preparedStatement.setString(3, status);
                    preparedStatement.setString(4, providerResponse);
                    preparedStatement.setTimestamp(5, sentTimestamp);
                    preparedStatement.setTimestamp(6, deliveredTimestamp);
                    preparedStatement.setDouble(7, price);
                    preparedStatement.setString(8, errorCode);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.log("Error processing SQS message: " + e.getMessage());
        }
        return null;
    }

    private Timestamp parseTimestamp(String timestampStr) {
        if (timestampStr != null && !timestampStr.isEmpty()) {
            try {
                Date parsedDate = DATE_FORMAT.parse(timestampStr);
                return new Timestamp(parsedDate.getTime());
            } catch (Exception e) {
                // Handle parsing exception if necessary
            }
        }
        return null;
    }
}
