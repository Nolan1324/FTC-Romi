package com.dekaresearch.simulation.romi;

import android.content.Context;

import com.dekaresearch.simulation.hardware.SimAccelerationSensor;
import com.dekaresearch.simulation.hardware.SimAnalogInputController;
import com.dekaresearch.simulation.hardware.SimDcMotor;
import com.dekaresearch.simulation.hardware.SimDcMotorEncoded;
import com.dekaresearch.simulation.hardware.SimDigitalChannel;
import com.dekaresearch.simulation.hardware.SimGyroSensor;
import com.dekaresearch.simulation.hardware.SimVoltageSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RomiHardwareFactory {
    public static HardwareMap createHardwareMap(Context context) {
        HardwareMap map = new HardwareMap(context);
        map.dcMotor.put("left_drive", new SimDcMotorEncoded(0, 4, 5));
        map.dcMotor.put("right_drive", new SimDcMotorEncoded(1, 6, 7));
        map.voltageSensor.put("battery", new SimVoltageSensor());

        for(int i = 0; i <= 3; i++) {
            map.digitalChannel.put("dio_" + i, new SimDigitalChannel(i));
        }

        map.gyroSensor.put("gyro", new SimGyroSensor());
        map.accelerationSensor.put("accelerometer", new SimAccelerationSensor());

        SimAnalogInputController analogInputController = new SimAnalogInputController();
        map.put("analog_input_controller", analogInputController);
        for(int i = 0; i <= 3; i++) {
            map.analogInput.put("analog_" + i, new AnalogInput(analogInputController, i));
        }

        return map;
    }


}
