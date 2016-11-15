package com.example.ru.w_email;

import java.io.Serializable;

/**
 * Created by Ru on 11/14/2016.
 */

public class InboxConfigure implements Serializable {
    private String from,replyto,recipients,subject,sentdate,content;

    public void setFrom(String from){
        this.from = from;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public void setSentDate(String date){
        this.sentdate = date;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setReplyto(String replyto){
        this.replyto = replyto;
    }
    public void setRecipients(String recipients){
        this.recipients = recipients;
    }

    public String getFrom(){
        return from;
    }
    public String getSubject(){
        return subject;
    }
    public String getSentDate(){
        return sentdate;
    }
    public String getContent(){
        return content;
    }
    public String getReplyto(){
        return replyto;
    }
    public String getRecipients(){
        return recipients;
    }

    @Override
    public String toString() {
        return this.from+" - "+this.sentdate;
    }
}
