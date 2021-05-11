package Views;

import Models.Todo;
import assets.Category;
import assets.Importance;
import assets.Status;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataBase{
    private Statement statement;
    private static final String DATABASE="jdbc:sqlite:todo.db";
    private static final String TABLE="todo_list";

    /**
     *
     * This is the database helper class.
     * Used for storing todos into the database called todo.db.
     * I am using a local SQLite database , it is created when you first run todo program.
     *
     * =CONSTRUCTOR=
     * Database(): the constructor's main job is to establish the connection to the todo.db, creates it if it doesnt exist.
     * It also creates the required tables (only if it does not already exists).
     * Error handling is used iun case something went wrong.
     */
    public DataBase() {
        try{
            Connection connection = DriverManager.getConnection(DATABASE);
            statement = connection.createStatement();
            //public Models.Todo(String text, LocalDateTime due, assets.Category cat, assets.Importance importance, assets.Status completion)
            statement.execute("CREATE TABLE IF NOT EXISTS "+TABLE +
                            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "text TEXT, " +
                            "due TIMESTAMP, " +
                            "category TEXT, " +
                            "importance TEXT, " +
                            "status TEXT)");


        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());

        }
    }

    /**
     * The insertIntoTodo method takes in 5 arguments , which are the properties of a todo.
     * I used a statement which takes the arguments, and inserts them into the todo_list table using the INSERT INTO SQL statement.
     * boolean output to prove the todo has been added to the list.
     */
    public boolean insertIntoTodo(Todo todo){
        try {
            statement.execute("INSERT INTO todo_list(text, due, category, importance, status) " +
                    "VALUES('"+todo.getText()+"','"+todo.getDue().toString()+"', '"+todo.getCat()+"','"+todo.getImportance()+"','"+todo.getCompletion()+"')");
            return true;
        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());
            return false;
        }


    }


    /**
     *The deleteFromTodo method takes 1 argument, id.
     * A query is executed which is used to delete the chosen todo from the table.
     * Each todo has a unique id, so using id differentiates between several todos that
     * may contain the same id.
     */
    public boolean deleteFromTodo(String id){
        try {
            statement.execute("DELETE FROM todo_list " +
                    "WHERE id = '"+id+"';");
            return true;
        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());
            return false;
        }
    }


    /**
     * getAllTodos method gets all the todos from the todos table.
     * Each todo is stored into the todos arrayList with the help of getTodoObject
     * method to deserialize the row into a Todo object.
     * The todos arraylist is returned at the end.
     */
    public ArrayList<Todo> getAllTodo(){
        ArrayList<Todo> todos= new ArrayList<>();
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM todo_list");
            while(res.next()){
                todos.add(getTodoObject(res.getString("ID"),
                        res.getString("text"),
                        res.getString("due"),
                        res.getString("category"),
                        res.getString("importance"),
                        res.getString("status")));

            }
        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());
        }
        return todos;
    }

    /**
     *The updateTodo method takes 1 argument, todo.
     * A query is executed which is used to update the chosen todo from the table.
     * the chosen todo's property is updated using the todo objects.
     * True is returned if everything went well, else false is returned.
     */
    public boolean updateTodo(Todo todo){
        try {
            statement.execute("UPDATE todo_list SET text = '"+todo.getText()+"', " +
                    "due = '"+todo.getDue()+"'," +
                    "category = '"+todo.getCat()+"'," +
                    "importance = '"+todo.getImportance()+"'," +
                    "status = '"+todo.getCompletion()+"' WHERE id = "+todo.getId());
            return true;

        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());
            return false;
        }
    }

    /**
     * searchTodo takes 1 input, keyword. It takes the keyword and performs a query on it.
     * The query retrieves all the todos that have text similar to the keyword.
     * After that, the results are iterated and reach row is deserialized into a Todo Object and then stored into
     * a todos arraylist, to which the arraylist is returned at the end.
     */
    public ArrayList<Todo> searchTodo(String keyword){
        ArrayList<Todo> todos= new ArrayList<>();
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM todo_list WHERE text LIKE '%"+keyword+"%'");
            while(res.next()){
                todos.add(getTodoObject(res.getString("ID"),
                        res.getString("text"),
                        res.getString("due"),
                        res.getString("category"),
                        res.getString("importance"),
                        res.getString("status")));

            }
        }catch (SQLException e){
            System.out.println("Something went wrong "+e.getMessage());
        }
        return todos;
    }




    //helper
    //this converts the inputs into a todo.
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
