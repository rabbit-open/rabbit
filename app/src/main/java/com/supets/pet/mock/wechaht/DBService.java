package com.supets.pet.mock.wechaht;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.supets.pet.mockui.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;


public class DBService {
	private final String DATABASE_FILENAME = "sns";

    private Context mContext;
	private SQLiteDatabase mysql;
    private static DBService dataBase;

	private DBService(Context context) {
		mContext = context;
		initSQLDataBase();
	}
	
	public static DBService getInstance(Context context) {
        if (dataBase == null) {
            dataBase = new DBService(context);
        }
        return dataBase;
    }
	
	public void close(){
		if(mysql!=null && mysql.isOpen()){
			mysql.close();
		}
	}

	public void execSQL(String sql, Object[] args) {
		mysql = openDatabase();
		if( mysql != null ) {
			mysql.execSQL(sql, args);
			mysql.close();
		}
	}

	public Cursor query(String sql, String[] args) {
		mysql = openDatabase();
		if( mysql != null ) {
			return mysql.rawQuery(sql, args);
		}
		return null;
	}

	public boolean insertBach(List<String> sqlList, List<Object[]> objlist) {
		mysql = openDatabase();
		
		boolean result = false;
		if( mysql != null ) {
			try {
				mysql.beginTransaction();
				for (int i = 0; i < sqlList.size(); i++) {
					mysql.execSQL(sqlList.get(i), objlist.get(i));
				}
				mysql.setTransactionSuccessful();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				mysql.endTransaction();
				mysql.close();
			}
		}
		return result;
	}

	public boolean insertBach(Vector<String> sqlList) {
		mysql = openDatabase();
		
		boolean result = false;
		if( mysql != null ) {
			try {
				mysql.beginTransaction();
				for (int i = 0; i < sqlList.size(); i++) {
					mysql.execSQL(sqlList.get(i));
				}
				mysql.setTransactionSuccessful();
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				mysql.endTransaction();
				mysql.close();

			}
		}
		return result;
	}

    private void initSQLDataBase() {
			copyDBFile();
    }
    
    private SQLiteDatabase openDatabase() {
        try {
        	File dbFile = mContext.getFileStreamPath(DATABASE_FILENAME);
        	
        	if (!dbFile.exists()) {
				InputStream is = mContext.getResources().openRawResource(R.raw.sns);
                FileOutputStream fos = new FileOutputStream(dbFile);
                
                byte[] buffer = new byte[1024 * 10];
                int count ;
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count);
                }
                
                fos.close();
                is.close();
            }      	
            
            return SQLiteDatabase.openOrCreateDatabase(dbFile.getAbsolutePath(), null);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    private void copyDBFile() {
    	try {
        	File dbFile = mContext.getFileStreamPath(DATABASE_FILENAME);

			if ( dbFile!=null && dbFile.exists()){
				dbFile.delete();
			}

        	if (!dbFile.exists()) {
				InputStream is = mContext.getResources().openRawResource(R.raw.sns);
                FileOutputStream fos = new FileOutputStream(dbFile);
                
                byte[] buffer = new byte[1024 * 10];
                int count ;
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count);
                }
                
                fos.close();
                is.close();
            }

        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
}
