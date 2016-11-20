
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author partho
 */
public class patternize_text_field {
    
    public void capitalize(JTextField textField){
        textField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent event){
                char keyChar=event.getKeyChar();
                String strValOfkey=String.valueOf(keyChar);
                int keyCode=event.getKeyCode();
                
                if(Character.isLowerCase(keyChar)){
                    event.setKeyChar(Character.toUpperCase(keyChar));
                }
            }
        });
    }
    
    public boolean isRegular(String s,String regExpString){
        return Pattern.compile(regExpString).matcher(s).matches();
    }
}
