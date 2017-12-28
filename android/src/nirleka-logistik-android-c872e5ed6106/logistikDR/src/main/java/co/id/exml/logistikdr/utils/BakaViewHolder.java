package co.id.exml.logistikdr.utils;

import co.id.exml.logistikdr.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BakaViewHolder {

	public View view;
	public ImageView icon;
	public TextView label, sublabel;

	public BakaViewHolder(View view) {
		this.view = view;
		icon = (ImageView) view.findViewById(R.id.activity_pickup_row_icon);
		label = (TextView) view.findViewById(R.id.activity_pickup_row_label);
		sublabel = (TextView) view.findViewById(R.id.activity_pickup_row_sublabel);
	}
}