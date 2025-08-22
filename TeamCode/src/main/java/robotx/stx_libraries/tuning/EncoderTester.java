package robotx.stx_libraries.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.XMotor;

/**
 * EncoderTester Class
 * <p>
 * Custom class by FTC Team 4969 RobotX for testing motor encoder positions.
 * <p>
 * Created by John Daniher on 2/6/2025.
 */
public class EncoderTester extends XModule {
    /// Array of motor config paths
    private final String[] motorPaths;

    /// Array of testing motors
    public final XMotor[] motors;
    /// Current index of testing motors
    public int index = 0;

    /// Unit to increment motor encoder positions by.
    public int unit = 10;

    /**
     * Basic EncoderTester Module.
     *
     * @param op The OpMode the EncoderTester Module is created in.
     * @param motorPaths The array of motor config paths.
     */
    public EncoderTester(OpMode op, String[] motorPaths) {
        super(op);
        this.motorPaths = motorPaths;
        motors = new XMotor[motorPaths.length];
        for(int i = 0; i < motorPaths.length; i++){
            motors[i] = new XMotor(op, motorPaths[i]);
        }
    }
    /**
     * Method which runs on bot initialization.
     * <p>
     * Initializes all motors provided through motorPaths."
     */
    @Override
    public void init(){
        for(XMotor motor : motors){
            motor.init();
            loopMotors.add(motor);
        }
    }

    /**
     * Method which runs while opMode running.
     * <p>
     * Updates telemetry display for controls, motor index, and position.
     */
    @Override
    public void loop(){
        opMode.telemetry.addData("Controls", "\nD-Pad Left: Unit / 10\nD-Pad Right: Unit * 10\nD-Pad Up: Position+\nD-Pad Down: Position-\nA: Cycle forwards through motors\nB: Cycle backwards through motors");
        opMode.telemetry.addData("Testing Motor: ", motorPaths[index]);
        opMode.telemetry.addData("Motor Position: ", motors[index].getPosition());
        super.loop();
    }

    /**
     * Method which checks controller inputs.
     * <p>
     * Checks bumpers for unit changes, dpad for position changes, and a/b for motor swapping.
     */
    @Override
    public void control_loop(){
        if(xGamepad1.left_bumper.wasPressed()){
            unit /= 10;
            if(unit < 1){
                unit = 1;
            }
        }
        if(xGamepad1.right_bumper.wasPressed()){
            unit *= 10;
            if(unit > 1000){
                unit = 1000;
            }
        }
        if(xGamepad1.dpad_down.wasPressed()){
            motors[index].increment(-unit);
        }
        if(xGamepad1.dpad_up.wasPressed()){
            motors[index].increment(unit);
        }
        if(xGamepad1.a.wasPressed()){
            index++;
            if(index >= motors.length){
                index = 0;
            }
        }
        if(xGamepad1.b.wasPressed()){
            index--;
            if(index < 0){
                index = motors.length-1;
            }
        }
    }
}
