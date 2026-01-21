package robotx.modules.opmode;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XOdometry;
import robotx.stx_libraries.util.Vector;

enum StartPosition{
    REDCorner, REDBack, BLUECorner, BLUEBack

}

public class PositionTracker extends XModule {

    XOdometry odometry;

    Vector vecA;
    Vector vecB;
    Vector vecC;

    Vector[] vectorA;
    Vector[] vectorB;
    Vector[] vectorC;


    public PositionTracker(OpMode op){
        super(op);
        odometry = new XOdometry(op);
    }

    @Override
    public void init(){
        odometry.init();
    }

    public boolean checkZone1(){

        return checkInTriangle(new Vector(odometry.getX(), odometry.getY()), vecA, vecB, vecC);
    }

    public boolean checkZone2(){

        return false;
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
}
