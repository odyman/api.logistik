package co.id.exml.logistikdr.sikuel;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;

public class SikuelPojo extends Model {
	public static void truncate(Class<? extends Model> type){
		TableInfo tableInfo = Cache.getTableInfo(type);
		ActiveAndroid.execSQL(
				String.format("DELETE FROM %s;",
						tableInfo.getTableName()));
		ActiveAndroid.execSQL(
				String.format("DELETE FROM sqlite_sequence WHERE name='%s';",
						tableInfo.getTableName()));
	}
}
