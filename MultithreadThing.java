
public class MultithreadThing implements Runnable {
	
	
	private int threadNumber;
	public MultithreadThing(int threadnumber) {
		this.threadNumber = threadNumber; 
		
	}
	@Override
	public void run() {
		for(int i = 1; i<=5; i++) {
			System.out.println(i + " from thread " + threadNumber);
				
			}
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		


