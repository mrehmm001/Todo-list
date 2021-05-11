package Models;

import assets.Category;
import assets.Importance;
import assets.Status;

import java.time.LocalDateTime;

//Models.Todo model class

/**
 * Models.Todo model class is the object representation of each record
 * within the Models.Todo SQLite table.
 */
public class Todo {
    //private variables
    private String text;
    private LocalDateTime due;
    private Category cat;
    private Importance importance;
    private Status completion;
    private int id;

    //Constructor
    public Todo(String text, LocalDateTime due, Category cat, Importance importance, Status completion) {
        this.text = text;
        this.due = due;
        this.cat = cat;
        this.importance = importance;
        this.completion = completion;
    }

    //constructor overloading used here, specifically for having an id
    public Todo(int id , String text, LocalDateTime due, Category cat, Importance importance, Status completion) {
        this.text = text;
        this.due = due;
        this.cat = cat;
        this.importance = importance;
        this.completion = completion;
        this.id = id;
    }


    //getter & setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDue() {
        return due;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Status getCompletion() {
        return completion;
    }

    public void setCompletion(Status completion) {
        this.completion = completion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //tostring method
    public String toString() {
        return cat.getColour()+"Models.Todo{" +
                "text='" + text + '\'' +
                ", due=" + due +
                ", cat=" + cat +
                ", importance=" + importance +
                ", completion=" + completion +
                "}\033[0m";
    }
}
