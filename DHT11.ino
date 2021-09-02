#include<DHT.h>
//创造DHT对象，输入其端口位置，以及DHT的型号
DHT DHT_11(A5,DHT11);
#define kaiguan 11
int val=0,mark=0,thre_value=90,zhuan=0,j=0;
String comdata="";
void setup() 
{
  // put your setup code here, to run once:
  Serial.begin(9600);//设置波特率
  DHT_11.begin();    //初始化DHT11传感器
  delay(2000);       //延迟函数
  pinMode(kaiguan, OUTPUT);
}
void loop()
{
  // put your main code here, to run repeatedly:
  float humidityN = DHT_11.readHumidity();  //读取传感器的湿度
  float temperatureN = DHT_11.readTemperature();//读取传感器的温度
   // 定义温度变量名
  String  temperature = "\"temperature(°C)\":";
   // 定义湿度变量名
  String  humidity = "\"humidity(%)\":";
  // 拼接字符串json
  String dataRes="{"+temperature+temperatureN+","+humidity+humidityN+"}";

  // 打印到串口
  Serial.print(dataRes);
  delay(2000);
while (Serial.available() > 0)
{
delay(2);
//读入之后将字符串，串接到comdata上面。

comdata += char(Serial.read());
//延时一会，让串口缓存准备好下一个数字，不延时会导致数据丢失，
delay(3);
mark=1;
}

if(mark=1){
  zhuan=0;
 for(int i = 0; i < comdata.length() ; i++)
        {
        //逐个分析comdata[i]字符串的文字，如果碰到文字是分隔符（这里选择逗号分割）则将结果数组位置下移一位
        //即比如11,22,33,55开始的11记到numdata[0];碰到逗号就j等于1了，
        //再转换就转换到numdata[1];再碰到逗号就记到numdata[2];以此类推，直到字符串结束
          
         
             //如果没有逗号的话，就将读到的数字*10加上以前读入的数字，
             //并且(comdata[i] - '0')就是将字符'0'的ASCII码转换成数字0（下面不再叙述此问题，直接视作数字0）。
             //比如输入数字是12345，有5次没有碰到逗号的机会，就会执行5次此语句。
             //因为左边的数字先获取到，并且numdata[0]等于0，
             //所以第一次循环是numdata[0] = 0*10+1 = 1
             //第二次numdata[0]等于1，循环是numdata[0] = 1*10+2 = 12
             //第三次是numdata[0]等于12，循环是numdata[0] = 12*10+3 = 123
             //第四次是numdata[0]等于123，循环是numdata[0] = 123*10+4 = 1234
             //如此类推，字符串将被变成数字0。
             
            zhuan = zhuan * 10 + (comdata[i] - '0');
          
        }
  if(zhuan%10!=0)
  {
  if(zhuan%10==1)
  {
  val=zhuan%10;
  }
  if(zhuan%10==2){
    val=0;
    }
  }
  if((zhuan/100)*10+(zhuan/10)%10!=0)
  {
    thre_value=(zhuan/100)*10+(zhuan/10)%10;
 
  }
  mark=0;
  comdata="";
}

 if(humidityN>=thre_value || val==1) {
    digitalWrite(kaiguan,LOW);
    }
    else
    {
      digitalWrite(kaiguan,HIGH);
      } 
}
