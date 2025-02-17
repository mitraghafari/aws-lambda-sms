package ca.op.lamda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class SmsToRDSLambda implements RequestHandler<CloudWatchLogsEvent, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DB_URL = "jdbc:mysql://appoplive.cvhe9zlamyok.us-west-2.rds.amazonaws.com:3306/email_track";
    private static final String DB_USER = "op";
    private static final String DB_PASSWORD = "BWcYT5YNDY8YQsYN";

    @Override
    public String handleRequest(CloudWatchLogsEvent event, Context context) {
        try {
            String base64EncodedPayload = event.getAwsLogs().getData();
            System.out.println(base64EncodedPayload);
            // Decompress payload
            String decompressedPayload = decompress(Base64.getDecoder().decode(base64EncodedPayload));

            // Parse payload
            ObjectMapper mapper = new ObjectMapper();
            CloudWatchPayload payload = mapper.readValue(decompressedPayload, CloudWatchPayload.class);
            String innerMessage = payload.getLogEvents().get(0).getMessage();
            SmsMessage smsMessage = mapper.readValue(innerMessage, SmsMessage.class);

            // Save to RDS
            saveToRDS(smsMessage);

            return "Successfully saved SMS message to RDS.";
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return "Failed to save SMS message to RDS: " + e.getMessage();
        }
    }

    public static String decompress(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        return new String(bos.toByteArray());
    }

    public static void saveToRDS(SmsMessage message) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO sms_logs (messageId, timestamp, numberOfMessageParts, destination, priceInUSD, smsType, providerResponse, dwellTimeMs, dwellTimeMsUntilDeviceAck, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, message.getNotification().getMessageId());
                pstmt.setTimestamp(2, Timestamp.valueOf(message.getNotification().getTimestamp()));
                pstmt.setInt(3, message.getDelivery().getNumberOfMessageParts());
                pstmt.setString(4, message.getDelivery().getDestination());
                pstmt.setDouble(5, message.getDelivery().getPriceInUSD());
                pstmt.setString(6, message.getDelivery().getSmsType());
                pstmt.setString(7, message.getDelivery().getProviderResponse());
                pstmt.setInt(8, message.getDelivery().getDwellTimeMs());
                pstmt.setInt(9, message.getDelivery().getDwellTimeMsUntilDeviceAck());
                pstmt.setString(10, message.getStatus());

                pstmt.executeUpdate();
            }
        }
    }

    // Include the SmsMessage class, its inner classes, and the CloudWatchPayload class
    // ... (as provided previously)


}
