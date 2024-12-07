package com.wmp.main;

import com.wmp.frame.SettingsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        JDialog settingsDialog = new JDialog();
        settingsDialog.setTitle("设置");

        settingsDialog.setLayout(null);
        //居中
        settingsDialog.setLocationRelativeTo(null);
        //大小
        settingsDialog.setSize(470, 340);
        //设置图标
        settingsDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\image\\icon.png"));

        SettingsPanel settingsPanel;
        try {
            settingsPanel = new SettingsPanel();
            settingsDialog.getContentPane().add(settingsPanel.getPanel());
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }

        settingsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                JOptionPane.showMessageDialog(null,
                        "设置已保存,请自己刷新",
                        "通知",
                         JOptionPane.INFORMATION_MESSAGE);
                System.out.println("设置已保存");
                try {

                    //输入新数据

                    FileOutputStream fos = new FileOutputStream("information.set");
                    fos.write(settingsPanel.getAllThing().getBytes(StandardCharsets.UTF_8));
                    fos.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



            }

        });


    }
}
