package org.kpu.ticketbox.payment;
public interface Pay {
	public static final int BANK_TRANSFER_PAYMENT = 1;
	public static final int CREDIT_CARD_PAYMENT = 2;
	public static final int MOBILE_PHONE_PAYMENT = 3;
	// �����ϱ� ( ��ȭ��ǰ��, ��ȭ����, ����, ���� ����)
	public abstract Receipt charge(String product, double amount, String name, String number);
}