package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.DriveSystem;
import robotx.modules.opmode.FlyWheel;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.Spindexer;
import robotx.stx_libraries.XOpMode;

@TeleOp(name = "OpMode 25-26", group = "CurrentOp")
public class OpMode2026 extends XOpMode {
    FlyWheel flyWheel;
    Spindexer spindexer;
    DriveSystem driveSystem;
    IntakeSystem intakeSystem;

    public void initModules() {
        driveSystem = new DriveSystem(this);
        activeModules.add(driveSystem);

        flyWheel = new FlyWheel(this);
        activeModules.add(flyWheel);

        spindexer = new Spindexer(this);
        activeModules.add(spindexer);

        intakeSystem = new IntakeSystem(this);
        activeModules.add(intakeSystem);
    }
}