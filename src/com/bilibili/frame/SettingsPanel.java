package com.bilibili.frame;

import com.bilibili.information.InformationLib;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

public class SettingsPanel{

    private TextArea panel;
    private String allThing;

    public SettingsPanel() throws IOException, ParseException {

        panel = new TextArea();
        panel.setBounds(0,0,450,300);
        panel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));

        InformationLib informationLib = new InformationLib();


        allThing = informationLib.getAllThing();
        panel.append(allThing);

    }

    public TextArea getPanel() {
        return panel;
    }

    public String getAllThing() {

        this.allThing = panel.getText();
        return allThing;
    }
}
