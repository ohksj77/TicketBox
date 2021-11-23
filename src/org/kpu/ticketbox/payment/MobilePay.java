package org.kpu.ticketbox.payment;

public class MobilePay implements Pay{
	public static final double MOBILE_COMMISION = 0.2;
	public Receipt charge(String product, double amount, String name, String number) {
		Receipt receipt = new Receipt(product, amount, name, number, MOBILE_COMMISION, "MobilePay");
		return receipt;
	}
}
