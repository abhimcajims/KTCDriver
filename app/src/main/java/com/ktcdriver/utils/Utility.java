package com.ktcdriver.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ktcdriver.R;
import com.ktcdriver.activities.setup.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * Created by Rakhi on 10/8/2018.
 */
public class Utility {

    public static Boolean checkemail(final String emai) {
        if(emai!=null)
        {
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            if(pattern.matcher(emai).matches())
            {
                return pattern.matcher(emai).matches();
            }
        }
        return false;
    }

    public static String BitMapToString(Bitmap bitmap){
        String temp="";
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
        }
        return temp;
    }


    private static ProgressDialog progressDialog;
    public void showProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
    }

    public void hideDialog(){
        progressDialog.dismiss();
    }

    public static DecimalFormat df2 = new DecimalFormat("#0.00");

    private static Toast t;
    public static void showToast(Context context, String msg){
        if(t != null)
            t.cancel();
        t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnectedOrConnecting())
            return true;

        return false;
    }

    public static void no_internet(final Activity activity, final Context context) {
        View v = activity.findViewById(android.R.id.content);
        Snackbar.make(v,"No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getResources().getString(R.string.retry),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!isNetworkConnected(context)){
                                    no_internet(activity, context);
                                }
//                                no_internet(v, context);
                            }
                        }).setActionTextColor(context.getResources().getColor(R.color.colorWhite)).show();

    }

    public void callFragment(Fragment fragment, FragmentManager manager, int container, String tag){

        manager.beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }
    public void callFragment1(Fragment fragment, FragmentManager manager, int container, String tag){

        manager.beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }
}
