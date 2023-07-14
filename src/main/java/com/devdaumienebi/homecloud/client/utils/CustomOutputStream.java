package com.devdaumienebi.homecloud.client.utils;

import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * 
 * @author Derick Daumienebi Sakpa
 *Class to redirect the console output to a @JTextArea
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;

    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
    	//SwingUtilities.invokeLater(() -> textArea.append(String.valueOf((char) b)));
        textArea.append(String.valueOf((char) b));
    }
}
