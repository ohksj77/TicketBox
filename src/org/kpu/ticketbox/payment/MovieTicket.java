package org.kpu.ticketbox.payment;
public class MovieTicket {
	public static final char SEAT_EMPTY_MARK = '-';
	public static final char SEAT_RESERVED_MARK = 'R';
	public static final char SEAT_PAY_COMPLETION_MARK = '$';
	private int row; // ÁÂ¼® Çà
	private int col; // ÁÂ¼® ¿­
	private char status; // ÁÂ¼® »óÅÂ - EMPTY, RESERVED, PAY_COMPLETION
	private int reservedId;
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public void setSeat(int row,int col) {
		this.row = row;
		this.col = col;
	}
	public void setReservedId(int id) {
		reservedId = id;
	}
	public int getReservedId() {
		return reservedId;
	}
}
