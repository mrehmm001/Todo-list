package Views;

import assets.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    //private variables
    private JButton listTodos,addTodo ,updateTodo, deleteTodo;
    private JPanel contentPanel, navPanel, navLinks;
    private JLabel navTitle;
    private Color navColor, selectedColor, transparent;
    private DataBase dataBase;

    /**
     * The constructor's main purpose is to establish the GUI application.
     * The first thing it does is, it establishes a database connection.
     * Then it creates the background and sets the panel's background to it.
     * Then it sets up the main features of the GUI e.g title, defaultcloseoperation & preferred size.
     * The contentPanel is added which will house the main content of each section of the TodoApp
     * The navigation bar is created and then added to the GUI.
     */
    public GUI()  {
        dataBase = new DataBase();//for the database

        //Adds the background
        //error handling is used here
        try {
            JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("wallpaper.jpg"))));
            this.setContentPane(background);
        }catch (IOException e){
            System.out.println("Something went wrong "+e.getMessage());
        }

        //setting up
        this.setTitle("TODO APP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setPreferredSize(new Dimension(1100,800));

        //creating the content panel.
        //contentPanel is responsible for containing the content of each page, e.g contentPanel will show a table list in listTodos
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(0f,0f,0f,0f));

        this.add(contentPanel, BorderLayout.CENTER);


        //creates navbar and adds it to the panel
        navBar();
        this.pack();//this causes this Window to be sized to fit the preferred size and layouts of its subcomponents

        showListTodo();
    }


    /**
     * The navbar function is used for setting up the navBar and then adding it to the GUI.
     * The navbar uses a Jpanel which will house all the navbar main components, e.g the buttons and the title.
     * At end of the function, the navbar is added to the WEST border of the layout.
     */
    public void navBar(){
        //Colour palette
        navColor = new Color(23, 23, 23);
        selectedColor = new Color(10, 10, 10);
        transparent = new Color(0f,0f,0f,0f);


        //Initialising the nav
        navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout());//defining the layout for the navbar as border layout

        navPanel.setPreferredSize(new Dimension(200,MAXIMIZED_VERT));

        //initialising the nav links panel
        navLinks = new JPanel();
        navLinks.setLayout(new GridBagLayout());//defining the layout for the nav links as grid layout

        //initialising the nav buttons
        int textSize=15;//for the text size
        listTodos = new CustomButton("LIST", textSize,selectedColor,  Color.white, "Arial Black");
        addTodo = new CustomButton("ADD", textSize,  navColor, Color.white, "Arial Black");
        updateTodo = new CustomButton("UPDATE", textSize,  navColor, Color.white, "Arial Black");
        deleteTodo = new CustomButton("DELETE", textSize,  navColor, Color.white, "Arial Black");

        //adding action listener
        listTodos.addActionListener(this);
        addTodo.addActionListener(this);
        updateTodo.addActionListener(this);
        deleteTodo.addActionListener(this);


        //Using gridbag constraints to order the layout my perferred way
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx=1;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = new Insets(50,0,10,0);
        //adding the buttons to nav links
        navLinks.add(listTodos,gbc);
        gbc.insets = new Insets(0,0,10,0);
        gbc.gridy=2;
        navLinks.add(addTodo,gbc);
        gbc.gridy=3;
        navLinks.add(updateTodo,gbc);
        gbc.gridy=4;
        navLinks.add(deleteTodo,gbc);
        navLinks.setOpaque(false);

        //adding the title and navLinks to the navPanel
        navPanel.add(customTitlePanel("TODO",transparent));
        navPanel.add(navLinks);


        //sets the navbar background
        navPanel.setBackground(navColor);

        //Finally adding the navbar to the west side of main jframe
        this.add(navPanel, BorderLayout.WEST);

    }


    /**
     * The showListTodo function purpose is to show the listpage section.
     * The contentPanel will display a table of todos.
     * I made a separate class which is responsible for the logic behind the listPage, so refer to {@link ListTodoGui} for the comments to it.
     * I then renamed the title to listTodo, and repainted and revalidate which re-renders the page.
     */
    public void showListTodo(){
        //remove any existing components first and set title
        contentPanel.removeAll();
        JPanel title = customTitlePanel("LIST TODO",new Color(0.23f, 0.23f, 0.23f , 0.9f));

        //add the listTodo and the title to the contentPanel
        contentPanel.add(new ListTodoGui(this, dataBase), BorderLayout.CENTER);
        contentPanel.add(title,BorderLayout.NORTH);

        //re render the page
        this.repaint();
        this.revalidate();
    }

    /**
     * The showAddTodo function purpose is to show the add todo section.
     * The contentPanel will display a form which the user will fill out to add a todo.
     * I made a separate class which is responsible for the logic behind the addtodo, so refer to {@link AddTodoGui} for the comments to it.
     * I then renamed the title to addTodo, and repainted and revalidate which re-renders the page.
     */
    public void showAddTodo(){
        //remove any existing components first and set title
        contentPanel.removeAll();
        JPanel title = customTitlePanel("ADD TODO",new Color(0.23f, 0.23f, 0.23f , 0.9f));

        //add the addTodo and the title to the contentPanel
        contentPanel.add(new AddTodoGui(this,dataBase), BorderLayout.CENTER);
        contentPanel.add(title,BorderLayout.NORTH);

        //re render the page
        this.repaint();
        this.revalidate();

    }


    /**
     * The showUpdateTodo function purpose is to show the update todo section.
     * The contentPanel will display a form which the user can select a todo to update and edit its properties.
     * I made a separate class which is responsible for the logic behind the updateTodo, so refer to {@link UpdateTodoGui} for the comments to it.
     * I then renamed the title to updateTodo, and repainted and revalidate which re-renders the page.
     */
    public void showUpdateTodo(){
        //remove any existing components first and set title
        contentPanel.removeAll();
        JPanel title = customTitlePanel("UPDATE TODO",new Color(0.23f, 0.23f, 0.23f , 0.9f));

        //add the addTodo and the title to the contentPanel
        contentPanel.add(new UpdateTodoGui(this,dataBase), BorderLayout.CENTER);
        contentPanel.add(title, BorderLayout.NORTH);

        //re render the page
        this.repaint();
        this.revalidate();

    }


    /**
     * The showDeleteTodo function purpose is to show the delete todo section.
     * The contentPanel will display a form which the user can select a todo to delete.
     * I made a separate class which is responsible for the logic behind the deleteTodo, so refer to {@link DeleteTodoGui} for the comments to it.
     * I then renamed the title to updateTodo, and repainted and revalidate which re-renders the page.
     */
    public void showDeleteTodo(){
        //remove any existing components first and set title
        contentPanel.removeAll();
        JPanel title = customTitlePanel("DELETE TODO",new Color(0.23f, 0.23f, 0.23f , 0.9f));

        //add the addTodo and the title to the contentPanel
        contentPanel.add(new DeleteTodoGui(this,dataBase), BorderLayout.CENTER);
        contentPanel.add(title, BorderLayout.NORTH);

        //re render the page
        this.repaint();
        this.revalidate();

    }

    //event listeners

    /**
     * The actionPerformed function is the implemented actionListener method, and its purpose is to listen for events from e.g button clicks.
     * For the context of this TODO GUI, the actionPerformed function is dedicated only for the navbar buttons.
     * When a user click e.g List, the name of the button is stored in a string variable, and is then checked against using a switch statement.
     * The list button on navbar will change colour so a selected colour to indicate it has been selected, and then the showListTodo() will be called.
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //stores the button name in the string variable called type
        String type = ((JButton) e.getSource()).getText();

        //checks which button was selected, and then sets the contentPanel to whichever was selected.
        switch (type){
            case "LIST":
                listTodos.setBackground(selectedColor);
                addTodo.setBackground(navColor);
                updateTodo.setBackground(navColor);
                deleteTodo.setBackground(navColor);

                showListTodo();
                break;
            case "ADD":
                addTodo.setBackground(selectedColor);
                listTodos.setBackground(navColor);
                updateTodo.setBackground(navColor);
                deleteTodo.setBackground(navColor);

                showAddTodo();
                break;
            case "UPDATE":
                updateTodo.setBackground(selectedColor);
                addTodo.setBackground(navColor);
                listTodos.setBackground(navColor);
                deleteTodo.setBackground(navColor);
                showUpdateTodo();
                break;
            case "DELETE" :
                deleteTodo.setBackground(selectedColor);
                addTodo.setBackground(navColor);
                updateTodo.setBackground(navColor);
                listTodos.setBackground(navColor);

                showDeleteTodo();
                break;
        }
    }



    //helper functions


    /**
     * The customeTitlePanel function's reponsability is to create a Jpanel which contains a JLabel , and it's meant to be a
     * Title heading.
     * The title is a little altered from the wireframe as I decided not add a underline.
     */
    public JPanel customTitlePanel(String title, Color color){
        //Create the Jpanel panel which would house the title content
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());//settings its layout

        //creating and setting up the Jlabel and its properties
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setSize(60,50);
        label.setFont(new Font("Arial Black",90,26));
        label.setPreferredSize(new Dimension(190,70));
        label.setForeground(Color.WHITE);

        //adding the label title to the panel
        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(color);//setting the panel's background colour

        //and finally returning the panel.
        return panel;

    }




}
