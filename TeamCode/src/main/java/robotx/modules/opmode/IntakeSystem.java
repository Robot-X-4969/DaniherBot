package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.stx_libraries.XModule;


public class IntakeSystem extends XModule {

    DcMotor intakeMotor;

    public IntakeSystem(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeMotor");


    }

    @Override
    public void control_loop() {

        super.control_loop();

        if(!dualPlayer){
            if (xGamepad1.a.isDown()) {
                intakeMotor.setPower(1.0);
            } else {
                intakeMotor.setPower(0.0);
            }
        }
        else{
            if (xGamepad2.a.isDown()) {
                intakeMotor.setPower(1.0);
            } else {
                intakeMotor.setPower(0.0);
            }
        }


    }

}
