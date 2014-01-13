package com.joel.sensorinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A fragment representing a single Sensor detail screen.
 * This fragment is either contained in a {@link SensorListActivity}
 * in two-pane mode (on tablets) or a {@link SensorDetailActivity}
 * on handsets.
 */
public class SensorDetailFragment extends Fragment implements SensorEventListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The sensor objects.
     */
    private SensorManager mSensorManager;
    private SensorContent.SensorWrapper mItem;

    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SensorDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = SensorContent.SENSOR_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Determine how many text views to create based on sensor type
        int sensorType = mItem.sensor.getType();
        int values;

        switch(sensorType){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_LIGHT:
            case Sensor.TYPE_PRESSURE:
            case Sensor.TYPE_PROXIMITY:
            case Sensor.TYPE_RELATIVE_HUMIDITY:
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GYROSCOPE:
            case Sensor.TYPE_MAGNETIC_FIELD:
            case Sensor.TYPE_GRAVITY:
            case Sensor.TYPE_LINEAR_ACCELERATION:
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                values = 3;
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                    values = 4;
            default:
                values = 3;
        }

        rootView = inflater.inflate(R.layout.fragment_sensor_detail, container, false);

        // Initially set text views with sensor data.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.sensor_name)).setText(mItem.sensor.getName());
            ((TextView) rootView.findViewById(R.id.sensor_vendor)).setText(mItem.sensor.getVendor());

            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.sensor_values);

            for( int i = 0; i < values; i++){
                TextView textView = new TextView(getActivity());
                textView.setText("0.0");
                textView.setTextSize(24);
                textView.setId(i);
                linearLayout.addView(textView);
            }
        }

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mItem.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.sensor_values);
        int i = 0;
        for(float val : sensorEvent.values){
            ((TextView) linearLayout.findViewById(i)).setText(""+val);
            i++;
        }
    }
}
