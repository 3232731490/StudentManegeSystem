package Entity;

/**
 * @author: 倪路
 * Time: 2021/6/27-20:03
 * StuNo: 1910400731
 * Class: 19104221
 * Description:
 */
public class SC {
    private String sno; //学号
    private String cno; //课程号
    private String score;   //成绩

    public SC(String sno, String cno, String score) {
        this.sno = sno;
        this.cno = cno;
        this.score = score;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
