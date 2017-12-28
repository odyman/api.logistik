package co.id.exml.logistikdr.utils;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

public class Makerot {
	
	public static Bitmap defaultQR( String codeData ){
		return Makerot.createBarCode(codeData, BarcodeFormat.QR_CODE, 200, 200 );
	}
	
	public static Bitmap createBarCode (String codeData, BarcodeFormat barcodeFormat, int codeHeight, int codeWidth) {

		try {
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel> ();
			hintMap.put (EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			Writer codeWriter;
			if (barcodeFormat == BarcodeFormat.QR_CODE) {
				codeWriter = new QRCodeWriter ();
			} else if (barcodeFormat == BarcodeFormat.CODE_128) {
				codeWriter = new Code128Writer ();
			} else {
				throw new RuntimeException ("Format Not supported.");
			}

			BitMatrix byteMatrix = codeWriter.encode (
					codeData,
					barcodeFormat,
					codeWidth,
					codeHeight,
					hintMap
					);

			int width   = byteMatrix.getWidth ();
			int height  = byteMatrix.getHeight ();

			Bitmap imageBitmap = Bitmap.createBitmap (width, height, Config.ARGB_8888);

			for (int i = 0; i < width; i ++) {
				for (int j = 0; j < height; j ++) {
					imageBitmap.setPixel (i, j, byteMatrix.get (i, j) ? Color.BLACK: Color.WHITE);
				}
			}

			return imageBitmap;

		} catch (WriterException e) {
			e.printStackTrace ();
			return null;
		}
	}
}
