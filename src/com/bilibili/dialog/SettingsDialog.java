package com.bilibili.dialog;

import com.bilibili.frame.SettingsPanel;
import com.bilibili.information.InformationLib;
import com.bilibili.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;

public class SettingsDialog extends JDialog {
    public SettingsDialog() {
        this.setTitle("设置");

        this.setLayout(null);
        //居中
        this.setLocationRelativeTo(null);
        //大小
        this.setSize(470, 340);
        //设置图标
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("lib\\image\\icon.png"));
        //窗口不可操作
        this.setModal(true);
        SettingsPanel settingsPanel;
        try {
            settingsPanel = new SettingsPanel();
            this.getContentPane().add(settingsPanel.getPanel());
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {


                int i = JOptionPane.showConfirmDialog(null,
                        "将数据保存,并刷新？",
                        "询问",
                        JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    InformationLib informationLib;
                    try {
                        informationLib = new InformationLib();
                        //输入新数据
                        informationLib.addInf(settingsPanel.getAllThing());
                    } catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        Main.reload(new InformationLib());
                    } catch (IOException | ParseException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }

        });

        this.setResizable(false);
        this.setVisible(true);
    }
}
