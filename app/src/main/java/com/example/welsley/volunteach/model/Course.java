package com.example.welsley.volunteach.model;

/**
 * Created by Welsley on 8/12/2017.
 */

public class Course{
    private int id;
    private String name;
    private int userId;
    private int branchId;
    private String time;
    private String duration;
    private String description;
    private int instId;
    private String booked;

    public Course(int id, String name, int userId, int branchId, String time, String duration, String description, int instId, String booked) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.branchId = branchId;
        this.time = time;
        this.duration = duration;
        this.description = description;
        this.instId = instId;
        this.booked = booked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstId() {
        return instId;
    }

    public void setInstId(int instId) {
        this.instId = instId;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }
}
