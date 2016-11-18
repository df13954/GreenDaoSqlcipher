# Greendao 数据库加密栗子

加密之后的数据库，root的手机，通过Root Explorer进入 /data/data/packName/databases/目标db文件，是无法打开

加密之后，apk成品会增加4-5M大小。这个东西，需要衡量下你的数据，是否需要加密

![image](https://github.com/rongdongliu/GreenDaoSqlcipher/blob/master/img/Screenshot_2016-11-08-11-42-51-995_com.speedsoftw.png)

## 打开数据库时候，需要一个唯一的key值
```
public void startup(Context mContext) {
        this.mContext = mContext;
        LogUtils.e("启动数据库");
        //这个地方，生成唯一的钥匙来访问数据库,方法是某些大神的。
        My_pwd = new UUIDGen(mContext).getUUID();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        getOpenHelper();
        //这里的db，必须是getEncryptedWritableDb，如果使用了这个helper.getWritableDatabase();就会异常。
        db = helper.getEncryptedWritableDb(My_pwd);
//        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = new DaoMaster(helper.getEncryptedWritableDb(My_pwd)).newSession();
    }
```

## 在application初始化的时候，可以先获取操作数据库的实例

 ```
 /**
     * 初始化数据库，打开数据库
     */
    private void initGreenDao() {
        databaseManager = new DatabaseManagerImpl();
        databaseManager.startup(this);
    }

    /**
     * 提供外部使用
     *
     * @return DaoSession
     */
    public static DaoSession getGreenDaoSessino() {
        return databaseManager.getDaoSession();
    }
 
 
 public synchronized DaoSession getDaoSession() {
        if (!checkDBStatus()) {
            return null;
        }
        if (daoSession == null) {
            DaoMaster daoMaster = new DaoMaster(helper.getEncryptedReadableDb(My_pwd));
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
	
```

