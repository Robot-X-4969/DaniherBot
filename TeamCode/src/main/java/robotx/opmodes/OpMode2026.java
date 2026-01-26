package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.CameraSystem;
import robotx.modules.opmode.DriveSystem;
import robotx.modules.opmode.FlyWheel;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.Spindexer;
import robotx.stx_libraries.XOpMode;

@TeleOp(name = "OpMode 25-26", group = "CurrentOp")
public class OpMode2026 extends XOpMode {

    @Override
    public void initModules() {
        super.initModules();
        DriveSystem drive = new DriveSystem(this);
        registerModule(drive);
        registerModule(new FlyWheel(this));
        registerModule(new Spindexer(this));
        registerModule(new IntakeSystem(this));
        registerModule(new CameraSystem(this, drive));

    }
}