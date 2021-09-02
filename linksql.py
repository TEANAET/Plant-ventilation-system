#!/usr/bin/python
# -*- coding: UTF-8 -*-
# 引入串口库(注意是serial,不是pyserial)
import serial
#引入数据库驱动
import pymysql
# 引入json库
import json
# 引入时间
import time

# 引入sqlalchemy中相关模块
from sqlalchemy import create_engine, MetaData
from sqlalchemy import Column, Integer, String, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# 连接数据库(有会自动忽略,无会自动创建)++
engine = create_engine("mysql+pymysql://admin_123:LJWws5997@rm-wz95294923s14942bao.mysql.rds.aliyuncs.com:3306/keshe1?charset=utf8")
# 基本类
Base = declarative_base()

# 表要继承基本类

# 设置温湿度数据的类


class Thermometer(Base):
    __tablename__ = 'thermometer'  # 表的名字

    # 定义各字段
    id = Column(Integer, primary_key=True)
    # 温度
    temperature = Column(String(5))
    # 湿度
    humidity = Column(String(3))
    # 时间戳
    timestamp = Column(String(20))
    #时间
    localtime=Column(String(20))


    def __str__(self):
        return self.id

class Kai(Base):
    __tablename__ = 'kai'  # 表的名字

    # 定义各字段
    id = Column(Integer, primary_key=True)
    # 开关
    kaiguan = Column(String(3))
    def __str__(self):
        return self.id


class Thre(Base):
    __tablename__ = 'thre_value'  # 表的名字

    # 定义各字段
    id = Column(Integer, primary_key=True)
    # 湿度阀值
    value = Column(String(4))
    
    def __str__(self):
        return self.id


# 创建表(有表会自动忽略,无表会自动创建)
Base.metadata.create_all(engine)
Session_class = sessionmaker(bind=engine)
Session = Session_class()
# 绑定引擎
metadata = MetaData(engine)
# 连接数据表
thermometer_table = Table('thermometer', metadata, autoload=True)
# 创建 insert 对象
ins = thermometer_table.insert()


# 设置端口变量和值
serialPosrt = "COM8"
# 设置波特率变量和值
baudRate = 9600
# 设置超时时间,单位为s
timeout = 0.5
# 接受串口数据
ser = serial.Serial(serialPosrt, baudRate, timeout=timeout)
data = b'1'

# 连接引擎
conn = engine.connect()
if conn:
    print("连接数据库成功！")
    # 循环获取数据(条件始终为真)
    while 1:
    # 读取接收到的数据的第一行
     strData = ser.readline()
    # 把拿到的数据转为字符串(串口接收到的数据为bytes字符串类型,需要转码字符串类型)
     strJson = str(strData, encoding='utf-8')
    # 如果有数据,则进行json转换
     if strJson:
        # 只有当检测到字符串中含有温湿度字符名时才进行json转码,其他的字符串内容不作操作
        if "temperature(°C)" in strJson:
            print("当前接受到的数据位->", strJson)
            # 字符串转为json(每个字符串变量名必须为双引号包括,而不是单引号)
            jsonData = json.loads(strJson)
            print("转码成功,当前类型为->", type(jsonData))
            # 温度
            temperature = jsonData["temperature(°C)"]
            # 湿度
            humidity = jsonData["humidity(%)"]
            # 时间戳
            timestamp = str(int(round(time.time() * 1000)))
            #获取时间
           # localtime = time.asctime( time.localtime(time.time()) )
            localtime=time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) 
            # 绑定要插入的数据
            ins = ins.values(temperature=temperature,
                             humidity=humidity, timestamp=timestamp,localtime=localtime)
           
            result = conn.execute(ins)
            data1 = Session.query(Kai).order_by(Kai.id.desc()).limit(1)
            data2 = Session.query(Thre).order_by(Thre.id.desc()).limit(1)
            data4=data2[0].value+data1[0].kaiguan
            data5=data4.encode(encoding='utf-8', errors = 'strict')
            write =ser.write(data5)
          
            
     else:
        print("当前无数据")
else:
    print("连接数据库失败！")