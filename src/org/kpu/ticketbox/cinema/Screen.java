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
	protected int ticketPrice; // 티켓 가격
	protected int rowMax; // 좌석 행 최대 값
	protected int colMax; // 좌석 열 최대 값
	protected String movieName; // 상영중인 영화 제목
	protected String movieStory; // 상영중인 영화 줄거리
	protected MovieTicket [][] seatArray; // 좌석 2차원 배열
	private int currentReservedId = 100;
	private HashMap<Integer, Receipt> receiptMap = new HashMap<Integer, Receipt>();
	public abstract void showMovieInfo(); // 영화 정보 보여주기
	Screen(String name, String story, int price, int row, int col) { // 생성자
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
	public void showScreenMenu() { // 상영관 메뉴 보여주기
		System.out.println("----------------------------");
		System.out.println(" 영화 메뉴 - " + movieName);
		System.out.println("----------------------------");
		System.out.println("1. 상영 영화 정보");
		System.out.println("2. 좌석 예약 현황");
		System.out.println("3. 좌석 예약 하기");
		System.out.println("4. 좌석 변경 하기");
		System.out.println("5. 좌석 결제 하기");
		System.out.println("6. 티켓 출력 하기");
		System.out.println("7. 메인 메뉴 이동");
		System.out.println();
	}
	public void showSeatMap() { // 상영관 좌석 예약 현황 보여주기
		System.out.println("     [좌석 예약 현황]");
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
			System.out.println("  [ 좌석 예약 ]");
			System.out.print("좌석 행 번호 입력(1 - " + colMax + ") : ");
			int col = scan.nextInt();
			System.out.print("좌석 열 번호 입력(1 - " + rowMax + ") : ");
			int row = scan.nextInt();
			if (row > rowMax || col > colMax || row < 1 || col < 1) {
				System.out.println("행의 값은 1 ~ " + colMax + "이고 열의 값은 1 ~ " + rowMax + "입니다.");
				continue;
			}
			if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
				System.out.println("이미 예약된 좌석입니다.");
				continue;
			}
			if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				System.out.println("이미 결제된 좌석입니다.");
				continue;
			}
			seatArray[col - 1][row - 1].setStatus(MovieTicket.SEAT_RESERVED_MARK);
			seatArray[col - 1][row - 1].setSeat(row, col);
			seatArray[col - 1][row - 1].setReservedId(currentReservedId + (int)(Math.random()*(rowMax*colMax + 1)));
			System.out.println("행[" + col + "] 열[" + row + "] " + 
			seatArray[col - 1][row - 1].getReservedId() + " 예약 번호로 접수되었습니다.");
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
		System.out.println("  [ 좌석 변경 ]");
		System.out.print("좌석 예약 번호 입력 : ");
		int num = scan.nextInt();
		if(checkReservedId(num) != null) {
			if(checkReservedId(num).getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
				while(true) {
					System.out.print("좌석 행 번호 입력(1 - " + colMax + ") : ");
					int col = scan.nextInt();
					System.out.print("좌석 열 번호 입력(1 - " + rowMax + ") : ");
					int row = scan.nextInt();
					if (row > rowMax || col > colMax || row < 1 || col < 1) {
						System.out.println("행의 값은 1 ~ " + colMax + "이고 열의 값은 1 ~ " + rowMax + "입니다.");
						continue;
					}
					if(checkReservedId(num) == seatArray[col - 1][row - 1]) {
						System.out.println("동일 좌석으로는 변경이 불가합니다.");
						continue;
					}
					if(seatArray[col - 1][row - 1].getStatus() == MovieTicket.SEAT_RESERVED_MARK) {
						System.out.println("이미 예약된 좌석입니다.");
						continue;
					}
					if(seatArray[col -1][row - 1].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
						System.out.println("이미 결제된 좌석입니다.");
						continue;
					}
					checkReservedId(num).setStatus(MovieTicket.SEAT_EMPTY_MARK);
					checkReservedId(num).setReservedId(0);
					seatArray[col - 1][row - 1].setReservedId(num);
					seatArray[col - 1][row - 1].setSeat(row, col);
					seatArray[col - 1][row - 1].setStatus(MovieTicket.SEAT_RESERVED_MARK);
					System.out.println("예약번호 " + checkReservedId(num).getReservedId()
							+ " 행[" + col + "] 열[" + row + "] 좌석으로 변경되었습니다.");
					return;
				}
			}
			else if(checkReservedId(num).getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
				System.out.println("결제가 완료되어 좌석을 변경할 수 없습니다.");
				return;
			}
		}
		else
			System.out.println("예약 번호를 다시 확인해주세요.");
	}
	public void payment() {
		Scanner scan = new Scanner(System.in);
		System.out.println(" [ 좌석 예약 결제 ]");
		System.out.print("예약 번호 입력 : ");
		int reserveNum = scan.nextInt();
		if(reserveNum < 100 || reserveNum > currentReservedId + rowMax*colMax) {
			System.out.println("예약 번호를 다시 확인해주세요.");
			return;
		}		
		for(int i = 0; i < seatArray.length; i++) {
			for(int j = 0; j < seatArray[i].length; j++) {
				if(seatArray[i][j].getReservedId() == reserveNum) {
					if(seatArray[i][j].getStatus() == MovieTicket.SEAT_PAY_COMPLETION_MARK) {
						System.out.println("이미 결제하셨습니다.");
						return;
					}
					System.out.println("----------------------------");
					System.out.println("       결제 방식 선택");
					System.out.println("----------------------------");
					System.out.println("1. 은행 이체");
					System.out.println("2. 카드 결제");
					System.out.println("3. 모바일 결제");
					System.out.print("결제 방식 입력(1~3) : ");
					int pay = scan.nextInt();
					switch(pay) {
					case Pay.BANK_TRANSFER_PAYMENT:{
						System.out.println(" [ 은행 이체 ]");
						System.out.print("이름 입력: ");
						String name = scan.next();
						System.out.print("은행 번호 입력(12자리수) : ");
						String num = scan.next();
						if(num.length() != 12) {
							System.out.println("12자리로 입력해주세요.");
							return;
						}
						BankTransfer bank = new BankTransfer();
						Receipt r = bank.charge(movieName, ticketPrice, name, num);
						System.out.println("은행 결제가 완료되었습니다. : " + r.getTotalAmount() + "원");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					case Pay.CREDIT_CARD_PAYMENT:{
						System.out.println(" [ 카드 결제 ]");
						System.out.print("이름 입력: ");
						String name = scan.next();
						System.out.print("카드 번호 입력(12자리수) : ");
						String num = scan.next();
						if(num.length() != 12) {
							System.out.println("12자리로 입력해주세요.");
							return;
						}
						CardPay card = new CardPay();
						Receipt r = card.charge(movieName, ticketPrice, name, num);
						System.out.println("카드 결제가 완료되었습니다. : " + r.getTotalAmount() + "원");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					case Pay.MOBILE_PHONE_PAYMENT:{
						System.out.println(" [ 모바일 결제 ]");
						System.out.print("이름 입력: ");
						String name = scan.next();
						System.out.print("휴대폰 번호 입력(11자리수) : ");
						String num = scan.next();
						if(num.length() != 11) {
							System.out.println("11자리로 입력해주세요.");
							return;
						}
						MobilePay mobile = new MobilePay();
						Receipt r = mobile.charge(movieName, ticketPrice, name, num);
						System.out.println("은행 결제가 완료되었습니다. : " + r.getTotalAmount() + "원");
						seatArray[i][j].setStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
						receiptMap.put(seatArray[i][j].getReservedId(), r);
						return;
					}
					default:
						System.out.println("결제 수단을 다시 확인해주세요.");
						return;
					}
				}
			}
		}
		System.out.println("예약 번호를 다시 확인해주세요.");
		return;
	}
	public void printTicket() {
		Scanner scan = new Scanner(System.in);
		System.out.println(" [ 좌석 티켓 출력 ]");
		System.out.print("예약 번호 입력 : ");
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
				System.out.println("결제를 완료해주세요.");
		}
		else
			System.out.println("예약 번호를 다시 확인해주세요.");
	}
	public HashMap<Integer,Receipt> getMap(){      // 추가한 함수
		return receiptMap;
	}
}