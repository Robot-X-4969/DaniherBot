package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.DriveSystem;
import robotx.modules.opmode.FlyWheel;
import robotx.stx_libraries.XOpMode;

@TeleOp(name = "OpMode 25-26", group = "CurrentOp")
public class OpMode2026 extends XOpMode {
    FlyWheel flyWheel;

    public void initModules() {
        flyWheel = new FlyWheel(this);
        activeModules.add(flyWheel);
    }
}