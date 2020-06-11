package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

public class Frame extends JFrame {

    public void Frame() {
        JFrame f = new JFrame("Zaplanuj wyłączenie kamputera");

        f.setSize(490, 290);
        f.setLocation(100, 150);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setDefaultLookAndFeelDecorated(true);

        try {
            URL iconURL = getClass().getResource("/com/company/timer_icon.png");
            System.out.println(iconURL);

            ImageIcon icon = new ImageIcon(iconURL);
            f.setIconImage(icon.getImage());
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        JLabel labelM = new JLabel("Wpisz czas:");
        labelM.setBounds(50, 50, 200, 30);

        JTextField time = new JTextField();
        time.setBounds(50, 100, 200, 30);


        JButton button = new JButton("Zatwierdz");
        button.setBounds(330, 100, 100, 30);

        JButton cancel = new JButton("Anuluj ostatni plan");
        cancel.setBounds(50, 150, 200, 30);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("gozin");
        listModel.addElement("minut");
        listModel.addElement("sekund");

        JList<String> list = new JList<>(listModel);
        list.setBounds(260, 90, 60, 55);


        ActionListener cancelListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plan(0, true);
            }
        };


        ActionListener planListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                try{
                    int timeInteger = Integer.parseInt(time.getText());

                        if (list.getSelectedIndex() == 0){
                            plan(timeInteger*3600, false);
                        }else if (list.getSelectedIndex() == 2){
                            plan(timeInteger, false);
                        } else {
                            plan(timeInteger * 60, false);
                        }

                }catch (NumberFormatException excp){
                    excp.fillInStackTrace();
                }

                time.setText("");
            }
        };

        time.addActionListener(planListener);
        button.addActionListener(planListener);
        cancel.addActionListener(cancelListener);

        f.add(cancel);
        f.add(list);
        f.add(labelM);
        f.add(time);
        f.add(button);

        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void plan(int timeToOff, boolean calcel){



        ProcessBuilder processBuilder = new ProcessBuilder();

        if(!calcel){
            processBuilder.command("cmd.exe", "/c", "shutdown -s -t " + timeToOff );
        }else{
            processBuilder.command("cmd.exe", "/c", "shutdown /a");
        }


        try{
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine())!= null){
                System.out.println(line);
            }

            int exitcode = process.waitFor();
            System.out.println("\nExited with error code : " + exitcode);

            if (exitcode == 1190){
                Error errorFrame = new Error();
                errorFrame.frame("Masz juz zaplanowane wyłączenie, najpierw je anuluj następnie zaplanuj ponownie." + exitcode, 520, 115, 220);
            }else if(exitcode == 1116){
                Error errorFrame = new Error();
                errorFrame.frame("Nie ma zaplanowaego wyłączenia.", 250,115, 95);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

