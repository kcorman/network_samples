
public class ThreadExample {
	private static boolean b = true;
	static int x = 5;
	public static void main(String[] args) throws InterruptedException {
		MyRunnable runnable = new MyRunnable();
		Thread t = new Thread(runnable);
		t.start();
		
		while(b) {
			System.out.println("Foo");
			Thread.sleep(1000);
			x++;
		}
	}
	
	public static void printBar() throws InterruptedException {
		while(true) {
			System.out.println("Bar: " + x);
			Thread.sleep(1000);
		}
	}

	private static class MyRunnable implements Runnable {
		@Override
		public void run() {
			try {
				printBar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
