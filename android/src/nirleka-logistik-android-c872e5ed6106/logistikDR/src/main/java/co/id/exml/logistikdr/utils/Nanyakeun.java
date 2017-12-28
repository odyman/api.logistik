package co.id.exml.logistikdr.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import co.id.exml.logistikdr.R;

public class Nanyakeun extends DialogFragment{
	private Context mContext;

	public static String tagNanyakeun = "SokTanyakeunHeula";

	private String title, message, okBtn = "OK", cancelBtn = "Cancel";
	private NanyakeunCallback callbackOk, callbackCancel;

	public static void taros( Context ctx, FragmentManager fmgr, String nanyakeunNaon, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel, String okB, String cancelB ){
		new Nanyakeun( ctx,tl, msg, clbkOk, clbkCancel, okB, cancelB ).show( fmgr, nanyakeunNaon );
	}

	public static void taros( Context ctx, FragmentManager fmgr, String nanyakeunNaon, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel, String okB ){
		new Nanyakeun( ctx,tl, msg, clbkOk, clbkCancel, okB ).show( fmgr, nanyakeunNaon );
	}

	public static void taros( Context ctx, FragmentManager fmgr, String nanyakeunNaon, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel ){
		new Nanyakeun( ctx,tl, msg, clbkOk, clbkCancel ).show( fmgr, nanyakeunNaon );
	}

	public static void taros( Context ctx, FragmentManager fmgr, String nanyakeunNaon, String tl, String msg, NanyakeunCallback clbkOk ){
		new Nanyakeun( ctx,tl, msg, clbkOk ).show( fmgr, nanyakeunNaon );
	}

	public static void taros( Context ctx, FragmentManager fmgr, String nanyakeunNaon, String tl, String msg ){
		new Nanyakeun( ctx,tl, msg ).show( fmgr, nanyakeunNaon );
	}

	public Nanyakeun()
	{
	}

    @SuppressLint("ValidFragment")
	public Nanyakeun( Context ctx, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel, String okB, String cancelB ) {
		this.mContext = ctx;
		this.title = tl;
		this.message = msg;
		this.callbackOk = clbkOk;
		this.callbackCancel = clbkCancel;
		this.okBtn = okB;
		this.cancelBtn = cancelB;
	}

    @SuppressLint("ValidFragment")
	public Nanyakeun( Context ctx, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel, String okB ) {
		this.mContext = ctx;
		this.title = tl;
		this.message = msg;
		this.callbackOk = clbkOk;
		this.callbackCancel = clbkCancel;
		this.okBtn = okB;
	}

    @SuppressLint("ValidFragment")
	public Nanyakeun( Context ctx, String tl, String msg, NanyakeunCallback clbkOk, NanyakeunCallback clbkCancel ) {
		this.mContext = ctx;
		this.title = tl;
		this.message = msg;
		this.callbackOk = clbkOk;
		this.callbackCancel = clbkCancel;
	}

    @SuppressLint("ValidFragment")
	public Nanyakeun( Context ctx, String tl, String msg, NanyakeunCallback clbkOk ) {
		this.mContext = ctx;
		this.title = tl;
		this.message = msg;
		this.callbackOk = clbkOk;
	}

    @SuppressLint("ValidFragment")
	public Nanyakeun( Context ctx, String tl, String msg ) {
		this.mContext = ctx;
		this.title = tl;
		this.message = msg;
	}

	@SuppressLint("NewApi")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder nanya = new AlertDialog.Builder(mContext);
		nanya.setIcon(R.drawable.ic_launcher);
		nanya.setTitle( title );
		nanya.setMessage( message );

		if( callbackOk != null ){
			nanya.setPositiveButton( okBtn, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if( callbackOk != null )
						callbackOk.callback(dialog, which);
				}
			});
		};
		if( callbackCancel != null ){
			nanya.setNegativeButton( cancelBtn, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if( callbackCancel != null )
						callbackCancel.callback(dialog, which);
				}
			});
		};


		return nanya.create();
	}
}
