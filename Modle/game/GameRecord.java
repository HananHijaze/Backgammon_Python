package game;
public class GameRecord {
    private String winnerName;
    private int score;
    private String date;
    private int duration;
	public GameRecord(String winnerName, int score, String date, int duration) {
		super();
		this.winnerName = winnerName;
		this.score = score;
		this.date = date;
		this.duration = duration;
	}
	public String getWinnerName() {
		return winnerName;
	}
	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

    
}
