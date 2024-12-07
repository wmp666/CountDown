package com.bilibili.information;

import com.bilibili.exception.InfLackErrorException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class InformationLib {

    private static File InfPath = null;
    private String allThing;

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

    public InformationLib() throws IOException, ParseException {

        String userHomeDir = System.getProperty("user.home");
        StringBuilder stringBuilder = new StringBuilder(userHomeDir);
        stringBuilder.append("\\AppData\\Roaming\\CountDown");
        InfPath = new File(stringBuilder.toString());
        boolean creativeDirB = InfPath.mkdirs();
        System.out.println("文件夹CountDown是否存在" + !creativeDirB);
        InfPath = new File(InfPath.getPath() + "\\information.set");


        System.out.println(userHomeDir);

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
        this.allThing = s;
        System.out.println(s);

        String[] tempInf = s.split("\\n");

        //遍历数组tempInf
        traver(tempInf);

        //查错
        if(tempInf.length != 7){
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


        //String[] tempInf02 = new String[tempInf.length];

        HashMap<String,String> tempInf02 = new HashMap<>();
        //处理数据
        for (int i = 0; i < tempInf.length; i++) {
            String[] temp = tempInf[i].split(":");
            tempInf02.put(temp[0],temp[1]);
        }

        //遍历数组tempInf02
        traver(tempInf02);

        try {
            //数据分类
            Set<String> keySet = tempInf02.keySet();
            for (String key : keySet) {
                switch (key){
                    case "Title"->{
                        title = tempInf02.get(key);
                    }
                    case "StartTime"->{
                        //开始时间
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                        startTime = dateFormat.parse(tempInf02.get(key));
                    }
                    case "FrameType"->{
                        //窗口类型
                        switch (tempInf02.get(key)){
                            case "dialog"->{
                                frameType = 0;
                            }
                            case "window"->{
                                frameType = 1;
                            }
                            case "screen"->{
                                frameType = 2;
                            }
                            default->{
                                throw new InfLackErrorException("Unexpected value: FrameType->" + tempInf02.get(key));
                            }
                        }
                    }
                    case "IsCanExit"->{
                        //是否可以关闭
                        switch (tempInf02.get(key)){
                            case "true"->{
                                isCanExit = true;
                            }

                            case "false"->{
                                isCanExit = false;
                            }
                            default->{
                                throw new InfLackErrorException("Unexpected value: IsCanExit->" + tempInf02.get(key));
                            }
                        }
                    }
                    case "IsTop"->{
                        //是否置顶
                        switch (tempInf02.get(key)){
                            case "true"->{
                                isCanTop = true;
                            }
                            case "false"->{
                                isCanTop = false;
                            }
                            default->{
                                throw new InfLackErrorException("Unexpected value: IsCanTop->" + tempInf02.get(key));
                            }
                        }
                    }
                    case "NumColor"->{
                        //数字颜色
                        switch (tempInf02.get("NumColor")){
                            case "white"->{
                                NumColor = Color.WHITE;
                            }
                            case "black"->{
                                NumColor = Color.BLACK;
                            }
                            case "blue"->{
                                NumColor = new Color(0X48CCFF);
                            }
                            case "red"->{
                                NumColor = new Color(0XFF4D4D);
                            }
                            case "green"->{
                                NumColor = new Color(0X4DFF4D);
                            }
                            default->{
                                throw new InfLackErrorException("Unexpected value: NumColor->" + tempInf02.get("NumColor"));
                            }

                        }
                    }
                    case "MainTheme"->{
                        //主题颜色
                        switch (tempInf02.get("MainTheme")){
                            case "white"->{
                                BGColor = Color.WHITE;
                                titleColor = Color.BLACK;
                            }
                            case "black"->{
                                BGColor = Color.BLACK;
                                titleColor = Color.WHITE;
                            }
                            default -> {
                                throw new InfLackErrorException("Unexpected value: MainTheme->" + tempInf02.get("MainTheme"));
                            }

                        }
                    }
                    default -> {
                        throw new InfLackErrorException("出现错误数据指向");
                    }
                }
                //System.out.println(key + ":" + tempInf02.get(key));
            }
            /*//标题
            title = tempInf02.get("Title");
            //开始时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            startTime = dateFormat.parse(tempInf02.get("StartTime"));
            //窗口类型
            switch (tempInf02.get("FrameType")){
                case "dialog"->{
                    frameType = 0;
                }
                case "window"->{
                    frameType = 1;
                }
                case "screen"->{
                    frameType = 2;
                }
                default->{
                    throw new InfLackErrorException("Unexpected value: FrameType->" + tempInf02.get("FrameType"));
                }
            }

            //是否可以关闭
            switch (tempInf02.get("IsCanExit")){
                case "true"->{
                    isCanExit = true;
                }

                case "false"->{
                    isCanExit = false;
                }
                default->{
                    throw new InfLackErrorException("Unexpected value: IsCanExit->" + tempInf02.get("IsCanExit"));
                }
            }

            //是否置顶
            switch (tempInf02.get("IsTop")){
                case "true"->{
                    isCanTop = true;
                }
                case "false"->{
                    isCanTop = false;
                }
                default->{
                    throw new InfLackErrorException("Unexpected value: IsTop->" + tempInf02.get("IsTop"));
                }
            }
            //数字颜色
            switch (tempInf02.get("NumColor")){
                    case "white"->{
                        NumColor = Color.WHITE;
                    }
                    case "black"->{
                        NumColor = Color.BLACK;
                    }
                    case "blue"->{
                        NumColor = new Color(0X48CCFF);
                    }
                    case "red"->{
                        NumColor = new Color(0XFF4D4D);
                    }
                    case "green"->{
                        NumColor = new Color(0X4DFF4D);
                    }
                    default->{
                        throw new InfLackErrorException("Unexpected value: NumColor->" + tempInf02.get("NumColor"));
                    }

                }

            //主题颜色
            switch (tempInf02.get("MainTheme")){
                    case "white"->{
                        BGColor = Color.WHITE;
                        titleColor = Color.BLACK;
                    }
                    case "black"->{
                        BGColor = Color.BLACK;
                        titleColor = Color.WHITE;
                    }
                    default -> {
                        throw new InfLackErrorException("Unexpected value: MainTheme->" + tempInf02.get("MainTheme"));
                    }

                }*/


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }catch (InfLackErrorException e) {

            addInf();
            throw new InfLackErrorException("出现错误数据或缺失数据");
        }


        fis.close();
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



    //遍历数组
    private static void traver(String[] temp) {

        System.out.println("遍历数组========");
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
        System.out.println("遍历结束========");
    }

    //遍历数组
    private static <K,T> void traver(HashMap<K,T> temp) {

        System.out.println("遍历数组========");

            System.out.println(temp);

        System.out.println("遍历结束========");
    }

    private void addInf() throws IOException {
        FileOutputStream fos = new FileOutputStream(InfPath);
        fos.write("""
                Title:标题
                StartTime:2026-01-01 00-00-00
                FrameType:window
                IsCanExit:true
                IsTop:false
                MainTheme:white
                NumColor:blue
                """.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    public void addInf(String allThing) throws IOException {
        this.allThing = allThing;
        FileOutputStream fos = new FileOutputStream(InfPath);
        fos.write(allThing.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    public String getTitle() {
        return title;
    }



    public Date getStartTime() {
        return startTime;
    }



    public int getFrameType() {
        return frameType;
    }



    public boolean isCanExit() {
        return isCanExit;
    }



    public boolean isCanTop() {
        return isCanTop;
    }



    public Color getNumColor() {
        return NumColor;
    }



    public Color getTitleColor() {
        return titleColor;
    }



    public Color getBGColor() {
        return BGColor;
    }

    public File getInfPath() {
        return InfPath;
    }

    public String getAllThing() {
        return allThing;
    }

}
