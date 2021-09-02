package mysqllink;


import android.os.Handler;
import android.os.Message;

/**
 * 子线程，读/写MySql数据库
 */
public class WorkDb {
    private Handler handler;
    public  WorkDb(Handler h){
        this.handler = h;
    }

    /**
     * 读数据
     */
    public void read(){
        new Thread(new DbThread()).start();
    }

    private  class DbThread implements  Runnable{
        @Override
        public void run() {
            if (WorkDb.this.handler!=null){
                Message msg = WorkDb.this.handler.obtainMessage();
                DBUtils db = new DBUtils();
                msg.obj = (Object) db.getdhtList();
                WorkDb.this.handler.sendMessage(msg);

            }
        }
    }


}
