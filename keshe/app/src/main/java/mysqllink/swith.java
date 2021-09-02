package mysqllink;

public class swith {
    private int id;
    private static String value;

    public void setId(int id) {
        this.id = id;
    }

    public  void setValue(String value) {
        swith.value = value;
    }

    public int getId() {
        return id;
    }

    public static String getValue() {
        return value;
    }
}
