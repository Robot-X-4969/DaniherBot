package robotx.modules.opmode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XOdometry;
import robotx.stx_libraries.util.Vector;

enum StartPosition{
    REDCorner, REDBack, BLUECorner, BLUEBack

}

public class PositionTracker extends XModule {

    XOdometry odometry;

    StartPosition startPosition = StartPosition.BLUEBack;

    //large triangle
    Vector vecA = new Vector(72.0, 72.0);
    Vector vecB = new Vector(0.0, 0.0);
    Vector vecC = new Vector(72.0, -72.0);

    //small triangle
    Vector vecD = new Vector(-72.0, -24.0);
    Vector vecE = new Vector(-48.0, 0.0);
    Vector vecF = new Vector(-72.0, 24.0);

    //goals
    Vector blueGoal = new Vector(60.0, -60.0);
    Vector redGoal = new Vector(60.0, 60.0);


    public PositionTracker(OpMode op){
        super(op);
        odometry = new XOdometry(op);
    }

    @Override
    public void init(){
        odometry.init();
        if(startPosition == StartPosition.BLUEBack) {
            odometry.getDevice().setPosition(new Pose2D(DistanceUnit.INCH, -63.0, -24.0, AngleUnit.DEGREES, 0.0));;
        } else if(startPosition == StartPosition.REDBack){
            odometry.getDevice().setPosition(new Pose2D(DistanceUnit.INCH, -63.0, 24.0, AngleUnit.DEGREES, 0.0));
        } else if(startPosition == StartPosition.BLUECorner){
            odometry.getDevice().setPosition(new Pose2D(DistanceUnit.INCH, 51.0, -51.0, AngleUnit.DEGREES, 180.0));
        } else if(startPosition == StartPosition.REDCorner){
            odometry.getDevice().setPosition(new Pose2D(DistanceUnit.INCH, 51.0, 51.0, AngleUnit.DEGREES, 180.0));
        }

    }

    public boolean checkZone1(){

        return checkInTriangle(new Vector(odometry.getX(), odometry.getY()), vecA, vecB, vecC);
    }

    public boolean checkZone2(){

        return checkInTriangle(new Vector(odometry.getX(), odometry.getY()), vecD, vecE, vecF);
    }

    public boolean checkInTriangle(Vector p, Vector a, Vector b, Vector c){

        Vector vecAB = Vector.subtractVectors(b, a);
        Vector vecBC = Vector.subtractVectors(c, b);
        Vector vecCA = Vector.subtractVectors(a, c);

        Vector vecAP = Vector.subtractVectors(p, a);
        Vector vecBP = Vector.subtractVectors(p, b);
        Vector vecCP = Vector.subtractVectors(p, c);

        double cross1 = Vector.crossProduct(vecAB, vecAP);
        double cross2 = Vector.crossProduct(vecBC, vecBP);
        double cross3 = Vector.crossProduct(vecCA, vecCP);

        return (cross1 >= 0 && cross2 >= 0 && cross3 >= 0) || (cross1 <= 0 && cross2 <= 0 && cross3 <= 0);

    }

    @Override
    public void loop(){
        super.loop();
        odometry.loop();
    }

    public double getX(){
        return odometry.getX();
    }

    public double getY(){
        return odometry.getY();
    }
}
