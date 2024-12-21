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

                mainContainer.initContent();
                mainContainer.initButton();
                mainContainer.repaint();

            try {
                Thread.sleep(200);//反复刷新
            } catch (InterruptedException e) {
                System.out.println("线程异常");
                throw new RuntimeException(e);
            }

            //mainContainer.removeAll();

        }
    }
}
