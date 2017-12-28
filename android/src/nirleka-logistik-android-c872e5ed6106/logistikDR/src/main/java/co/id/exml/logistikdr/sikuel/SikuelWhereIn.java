package co.id.exml.logistikdr.sikuel;

import android.text.TextUtils;

public class SikuelWhereIn {

	public static String buildWhereInTanda( String kolom, int[] ids, boolean not ){
		Character[] placeholdersArray = new Character[ids.length];
		for (int i = 0; i < ids.length; i++) {
			placeholdersArray[i] = '?';
		};
		String placeholders = TextUtils.join(",", placeholdersArray);
		return kolom + " "+( not ? "NOT " : "" )+"IN ( "+placeholders+" )";
	}

}
