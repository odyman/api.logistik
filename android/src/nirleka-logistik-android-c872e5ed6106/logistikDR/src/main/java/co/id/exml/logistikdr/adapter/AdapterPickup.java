package co.id.exml.logistikdr.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoPickupOrder;
import co.id.exml.logistikdr.utils.BakaViewHolder;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class AdapterPickup extends ArrayAdapter<DojoPickupOrder> {
	private final Context context;
	private final List<DojoPickupOrder> values;

	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private TextDrawable.IBuilder mDrawableBuilder;

	public AdapterPickup(Context context, List<DojoPickupOrder> values) {
		super(context, R.layout.activity_pickup, values);
		this.context = context;
		this.values = values;

		mDrawableBuilder = TextDrawable.builder().roundRect(4);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate( context, R.layout.activity_pickup_row_bottom, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DojoPickupOrder item = values.get(position);

		bauHitut( holder, item );
		holder.label.setText(item.nama_customer);
		holder.sublabel.setText(item.nama_customer_location);

		return convertView;
	}


	private void bauHitut(ViewHolder holder, DojoPickupOrder item) {
		if( item != null ){
			TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.nama_customer.substring(0, 2)), mColorGenerator.getColor(item.nama_customer));
			holder.icon.setImageDrawable(drawable);
			holder.view.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	private static class ViewHolder extends BakaViewHolder{

		public ViewHolder(View view) {
			super(view);
		}

	}

} 
