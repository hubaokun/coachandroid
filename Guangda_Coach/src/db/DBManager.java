package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import xiaoba.coach.net.result.Area;
import xiaoba.coach.net.result.City;
import xiaoba.coach.net.result.Province;
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    private Context mContext;
    public DBManager(Context context) {
        mContext=context;
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<Area> queryArea(String cityid) {
        ArrayList<Area> arealist = new ArrayList<Area>();
        Cursor c = queryTheCursor(cityid);
        while (c.moveToNext()) {
            Area area = new Area();
            area.zoneid = c.getString(c.getColumnIndex("areaid"));
            area.zonename = c.getString(c.getColumnIndex("area"));
            arealist.add(area);
        }
        c.close();
        return arealist;
    }
    /**
     * @return
     */
    public List<City> queryCity(String provinid) {
        ArrayList<City> citylist = new ArrayList<City>();
        Cursor c = queryAllCity(provinid);
        while (c.moveToNext()) {
            City city = new City();
            city.cityid = c.getString(c.getColumnIndex("cityid"));
            city.cityname = c.getString(c.getColumnIndex("city"));
            citylist.add(city);
        }
        c.close();
        return citylist;
    }

    public List<Province> queryProvince()
    {
        ArrayList<Province> provincelist = new ArrayList<Province>();
        Cursor c = queryAllProvince();
        while (c.moveToNext())
        {
        	  Province province = new Province();
              province.provinceId = c.getString(c.getColumnIndex("provinceid"));
              province.provinceName = c.getString(c.getColumnIndex("province"));
              provincelist.add(province);
        }
        c.close();
        return provincelist;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor(String cityid) {
        Cursor c = db.rawQuery("SELECT * FROM T_Zone Where cityid=?",  new String[]{cityid});
        return c;
    }
    

    
    public Cursor queryAllCity(String provinceid){
        Cursor c = db.rawQuery("SELECT * FROM T_City where provinceid=? ", new String[]{provinceid});
        return c;
    }



    public Cursor queryAllProvince()
    {
        Cursor c = db.rawQuery("SELECT * FROM T_Province",null);
        return c;
    }
    public void closeDB() {
        db.close();
    }
}
