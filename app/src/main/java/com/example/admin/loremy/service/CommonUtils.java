package com.example.admin.loremy.service;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {
    private static boolean enableDebug = true;

    public static int[] ScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics displaymetrics = context.getResources()
                .getDisplayMetrics();
        size[0] = displaymetrics.widthPixels;
        size[1] = displaymetrics.heightPixels;

        return size;
    }

    public static double convertCurrencyVnToDouble(String currency) {
        double price = 0;
        try {
            price = parse(currency, new Locale("vi", "VN")).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static BigDecimal parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView, int numberColumn) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int oneHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(gridView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
            oneHeight = listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (totalHeight / numberColumn);
        if ((listAdapter.getCount() % numberColumn) == 0) {

        } else {
            params.height = params.height + (oneHeight / 2);
        }
        Log.d("lam", "setGridViewHeightBasedOnChildren: " + listAdapter.getCount());
        params.height = (params.height / numberColumn);
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }

    public static void setEditTextVietNamCurrecy(final EditText editTextCurrecy) {
        editTextCurrecy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextCurrecy.removeTextChangedListener(this);
                if (!editTextCurrecy.getText().toString().isEmpty()) {
                    String currecy = editTextCurrecy.getText().toString().replace(".", "").replace(",", "").replace(" ₫", "");
                    Log.d("lam", "afterTextChanged: " + currecy);
                    double doubleCurrecy = Double.parseDouble(currecy);
                    Log.d("lam", "afterTextChanged: " + doubleCurrecy);
                    editTextCurrecy.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(doubleCurrecy).toString().replace(" ₫", ""));
                    editTextCurrecy.setSelection(editTextCurrecy.getText().toString().length());
                }
                editTextCurrecy.addTextChangedListener(this);
            }
        });

    }

    public static String stringVietNamCurrency(double currency) {
        String stringCurrency = "";
        stringCurrency = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(currency);
        return stringCurrency;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static boolean checkNetwork(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }


    public static void toggleGPS(Context context, boolean enable) {
        @SuppressWarnings("deprecation")
        String provider = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps") == enable) {
            return; // the GPS is already in the requested state
        }
        final Intent poke = new Intent();
        poke.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        poke.setData(Uri.parse("3"));
        context.sendBroadcast(poke);
    }

    public static String formattedDateTime(long time, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static boolean stringIsValid(String str) {
        if (str != null && !str.trim().equals("")
                && !str.toLowerCase().equals("null")) {
            return true;
        }
        return false;
    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();

            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static String getCurrentTimeWithFormat(String formatString) {
        long currentTime = System.currentTimeMillis();
        return formattedDateTime(currentTime, formatString);
    }

    public static Date getDateFromString(String dateString, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("vi", "VN"));
        try {
            return sdf.parse(dateString);
        } catch (Exception ignore) {
        }
        return null;
    }

    public static String getFormattedTime(long millis, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(millis));
    }

    public static String getDurationTime(long startTimeInMillis) {
        long currentMillisTime = System.currentTimeMillis() - startTimeInMillis;
        long totalSeconds = currentMillisTime / 1000;
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        int days = (int) totalSeconds / (24 * 3600);

        StringBuilder builder = new StringBuilder();
        String minutesSeconds = String.format("%02d:%02d", minutes, seconds);
        if (hours > 0) {
            String hoursStr = String.format("%02d", hours);
            builder.append(hoursStr).append(":");
        }

        if (days > 0) {
            String daysStr = String.format("%02d", days);
            builder.append(daysStr).append(":");
        }

        builder.append(minutesSeconds);

        return builder.toString();
    }


    public static int getListViewHeight(ListView list) {
        ListAdapter adapter = list.getAdapter();

        int listviewHeight = 0;

        list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED));

        listviewHeight = list.getMeasuredHeight() * adapter.getCount()
                + (adapter.getCount() * list.getDividerHeight());

        return listviewHeight;
    }

    public static String getBase64StringFromFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileInputStream fis = new FileInputStream(f);

                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf))) {
                    baos.write(buf, 0, n);
                }

                byte[] videoBytes = baos.toByteArray();
                String stringBase64 = Base64.encodeToString(videoBytes,
                        Base64.DEFAULT);
                fis.close();
                baos.close();
                return stringBase64;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Dialog showOkDialog(Context context,
                                      String message, OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", onClickListener);
        builder.show();
        return builder.create();
    }

    public static Dialog showOkDialog(Context context,
                                      String message, boolean isCancelable, OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", onClickListener);
        builder.setCancelable(isCancelable);
        builder.show();
        return builder.create();
    }

    public static void callPhone(Context context, String phone) {
        try {
            Intent callItent = new Intent(Intent.ACTION_CALL);
            callItent.setData(Uri.parse("tel:"
                    + phone.replace(" ", "")
                    .replace("-", "").replace("/", "")
                    .replace("x", "").replace(")", "")
                    .replace("(", "").replace(".", "")));
            callItent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            context.startActivity(callItent);
        } catch (Exception e) {
        }
    }

    public static Dialog showOkCancelDialog(Context context,
                                            String message, String nameNegativeButton, String namePositiveButton, OnClickListener okClickListenerNegativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNegativeButton(nameNegativeButton, okClickListenerNegativeButton);
        builder.setPositiveButton(namePositiveButton, null);
        builder.show();
        return builder.create();
    }


    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        if (!stringIsValid(phoneNumber)) {
            phoneNumber = tMgr.getSubscriberId();
        }
        Log.e("Phone", phoneNumber + "");
        return phoneNumber;
    }

    public static String getDeviceID(Context context) {
        TelephonyManager mngr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = mngr.getDeviceId();
        return deviceID;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String getStringResource(int pathString, Context context) {
        String s = context.getResources().getString(pathString);
        return s;
    }


}


