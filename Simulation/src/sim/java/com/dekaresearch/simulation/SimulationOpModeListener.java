package com.dekaresearch.simulation;

import com.dekaresearch.simulation.data.PWMData;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.RobotLog;
import com.dekaresearch.simulation.data.DriverStationData;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;

import java.util.List;

import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl.DEFAULT_OP_MODE_NAME;

public class SimulationOpModeListener implements OpModeManagerNotifier.Notifications {

    private static final String TAG = "SimulationOpModeListener";

    private static SimulationOpModeListener instance = new SimulationOpModeListener();

    private OpModeManagerImpl opModeManager;

    public static SimulationOpModeListener getInstance() {
        return instance;
    }

    public void init(OpModeManagerImpl opModeManager) {
        this.opModeManager = opModeManager;
        opModeManager.registerListener(this);
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {
        RobotLog.ii(TAG, opModeManager.getActiveOpModeName() + " init");

        if(!activeOpModeIsStop()) {
            DriverStationData.getInstance().autonomous.set(activeOpModeIsAutonomous());
        }
    }

    @Override
    public void onOpModePreStart(OpMode opMode) {
        RobotLog.ii(TAG, opModeManager.getActiveOpModeName() + " start");

        if(!activeOpModeIsStop()) {
            RobotLog.ii(TAG, "ENABLE");
            DriverStationData.getInstance().enabled.set(true);
        }
    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        RobotLog.ii(TAG, opModeManager.getActiveOpModeName() + " stop");

        if(!activeOpModeIsStop()) {
            for(int i = 0; i < PWMData.MAX_DEVICES; i++) {
                PWMData.getInstances()[i].speed.set(0.0);
            }
            DriverStationData.getInstance().enabled.set(false);
        }
    }

    private boolean activeOpModeIsStop() {
        return opModeManager.getActiveOpModeName().equals(DEFAULT_OP_MODE_NAME);
    }

    private boolean activeOpModeIsAutonomous() {
        List<OpModeMeta> modes = RegisteredOpModes.getInstance().getOpModes();
        for(OpModeMeta mode : modes) {
            if(mode.name.equals(opModeManager.getActiveOpModeName())) {
                return mode.flavor == OpModeMeta.Flavor.AUTONOMOUS;
            }
        }
        return false;
    }
}