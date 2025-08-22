package robotx.stx_libraries.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.stx_libraries.XModule;

/**
 * TankDrive Class
 * <p>
 * Custom class by FTC Team 4969 RobotX for basic two wheel drive.
 * <p>
 * Created by John Daniher on 2/5/2025.
 */
public class TankDrive extends XModule {
    /**
     * Two wheel drive stencil module.
     *
     * @param op The opMode the TankOrientationDrive Module will be in.
     */
    public TankDrive(OpMode op) {
        super(op);
        motorsPerSide = 1;
        leftMotors = new DcMotor[1];
        rightMotors = new DcMotor[1];
    }

    /**
     * Two wheel drive stencil module with a given amount of motors per side.
     *
     * @param op            The opMode the TankOrientationDrive Module will be in.
     * @param motorsPerSide The amount of motors per side of the drive train.
     */
    public TankDrive(OpMode op, int motorsPerSide) {
        super(op);
        this.motorsPerSide = motorsPerSide;
        leftMotors = new DcMotor[motorsPerSide];
        rightMotors = new DcMotor[motorsPerSide];
    }


    /// The number of motors per side of the robot's drive train.
    final int motorsPerSide;

    /// An array of all motors on the left side.
    public final DcMotor[] leftMotors;

    /// An array of all motors on the right side.
    public final DcMotor[] rightMotors;

    private double y;
    private double r;

    /**
     * Toggle on whether or not slow mode is active.
     * <p>
     * If active, power = 0.5.
     */
    public boolean slowMode = false;
    /**
     * Toggle on whether or not super slow mode is active.
     * <p>
     * If active, power = 0.2.
     */
    public boolean superSlowMode = false;

    /// The current percent power of the motors, ranging -1 to 1.
    public double power = 0.75;

    /// Initialization function. This method, by default, initializes the motors and gyroSensor.
    @Override
    public void init() {
        if (motorsPerSide > 1) {
            for (int i = 0; i < leftMotors.length; i++) {
                leftMotors[i] = opMode.hardwareMap.dcMotor.get("left" + i);
            }
            for (int i = 0; i < rightMotors.length; i++) {
                rightMotors[i] = opMode.hardwareMap.dcMotor.get("rightMotor" + i);
            }
        } else {
            leftMotors[0] = opMode.hardwareMap.dcMotor.get("left");
            rightMotors[0] = opMode.hardwareMap.dcMotor.get("rightMotor");
        }
    }

    /**
     * Toggles slow mode.
     * <p>
     * When slow mode is active, power = 0.5.
     */
    public void toggleSlow() {
        slowMode = !slowMode;
        superSlowMode = false;
    }

    /**
     * Toggles super slow mode.
     * <p>
     * When super slow mode is active, power = 0.2;
     */
    public void toggleSuperSlow() {
        slowMode = false;
        superSlowMode = !superSlowMode;
    }

    /// Refreshes the variables tracking the joystick movements
    public void refreshSticks() {
        y = xGamepad1.left_stick_y;
        r = xGamepad1.right_stick_x;
    }

    /**
     * Calculates and sets the power of each motor.
     *
     * @param power The percent power, ranging -1 to 1, to power each motor to.
     */
    public void powerMotors(double power) {
        final double s = Math.pow(Math.max(Math.abs(y), Math.abs(r)), 2) / ((y * y) + (r * r));

        final double lPow = (y + r) * (s) * power;
        final double rPow = (y - r) * (s) * power;

        for (DcMotor motor : leftMotors) {
            motor.setPower(lPow);
        }
        for (DcMotor motor : rightMotors) {
            motor.setPower(rPow);
        }
    }

    /**
     * Sets the robot to drive at a given power.
     *
     * @param power The power to drive the robot at; +: forwards
     */
    public void drive(double power) {
        y = power;
        powerMotors(1);
    }

    /**
     * Sets the robot to rotate at a given power.
     *
     * @param power The power to rotate the robot at; +: clockwise
     */
    public void rotate(double power) {
        r = power;
        powerMotors(1);
    }

    /// Stops all motors of the drive train.
    public void stopMotors(){
        y = 0;
        r = 0;
        powerMotors(1);
    }

    /// Control loop function. By default, this reads joystick values.
    @Override
    public void control_loop() {
        super.control_loop();
        refreshSticks();
    }

    /**
     * Loop method which runs while opMode is active.
     * <p>
     * Runs getHeadingAngle(), calculates and powers motors.
     */
    @Override
    public void loop() {
        super.loop();
        if (power > 1) {
            power = 1;
        }
        if (power < 0.25) {
            power = 0.25;
        }

        // Temporary power variable
        double pow = power;

        if (superSlowMode) {
            pow = 0.2;
        } else if (slowMode) {
            pow = 0.5;
        }

        powerMotors(pow);
    }
}