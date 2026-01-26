package robotx.stx_libraries.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;


public class XOdometry {

    private GoBildaPinpointDriver device;
    //Offset from center of robot
    final double xOffset = 38.0;
    final double yOffset = 138.0;

    OpMode op;

    Pose2D robotPosition;

    public XOdometry(OpMode op){
        this.op = op;
    }

    public void init(){
        device = op.hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");

        device.setOffsets(xOffset, yOffset, DistanceUnit.MM);

        device.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        device.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);

        device.resetPosAndIMU();

    }

    public void getData(){
        update();
        //op.telemetry.addData("X:", getX());
        //op.telemetry.addData("Y:", getY());
    }

    public void update(){
        device.update();
    }

    public double getX() {
        return device.getPosition().getX(DistanceUnit.INCH);
    }

    public double getY() {
        return device.getPosition().getY(DistanceUnit.INCH);
    }

    public double getHeading() {
        return device.getPosition().getHeading(AngleUnit.DEGREES);
    }

    public Pose2D getPose() {
        return device.getPosition();
    }

    public void reset() {
        device.resetPosAndIMU();
    }

    public void loop(){
        getData();
    }

    public GoBildaPinpointDriver getDevice(){
        return device;
    }




}






