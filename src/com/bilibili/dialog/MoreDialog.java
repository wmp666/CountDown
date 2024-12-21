package com.bilibili.dialog;

import com.bilibili.dialog.panelForMore.DataPanel;
import com.bilibili.dialog.panelForMore.InfPanel;
import com.bilibili.dialog.panelForMore.OtherPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoreDialog extends JDialog {
    private final JPanel choosePane = new JPanel();

    private int mainPaneWidth = 400;
    //private JPanel mainPane = new JPanel();
    public MoreDialog(){

        choosePane.setBounds(0,0, 100, 400);

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(new JButton("数据"));
        buttons.add(new JButton("目录"));
        buttons.add(new JButton("其他"));

        int i = 0;
        for (JButton button : buttons) {
            button.setBounds(0,40 * i,choosePane.getWidth(),40);
            button.setBackground(new Color(0xE6E6E6));
            button.setForeground(new Color(0x000000));
            button.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
            button.setBorder(null);//去除边框
            button.setFocusPainted(false);//去除焦点
            button.setOpaque(false);//去除背景


            int finalI = i;


            button.addActionListener(e -> {

                this.getContentPane().removeAll();
                this.getContentPane().add(choosePane);
                if(finalI == 0){
                    InfPanel mainPanel = new InfPanel(choosePane.getWidth(),0,400, 400);
                    mainPaneWidth = mainPanel.getWidth();
                    this.getContentPane().add(mainPanel);
                }else if(finalI == 1){
                    DataPanel mainPanel = new DataPanel();

                    this.getContentPane().add(mainPanel);
                }else{
                    OtherPanel mainPanel = new OtherPanel();

                    this.getContentPane().add(mainPanel);
                }

                this.getContentPane().repaint();

            });

            choosePane.add(button);
            i++;
        }

        choosePane.setLayout(null);
        //choosePane.setBackground(new Color(0x000000));


        this.getContentPane().add(choosePane);




        initFrame();

        this.setVisible(true);
    }

    private void initFrame() {
        this.setTitle("更多");
        this.setSize(choosePane.getWidth() + mainPaneWidth
                , choosePane.getHeight());
        //居中
        this.setLocationRelativeTo(null);
        //窗口无法调节
        this.setResizable(false);
        //窗口不可操作
        this.setModal(true);

    }
}
