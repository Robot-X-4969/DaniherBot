package robotx.stx_libraries.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

public class XCRServo {

    private final OpMode op;
    private final String servoPath;

    double power;

    private CRServoImplEx crServo;

    public XCRServo(OpMode op, String servoPath){
        this.op = op;
        this.servoPath = servoPath;
    }

    public void init(){

        crServo = op.hardwareMap.get(CRServoImplEx.class, servoPath);

    }

    public void rotate(double power){

        crServo.setPower(power);

    }


    public void stop(){

        crServo.setPower(0.0);

    }





}
