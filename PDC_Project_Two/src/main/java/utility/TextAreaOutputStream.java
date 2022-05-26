/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author jestr
 */
public class TextAreaOutputStream extends OutputStream
{
    private JTextArea text;
    
    public TextAreaOutputStream(JTextArea text)
    {
        this.text = text;
    }

    @Override
    public void write(int b) throws IOException
    {
        text.append(String.valueOf((char)b));
        text.setCaretPosition(text.getDocument().getLength());
        text.update(text.getGraphics());
    }
}
