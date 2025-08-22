package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.DriveSystem;
import robotx.stx_libraries.XOpMode;

@TeleOp(name = "OpMode 25-26", group = "CurrentOp")
public class OpMode2026 extends XOpMode {
    DriveSystem driveSystem;

    public void initModules() {
        driveSystem = new DriveSystem(this);
        activeModules.add(driveSystem);
    }
}