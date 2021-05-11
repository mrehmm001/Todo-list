package assets;

import Models.Todo;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class HelperFunctions {

    /**
     * formatSpinners formats takes a spinner as argument and formats its properties.
     * In this instance, it creates 5px padding for the spinner, and then returns it.
     */
    public static JSpinner formatSpinner(JSpinner spinner){
        JComponent editor = spinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinner.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        return spinner;
    }

    /**
     * formatTextField takes jTextField as argument and alters the textfield by giving it a padding of 5px, and then returns the textfield.
     */
    public static JTextField formatTextField (JTextField field){
        field.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        return field;
    }

    /**
     * getFormattedDate gets the selected date and time from datePicker and timeSpinner and formats it like YYYYMMDD:HH:SS so that LocalDateTime can
     * accept it.
     */
    public static String getFormattedDate(JXDatePicker datePicker, JSpinner timeSpinner){
        String date = datePicker.getDate().toInstant().toString().split("T")[0];
        String time = timeSpinner.getValue().toString().split(" ")[3];
        return date+"T"+time;
    }


    /**
     * The getTodo takes the string properties of a todo and converts it into a Todo object and returns it.
     */
    public static Todo getTodo(String text, String due, String cat, String importance, String completion){
        Category category = null;
        Importance importance1 = null;
        Status status = null;
        switch(completion){
            case "PENDING":
                status = Status.PENDING;
                break;
            case "STARTED":
                status = Status.STARTED;
                break;
            case "PARTIAL":
                status = Status.PARTIAL;
                break;
            case "COMPLETED":
                status = Status.COMPLETED;
                break;
        }

        switch (importance){
            case "LOW":
                importance1 = Importance.LOW;
                break;
            case "NORMAL":
                importance1 = Importance.NORMAL;
                break;
            case"HIGH":
                importance1 = Importance.HIGH;
                break;
        }

        switch (cat){
            case "RED":
                category = Category.RED;
                break;
            case "WHITE":
                category = Category.WHITE;
                break;
            case "BLUE":
                category = Category.BLUE;
                break;
            case "PURPLE":
                category = Category.PURPLE;
                break;
            case "YELLOW":
                category = Category.YELLOW;
                break;
            case "GREEN":
                category = Category.GREEN;
                break;
        }

        return new Todo(text,
                LocalDateTime.parse(due),
                category,
                importance1,
                status);
    }

    public Todo getTodoObject(String id,String text, String due, String cat, String importance, String completion){
        Category category=null;
        Importance importance1=null;
        Status status=null;

        switch (cat){
            case "RED":
                category = Category.RED;
                break;
            case "WHITE":
                category = Category.WHITE;
                break;
            case "BLUE":
                category = Category.BLUE;
                break;
            case "PURPLE":
                category = Category.PURPLE;
                break;
            case "YELLOW":
                category = Category.YELLOW;
                break;
            case "GREEN":
                category = Category.GREEN;
                break;
        }

        switch (importance){
            case "LOW":
                importance1 = Importance.LOW;
                break;
            case "NORMAL":
                importance1 = Importance.NORMAL;
                break;
            case "HIGH":
                importance1 = Importance.HIGH;
                break;
        }

        switch (completion){
            case "PENDING":
                status = Status.PENDING;
                break;
            case "STARTED":
                status = Status.STARTED;
                break;
            case "PARTIAL":
                status = Status.PARTIAL;
                break;
            case "COMPLETED":
                status = Status.COMPLETED;
                break;
        }
        return new Todo(Integer.parseInt(id),text, LocalDateTime.parse(due), category, importance1, status);
    }


}
