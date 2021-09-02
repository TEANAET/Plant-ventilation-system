package mysqllink;

public class thrval {
    private int id;

    public void setId(int id) {
        this.id = id;
    }



    private  static String ther_value;

    public  void setTher_value(String ther_value) {
        thrval.ther_value = ther_value;
    }

    public int getId() {
        return id;
    }

    public static String getTher_value() {
        return ther_value;
    }
}
