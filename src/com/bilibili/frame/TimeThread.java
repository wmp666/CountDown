package com.bilibili.frame;

public class TimeThread extends Thread{
    private MainContainers mainContainer;
    public TimeThread(MainContainers mainContainer)
    {
        this.mainContainer = mainContainer;
    }
    public void run()
    {
        while(true)
        {
                //setThing();
                mainContainer.initContent();
                mainContainer.repaint();
            try {
                Thread.sleep(300);//反复刷新
            } catch (InterruptedException e) {
                System.out.println("线程异常");

                throw new RuntimeException(e);
            }
            //mainContainer.removeAll();

        }
    }
}
