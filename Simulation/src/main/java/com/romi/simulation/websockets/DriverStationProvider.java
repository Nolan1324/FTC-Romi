package com.romi.simulation.websockets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.romi.simulation.data.DriverStationData;

import org.firstinspires.ftc.robotcore.external.Consumer;

public class DriverStationProvider extends Provider {
    private final DriverStationData data = DriverStationData.getInstance();

    public DriverStationProvider() {
        super("DriverStation", "");
    }

    @Override
    public void registerCallbacks() {
        data.enabled.registerCallback(new BasicCallback<Boolean>(">enabled"));
    }

    @Override
    public void unregisterCallbacks() {
        data.enabled.unregisterAllCallbacks();
    }

    @Override
    public void onNetValueChanged(String key, JsonElement value) {

    }
}
