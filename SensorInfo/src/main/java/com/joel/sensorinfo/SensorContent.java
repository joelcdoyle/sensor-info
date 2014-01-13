package com.joel.sensorinfo;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joeldoyle on 1/9/14.
 */
public class SensorContent {

    public static List<Sensor> SENSORS;

    public static Map<String, SensorWrapper> SENSOR_MAP = new HashMap<String, SensorWrapper>();

    public static void setManager(SensorManager sensorManager) {
        SENSORS = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : SENSORS) {
            SENSOR_MAP.put(sensor.getName(), new SensorWrapper(sensor));
        }

    }

    public static class SensorWrapper {
        public String id;
        public Sensor sensor;

        public SensorWrapper(Sensor sensor) {
            this.id = sensor.getName();
            this.sensor = sensor;
        }

    }
}
