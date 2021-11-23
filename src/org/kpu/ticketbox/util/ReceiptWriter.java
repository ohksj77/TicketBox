package org.kpu.ticketbox.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.kpu.ticketbox.payment.Receipt;

public class ReceiptWriter {
	public void printConsole(HashMap<Integer, Receipt> map) {
		if(map.size() == 0) {
			System.out.println("정보가 없습니다.");
			return;
		}
		Set<Integer> keyset = map.keySet();
		Iterator<Integer> it = keyset.iterator();
		for(int i = 0; i < map.size(); i++)
			System.out.println(map.get(it.next()).toBackupString());
	}
}
