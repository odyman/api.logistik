package co.id.exml.logistikdr.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitBase64 {

	public static final int QUALITY = 100;
	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;
	
	public static final int WIDTH_SMALL = 120;
	public static final int HEIGHT_SMALL = 120;

	public static String encodeTobase64(Bitmap image){
		Bitmap immagex = Bitmap.createScaledBitmap( image, BitBase64.WIDTH, BitBase64.HEIGHT, false);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		immagex.compress(Bitmap.CompressFormat.JPEG, BitBase64.QUALITY, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

		//Log.e("LOOK", imageEncoded);
		return imageEncoded;
	}

	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}

	public static Bitmap decodeBase64Small(String input) {
		return Bitmap.createScaledBitmap( BitBase64.decodeBase64( input ), BitBase64.WIDTH_SMALL, BitBase64.HEIGHT_SMALL, false);
	}

}