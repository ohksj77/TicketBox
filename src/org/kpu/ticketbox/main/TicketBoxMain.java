package org.kpu.ticketbox.main;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.kpu.ticketbox.cinema.Screen;

public class TicketBoxMain {
	public static void main(String[] args) {
		TicketBox ticketBox = new TicketBox();
		Scanner scan = new Scanner(System.in);
		Screen screen = null;
		boolean mainMenu = true; // �󿵰� ���� �޴� ���
		ticketBox.initScreen(); // 3���� ��ũ�� ��ü�� ����
		while(true) {
			if(mainMenu) {
				screen = ticketBox.selectScreen();
				if( screen == null ) {
					System.out.print(" �ȳ��� ������ !");
					scan.close();
					System.exit(0);
				}
				mainMenu = false;
			}
			screen.showScreenMenu();
			System.out.print(" �޴��� �����ϼ��� >> ");
			try {
				int select = scan.nextInt();
				switch(select) {
				case 1: // ��ũ�� �� ��ȭ ���� ����
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
					System.out.println("��ȣ�� Ȯ�����ּ���.");
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("�ٽ� Ȯ�����ּ���.");
				scan = new Scanner(System.in);
			}
		}
	}
}