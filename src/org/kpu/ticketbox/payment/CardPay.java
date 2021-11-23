package org.kpu.ticketbox.payment;

public class CardPay implements Pay {
	public static final double CARD_COMMISION = 0.15;
	public Receipt charge(String product, double amount, String name, String number) {
		Receipt receipt = new Receipt(product, amount, name, number, CARD_COMMISION,"CardPay");
		return receipt;
	}
}