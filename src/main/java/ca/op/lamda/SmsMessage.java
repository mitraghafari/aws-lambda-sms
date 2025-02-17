package ca.op.lamda;

public class SmsMessage {

    private Notification notification;
    private Delivery delivery;
    private String status;

    public static class Notification {
        private String messageId;
        private String timestamp;

        // Getter and Setter for messageId
        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        // Getter and Setter for timestamp
        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class Delivery {
        private int numberOfMessageParts;
        private String destination;
        private double priceInUSD;
        private String smsType;
        private String providerResponse;
        private int dwellTimeMs;
        private int dwellTimeMsUntilDeviceAck;

        // Getter and Setter for numberOfMessageParts
        public int getNumberOfMessageParts() {
            return numberOfMessageParts;
        }

        public void setNumberOfMessageParts(int numberOfMessageParts) {
            this.numberOfMessageParts = numberOfMessageParts;
        }

        // Getter and Setter for destination
        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        // Getter and Setter for priceInUSD
        public double getPriceInUSD() {
            return priceInUSD;
        }

        public void setPriceInUSD(double priceInUSD) {
            this.priceInUSD = priceInUSD;
        }

        // Getter and Setter for smsType
        public String getSmsType() {
            return smsType;
        }

        public void setSmsType(String smsType) {
            this.smsType = smsType;
        }

        // Getter and Setter for providerResponse
        public String getProviderResponse() {
            return providerResponse;
        }

        public void setProviderResponse(String providerResponse) {
            this.providerResponse = providerResponse;
        }

        // Getter and Setter for dwellTimeMs
        public int getDwellTimeMs() {
            return dwellTimeMs;
        }

        public void setDwellTimeMs(int dwellTimeMs) {
            this.dwellTimeMs = dwellTimeMs;
        }

        // Getter and Setter for dwellTimeMsUntilDeviceAck
        public int getDwellTimeMsUntilDeviceAck() {
            return dwellTimeMsUntilDeviceAck;
        }

        public void setDwellTimeMsUntilDeviceAck(int dwellTimeMsUntilDeviceAck) {
            this.dwellTimeMsUntilDeviceAck = dwellTimeMsUntilDeviceAck;
        }
    }

    // Getter and Setter for notification
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    // Getter and Setter for delivery
    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
