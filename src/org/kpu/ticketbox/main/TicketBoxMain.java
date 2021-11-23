package org.kpu.ticketbox.main;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.kpu.ticketbox.cinema.Screen;

public class TicketBoxMain {
	public static void main(String[] args) {
		TicketBox ticketBox = new TicketBox();
		Scanner scan = new Scanner(System.in);
		Screen screen = null;
		boolean mainMenu = true; // 상영관 선택 메뉴 사용
		ticketBox.initScreen(); // 3개의 스크린 객체를 생성
		while(true) {
			if(mainMenu) {
				screen = ticketBox.selectScreen();
				if( screen == null ) {
					System.out.print(" 안녕히 가세요 !");
					scan.close();
					System.exit(0);
				}
				mainMenu = false;
			}
			screen.showScreenMenu();
			System.out.print(" 메뉴를 선택하세요 >> ");
			try {
				int select = scan.nextInt();
				switch(select) {
				case 1: // 스크린 상영 영화 정보 보기
					screen.showMovieInfo();
					break;
				case 2:
					screen.showSeatMap();
					break;
				case 3:
					screen.reserveTicket();
					break;
				case 4:
					screen.changeTicket();
					break;
				case 5:
					screen.payment();
					break;
				case 6:
					screen.printTicket();
					break;
				case 7:
					mainMenu = true;
					break;
				default:
					System.out.println("번호를 확인해주세요.");
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("다시 확인해주세요.");
				scan = new Scanner(System.in);
			}
		}
	}
}