package org.kpu.ticketbox.payment;

public class Receipt {
	String client; // ����� �̸�
	String productName; // ��ȭ ��ǰ �̸�
	String payMethod; // ���� ����
	String payNumber; // ���� ����(��ȣ)
	double subTotalAmount; // Ŀ�̼� ������ �ݾ�
	double totalAmount; // Ŀ�̼� ������ �ݾ�
	public Receipt(String product, double amount, String name, String number, double commision, String payMethod) {
		productName = product;
		client = name;
		payNumber = number;
		subTotalAmount = amount;
		totalAmount = Math.round(amount*(1 + commision));
		this.payMethod = payMethod;
	}
	public double getTotalAmount() {        // �߰��� �Լ�
		return totalAmount;
	}
	public String toString() {
		return "[ Client : " + client + " ]" + "\n[ Product : " + productName + " ]" + 
				"\n[ PayMethod : " + payMethod + " ]" + "\n[ PayNumber : " + payNumber + " ]"
				+ "\n[ Subtotal : " + subTotalAmount + " ]" + "\n[ Total : " + totalAmount + " ]";
	}
	public String toBackupString() {
		return client + "," + productName + "," + payMethod + "," + payNumber + "," + subTotalAmount + "," +totalAmount;
	}
}
