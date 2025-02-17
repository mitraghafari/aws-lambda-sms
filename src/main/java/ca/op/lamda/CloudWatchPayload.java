package ca.op.lamda;

import java.util.List;

public class CloudWatchPayload {

    private String messageType;
    private String owner;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLogGroup() {
        return logGroup;
    }

    public void setLogGroup(String logGroup) {
        this.logGroup = logGroup;
    }

    public String getLogStream() {
        return logStream;
    }

    public void setLogStream(String logStream) {
        this.logStream = logStream;
    }

    public List<String> getSubscriptionFilters() {
        return subscriptionFilters;
    }

    public void setSubscriptionFilters(List<String> subscriptionFilters) {
        this.subscriptionFilters = subscriptionFilters;
    }

    private String logGroup;
    private String logStream;
    private List<String> subscriptionFilters;
    private List<LogEvent> logEvents;

    public static class LogEvent {
        private String id;
        private long timestamp;
        private String message;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        // Getter for message
        public String getMessage() {
            return message;
        }

        // Setter for message
        public void setMessage(String message) {
            this.message = message;
        }

        // Other getters and setters for id and timestamp if needed
    }

    // Getter for logEvents
    public List<LogEvent> getLogEvents() {
        return logEvents;
    }

    // Setter for logEvents
    public void setLogEvents(List<LogEvent> logEvents) {
        this.logEvents = logEvents;
    }

    // Other getters and setters for other properties of CloudWatchPayload if needed
}
