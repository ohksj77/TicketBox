package org.kpu.ticketbox.cinema;

public class AnimationScreen extends Screen {
	public AnimationScreen(String name, String story, int price, int row, int col) {
		super(name, story, price, row, col);
	}
	public void showMovieInfo() {
		System.out.println("----------------------------");
		System.out.println(" " + movieName + " �Ұ�");
		System.out.println("----------------------------");
		System.out.println("��ȭ�� : �ִϸ��̼� ��ȭ 2��");
		System.out.println("�ٰŸ� : " + movieStory);
		System.out.println("���� : " + ticketPrice + "��");
	}
} 