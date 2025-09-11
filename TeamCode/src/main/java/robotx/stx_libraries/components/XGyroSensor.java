package robotx.stx_libraries.components;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class XGyroSensor {

    public enum ImuModel {
        BHI260,
        BNO055,
        IMU
    }

    final String path;
    final ImuModel model;
    final OpMode op;

    BHI260IMU bhi;
    BNO055IMU bno;
    IMU imu;

    public XGyroSensor(OpMode op, ImuModel model){
        path = "imu";
        this.op = op;
        this.model = model;
    }

    public XGyroSensor(OpMode op, ImuModel model, String path){
        this.path = path;
        this.op = op;
        this.model = model;
    }

    public void init(){
        imu = op.hardwareMap.get(IMU.class, path);

        RevHubOrientationOnRobot.LogoFacingDirection logo = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usb  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        IMU.Parameters params = new IMU.Parameters(
                new RevHubOrientationOnRobot(logo, usb)
        );
        imu.initialize(params);

        switch(model) {
            case BHI260:
                bhi = op.hardwareMap.get(BHI260IMU.class, path);
                bhi.initialize();
                break;
            case BNO055:
                bno = op.hardwareMap.get(BNO055IMU.class, path);

                BNO055IMU.Parameters bnoParams = new BNO055IMU.Parameters();

                bno.initialize(bnoParams);
                break;
        }
    }

    public Orientation getOrientation() {
        switch(model) {
            case BHI260:
                return bhi.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            case BNO055:
                return bno.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            case IMU:
                return imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        }
        return null;
    }

    public double getYaw(){
        switch(model) {
            case BHI260:
                return bhi.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
            case BNO055:
                return bno.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
            case IMU:
                return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        }
        return 0;
    }
}
