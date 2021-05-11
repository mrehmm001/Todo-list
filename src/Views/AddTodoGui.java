package Views;

import Models.Todo;
import assets.*;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.util.Date;

public class AddTodoGui extends JPanel implements ActionListener {

    /**
     * The AddTodoGui displays a form for the user to fill out.
     * The form contains input, each input representing one of the properties of a todo.
     * When the user clicks submit, the todo is added to the database and then the user
     * is presented with a "TODO added" dialog message to confirm that the todo was added,
     * they can check to see in list page and the todo will show there.
     */

    //private variables
    private JLabel textLabel, dueLabel, categoryLabel, importanceLabel, statusLabel;
    private String[] categoriesArray, importanceArray, statusArray;
    private JTextField textEditText;
    private JXDatePicker datePicker;
    private JSpinner categoriesSpinner, importanceSpinner, statusSpinner, timeSpinner;
    private JButton submit;
    private Color navColor, selectedColor, transparent;
    private DataBase dataBaseHelper;
    private GUI parent;

    /**
     * The constructor takes two inputs, parent * database, it does all the heavy work such as
     * setting up the layout for this section.
     */
    public AddTodoGui(GUI parent , DataBase dataBase) {
        //Initialising parent and database helper.
        this.parent = parent;
        dataBaseHelper = dataBase;

        //Colour palette
        navColor = new Color(23, 23, 23);
        selectedColor = new Color(10, 10, 10);
        transparent = new Color(0f,0f,0f,0f);

        //set up the layout
        setLayout(new BorderLayout());
        setBackground(transparent);
        setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

        //Labels
        textLabel = new JLabel("Text: ", SwingConstants.RIGHT);
        dueLabel = new JLabel("Due: ", SwingConstants.RIGHT);
        categoryLabel = new JLabel("Category: ", SwingConstants.RIGHT);
        importanceLabel = new JLabel("Importance: ", SwingConstants.RIGHT);
        statusLabel = new JLabel("Status: ", SwingConstants.RIGHT);

        //Set label colour to white
        textLabel.setForeground(Color.white);
        dueLabel.setForeground(Color.white);
        categoryLabel.setForeground(Color.white);
        importanceLabel.setForeground(Color.white);
        statusLabel.setForeground(Color.white);

        //arrays for some inputs
        categoriesArray = new String[]{"RED", "WHITE", "BLUE", "PURPLE", "YELLOW", "GREEN"};
        importanceArray = new String[]{"LOW", "NORMAL", "HIGH"};
        statusArray = new String[]{"PENDING", "STARTED", "PARTIAL", "COMPLETED"};

        //inputs

        //Datepicker and Timepicker is initialised here
        datePicker = new JXDatePicker();
        datePicker.setDate(new Date());//sets the current date
        datePicker.getEditor().setEditable(false);
        timeSpinner =HelperFunctions.formatSpinner( new JSpinner( new SpinnerDateModel() ));
        JSpinner.DateEditor time = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(time);
        timeSpinner.setValue(new Date()); //the current time will be shown

        //Other inputs are initialised
        textEditText = HelperFunctions.formatTextField(new JTextField(""));
        categoriesSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(categoriesArray)));
        importanceSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(importanceArray)));
        statusSpinner = HelperFunctions.formatSpinner(new JSpinner(new SpinnerListModel(statusArray)));


        //Date picker event listener, triggered when ever user selects a date
        datePicker.getEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                //re-renders the section after date is picked
                parent.repaint();
            }
        });



        //wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridBagLayout());
        wrapper.setBackground(transparent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx=0;
        gbc.gridy=0;
        wrapper.add(textLabel,gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(textEditText, gbc);
        gbc.weightx=0;


        gbc.gridy=1;
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
        gbc.weightx=0;
        wrapper.add(dateWrapper, gbc);

        gbc.gridy=2;
        gbc.gridx=0;
        wrapper.add(categoryLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(categoriesSpinner, gbc);
        gbc.weightx=0;

        gbc.gridy=3;
        gbc.gridx=0;
        wrapper.add(importanceLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(importanceSpinner, gbc);
        gbc.weightx=0;

        gbc.gridy=4;
        gbc.gridx=0;
        wrapper.add(statusLabel, gbc);
        gbc.gridx=1;
        gbc.weightx=1;
        wrapper.add(statusSpinner, gbc);
        gbc.weightx=0;

        //submit button
        submit = new CustomButton("ADD TODO");
        submit.addActionListener(this);

        gbc.gridy=5;
        gbc.gridx=1;
        wrapper.add(submit, gbc);

        //Finally adds the add todo content to the panel centre
        add(wrapper, BorderLayout.CENTER);

    }

    /**
     * The actionPerformed is triggered when the add todo button is pressed.
     * The user is presented with a "Todo added" dialog message.
     * The todo is then added to the database
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(formValidate()){
            JOptionPane.showMessageDialog(this,
                    "TODO added!");
            dataBaseHelper.insertIntoTodo(HelperFunctions.getTodo(textEditText.getText(),
                                                    HelperFunctions.getFormattedDate(datePicker, timeSpinner),
                                                    categoriesSpinner.getValue().toString(),
                                                    importanceSpinner.getValue().toString(),
                                                    statusSpinner.getValue().toString()));

        }
    }

    //helper function

    /**
     *Form validate checks to see if text input field isnt empty, and return true if its not empty.
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








}
