package assets;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    /**
     * The CustomButton class extends Jbutton and customises the look for the button which better suits the theme of the TODOGUI.
     * I made use of two constructors, mainly the second one allowing more options for customisation.
     * Since it makes use of inheritence of Jbutton, it means all the relevant methods & properties are extended, and I can add
     * the CustomButton to the panel just like any other component.
     */


    //default
    public CustomButton(String label){
        setOpaque(true);
        setBorderPainted(false);
        setText(label);
        setFont(new Font("Arial Black",10,15));
        setBackground(new Color(23, 23, 23));
        setForeground(Color.white);
        setPreferredSize(new Dimension(150,35));
        setFocusPainted(false);
    }


    //customizable
    public CustomButton(String label, int textSize, Color bgColor, Color fontColor, String font){
        setOpaque(true);
        setBorderPainted(false);
        setText(label);
        setFont(new Font(font,10,textSize));
        setBackground(bgColor);
        setForeground(fontColor);
        setPreferredSize(new Dimension(200,textSize+20));
        setFocusPainted(false);
    }



}
