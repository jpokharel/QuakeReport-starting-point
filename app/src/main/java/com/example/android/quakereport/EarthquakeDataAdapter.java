package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jiwanpokharel89 on 9/4/2017.
 */

public class EarthquakeDataAdapter extends ArrayAdapter {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public EarthquakeDataAdapter(@NonNull Context context, @NonNull ArrayList<EarthquakeData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        EarthquakeData earthquakeData = (EarthquakeData) getItem(position);

        TextView magnitudeText = (TextView) convertView.findViewById(R.id.magnitude_text);
        String formattedMagnitude = formatMagnitude(earthquakeData.getMagnitude());
        magnitudeText.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeText.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquakeData.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String[] placeSubStrings = earthquakeData.getPlace().split("of");
        String proximity, place;
        if (placeSubStrings.length == 1){
            proximity = "Near the";
            place = placeSubStrings[0].trim();
        }
        else{
            proximity= placeSubStrings[0].trim() + " of";
            place = placeSubStrings[1].trim();
        }
        TextView pronounText = (TextView) convertView.findViewById(R.id.place_proximity_text);
        pronounText.setText(proximity);

        TextView placeText = (TextView) convertView.findViewById(R.id.place_text);
        placeText.setText(place);

        Date dateObject = new Date(earthquakeData.getDateOfEvent());
        TextView dateText = (TextView) convertView.findViewById(R.id.date_text);
        String formattedDate = formatDate(dateObject);

        dateText.setText(formattedDate);

        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        String formattedTime = formatTime(dateObject);
        timeText.setText(formattedTime);

        return convertView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return the color per magnitude
     */
    private int getMagnitudeColor(double magnitude){
        int colorResId;
        switch ((int) Math.floor(magnitude)){
            case 0:
            case 1:
                colorResId = R.color.magnitude1;
                break;
            case 2:
                colorResId = R.color.magnitude2;
                break;
            case 3:
                colorResId = R.color.magnitude3;
                break;
            case 4:
                colorResId = R.color.magnitude4;
                break;
            case 5:
                colorResId = R.color.magnitude5;
                break;
            case 6:
                colorResId = R.color.magnitude6;
                break;
            case 7:
                colorResId = R.color.magnitude7;
                break;
            case 8:
                colorResId = R.color.magnitude8;
                break;
            case 9:
                colorResId = R.color.magnitude9;
                break;
            default:
                colorResId = R.color.magnitude10plus;
                break;

        }
        return ContextCompat.getColor(getContext(),colorResId);
    }


}
