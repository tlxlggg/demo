package com.demo.multi_thread.Volatile;

/**
 * 可见性问题系列1
 * 如下代码：
 * 会一直在run()方法中进行死循环；
 * 原因是：
 * 		main线程在进行thread.setRunning(false)后，
 * 		由于isRunning没有被volatile修饰，	
 * 		所以在mian线程中修改的isRunning并不会被立即刷入主内存中，
 * 		在thread线程中也就感知不到isRunning的修改
 * @author tangliang
 *
 */
public class Thread1 extends Thread{
	
	private boolean isRunning = true;
	int m;

	public static void main(String[] args) throws InterruptedException {
		Thread1 thread = new Thread1();
        thread.start();
        Thread.sleep(1000);
        thread.setRunning(false);

        System.out.println("已经赋值为false");
	}

	@Override
	public void run() {
		System.out.println("进入run了");
        while (isRunning == true) {
            int a=2;
            int b=3;
            int c=a+b;
            m=c;
        }
        System.out.println(m);
        System.out.println("线程被停止了！");
	}
	
	public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
