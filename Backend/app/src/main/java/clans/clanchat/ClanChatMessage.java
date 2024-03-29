package clans.clanchat;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import org.apache.logging.log4j.message.Message;

import java.util.Date;

@Entity
@Table(name = "clan-messages")
public class ClanChatMessage {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String userName;

    @Lob
    private String content;

    @Column
    private int clanid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time-sent")
    private Date sent = new Date();

    public ClanChatMessage() {
    }

    public ClanChatMessage(String userName, String content,int clanid) {
        this.clanid = clanid;
        this.userName = userName;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClanid() {
        return clanid;
    }

    public void setClanid(int clanid) {
        this.clanid = clanid;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }
}
