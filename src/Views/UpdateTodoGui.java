package Views;

import Models.Todo;
import assets.*;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class UpdateTodoGui extends JPanel implements ActionListener {
    /**
     * The UpdateTodoGui class inherits from Jpanel and its main purpose is to display a form
     * to the user, the form will involve about 6 inputs, each relating to each property of the Todo.
     * The user can select the TODO they which to update, and in turn update any of its properties.
     * When they successfully update it, they will be shown a Dialog message confirming that the todo
     * has been updated.
     *
     * If there are no todos, the user will receive a dialog message saying there is nothing to update.
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
     * The constructor basically does all the heavy work of setting up the update form.
     */
    public UpdateTodoGui(GUI parent, DataBase dataBase) {
        //initialising parent and databasehelper so that these can be accessed globally.
        this.parent = parent;
        dataBaseHelper = dataBase;

        //Retrieves all the todos from the database & stores it in todoArr arraylist.
        todosArr = dataBaseHelper.getAllTodo();



        //Colour palette
        navColor = new Color(23, 23, 23);
        selectedColor = new Color(10, 10, 10);
        transparent = new Color(0f,0f,0f,0f);


        //Sets up the layout
        setLayout(new BorderLayout());
        setBackground(transparent);
        setBorder(BorderFactory.createEmptyBorder(100,100,100,100));//Creates a margin of 100px all around


        //If there are no todos, then the dialog "Nothing to update!" is shown and the nothing after that happens
        if(todosArr.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Nothing to update!",
                    "!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }



        //Labels
        selectTodoLabel = new JLabel("Select TODO: ", SwingConstants.RIGHT);
        textLabel = new JLabel("Text: ", SwingConstants.RIGHT);
        dueLabel = new JLabel("Due: ", SwingConstants.RIGHT);
        categoryLabel = new JLabel("Category: ", SwingConstants.RIGHT);
        importanceLabel = new JLabel("Importance: ", SwingConstants.RIGHT);
        statusLabel = new JLabel("Status: ", SwingConstants.RIGHT);

        //change text colour
        selectTodoLabel.setForeground(Color.WHITE);
        textLabel.setForeground(Color.white);
        dueLabel.setForeground(Color.white);
        categoryLabel.setForeground(Color.white);
        importanceLabel.setForeground(Color.white);
        statusLabel.setForeground(Color.white);

        //arrays for some inputs
        categoriesArray = new String[]{"RED", "WHITE", "BLUE", "PURPLE", "YELLOW", "GREEN"};
        importanceArray = new String[]{"LOW", "NORMAL", "HIGH"};
        statusArray = new String[]{"PENDING", "STARTED", "PARTIAL", "COMPLETED"};

        //Gets all the todosNames from todoArr
        ArrayList<String> todoNames = new ArrayList<>();
        for(int i=0; i<todosArr.size();i++){
            Todo todo = todosArr.get(i);
            todoNames.add(i+1+". "+todo.getText());
        }


        //Sets the date picker, using an external library called JXDatePicker
        datePicker = new JXDatePicker();
        datePicker.setDate(new Date());//sets the current date
        datePicker.getEditor().setEditable(false);//Makes the date uneditable, so the user cannot further edit it after selecting a date

        //Setting up the timespinner, which is apart of the dateinput section, and it allows the user to select the time
        timeSpinner =HelperFunctions.formatSpinner( new JSpinner( new SpinnerDateModel() ));
        JSpinner.DateEditor time = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(time);
        timeSpinner.setValue(new Date()); //the current time will be shown


        //setting up inputs for the form
        textEditText = HelperFunctions.formatTextField(new JTextField(""));
        todos = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(todoNames)));
        todos.getEditor().setEnabled(false);
        categoriesSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(categoriesArray)));
        importanceSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(importanceArray)));
        statusSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(statusArray)));

        //Date picker event listener is set up here, is called whenever a date is picked
        datePicker.getEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                //Upon selecting a date, GUI re renders.
                parent.repaint();
            }
        });

        //Setting the text / values to the inputs to the property of the first todo
        textEditText.setText(todosArr.get(0).getText());
        categoriesSpinner.setValue(todosArr.get(0).getCat().toString());
        importanceSpinner.setValue(todosArr.get(0).getImportance().toString());
        statusSpinner.setValue(todosArr.get(0).getCompletion().toString());
        datePicker.getEditor().setValue(Timestamp.valueOf(todosArr.get(0).getDue()));
        timeSpinner.setValue(Timestamp.valueOf(todosArr.get(0).getDue()));





        //spinner event listener
        spinnerListener();

        //wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridBagLayout());
        wrapper.setBackground(transparent);

        //Gridbag constraints for custom layout constraints
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



        gbc.gridy=1;
        gbc.gridx=0;
        wrapper.add(textLabel,gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(textEditText, gbc);
        gbc.weightx=0;

        gbc.gridy=2;
        gbc.gridx=0;
        wrapper.add(dueLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        JPanel dateWrapper = new JPanel();
        dateWrapper.setLayout(new GridBagLayout());
        dateWrapper.setLayout(new BorderLayout());
        dateWrapper.setBackground(transparent);
        dateWrapper.add(datePicker, BorderLayout.CENTER);
        dateWrapper.add(timeSpinner, BorderLayout.EAST);

        wrapper.add(dateWrapper, gbc);
        gbc.weightx=0;

        gbc.gridy=3;
        gbc.gridx=0;
        wrapper.add(categoryLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(categoriesSpinner, gbc);
        gbc.weightx=0;

        gbc.gridy=4;
        gbc.gridx=0;
        wrapper.add(importanceLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(importanceSpinner, gbc);
        gbc.weightx=0;

        gbc.gridy=5;
        gbc.gridx=0;
        wrapper.add(statusLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(statusSpinner, gbc);
        gbc.weightx=0;

        //submit button
        submit = new CustomButton("UPDATE TODO");
        submit.addActionListener(this);

        gbc.gridy=6;
        gbc.gridx=1;
        wrapper.add(submit, gbc);

        add(wrapper, BorderLayout.CENTER);


    }

    //event listeners

    /**
     * Here, when the submit button is pressed, the actionPerformed listener is triggered, and here the user will see the
     * "TODO updated!" dialog message, and from here the edited properties are collected, and using the databaseHelper,
     *  updates the todo.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(formValidate()){
            JOptionPane.showMessageDialog(this,
                    "TODO updated!");
            Todo todo = HelperFunctions.getTodo(textEditText.getText(),
                    HelperFunctions.getFormattedDate(datePicker,timeSpinner),
                    categoriesSpinner.getValue().toString(),
                    importanceSpinner.getValue().toString(),
                    statusSpinner.getValue().toString());
            todo.setId(todosArr.get(getSelectedTodoIndex()).getId());
            dataBaseHelper.updateTodo(todo);


        }
    }

    //helper functions

    /**
     * Used for form validation. In this case, would want to check if the text is not empty, else user will
     * ger a warning dialog message if "Give todo a name!"
     */
    private boolean formValidate(){
        if(textEditText.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,
                    "Give todo a name!",
                    "Invalid form",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }


    /**
     * This returns the index of the selected todo from the todoArr
     */
    private int getSelectedTodoIndex(){
        return Integer.parseInt(todos.getValue().toString().split(" ")[0].replace(".",""))-1;
    }


    /**
     * the spinnerListener function adds an eventListener to the select todos spinner.
     * Whenever the the user selects a todo, the spinnerListener is triggered and in turn it executes these
     * lines of codes which are responsible for setting the input field's properties to the selected todo's
     * properties.
     */
    private void spinnerListener(){
        todos.addChangeListener(l->{
            Todo todo = todosArr.get(getSelectedTodoIndex());
            textEditText.setText(todo.getText());
            System.out.println(todo.getCat());
            categoriesSpinner.setValue(todo.getCat().toString());
            importanceSpinner.setValue(todo.getImportance().toString());
            statusSpinner.setValue(todo.getCompletion().toString());
            datePicker.getEditor().setValue(Timestamp.valueOf(todo.getDue()));
            timeSpinner.setValue(Timestamp.valueOf(todo.getDue()));
        });


    }






}


