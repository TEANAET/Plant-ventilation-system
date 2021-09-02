package mysqllink;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 */
public class DBUtils {
     public List<dht1> retList = new ArrayList<>();//定义一个dht类型的列表

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

//    private static String url = "jdbc:mysql://localhost:3306/map_designer_test_db";

    private static String user = "admin_123";// 用户名

    private static String password = "LJWws5997";// 密码

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "rm-wz95294923s14942bao.mysql.rds.aliyuncs.com";// 数据库连接地址

            // 尝试建立到给定数据库URL的连接
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName,
                    user, password);

        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }


    //登录
    public boolean login(String name,String password) { //建立User类型的login方法，查找输入的用户名和密码是否存在
        Connection connection = getConn("keshe1");
        String sql = "select * from user_comunt where name=? and password =?";
      //  User adminRst = null; //声明一个对象
        try {
            PreparedStatement prst = connection.prepareStatement(sql);//把sql语句传给数据库操作对象
            prst.setString(1,name);//setString 方法用于绑定映射类型为 String 的参数。参数2用于指定对应的参数值。
            prst.setString(2, password);
            ResultSet executeQuery = prst.executeQuery();//prst.executeQuery()是执行这条sql语言
            //ResultSet executeQuery 存放的是从数据库中，返回来的数据结果。
            if(executeQuery.next()) {   //判断executeQuery()中有无结果
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();; //关闭数据库的连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //注册用户
    public boolean regist(String name,String password) { //建立User类型的login方法，查找输入的用户名和密码是否存在
        Connection connection = getConn("keshe1");
        String sql = "insert into user_comunt(name,password) values(?,?)";
        try {
            PreparedStatement prst = connection.prepareStatement(sql);//把sql语句传给数据库操作对象
            prst.setString(1,name);//setString 方法用于绑定映射类型为 String 的参数。参数2用于指定对应的参数值。
            prst.setString(2, password);
          return prst.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }}

    //获取实时信息
    public static void getDeviceList() {
        Connection connection = getConn("keshe1");
       // List<dht> retList = new ArrayList<dht>();//定义一个dht类型的列表
        String sqlString = "select id,temperature,humidity,localtime from thermometer order by id DESC limit 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlString);
            ResultSet executeQuery = ps.executeQuery();
            while(executeQuery.next()) {
                dht dece = new dht();
                dece.setId(executeQuery.getInt("id"));
                dece.setTem(executeQuery.getString("temperature"));
                dece.setHum(executeQuery.getString("humidity"));
                dece.setTime(executeQuery.getString("localtime"));
                //retList.add(dece); //向列表中添加dht类型数据(dece)


            }
            connection.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //获取当前温湿度阀值
    public static void getther_value() {
        Connection connection = getConn("keshe1");
        // List<dht> retList = new ArrayList<dht>();//定义一个dht类型的列表
        String sqlString = "select id,value from thre_value order by id DESC limit 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlString);
            ResultSet executeQuery = ps.executeQuery();
            while(executeQuery.next()) {
                thrval thrval=new thrval();
                thrval.setId(executeQuery.getInt("id"));

                thrval.setTher_value(executeQuery.getString("value"));
                //retList.add(dece); //向列表中添加dht类型数据(dece)


            }
            connection.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //打开除湿器开关
    public static void Insert_on() {
        Connection connection = getConn("keshe1");
        //3.sql语句
        String sql = "insert into kai set kaiguan='1'";
        try {
            //4.获取用于向数据库发送sql语句的ps
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute(sql);
            connection.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //关闭除湿器开关
    public static void Insert_off() {
        Connection connection = getConn("keshe1");
        //3.sql语句
        String sql1 = "insert into kai set kaiguan='2'";
        try {
            //4.获取用于向数据库发送sql语句的ps
            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.execute(sql1);
            connection.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //修改除湿器湿度阀值
    public static void Insert_value(String val){
        Connection connection = getConn("keshe1");
        //3.sql语句
        String sql1 = "insert into thre_value (value) VALUES ('"+val+"')";
        try {
            //4.获取用于向数据库发送sql语句的ps
            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.execute(sql1);
            connection.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //获取最近20条温湿度数据
    public List<dht1> getdhtList() {
        List<dht1> retList1 = new ArrayList<>();
        Connection connection = getConn("keshe1");
        String sqlString = "select id,temperature,humidity,localtime from thermometer order by id DESC limit 0,20";
        try {
            PreparedStatement ps = connection.prepareStatement(sqlString);
            ResultSet executeQuery = ps.executeQuery();
            while (executeQuery.next()) {
                dht1 dece = new dht1();
                dece.setId(executeQuery.getInt("id"));
                dece.setTem1(executeQuery.getFloat("temperature"));
                dece.setHum1(executeQuery.getString("humidity"));
                dece.setLocaltime1(executeQuery.getString("localtime"));
                retList1.add(dece); //向列表中添加dht类型数据(dece)
            }

            connection.close();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return retList1;
    }
}