package denny.com.attendancetracker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import denny.com.attendancetracker.R;

public class Helper {

    public static RequestOptions getImageOptions(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder);
        requestOptions.error(R.drawable.ic_placeholder);
        requestOptions.fitCenter();

        return requestOptions;
    }

    public static void showSnackBar(View view, Context context){
        Snackbar snackbar = Snackbar
                .make(view, context.getResources().getString(R.string.no_connection), Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    public static int dpToPx(int dp, Resources resources) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));
    }

    public static void showToast(Activity activity, String message){
        if(activity != null){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFormattedDate(String dateString){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date newDate = null;
        try {
            newDate = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(newDate);
    }

    public static String getSubmitDobFormattedDate(String dateString){
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        Date newDate = null;
        try {
            newDate = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(newDate);
    }

    public static String getApprovalFormattedDate(String dateString){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date newDate = null;
        try {
            newDate = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(newDate);
    }

    public static boolean isTokenValid(){
        JWT jwt = new JWT(DatabaseHelper.getEmployee().getToken());

        if(!jwt.isExpired(0)){
            return true;
        }

        return false;
    }
}
