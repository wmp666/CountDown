package com.bilibili.information;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InformationLib {

    private File InfPath;

    private String title;
    private Date startTime;

    //0-弹窗式 1-窗口式 2-全屏式 3-屏保式
    private int frameType;
    //true-可以关闭 false-不可以关闭
    private boolean isCanExit;
    //true-置顶 false-不置顶
    private boolean isCanTop;

    private Color NumColor;
    private Color titleColor;
    private Color BGColor;

    //将数据以空格分成三段
    //1-天数的颜色
    //2-标题的颜色
    //3-背景的颜色

    public InformationLib() {
    }

    public InformationLib(String title, Date startTime,
                          int frameType, boolean isCanExit, boolean isCanTop,
                          Color numColor, Color titleColor, Color BGColor) {
        this.title = title;
        this.startTime = startTime;
        this.frameType = frameType;
        this.isCanExit = isCanExit;
        this.isCanTop = isCanTop;
        NumColor = numColor;
        this.titleColor = titleColor;
        this.BGColor = BGColor;
    }

    public InformationLib(File infPath) throws IOException, ParseException {
        this.InfPath = infPath;

        boolean b = !(InfPath.exists());
        while (b){
            System.out.println("文件不存在");
            JOptionPane.showMessageDialog(null,
                    "information.set文件不存在",
                    "error",
                    JOptionPane.INFORMATION_MESSAGE);
            InfPath.createNewFile();
            b = !(InfPath.exists());
        }

        if(InfPath.length() == 0){
            //添加默认数据
            System.out.println("文件为空");
            JOptionPane.showMessageDialog(null,
                    "information.set文件为空，正在添加默认数据",
                    "error",
                    JOptionPane.INFORMATION_MESSAGE);
            addInf();
        }

        FileInputStream fis = new FileInputStream(InfPath);
        String s = new String(fis.readAllBytes(),"UTF-8");
        System.out.println(s);

        String[] tempInf = s.split("\\n");

        //遍历数组tempInf
        traver(tempInf);

        //查错
        if(tempInf.length != 8){
            JOptionPane.showMessageDialog(null,
                    "information.set文件中,数据数量错误,建议更正",
                    "error",
                    JOptionPane.ERROR_MESSAGE);

            int i = JOptionPane.showConfirmDialog(null,
                    "将错误的数据删除，同时使用默认数据？",
                    "询问",
                    JOptionPane.YES_NO_OPTION);

            if (i == 0){
                addInf();
                JOptionPane.showMessageDialog(null,
                        "已设置为默认",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null,
                        "开始重新加载...",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE);

                String s1 = new String(fis.readAllBytes(),"UTF-8");
                tempInf = s1.split("\\n");
            }else{
                System.exit(0);
            }

        }


        String[] tempInf02 = new String[tempInf.length];
        //处理数据
        for (int i = 0; i < tempInf.length; i++) {
            tempInf02[i] = tempInf[i].split(":")[1];
        }

        //遍历数组tempInf02
        traver(tempInf02);

        title = tempInf02[0];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        startTime = dateFormat.parse(tempInf02[1]);
        frameType = Integer.parseInt(tempInf02[2]);
        if (tempInf02[3] == "true"){
            isCanExit = true;
        }else if (tempInf02[3] == "false"){
            isCanExit = false;
        }

        if (tempInf02[4] == "true"){
            isCanTop = true;
        }else if (tempInf02[4] == "false"){
            isCanTop = false;
        }
        int temp01 = Integer.parseInt(tempInf02[5], 16);
        NumColor = new Color(temp01);
        int temp02 = Integer.parseInt(tempInf02[6], 16);
        titleColor = new Color(temp02);
        int temp03 = Integer.parseInt(tempInf02[7], 16);
        BGColor = new Color(temp03);


        fis.close();
    }

    //遍历数组
    private static void traver(String[] temp) {

        System.out.println("遍历数组========");
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
        System.out.println("遍历结束========");
    }

    private void addInf() throws IOException {
        FileOutputStream fos = new FileOutputStream(InfPath);
        fos.write("""
                Title:标题
                StartTime:2026-01-01 00-00-00
                FrameType:1
                IsCanExit:true
                IsCanTop:true
                NumColor:48CCFF
                TitleColor:000000
                BGColor:FFFFFF
                """.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getFrameType() {
        return frameType;
    }

    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public boolean isCanExit() {
        return isCanExit;
    }

    public void setCanExit(boolean canExit) {
        isCanExit = canExit;
    }

    public boolean isCanTop() {
        return isCanTop;
    }

    public void setCanTop(boolean canTop) {
        isCanTop = canTop;
    }

    public Color getNumColor() {
        return NumColor;
    }

    public void setNumColor(Color numColor) {
        NumColor = numColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
    }

    public Color getBGColor() {
        return BGColor;
    }

    public void setBGColor(Color BGColor) {
        this.BGColor = BGColor;
    }

    public File getInfPath() {
        return InfPath;
    }

    public void setInfPath(File infPath) {
        InfPath = infPath;
    }
}
