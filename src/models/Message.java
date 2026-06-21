package models;

import java.sql.Timestamp;

public class Message {
    private int id;
    private String content;
    private Timestamp sendDate;
    private ChefService sender;
    private ChefService receiver;

    public Message(int id, String content, Timestamp sendDate, ChefService sender, ChefService receiver) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.sender = sender;
        this.receiver = receiver;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Timestamp getSendDate() { return sendDate; }
    public void setSendDate(Timestamp sendDate) { this.sendDate = sendDate; }
    public ChefService getSender() { return sender; }
    public void setSender(ChefService sender) { this.sender = sender; }
    public ChefService getReceiver() { return receiver; }
    public void setReceiver(ChefService receiver) { this.receiver = receiver; }
}