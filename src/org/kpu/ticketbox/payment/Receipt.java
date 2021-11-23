package org.kpu.ticketbox.payment;

public class Receipt {
	String client; // 사용자 이름
	String productName; // 영화 상품 이름
	String payMethod; // 결제 수단
	String payNumber; // 결제 정보(번호)
	double subTotalAmount; // 커미션 제외한 금액
	double totalAmount; // 커미션 포함한 금액
	public Receipt(String product, double amount, String name, String number, double commision, String payMethod) {
		productName = product;
		client = name;
		payNumber = number;
		subTotalAmount = amount;
		totalAmount = Math.round(amount*(1 + commision));
		this.payMethod = payMethod;
	}
	public double getTotalAmount() {        // 추가한 함수
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
