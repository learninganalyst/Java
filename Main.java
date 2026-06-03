
public class Main {
	static volatile boolean keepLooping = true; 
	public static void main(String[] args) throws InterruptedException {		
	Thread looper = new Thread(() -> {
		System.out.println("Starting the looping thread!");
		
		long count = 0;
		if(keepLooping) {
		while(true) {
			count++;
		}
		}
		System.out.println("Done looping! Loop count: " + count);
	});
		
	looper.start();
	Thread.sleep(100);
	keepLooping = false;
	System.out.println("KeepLooping has been set to false");
	
	looper.join();
	System.out.println("Main: Program finished.");
	}
}

