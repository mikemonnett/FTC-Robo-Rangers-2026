package org.firstinspires.ftc.teamcode.pedro;

import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;

public class Tuning {
    // Tuners go here

    @Tuner
    public static Procedure pinpointTuner() {
        return new PinpointTuner();
    }

}

