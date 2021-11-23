package org.kpu.ticketbox.cinema;

public class PremiumScreen extends Screen {
	public PremiumScreen(String name, String story, int price, int row, int col) {
		super(name, story, price, row, col);
	}
	public void showMovieInfo() {
		System.out.println("----------------------------");
		System.out.println(" " + movieName + " 소개");
		System.out.println("----------------------------");
		System.out.println("영화관 : 프리미엄 영화 3관");
		System.out.println("줄거리 : " + movieStory);
		System.out.println("가격 : " + ticketPrice + "원");
	}
}