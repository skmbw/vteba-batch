package com.vteba.test;

public class TestSync {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成方法存根
		TxtThread tt = new TxtThread();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
	}
}

class TxtThread implements Runnable {
	int num = 100;
	String str = new String();

	public void run() {
		while (true) {
			synchronized (str) {
				if (num > 0) {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.getMessage();
					}
					System.out.println(Thread.currentThread().getName() + "this is " + num--);
				}
			}
		}
	}
}
