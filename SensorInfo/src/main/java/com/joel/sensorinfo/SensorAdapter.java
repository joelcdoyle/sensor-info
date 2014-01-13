package com.joel.sensorinfo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joeldoyle on 1/9/14.
 */
public class SensorAdapter extends ArrayAdapter {
    protected Context context;
    private LayoutInflater layoutInflater;
    private int resource;

    public SensorAdapter(Context context, int resource, List<Sensor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        layoutInflater = ((Activity) this.context).getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /* Initialize */
        SensorHolder sensorHolder = null;
        View sensorRow = convertView;

        /* Re-use invisible rows */
        if (convertView == null) {

            sensorRow = layoutInflater.inflate(this.resource, parent, false);

            sensorHolder = new SensorHolder();

            sensorHolder.name = (TextView) sensorRow.findViewById(R.id.sensor_name);
            sensorHolder.vendor = (TextView) sensorRow.findViewById(R.id.sensor_vendor);
            sensorHolder.resolution = (TextView) sensorRow.findViewById(R.id.sensor_resolution);
            sensorHolder.maxRange = (TextView) sensorRow.findViewById(R.id.sensor_max_range);
            sensorHolder.version = (TextView) sensorRow.findViewById(R.id.sensor_version);
            sensorRow.setTag(sensorHolder);
        } else {
            sensorRow = convertView;
            sensorHolder = (SensorHolder) sensorRow.getTag();
        }

        /* Extract sensor info */
        Sensor sensor;
        sensor = (Sensor) getItem(position);
        String sensorName = sensor.getName();
        String sensorVendor = sensor.getVendor();
        float sensorMaxRange = sensor.getMaximumRange();
        float sensorPower = sensor.getPower();
        float sensorVersion = sensor.getVersion();
        float sensorResolution = sensor.getResolution();

        /* Populate views with sensor data */
        sensorHolder.name.setText(sensorName);
        sensorHolder.vendor.setText(String.format("Vendor: %s", sensorVendor));
        sensorHolder.resolution.setText(String.format("Resolution: %s", sensorResolution));
        sensorHolder.maxRange.setText(String.format("MaxRange: %s", sensorMaxRange));
        sensorHolder.version.setText(String.format("Version: %s", sensorVersion));

        return sensorRow;
    }


    static class SensorHolder {
        TextView name, vendor, version, resolution, maxRange;
    }
}
