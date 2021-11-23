package org.kpu.ticketbox.cinema;
import org.kpu.ticketbox.payment.BankTransfer;
import org.kpu.ticketbox.payment.CardPay;
import org.kpu.ticketbox.payment.MobilePay;
import org.kpu.ticketbox.payment.MovieTicket;
import org.kpu.ticketbox.payment.Pay;
import org.kpu.ticketbox.payment.Receipt;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Screen {
	protected int ticketPrice; // Ƽ�� ����
	protected int rowMax; // �¼� �� �ִ� ��
	protected int colMax; // �¼� �� �ִ� ��
	protected String movieName; // ������ ��ȭ ����
	protected String movieStory; // ������ ��ȭ �ٰŸ�
	protected MovieTicket [][] seatArray; // �¼� 2���� �迭
	private int currentReservedId = 100;
	private HashMap<Integer, Receipt> receiptMap = new HashMap<Integer, Receipt>();
	public abstract void showMovieInfo(); // ��ȭ ���� �����ֱ�
	Screen(String name, String story, int price, int row, int col) { // ������
		movieName = name;
		movieStory = story;
		ticketPrice = price;
		rowMax = row;
		colMax = col;
		seatArray = new MovieTicket[colMax][rowMax];
		for(int i = 0; i < seatArray.length; i++) {
			for(int j = 0; j < seatArray[i].length; j++){
				seatArray[i][j] = new MovieTicket();
				seatArray[i][j].setStatus(MovieTicket.SEAT_EMPTY_MARK);
			}
		}
	}
	public void showScreenMenu() { // �󿵰� �޴� �����ֱ�
		System.out.println("----------------------------");
		System.out.println(" ��ȭ �޴� - " + movieName);
		System.out.println("----------------------------");
		System.out.println("1. �� ��ȭ ����");
		System.out.println("2. �¼� ���� ��Ȳ");
		System.out.println("3. �¼� ���� �ϱ�");
		System.out.println("4. �¼� ���� �ϱ�");
		System.out.println("5. �¼� ���� �ϱ�");
		System.out.println("6. Ƽ�� ��� �ϱ�");
		System.out.println("7. ���� �޴� �̵�");
		System.out.println();
	}
	public void showSeatMap() { // �󿵰� �¼� ���� ��Ȳ �����ֱ�
		System.out.println("     [�¼� ���� ��Ȳ]");
		System.out.print("    ");
		for (int i = 0; i < colMax; i++) {
			System.out.print(" [" + (i + 1) + "]");
		}
		System.out.println();
		for (int i = 0; i < rowMax; i++) {
			if (i != 9)
				System.out.print(" [" + (i + 1) + "]");
			else
				System.out.print("[" + (i + 1) + "]");
			for (int j = 0; j < rowMax; j++)
				System.out.print(" [" + seatArray[i][j].getStatus() + "]");
			System.out.println();
		}
	}
	public void reserveTicket() {
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("  [ �¼� ���� ]");
			System.out.print("�¼� �� ��ȣ �Է�(1 - " + colMax + ") : ");
			int col = scan.nextInt();
			System.out.print("�¼� �� ��ȣ �Է�(1 - " + rowMax + ") : ");
			int row = scan.nextInt();
			if (row > rowMax || col > colMax || row < 1 || col < 1) {
				System.out.println("���� ���� 1 ~ " + colMax + "�̰� ���� ���� 1 ~ " + rowMax + "�Դϴ�.");
				continue;
			}
			if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
				System.out.println("�̹� ����� �¼��Դϴ�.");
				continue;
			}
			if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				System.out.println("�̹� ������ �¼��Դϴ�.");
				continue;
			}
			seatArray[col - 1][row - 1].setStatus(MovieTicket.SEAT_RESERVED_MARK);
			seatArray[col - 1][row - 1].setSeat(row, col);
			seatArray[col - 1][row - 1].setReservedId(currentReservedId + (int)(Math.random()*(rowMax*colMax + 1)));
			System.out.println("��[" + col + "] ��[" + row + "] " + 
			seatArray[col - 1][row - 1].getReservedId() + " ���� ��ȣ�� �����Ǿ����ϴ�.");
			return;
		}
	}
	private MovieTicket checkReservedId(int id) {
		for(int i = 0; i < seatArray.length; i++) {
			for(int j = 0; j < seatArray[i].length; j++) {
				if(seatArray[i][j].getReservedId() == id) {
					return seatArray[i][j];
				}
			}
		}
		return null;
	}
	public void changeTicket () {
		Scanner scan = new Scanner(System.in);
		System.out.println("  [ �¼� ���� ]");
		System.out.print("�¼� ���� ��ȣ �Է� : ");
		int num = scan.nextInt();
		if(checkReservedId(num) != null) {
			if(checkReservedId(num).getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
				while(true) {
					System.out.print("�¼� �� ��ȣ �Է�(1 - " + colMax + ") : ");
					int col = scan.nextInt();
					System.out.print("�¼� �� ��ȣ �Է�(1 - " + rowMax + ") : ");
					int row = scan.nextInt();
					if (row > rowMax || col > colMax || row < 1 || col < 1) {
						System.out.println("���� ���� 1 ~ " + colMax + "�̰� ���� ���� 1 ~ " + rowMax + "�Դϴ�.");
						continue;
					}
					if(checkReservedId(num) == seatArray[col - 1][row - 1]) {
						System.out.println("���� �¼����δ� ������ �Ұ��մϴ�.");
						continue;
					}
					if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
						System.out.println("�̹� ����� �¼��Դϴ�.");
						continue;
					}
					if(seatArray[col -1][row - 1].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
						System.out.println("�̹� ������ �¼��Դϴ�.");
						continue;
					}
					checkReservedId(num).setStatus(MovieTicket.SEAT_EMPTY_MARK);
					checkReservedId(num).setReservedId(0);
					seatArray[col - 1][row - 1].setReservedId(num);
					seatArray[col - 1][row - 1].setSeat(row, col);
					seatArray[col - 1][row - 1].setStatus(MovieTicket.SEAT_RESERVED_MARK);
					System.out.println("�����ȣ " + checkReservedId(num).getReservedId()
							+ " ��[" + col + "] ��[" + row + "] �¼����� ����Ǿ����ϴ�.");
					return;
				}
			}
			else if(checkReservedId(num).getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				System.out.println("������ �Ϸ�Ǿ� �¼��� ������ �� �����ϴ�.");
				return;
			}
		}
		else
			System.out.println("���� ��ȣ�� �ٽ� Ȯ�����ּ���.");
	}
	public void payment() {
		Scanner scan = new Scanner(System.in);
		System.out.println(" [ �¼� ���� ���� ]");
		System.out.print("���� ��ȣ �Է� : ");
		int reserveNum = scan.nextInt();
		if(reserveNum < 100 || reserveNum > currentReservedId + rowMax*colMax) {
			System.out.println("���� ��ȣ�� �ٽ� Ȯ�����ּ���.");
			return;
		}		
		for(int i = 0; i < seatArray.length; i++) {
			for(int j = 0; j < seatArray[i].length; j++) {
				if(seatArray[i][j].getReservedId() == reserveNum) {
					if(seatArray[i][j].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
						System.out.println("�̹� �����ϼ̽��ϴ�.");
						return;
					}
					System.out.println("----------------------------");
					System.out.println("       ���� ��� ����");
					System.out.println("----------------------------");
					System.out.println("1. ���� ��ü");
					System.out.println("2. ī�� ����");
					System.out.println("3. ����� ����");
					System.out.print("���� ��� �Է�(1~3) : ");
					int pay = scan.nextInt();
					switch(pay) {
					case Pay.BANK_TRANSFER_PAYMENT:{
						System.out.println(" [ ���� ��ü ]");
						System.out.print("�̸� �Է�: ");
						String name = scan.next();
						System.out.print("���� ��ȣ �Է�(12�ڸ���) : ");
						String num = scan.next();
						if(num.length() != 12) {
							System.out.println("12�ڸ��� �Է����ּ���.");
							return;
						}
						BankTransfer bank = new BankTransfer();
						Receipt r = bank.charge(movieName, ticketPrice, name, num);
						System.out.println("���� ������ �Ϸ�Ǿ����ϴ�. : " + r.getTotalAmount() + "��");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					case Pay.CREDIT_CARD_PAYMENT:{
						System.out.println(" [ ī�� ���� ]");
						System.out.print("�̸� �Է�: ");
						String name = scan.next();
						System.out.print("ī�� ��ȣ �Է�(12�ڸ���) : ");
						String num = scan.next();
						if(num.length() != 12) {
							System.out.println("12�ڸ��� �Է����ּ���.");
							return;
						}
						CardPay card = new CardPay();
						Receipt r = card.charge(movieName, ticketPrice, name, num);
						System.out.println("ī�� ������ �Ϸ�Ǿ����ϴ�. : " + r.getTotalAmount() + "��");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					case Pay.MOBILE_PHONE_PAYMENT:{
						System.out.println(" [ ����� ���� ]");
						System.out.print("�̸� �Է�: ");
						String name = scan.next();
						System.out.print("�޴��� ��ȣ �Է�(11�ڸ���) : ");
						String num = scan.next();
						if(num.length() != 11) {
							System.out.println("11�ڸ��� �Է����ּ���.");
							return;
						}
						MobilePay mobile = new MobilePay();
						Receipt r = mobile.charge(movieName, ticketPrice, name, num);
						System.out.println("���� ������ �Ϸ�Ǿ����ϴ�. : " + r.getTotalAmount() + "��");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					default:
						System.out.println("���� ������ �ٽ� Ȯ�����ּ���.");
						return;
					}
				}
			}
		}
		System.out.println("���� ��ȣ�� �ٽ� Ȯ�����ּ���.");
		return;
	}
	public void printTicket() {
		Scanner scan = new Scanner(System.in);
		System.out.println(" [ �¼� Ƽ�� ��� ]");
		System.out.print("���� ��ȣ �Է� : ");
		int num = scan.nextInt();
		if(checkReservedId(num)!= null) {
			if(checkReservedId(num).getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				Receipt r = receiptMap.get(num);
				System.out.println("----------------------------");
				System.out.println("        Receipt");
				System.out.println("----------------------------");
				System.out.println(r.toString());
			}
			else
				System.out.println("������ �Ϸ����ּ���.");
		}
		else
			System.out.println("���� ��ȣ�� �ٽ� Ȯ�����ּ���.");
	}
	public HashMap<Integer,Receipt> getMap(){      // �߰��� �Լ�
		return receiptMap;
	}
}