package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;

public class Error extends JFrame{

    public void frame(String textToShow, int fSizeWidth, int fSizeHeight, int buttonY) {
        JFrame f = new JFrame("Błąd");
        f.setSize(fSizeWidth, fSizeHeight);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setDefaultLookAndFeelDecorated(true);

        try {
            URL iconURL = getClass().getResource("/com/company/error.png");
            System.out.println(iconURL);

            ImageIcon icon = new ImageIcon(iconURL);
            f.setIconImage(icon.getImage());
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        JLabel labelM = new JLabel(textToShow);
        labelM.setBounds(20, 10, fSizeWidth, 40);

        JButton close = new JButton("OK");
        close.setBounds(buttonY, 45, 55, 35);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();

            }
        });

        f.setResizable(false);
        f.add(labelM);
        f.add(close);
        f.setLayout(null);
        f.setVisible(true);
    }
}
