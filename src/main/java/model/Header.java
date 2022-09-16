
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Header {

    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("eventSource")
    @Expose
    private String eventSource;
    @SerializedName("sequenceNumber")
    @Expose
    private Integer sequenceNumber;
    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("notificationId")
    @Expose
    private String notificationId;
    @SerializedName("executionTimestamp")
    @Expose
    private String executionTimestamp;
    @SerializedName("publishedTimestamp")
    @Expose
    private String publishedTimestamp;
    @SerializedName("messageType")
    @Expose
    private Object messageType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(String executionTimestamp) {
        this.executionTimestamp = executionTimestamp;
    }

    public String getPublishedTimestamp() {
        return publishedTimestamp;
    }

    public void setPublishedTimestamp(String publishedTimestamp) {
        this.publishedTimestamp = publishedTimestamp;
    }

    public Object getMessageType() {
        return messageType;
    }

    public void setMessageType(Object messageType) {
        this.messageType = messageType;
    }

}