package com.demo.multi_thread.Volatile;

/**
 * 可见性问题系列2
 * 如下代码：
 * 在Thread1的基础上，新增输出语句或者sleep语句；
 * 则在run()方法中不会出现死循环
 * 原因是：
 * 		只要CPU有时间，JVM会尽力去保证变量值的更新；
 * 		加入输出语句或者sleep语句后，CPU就有时间了
 * @author tangliang
 *
 */
public class Thread2 extends Thread {
	
	private boolean isRunning = true;
	int m;

	public static void main(String[] args) throws InterruptedException {
		Thread2 thread = new Thread2();
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
            
            //System.out.println(m);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
