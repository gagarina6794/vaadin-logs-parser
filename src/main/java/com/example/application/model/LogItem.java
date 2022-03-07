package com.example.application.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "logs")
public class LogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Integer id;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "thread", nullable = false)
    private String thread;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "info", nullable = true)
    @Lob
    private String info;

    public LogItem() {
    }

    public LogItem(String level, String date, String thread, String location, String info) {
        this.level = level;
        this.date = date;
        this.thread = thread;
        this.location = location;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogItem logItem = (LogItem) o;
        return Objects.equals(id, logItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LogItem{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", date='" + date + '\'' +
                ", thread='" + thread + '\'' +
                ", location='" + location + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}