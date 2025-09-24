package robotx.modules.opmode;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.drive.MecanumOrientationDrive;

public class DriveSystem extends MecanumOrientationDrive {
    public DriveSystem(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        super.init();

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if (xGamepad1.dpad_down.wasPressed()) {
            resetOrientation();
        }


    }

}