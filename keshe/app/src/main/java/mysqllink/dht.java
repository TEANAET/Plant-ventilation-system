package mysqllink;

public class dht {
    private int id;
    private static String tem;
    private static String hum;
    private static String time;

    public  static String getTime() {
        return time;
    }

    public void setTime(String time) {
        dht.time = time;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public int getId() {
        return id;
    }

    public static String getTem() {
        return tem;
    }

    public static String getHum() {
        return hum;
    }
}
