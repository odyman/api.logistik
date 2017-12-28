package co.id.exml.logistikdr.utils;

import android.annotation.SuppressLint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.annotation.Column;

public class Pijat {

	public static JSONArray DOJO2JSONArray( List<?> listKelas ){
		JSONArray json = new JSONArray();
		for( Object kelas : listKelas ) {
			json.put( Pijat.DOJO2JSONObject(kelas));
		}
		return json;
	}

	public static JSONObject DOJO2JSONObject( Object kelas ){
		JSONObject json = new JSONObject();

		Class<?> objClass = kelas.getClass();
		Field[] fields = objClass.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			Annotation[] annotations = field.getDeclaredAnnotations();
			for(Annotation annotation : annotations){
				if(annotation instanceof Column){
					Column annon = (Column) annotation;
					String name = annon.name();
					Object value;
					try {
						value = field.get( kelas );

						if( value == null ) {
							value = new String("");
						}

						try {
							json.put(name, value);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return json;
	}

	@SuppressLint("NewApi")
	public static void JSON2DOJO( JSONArray json, Class<?> kelas ){
		ActiveAndroid.beginTransaction();
		try {
			Class<?> clazz = Class.forName(kelas.getName());
			try {
				for( int i = 0; i < json.length(); i++ ){
					try {
						Object instance = clazz.newInstance();
						sakitHati( instance, json.getJSONObject(i), kelas );
					} catch (JSONException e) {
						e.printStackTrace();
					}
				};
				ActiveAndroid.setTransactionSuccessful();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
	}

	public static void sakitHati( Object instance, JSONObject json, Class<?> kelas ){
		for ( Field f : kelas.getDeclaredFields() ){
			if( json.has( f.getName() ) ) {
				if( f.getType().isAssignableFrom(String.class)){
					try {
						set( instance, f.getName(), json.getString(f.getName()));
					} catch (JSONException e) {
						e.printStackTrace();
					};
				}else{
					try {
						set( instance, f.getName(), json.getInt(f.getName()));
					} catch (JSONException e) {
						e.printStackTrace();
					};
				};
			};
		};
		try {
			Method method = kelas.getDeclaredMethod( "doSave" );
			try {
				method.invoke( instance );
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static boolean set(Object object, String fieldName, Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
	}
}
