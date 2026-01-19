package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.stx_libraries.drive.MecanumDrive;

public class DriveSystemSimple extends MecanumDrive {
    public DriveSystemSimple(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        super.init();

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void control_loop() {
        super.control_loop();
        if (xGamepad1.dpad_up.wasPressed()) {
            power += 0.25;
        }
        if (xGamepad1.dpad_down.wasPressed()) {
            power -= 0.25;
        }
    }
}