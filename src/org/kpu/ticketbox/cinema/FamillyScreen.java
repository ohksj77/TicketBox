package org.kpu.ticketbox.cinema;

public class FamillyScreen extends Screen {
	public FamillyScreen(String name, String story, int price, int row, int col) {
		super(name, story, price, row, col);
	}
	public void showMovieInfo() {
		System.out.println("----------------------------");
		System.out.println(" " + movieName + " �Ұ�");
		System.out.println("----------------------------");
		System.out.println("��ȭ�� : ���� ��ȭ 1��");
		System.out.println("�ٰŸ� : " + movieStory);
		System.out.println("���� : " + ticketPrice + "��");
	}
}