package org.kpu.ticketbox.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.kpu.ticketbox.payment.Receipt;

public class Statistics {
	public static double sum(HashMap<Integer, Receipt> map) {
		Set<Integer> keyset = map.keySet();
		Iterator<Integer> it = keyset.iterator();
		double sum = 0.0;
		for(int i = 0; i < map.size(); i++)
			sum += map.get(it.next()).getTotalAmount();
		return sum;
	}
}
