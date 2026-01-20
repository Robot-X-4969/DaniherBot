package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.stx_libraries.XOpMode;
import robotx.stx_libraries.drive.MecanumOrientationDrive;

@TeleOp(name = "Basic Drive", group = "CurrentOp")
public class BasicDriveOp extends XOpMode {
    MecanumOrientationDrive mecanumOrientationDrive;

    @Override
    public void initModules() {
        super.initModules();
        registerModule(new MecanumOrientationDrive(this));
    }
}