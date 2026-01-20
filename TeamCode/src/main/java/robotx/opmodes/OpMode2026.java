package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        registerModule(new DriveSystem(this));
        registerModule(new FlyWheel(this));
        registerModule(new Spindexer(this));
        registerModule(new IntakeSystem(this));
    }
}