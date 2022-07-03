package de.iu.jakarta_jsf.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static de.iu.jakarta_jsf.services.ReServiceImpl.REVIEWING;

/**
 * This Entity is connected to RequestedValueEntities and bunches them together
 * into one request a peer-reviewer has to review. It provides information like when the request has been created.
 */
@Entity
@Table(name = "Request")
public class RequestEntity {

    public static final String COLUMN_AUTHOR_NAME = "authorName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;

    @Column(name = COLUMN_AUTHOR_NAME)
    private String authorName;

    private String status = REVIEWING;
    private String date;
    private Boolean alreadySaved = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<RequestedValueEntity> subRequests = new ArrayList<>();

    /**
     * getter and setter
     */
    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String name) {
        this.authorName = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String time) {
        this.date = time;
    }

    public boolean getAlreadySaved() {
        return alreadySaved;
    }

    public void setAlreadySaved(Boolean alreadySaved) {
        this.alreadySaved = alreadySaved;
    }

    public List<RequestedValueEntity> getSubRequests() {
        return subRequests;
    }

    public void setSubRequests(List<RequestedValueEntity> subRequests) {
        this.subRequests = subRequests;
    }
}
