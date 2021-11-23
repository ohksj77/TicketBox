package org.kpu.ticketbox.main;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.kpu.ticketbox.cinema.AnimationScreen;
import org.kpu.ticketbox.cinema.FamillyScreen;
import org.kpu.ticketbox.cinema.PremiumScreen;
import org.kpu.ticketbox.cinema.Screen;
import org.kpu.ticketbox.payment.Receipt;
import org.kpu.ticketbox.util.ReceiptWriter;
import org.kpu.ticketbox.util.Statistics;

public class TicketBox {
	private FamillyScreen famillyScreen;
	private AnimationScreen animationScreen;
	private PremiumScreen premiumScreen;
	public static final String ADMIN_PASSWORD = "admin";
	public void initScreen() {
		// Screen(��ȭ����, ��ȭ �ٰŸ�, Ƽ�ϰ���, �¼�(rowMax), �¼�(colMax))
		famillyScreen = new FamillyScreen("��� �����", 
				"�̾�۶������� ���׷�Ÿ���� ����ģ�� �����̺ꡯ�� ������ ����ȸ��� �����", 8000, 10, 10);
		animationScreen = new AnimationScreen("�λ��̵� �ƿ�", 
				"��� ����� �Ӹ��ӿ� �����ϴ� ���� ��Ʈ�� ���� �װ����� ��ö�־� ������ ���ϴ�", 10000, 10, 10);
		premiumScreen = new PremiumScreen("���̽�", 
				"�����۾������� �������� ������ ������ ������� �� 30���� ��ã�� ���� ���̽��ǽ� ������", 25000, 5, 5); 
	}
	public Screen selectScreen() {           //�߰��� �Լ�
		Scanner scan = new Scanner(System.in);
		System.out.println("----------------------------");
		System.out.println("-     �󿵰� ���� �޴�     -");
		System.out.println("----------------------------");
		System.out.println("1. ���� ��ȭ 1��");
		System.out.println("2. �ִϸ��̼� ��ȭ 2��");
		System.out.println("3. �����̾� ��ȭ 3�� (Ŀ��, ������ġ ����)");
		System.out.println("9. ������ �޴�");
		System.out.println("   ����(1~3, 9)�� ����");
		System.out.println();
		System.out.print("�󿵰� ���� : ");
		try {
			int select = scan.nextInt();
			switch(select) {
			case 1:
				return famillyScreen;
			case 2:
				return animationScreen;
			case 3:
				return premiumScreen;
			case 9:
				managerMode();
				break;
			default:
				return null;
			}
		}catch(InputMismatchException e) {
			scan = new Scanner(System.in);
		}
		return null;
	}
	void managerMode() {
		Scanner scan = new Scanner(System.in);
		System.out.print("��ȣ �Է� : ");
		String passwd = scan.next();
		if(passwd.equals(ADMIN_PASSWORD)) {
			System.out.println("----------------------------");
			System.out.println("----     ������ ���    ----");
			System.out.println("----------------------------");
			HashMap<Integer,Receipt> f = famillyScreen.getMap();
			HashMap<Integer,Receipt> a = animationScreen.getMap();
			HashMap<Integer,Receipt> p = premiumScreen.getMap();
			int ticket = f.size() + a.size() + p.size();
			System.out.println("��ȭ�� �� Ƽ�� �Ǹŷ� : " + ticket);
			Statistics s = new Statistics();
			System.out.println("���� ��ȭ�� ���� �Ѿ� : " + s.sum(f));
			System.out.println("�ִϸ��̼� ��ȭ�� ���� �Ѿ� : " + s.sum(a));
			System.out.println("�����̾� ��ȭ�� ���� �Ѿ� : " + s.sum(p));
			ReceiptWriter rw = new ReceiptWriter();
			System.out.println("���� ��ȭ�� ������ ��ü ���");
			rw.printConsole(f);
			System.out.println("�ִϸ��̼� ��ȭ�� ������ ��ü ���");
			rw.printConsole(a);
			System.out.println("�����̾� ��ȭ�� ������ ��ü ���");
			rw.printConsole(p);
			System.out.println();
		}
		else
			System.out.println("������ ��ȣ�� �ٽ� Ȯ�����ּ���.");
	}
}