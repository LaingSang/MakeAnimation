package jiankong.jk.makeupanimation;

public class ChartBean {
    private int score;
    private String title;

    public ChartBean(int score, String title) {
        this.score = score;
        this.title = title;
    }

    public ChartBean() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
