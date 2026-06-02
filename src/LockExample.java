import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
	public static void main(String[] args) {
		try {
		Lock lock = new ReentrantLock(true);
		lock.lock();
		}finally {
	
		lock.unlock;
	}

}
