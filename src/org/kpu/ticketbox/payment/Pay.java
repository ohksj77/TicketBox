package org.kpu.ticketbox.payment;
public interface Pay {
	public static final int BANK_TRANSFER_PAYMENT = 1;
	public static final int CREDIT_CARD_PAYMENT = 2;
	public static final int MOBILE_PHONE_PAYMENT = 3;
	// 결재하기 ( 영화상품명, 영화가격, 고객명, 결제 정보)
	public abstract Receipt charge(String product, double amount, String name, String number);
}