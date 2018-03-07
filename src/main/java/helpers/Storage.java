package helpers;

import java.util.HashMap;
import java.util.Map;

public class Storage {
	
	/**
	 * the maximum nb of stored objects is 2^32
	 */
	
	private int count = 1;
	private Map<Integer, Object> map = new HashMap<Integer,Object>();
	
	public Object get(int key){
		return map.get(key);
	}
	
	public int store(Object obj){
		map.put(count, obj);
		int res = count;
		count++;
		return res;
	}
	
	public void remove(int key){
		map.put(key, null);
	}


}
