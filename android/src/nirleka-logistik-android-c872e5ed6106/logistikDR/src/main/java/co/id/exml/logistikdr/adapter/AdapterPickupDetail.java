package co.id.exml.logistikdr.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.utils.BakaViewHolder;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class AdapterPickupDetail extends ArrayAdapter<DojoPickupDetail> {
	private final Context context;
	private final List<DojoPickupDetail> values;

	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private TextDrawable.IBuilder mDrawableBuilder;

	public AdapterPickupDetail(Context context, List<DojoPickupDetail> values) {
		super(context, R.layout.activity_pickup, values);
		this.context = context;
		this.values = values;

		mDrawableBuilder = TextDrawable.builder().beginConfig().fontSize(20).endConfig().roundRect(4);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate( context, R.layout.activity_pickup_row, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DojoPickupDetail item = values.get(position);
		
		String warnaUnchange = ( item.belum_ada_perasaan_ke_server == 1 ? "#ff0000" : "#333333" );

		bauHitut( holder, item );

		//kode awb? major, jika no resi tidak null, maka concat
		holder.label.setText(item.no_resi);
		holder.sublabel.setText(item.nama_penerima);
		holder.label.setTextColor(Color.parseColor(warnaUnchange));
		holder.sublabel.setTextColor(Color.parseColor(warnaUnchange));

		return convertView;
	}


	private void bauHitut(ViewHolder holder, DojoPickupDetail item) {
		if( item != null ){
			String custm = String.valueOf(item.no_resi );
			custm = ( custm.length() >= 6 ) ? custm.substring(0,6) : custm.substring(0,1);
			TextDrawable drawable = mDrawableBuilder.build( custm, mColorGenerator.getColor(item.no_resi));
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
