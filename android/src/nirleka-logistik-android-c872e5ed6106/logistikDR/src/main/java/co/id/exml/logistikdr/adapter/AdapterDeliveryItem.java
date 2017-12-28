package co.id.exml.logistikdr.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.utils.BakaViewHolder;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class AdapterDeliveryItem extends ArrayAdapter<DojoDeliveryItem> {
	private final Context context;
	private final List<DojoDeliveryItem> values;

	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private TextDrawable.IBuilder mDrawableBuilder;

	public AdapterDeliveryItem(Context context, List<DojoDeliveryItem> values) {
		super(context, R.layout.activity_pickup, values);
		this.context = context;
		this.values = values;

		mDrawableBuilder = TextDrawable.builder().beginConfig().fontSize(20).endConfig().roundRect(4);
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

		DojoDeliveryItem item = values.get(position);

		String warnaUnchange = "#36B877";
		if ( item.belum_ada_perasaan_ke_server == 0 && item.ID_Status_Detail >= 2 ) {
			warnaUnchange = "#333333";
		} else if ( item.belum_ada_perasaan_ke_server > 0 && item.ID_Status_Detail >= 2 ) {
			warnaUnchange = "#ff0000";
		};

		bauHitut( holder, item );
		holder.label.setText(item.kode_barcode_awal);

		holder.sublabel.setText( item.keterangan_awal );

		holder.label.setTextColor(Color.parseColor(warnaUnchange));
		holder.sublabel.setTextColor(Color.parseColor(warnaUnchange));

		return convertView;
	}


	private void bauHitut(ViewHolder holder, DojoDeliveryItem item) {
		if( item != null ){
			String custm = String.valueOf(item.kode_barcode );
			custm = ( custm.length() >= 6 ) ? custm.substring(0,6) : custm.substring(0,1);
			TextDrawable drawable = mDrawableBuilder.build( custm, mColorGenerator.getColor(item.kode_barcode));
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
