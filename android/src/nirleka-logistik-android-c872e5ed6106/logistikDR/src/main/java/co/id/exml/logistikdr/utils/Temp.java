package co.id.exml.logistikdr.utils;

import java.io.Serializable;

import android.graphics.Bitmap;

public final class Temp implements Serializable {

	private static final long serialVersionUID = 6111325107053419204L;

	private String key, val;
	private Bitmap citra = null;
	private String data;
	private TempInfterface tombol = null;
	private Temp[] tempVertical;
	private int tempVerticalCol = 2;
	
	public int getTempVerticalCol() {
		return tempVerticalCol;
	}
	public void setTempVerticalCol(int tempVerticalCol) {
		this.tempVerticalCol = tempVerticalCol;
	}
	public Temp[] getTempVertical() {
		return tempVertical;
	}
	public void setTempVertical(Temp[] tempVertical) {
		this.tempVertical = tempVertical;
	}
	public TempInfterface getTombol() {
		return tombol;
	}
	public void setTombol(TempInfterface tombol) {
		this.tombol = tombol;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public boolean isHideLabel() {
		return hideLabel;
	}
	public void setHideLabel(boolean hideLabel) {
		this.hideLabel = hideLabel;
	}
	private boolean hideLabel = false;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Bitmap getCitra() {
		return citra;
	}
	public void setCitra(Bitmap citra) {
		this.citra = citra;
	}
	public Temp(String key, String val, Bitmap citra, boolean hideLabel ) {
		super();
		this.key = key;
		this.val = val;
		this.citra = citra;
		this.hideLabel = hideLabel;
	}
	public Temp(String key, String val, Bitmap citra ) {
		super();
		this.key = key;
		this.val = val;
		this.citra = citra;
	}
	public Temp(String key, String val, String data ) {
		super();
		this.key = key;
		this.val = val;
		this.data = data;
	}
	public Temp(String key, String val ) {
		super();
		this.key = key;
		this.val = val;
	}
	public Temp(String key, String val, boolean hideLabel  ) {
		super();
		this.key = key;
		this.val = val;
		this.hideLabel = hideLabel;
	}
	public Temp(String key, String val, TempInfterface tombol, boolean hideLabel ) {
		super();
		this.key = key;
		this.val = val;
		this.tombol = tombol;
		this.hideLabel = hideLabel;
	}
	public Temp(String key, String val, Temp[] tempVertical ) {
		super();
		this.key = key;
		this.val = val;
		this.tempVertical = tempVertical;
	}
	public Temp(String key, String val, Temp[] tempVertical, boolean hideLabel ) {
		super();
		this.key = key;
		this.val = val;
		this.tempVertical = tempVertical;
		this.hideLabel = hideLabel;
	}
	public Temp(String key, String val, Temp[] tempVertical, boolean hideLabel, int tempVerticalCol ) {
		super();
		this.key = key;
		this.val = val;
		this.tempVertical = tempVertical;
		this.hideLabel = hideLabel;
		this.tempVerticalCol = tempVerticalCol;
	}
	public Temp(String key, String val, TempInfterface tombol ) {
		super();
		this.key = key;
		this.val = val;
		this.tombol = tombol;
	}
	public Temp(){
		super();
	}

	@Override
	public String toString() {
		return  key + " " + val;
	}
}
