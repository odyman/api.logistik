package co.id.exml.logistikdr.utils;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import co.id.exml.logistikdr.R;

public class SuratCinta {

	public static View manaSuratKu( Context ctx, Temp[] temp ){
		SuratCinta srt = new SuratCinta( ctx );
		return srt.createInfoData( temp );
	}

	Context context = null;

	public SuratCinta( Context ctx ){
		this.context = ctx;
	}

	private TextView buildLabel( Temp tmp ){
		TextView tkey = buildTextView( tmp.getKey() );

		tkey.setTypeface( null, Typeface.BOLD );
		tkey.setPadding(20, 20, 20, 4);
		tkey.setTextSize(17);
		return tkey;
	}

	private View[] createLabelField( Temp tmp ){
		View[] view = new View[2];

		if( tmp.getCitra() != null ){
			ImageView tval = buildImageView( tmp.getCitra() );

			if( tmp.isHideLabel() ){
				view[ 0 ] = tval;
			}else{
				view[ 0 ] = buildLabel( tmp );
				view[ 1 ] = tval;
			};
		} else if( tmp.getTombol() != null ){
			Button tval = buildButtonView( tmp.getVal(), tmp.getTombol() );

			if( tmp.isHideLabel() ){
				view[ 0 ] = tval;
			}else{
				view[ 0 ] = buildLabel( tmp );
				view[ 1 ] = tval;
			};
		} else if( tmp.getTempVertical() != null ){
			View tval = createInfoDataVertical( tmp.getTempVerticalCol(), tmp.getTempVertical() );

			if( tmp.isHideLabel() ){
				view[ 0 ] = tval;
			}else{
				view[ 0 ] = buildLabel( tmp );
				view[ 1 ] = tval;
			};
		}else{
			TextView tval = buildTextView( tmp.getVal() );
			tval.setPadding(20, 4, 20, 15);
			tval.setTextSize(17);

			if( tmp.isHideLabel() ){
				view[ 0 ] = tval;
			}else{
				view[ 0 ] = buildLabel( tmp );
				view[ 1 ] = tval;
			};
		}

		return view;
	}

	private TextView buildTextView( String val ){
		LayoutParams lparams = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT );
		TextView text = new TextView( context );
		text.setLayoutParams(lparams);
		text.setText( val );
		return text;
	}

	private ImageView buildImageView( Bitmap val ){
		LayoutParams lparams = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT );
		ImageView image = new ImageView( context );
		image.setLayoutParams(lparams);
		image.setImageBitmap( val );
		return image;
	}

	private Button buildButtonView( String val, final TempInfterface inf ){
		LayoutParams lparams = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT );
		Button tombol = new Button( context );
		tombol.setLayoutParams(lparams);
		tombol.setText( val );
		tombol.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				inf.onHandler();
			}
		});
		return tombol;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	private View createInfoData( Temp[] temp ){
		LinearLayout indukLayout = new LinearLayout( context );
		indukLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		indukLayout.setOrientation(LinearLayout.VERTICAL);

		for( Temp tmp : temp ){
			if ( tmp != null ) {
				for( View vw : createLabelField( tmp ) ) {
					if( vw != null )
						indukLayout.addView( vw );
				}
			}
		};

		ScrollView indukLayoutScroll = new ScrollView( context );
		indukLayoutScroll.setBackgroundColor( android.R.color.transparent );
		indukLayoutScroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		indukLayoutScroll.setVerticalScrollBarEnabled(false);
		indukLayoutScroll.setHorizontalScrollBarEnabled(false);
		indukLayoutScroll.addView( indukLayout );

		return indukLayoutScroll;
	}

	@SuppressLint("ResourceAsColor")
	private View createInfoDataVertical( int col, Temp[] temp ){
		int leftRight = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin2);
		int bottomTop = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin2);

		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

		TableLayout tableLayout = new TableLayout(context);
		for( int i = 0; i < col; i++ ){
			tableLayout.setColumnStretchable( i, true);
		};
		tableLayout.setLayoutParams(tableParams);
		tableLayout.setPadding(leftRight, bottomTop, leftRight, bottomTop);

		TableRow tableRow = null;

		int countRow = 0;
		for( Temp tmp : temp ){
			if ( tmp != null ) {
				for( View vw : createLabelField( tmp ) ) {
					if( vw != null ) {
						if( tableRow == null ) {
							tableRow = new TableRow(context);
							tableRow.setLayoutParams(tableParams);
						};
						tableRow.addView( vw, rowParams );
						countRow += 1;
						if ( ( countRow > 0 && ( countRow % col == 0 ) ) || ( countRow >= temp.length ) ) {
							tableLayout.addView( tableRow, tableParams );
							tableRow = null;
						};
					}
				}
			}
		};

		return tableLayout;
	}
}
