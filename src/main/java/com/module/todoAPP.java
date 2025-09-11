package com.model;
import java.time.LocalDateTime;
public class todoAPP{

    private int id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public todoAPP(){
        this.created_at=LocalDateTime.now();
        this.updated_at=LocalDateTime.now();
    }

    public todoAPP(String title,String description){
        this();
        this.title=title;
        this.description=description;
    }

    public todoAPP(int id,String title,String description,boolean completed,LocalDateTime created_at,LocalDateTime updated_at){
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public int getid(){
        return id;
    }
    public void setid(int id){
        this.id = id;
    }
}