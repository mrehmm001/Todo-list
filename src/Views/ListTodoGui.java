package Views;

import Models.Todo;
import assets.Category;
import assets.CustomButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class ListTodoGui extends JPanel {
    /**
     * The ListTodoGui class inherits from JPanel, so it has access to all of Jpanel's methods.
     * It's purpose is to list out todos, but as well as to be able to search for todos using a
     * specific keyword.
     * The ListTodoGui makes use of the Jtable component which allows it to list out the Todos in
     * a tabular form, which makes it much more neater.
     * Each row is coloured to their chosen category colour, e.g if category is blue, the whole row
     * will be blue.
     */

    //private variables
    private Color navColor, selectedColor, transparent;
    private DataBase dataBaseHelper;
    private GUI parent;


    /**
     * The constructor takes two arguments, parent & dataBaseHelper.
     * It sets up the layout, and adds a search feature as well as the list table.     *
     */
    public ListTodoGui(GUI parent, DataBase dataBaseHelper) {

        //Colour palette
        navColor = new Color(23, 23, 23);
        selectedColor = new Color(10, 10, 10);
        transparent = new Color(0f,0f,0f,0f);

        //initialises the DataBaseHelper , will be able to use the DataBaseHelper globally now for other functions to use.
        this.dataBaseHelper = dataBaseHelper;
        //The parent is initialised , and will be able to use globally.
        this.parent = parent;

        //set up process
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        setBackground(transparent);


        //Search section setup

        //The edit text used for entering search keyword
        JTextField search = new JTextField();

        //Button for enabling search
        JButton searchButton = new CustomButton("SEARCH");

        //Search wrapper which will house the textfield and the buttoon
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new BorderLayout());
        searchWrapper.add(search, BorderLayout.CENTER);
        searchWrapper.add(searchButton, BorderLayout.EAST);

        //Search button event listener established, and will listen for any button clicks.
        searchButton.addActionListener(p->{
            //Upon button click, the listTodos is called, and will relist out the todos relative to the search keyword
            listTodos(search.getText().toString());
        });

        //Adds the search section to the North of the layout
        add(searchWrapper, BorderLayout.NORTH);

        //calls the listTodos function, which in turn adds the table to the center of the layout.
        listTodos("");

    }

    /**
     * ListTodos function takes a keyword, and uses the database helper's method of searchTodo which lists all the todos
     * that have a todo name similar to the search keyword.
     * The listTodos makes use of Jtable which is how I was able to list the todos in tabular form.
     */
    private void listTodos(String keyword){
        //Upon the listTodos function call, the center of the panel is cleared.
        try {
            BorderLayout layout = (BorderLayout)this.getLayout();
            this.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }catch (NullPointerException e){

        }

        //Setting up the Table

        String[] columns = {"TEXT", "DUE", "CATEGORY", "IMPORTANCE", "STATUS"};//The columns for the table
        //Retrieves all the todos from the database using the keyword, and stores the results into the todoArrayList
        ArrayList<Todo> todoArrayList = dataBaseHelper.searchTodo(keyword);
        String[][] rows = new String[todoArrayList.size()][5];//the rows needed for the table, currently empty
        int index=0;

        //Traverses the todoArrayList and adds the property to rows, each row representing a todo.
        for(Todo todo : todoArrayList){
            rows[index++]=new String[]{todo.getText(),
                    todo.getDue().toString(),
                    String.valueOf(todo.getCat()),
                    String.valueOf(todo.getImportance()),
                    String.valueOf(todo.getCompletion())};
        }

        //Calling the Jtable which takes rows and columns as input, and storing it in table variable.
        JTable table = new JTable(rows, columns)
        {
            //This is where the row colouring happens
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);//Gets the row

                String color = (this.getModel().getValueAt(row, 2)).toString();//gets the colour category of the row

                //Switch statement to check which colour category it is.
                //E.g if category is blue, the row's background colour will become, and also the text will
                //become white (or black depending on the colour) to make the text easier to read.
                switch (color){
                    case "RED":
                        c.setForeground(Color.white);
                        c.setBackground(Category.RED.getColour());
                        break;
                    case "WHITE":
                        c.setForeground(Color.black);
                        c.setBackground(Category.WHITE.getColour());
                        break;
                    case "BLUE":
                        c.setForeground(Color.white);
                        c.setBackground(Category.BLUE.getColour());
                        break;
                    case "PURPLE":
                        c.setForeground(Color.white);
                        c.setBackground(Category.PURPLE.getColour());
                        break;
                    case "YELLOW":
                        c.setForeground(Color.black);
                        c.setBackground(Category.YELLOW.getColour());
                        break;
                    case "GREEN":
                        c.setForeground(Color.BLACK);
                        c.setBackground(Category.GREEN.getColour());
                        break;

                }

                return c;
            }
        };



        //Settings up the scroll pane, which takes table as input (which houses the table)
        JScrollPane sp=new JScrollPane(table);

        //removing the table & the scrollpane's background.
        table.setBackground(transparent);
        sp.getViewport().setBackground(transparent);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBackground(new Color(0,0,0,0.5f));// changing the scrollpane to a more translucent black colour.

        add(sp, BorderLayout.CENTER);//adds the scroll pane

        //re-renders the GUI.
        parent.repaint();
        parent.revalidate();

    }

}