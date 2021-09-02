package mysqllink;

public class User {
    private int id; //管理员账户id
    private  String name;//管理员账户名称
    private String password; //管理员登陆密码

    public User(){}

    public User(int id,String name,String password){
        this.id=id;
        this.name=name;
        this.password=password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
