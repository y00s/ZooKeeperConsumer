import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.zookeeper.KeeperException;


public class main {

	/**
	 * This function extract the characters with the least time appearances
	 * and return as a String, in case there is non returns null
	 *
	 * Written by yosef
	 */
	static String getMinimumAperanceChars(String text){
		String result ="";
		Map<Character, Integer> map = new HashMap<Character, Integer>();

		for (int i = 0; i < text.length(); i++) {
			Character key = text.charAt(i);
			if (map.containsKey(key)) {
				map.put(key, map.get(key)+1);
			}
			else {
				map.put(key, 1);
			}
		}

		Integer min = Collections.min(map.values());

		for (Character letter : map.keySet()) {
			if (map.get(letter)==min) {
				result.concat(letter.toString());		
			}

		}
		System.out.println(result);
		return result;

	}




	public static void main(String[] args) {

		System.out.println("Starting...");
		// Start the connection with ZooKeeper and get the 2 queues, TEXTS and RESULT.
		String address= "127.0.0.1:2181";
		SyncPrimitive.Queue textsQueue = new SyncPrimitive.Queue(address, "/TEXTS");
		SyncPrimitive.Queue resultQueue = new SyncPrimitive.Queue(address, "/RESULT");
		try {
			// Process the texts by sending to getMinimumAperanceChars() function.
			String text1 = textsQueue.consume();
			String text2 = textsQueue.consume();
			
			//enter the result into the RESULT queue
			resultQueue.produce(getMinimumAperanceChars(text1));
			resultQueue.produce(getMinimumAperanceChars(text2));
			
			System.out.println("Done.");
			
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
