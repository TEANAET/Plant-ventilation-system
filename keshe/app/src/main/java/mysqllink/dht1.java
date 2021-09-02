package mysqllink;

public class dht1 {
    private int id;
    private  float tem1;
    private   String hum1;
    private  static String localtime1;

    public void setId(int id) {
        this.id = id;
    }

    public  String getHum1() {
        return hum1;
    }

    public void setTem1(float tem1) {
        this.tem1 = tem1;
    }

    public void setHum1(String hum1) {
        this.hum1 = hum1;
    }

    public void setLocaltime1(String localtime1) {
        this.localtime1 = localtime1;
    }

    public static String getLocaltime1() {
        return localtime1;
    }



    public int getId() {
        return id;
    }

    public float getTem1() {
        return tem1;
    }
}
