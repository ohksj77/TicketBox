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
		// Screen(영화제목, 영화 줄거리, 티켓가격, 좌석(rowMax), 좌석(colMax))
		famillyScreen = new FamillyScreen("비긴 어게인", 
				"싱어송라이터인 ‘그레타’는 남자친구 ‘데이브’가 메이저 음반회사와 계약을", 8000, 10, 10);
		animationScreen = new AnimationScreen("인사이드 아웃", 
				"모든 사람의 머릿속에 존재하는 감정 컨트롤 본부 그곳에서 불철주야 열심히 일하는", 10000, 10, 10);
		premiumScreen = new PremiumScreen("보이스", 
				"현장작업반장인 전직형사 서준은 가족과 동료들의 돈 30억을 되찾기 위해 보이스피싱 조직을", 25000, 5, 5); 
	}
	public Screen selectScreen() {           //추가한 함수
		Scanner scan = new Scanner(System.in);
		System.out.println("----------------------------");
		System.out.println("-     상영관 메인 메뉴     -");
		System.out.println("----------------------------");
		System.out.println("1. 가족 영화 1관");
		System.out.println("2. 애니메이션 영화 2관");
		System.out.println("3. 프리미엄 영화 3관 (커피, 샌드위치 제공)");
		System.out.println("9. 관리자 메뉴");
		System.out.println("   선택(1~3, 9)외 종료");
		System.out.println();
		System.out.print("상영관 선택 : ");
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
		System.out.print("암호 입력 : ");
		String passwd = scan.next();
		if(passwd.equals(ADMIN_PASSWORD)) {
			System.out.println("----------------------------");
			System.out.println("----     관리자 기능    ----");
			System.out.println("----------------------------");
			HashMap<Integer,Receipt> f = famillyScreen.getMap();
			HashMap<Integer,Receipt> a = animationScreen.getMap();
			HashMap<Integer,Receipt> p = premiumScreen.getMap();
			int ticket = f.size() + a.size() + p.size();
			System.out.println("영화관 총 티켓 판매량 : " + ticket);
			Statistics s = new Statistics();
			System.out.println("가족 영화관 결제 총액 : " + s.sum(f));
			System.out.println("애니메이션 영화관 결제 총액 : " + s.sum(a));
			System.out.println("프리미엄 영화관 결제 총액 : " + s.sum(p));
			ReceiptWriter rw = new ReceiptWriter();
			System.out.println("가족 영화관 영수증 전체 출력");
			rw.printConsole(f);
			System.out.println("애니메이션 영화관 영수증 전체 출력");
			rw.printConsole(a);
			System.out.println("프리미엄 영화관 영수증 전체 출력");
			rw.printConsole(p);
			System.out.println();
		}
		else
			System.out.println("관리자 암호를 다시 확인해주세요.");
	}
}