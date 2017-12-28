package co.id.exml.logistikdr.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.utils.BakaViewHolder;
import co.id.exml.logistikdr.utils.Temp;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class AdapterBaka extends ArrayAdapter<Temp> {
	private final Context context;
	private List<Temp> values, original;
	private NameFilter filter;

	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private TextDrawable.IBuilder mDrawableBuilder;

	private int layoutResources;

	public AdapterBaka(Context context, List<Temp> valuesx, int layoutRes ) {
		super(context, R.layout.activity_pickup, valuesx);
		this.context = context;
		this.original = new ArrayList<Temp>(valuesx);
		this.values = new ArrayList<Temp>(valuesx);
		this.layoutResources = layoutRes;
		this.filter  = new NameFilter();

		mDrawableBuilder = TextDrawable.builder().roundRect(4);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate( context, layoutResources, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Temp item = values.get(position);

		bauHitut( holder, item );
		holder.label.setText(item.getKey());
		holder.sublabel.setText(item.getVal());

		return convertView;
	}


	private void bauHitut(ViewHolder holder, Temp item) {
		if( item != null ){
			TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.getKey().substring(0, 2)), mColorGenerator.getColor(item.getKey()));
			holder.icon.setImageDrawable(drawable);
			holder.view.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	private static class ViewHolder extends BakaViewHolder{

		public ViewHolder(View view) {
			super(view);
		}

	}

	@Override
	public Filter getFilter() {
		if (filter == null){
			filter  = new NameFilter();
		}
		return filter;
	}

	private class NameFilter extends Filter {

		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if(constraint != null && constraint.toString().length() > 0 ){
				ArrayList<Temp> filteredItems = new ArrayList<Temp>();

				for(int i = 0, l = original.size(); i < l; i++){
					Temp nameList = original.get(i);
					if(nameList.toString().toLowerCase().contains(constraint))
						filteredItems.add(nameList);
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			}else{
				synchronized(this){
					result.values = original;
					result.count = original.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			values = (ArrayList<Temp>)results.values;
			notifyDataSetChanged();
			clear();
			for(int i = 0, l = values.size(); i < l; i++)
				add(values.get(i));
			notifyDataSetInvalidated();
		}
	}

} 
