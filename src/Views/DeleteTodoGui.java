package Views;

import Models.Todo;
import assets.CustomButton;
import assets.HelperFunctions;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteTodoGui extends JPanel implements ActionListener {
    /**
     * The DeleteTodoGui displays 1 input field for the user to select a todo they wish to delete.
     * When they press delete, a "TODO deleted!" dialog message.
     * If theres no todos, the user will receive a "Nothing to delete" message
     */


    //private variables
    private JLabel textLabel, dueLabel, categoryLabel, importanceLabel, statusLabel, selectTodoLabel;
    private String[] categoriesArray, importanceArray, statusArray;
    private JTextField textEditText;
    private JXDatePicker datePicker;
    private JSpinner todos,categoriesSpinner, importanceSpinner, statusSpinner , timeSpinner;
    private JButton submit;
    private Color navColor, selectedColor, transparent;
    private DataBase dataBaseHelper;
    private ArrayList<Todo> todosArr;
    private GUI parent;

    /**
     * The constructor takes two arguments, the parent & the database connection.
     * The constructor basically does all the heavy work of setting up the delete form.
     */
    public DeleteTodoGui(GUI parent, DataBase dataBase) {
        //Initialising parent and database helper.
        this.parent = parent;
        dataBaseHelper = dataBase;

        //retrieves all the todos from the database and stores it in an arraylist
        todosArr = dataBaseHelper.getAllTodo();

        //colour palette
        navColor = new Color(23, 23, 23);
        selectedColor = new Color(10, 10, 10);
        transparent = new Color(0f,0f,0f,0f);

        //setting up the layout
        setLayout(new BorderLayout());
        setBackground(transparent);
        setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        //Display a "Nothing to delete!" dialog if todoArr is empty, nothing after that happens.
        if(todosArr.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Nothing to delete!",
                    "!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }



        //Labels
        selectTodoLabel = new JLabel("Select TODO: ", SwingConstants.RIGHT);

        //change text colour
        selectTodoLabel.setForeground(Color.WHITE);

        //retrieves all the todo text names from todoArr and stores it in todoNames arrayList
        ArrayList<String> todoNames = new ArrayList<>();
        for(int i=0; i<todosArr.size();i++){
            Todo todo = todosArr.get(i);
            todoNames.add(i+1+". "+todo.getText());
        }

        //inputs
        todos = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(todoNames)));
        todos.getEditor().setEnabled(false);


        //wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridBagLayout());
        wrapper.setBackground(transparent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx=0;

        gbc.gridy=0;
        wrapper.add(selectTodoLabel,gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(todos, gbc);
        gbc.weightx=0;


        //submit button
        submit = new CustomButton("DELETE TODO");
        submit.addActionListener(this);

        gbc.gridy=6;
        gbc.gridx=1;
        wrapper.add(submit, gbc);

        add(wrapper, BorderLayout.CENTER);


    }


    //helper functions

    /**
     * This returns the index of the selected todo from the todoArr
     */
    private int getSelectedTodoIndex(){
        return Integer.parseInt(todos.getValue().toString().split(" ")[0].replace(".",""))-1;
    }


    //event listeners

    /**
     * Here, when the submit button is pressed, the actionPerformed listener is triggered, and here the user will see the
     * "TODO deleted!" dialog message, and from here the selected todo will be deleted using the databaseHelper.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                "TODO deleted!");
        Todo todo = todosArr.get(getSelectedTodoIndex());
        dataBaseHelper.deleteFromTodo(Integer.toString(todo.getId()));
    }


}


