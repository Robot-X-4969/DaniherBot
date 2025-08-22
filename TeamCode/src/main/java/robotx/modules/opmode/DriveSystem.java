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

    public double dash = 10;

    @Override
    public void init(){
        super.init();

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if(xGamepad1.a.isDown()){
            if(dash >= 1){
                power = 1;
            } else {
                power = 0.5;
            }
            dash = Math.max(dash-0.1, 0);
        } else {
            power = 0.5;
            dash = Math.min(dash+0.2, 10);
        }

        if(xGamepad1.x.isDown() && xGamepad1.b.wasPressed()){
            resetOrientation();
        }
    }

    @Override
    public void loop() {
        super.loop();

        StringBuilder display = new StringBuilder();
        for(int i = 1; i < 11; i++){
            if(i <= dash){
                display.append("■");
            } else {
                display.append("□");
            }
        }

        opMode.telemetry.addData("Boost", display.toString());
        opMode.telemetry.update();
    }
}