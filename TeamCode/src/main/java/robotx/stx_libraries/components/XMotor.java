package robotx.stx_libraries.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import robotx.stx_libraries.util.Scheduler;
import robotx.stx_libraries.util.Stopwatch;


/**
 * XMotor Class
 * <p>
 * Custom class by FTC Team 4969 RobotX to better implement encoder motors.
 * <p>
 * Created by John Daniher 1/09/25
 */
public class XMotor {
    private final OpMode op;
    private final String motorPath;

    private DcMotorEx motor;
    private double power = 0;

    private XMotor following;

    private boolean fixed = false;
    private int targetPosition = 0;
    private int position = 0;
    public double currentRPM = 0;

    private final Stopwatch stopWatch = new Stopwatch();

    private boolean brakes = true;
    private boolean reverse = false;
    private boolean safe = false;
    private int holdRange = 0;
    private double safeRPM = 100;

    private double TICKS_PER_REVOLUTION = 1500;
    private double rpm = 0;

    private double rpmCoef = 0;
    private boolean rpmCoefSet = false;
    private final Stopwatch rpmStopwatch = new Stopwatch(100);
    private int lastPos = 0;

    private int min;
    private int max;

    private boolean estimateRPM = false;
    double P;
    double I = 0;
    double D;
    double lastError;
    double lastTime;

    double maxVelocity;


    /**
     * XMotor Constructor
     * <p>
     * Implements DcMotor with encoders more efficiently.
     *
     * @param op        The OpMode in which the motor runs.
     * @param motorPath The name the motor is configured to through the RevHub.
     */
    public XMotor(OpMode op, String motorPath) {
        this.op = op;
        this.motorPath = motorPath;
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
    }

    /**
     * XMotor Constructor
     * <p>
     * Implements DcMotor with encoders monitoring rpm more efficiently.
     * <p>
     * See manufacturer data for values.
     *
     * @param op        The OpMode in which the motor runs.
     * @param motorPath The name the motor is configured to through the RevHub.
     * @param TICKS_PER_REVOLUTION       The preset encoder ticks per revolution of the motor.
     */
    public XMotor(OpMode op, String motorPath, double TICKS_PER_REVOLUTION) {
        this.op = op;
        this.motorPath = motorPath;
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
        this.TICKS_PER_REVOLUTION = TICKS_PER_REVOLUTION;
        safe = true;
    }

    /**
     * XMotor Constructor
     * <p>
     * Implements DcMotor with encoders with set range more efficiently.
     *
     * @param op        The OpMode in which the motor runs.
     * @param motorPath The name the motor is configured to through the RevHub.
     * @param min       The minimum encoder position the motor may run to.
     * @param max       The maximum encoder position the motor may run to.
     */
    public XMotor(OpMode op, String motorPath, int max, int min) {
        this.op = op;
        this.motorPath = motorPath;
        this.max = max;
        this.min = min;
    }

    /**
     * Initializes the motor by mapping and resetting the motor and encoders.
     */
    public void init() {
        motor = op.hardwareMap.get(DcMotorEx.class, motorPath);
        reset();

        lastTime = System.currentTimeMillis() / 1000.0;
        lastError = 0;
    }

    /**
     * Returmns teh current power of the motor.
     *
     * @return The current power of the motor.
     */
    public double getPower() {
        return motor.getPower();
    }

    /**
     * Returmns teh current power of the motor.
     *
     * @return The current power of the motor.
     */
    public int getPosition() {
        position = motor.getCurrentPosition();
        return motor.getCurrentPosition();
    }

    /**
     * Sets the power of the motor without changing the mode.
     *
     * @param power The new power for the motor to run at.
     */
    public void setPower(double power) {
        if (power > 1) {
            power = 1;
        } else if (power < -1) {
            power = -1;
        }
        this.power = power;
        rpmStopwatch.startTimer(100);
        motor.setPower(power);
    }

    /**
     * Stops the motor; sets power to 0, stops timer, and changes mode to indefinite
     */
    public void stop() {
        power = 0;
        rpmStopwatch.clearTimer();
        setIndefiniteRotation(0);
    }

    /**
     * Toggles the motor's brake system.
     */
    public void toggleBrakes() {
        brakes = !brakes;
        if (brakes) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    /**
     * Sets the motor's brake system.
     *
     * @param brakes State to set the brake system to.
     */
    public void setBrakes(boolean brakes) {
        this.brakes = brakes;
        if (brakes) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    /**
     * Toggle's the motor's direction.
     */
    public void toggleDirection() {
        reverse = !reverse;
        if (reverse) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    /**
     * Sets the motor's direction.
     *
     * @param direction Direction to set the motor to; true: forwards, false: reverse
     */
    public void setDirection(boolean direction) {
        reverse = !direction;
        if (reverse) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    /**
     * Toggles the motor's safety system.
     * <p>
     * When a motor is "safe", it will power off when it faces severe resistance. Each motor is safe by default.
     */
    public void toggleSafe() {
        safe = !safe;
    }

    /**
     * Sets the motor's safety system.
     * <p>
     * When a motor is "safe", it will power off when it faces severe resistance. Each motor is safe by default.
     *
     * @param safe State to set the safety system to.
     */
    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    /**
     * Sets the encoder ticks per revolution of the motor.
     * <p>
     * See manufacturer data for values.
     *
     * @param TICKS_PER_REVOLUTION The encoder ticker per revolution of the motor.
     */
    public void setTICKS_PER_REVOLUTION(long TICKS_PER_REVOLUTION) {
        this.TICKS_PER_REVOLUTION = TICKS_PER_REVOLUTION;
    }

    /**
     * Sets the coefficient used to rotate a motor at a given rpm and disables automatic tuning.
     *
     * @param rpmCoef The motor's RPM coefficient.
     */
    public void setRPMCoefficient(double rpmCoef) {
        rpmCoefSet = true;
        this.rpmCoef = rpmCoef;
    }

    /**
     * Sets the threshold rpm before the safety system cuts the motor off.
     *
     * @param rpm The revolutions per minute to set the safety rpm to.
     */
    public void setSafeRPM(double rpm) {
        safeRPM = rpm;
    }

    /**
     * Sets the motor to a given RPM.
     * <p>
     * Note: requires that the TICKS_PER_REVOLUTION coefficient be tuned or preset.
     *
     * @param rpm The revolutions per minute to set the motor to.
     */
    public void setRPM(double rpm) {
        this.rpm = rpm;
        if(estimateRPM){
            setPower(rpm * rpmCoef);
        } else {
            motor.setVelocity(rpm / 60, AngleUnit.DEGREES);
        }
    }

    /**
     * Estimates the current RPM of the motor and adjusts the coefficient accordingly.
     * @return Returns the current RPM of the motor.
     */
    public double calculateRPM(){
        final int tickDiff = Math.abs(position - lastPos);
        final double r = ((double) tickDiff) / TICKS_PER_REVOLUTION;
        final long timeDiff = rpmStopwatch.elapsedMillis();
        currentRPM = r / ((double) timeDiff) * 1000 * 60;

        if (safe) {
            if (currentRPM < safeRPM * Math.abs(power)) {
                stop();
                return currentRPM;
            }
        }
        if (!rpmCoefSet && power != 0) {
            rpmCoef = Math.abs(power / currentRPM);
        }

        if(rpm != 0 && estimateRPM){
            setPower(rpm * rpmCoef);
        }
        return currentRPM;
    }

    /**
     * Toggles the use of manual RPM estimation vs. REV supported RPM management.
     */
    public void toggleRPMEstimation() {
        estimateRPM = !estimateRPM;
    }

    /**
     * Sets the use of manual RPM estimation vs. REV supported RPM management.
     *
     * @param value The value to set RPM estimation to.
     */
    public void setRPMEstimation(boolean value) {
        estimateRPM = value;
    }

    /**
     * Sets the motor to rotate indefinitely.
     */
    public void setIndefiniteRotation() {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cancelTimer();
        fixed = false;
    }

    /**
     * Sets the motor to rotate indefinitely at a given power.
     *
     * @param power Power to set the motor to.
     */
    public void setIndefiniteRotation(double power) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cancelTimer();
        fixed = false;

        setPower(power);
    }

    /**
     * Sets the motor to rotate for a given duration
     *
     * @param milliseconds Duration, in milliseconds, for the motor to rotate.
     */
    public void setTimedRotation(int milliseconds) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        stopWatch.startTimer(milliseconds);
        fixed = false;
    }

    /**
     * Sets the motor to rotate for a given duration at a given power.
     *
     * @param milliseconds Duration, in milliseconds, for the motor to rotate.
     * @param power        Power to set the motor to.
     */
    public void setTimedRotation(int milliseconds, double power) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        stopWatch.startTimer(milliseconds);
        fixed = false;

        setPower(power);
    }

    /**
     * Sets the motor to rotate for a given duration through a given scheduler.\
     * <p>
     * Event is scheduled under ID "{motorPath}Stop".
     *
     * @param milliseconds Duration, in milliseconds, for the motor to rotate.
     * @param scheduler    The Scheduler object to schedule the motor's stop through.
     */
    public void setTimedRotation(Scheduler scheduler, int milliseconds) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        scheduler.schedule(milliseconds, motorPath + "Stop", this::stop);
    }

    /**
     * Sets the motor to rotate for a given duration at a given power through a given scheduler.\
     * <p>
     * Event is scheduled under ID "{motorPath}Stop".
     *
     * @param milliseconds Duration, in milliseconds, for the motor to rotate.
     * @param power        The power to set the motor to.
     * @param scheduler    The Scheduler object to schedule the motor's stop through.
     */
    public void setTimedRotation(Scheduler scheduler, int milliseconds, double power) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(power);
        scheduler.schedule(milliseconds, motorPath + "Stop", this::stop);
    }

    /**
     * Cancels any scheduled stop of the motor.
     */
    public void cancelTimer() {
        stopWatch.clearTimer();
    }

    /**
     * Cancels any scheduled stop of the motor in a given Scheduler.
     *
     * @param scheduler The Scheduler to cancel the timer within.
     */
    public void cancelTimer(Scheduler scheduler) {
        scheduler.cancel(motorPath + "Stop");
    }

    /**
     * Resets the motor's encoders.
     */
    public void reset() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        position = 0;
        lastPos = 0;
    }

    /**
     * Sets the motor to rotate to a given position.
     *
     * @param targetPosition The target position of the motor.
     */
    public void setFixedRotation(int targetPosition) {
        this.targetPosition = targetPosition;
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fixed = true;
    }

    /**
     * Sets the motor to rotate to a given position at a given power.
     *
     * @param targetPosition The target position of the motor.
     * @param power          Power to set the motor to.
     */
    public void setFixedRotation(int targetPosition, double power) {
        this.targetPosition = targetPosition;
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fixed = true;
        setPower(power);
    }

    /**
     * Sets the motor to rotate to a given encoder position and remain there.
     *
     * @param targetPosition The target position of the motor
     */
    public void setPosition(int targetPosition) {
        this.targetPosition = targetPosition;
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets the motor to rotate at a given power to a given encoder position and remain there.
     *
     * @param targetPosition The target position of the motor
     * @param power          Power to set the motor to.
     */
    public void setPosition(int targetPosition, double power) {
        this.targetPosition = targetPosition;
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);
    }

    /**
     * Holds the motor at target position, acting as a servo.
     *
     * @param hold Amount of encoder ticks to hold within.
     * @param power Power of movements.
     */
    public void hold(int hold, double power) {
        getPosition();
        final int error = targetPosition - position;

        if (Math.abs(error) <= hold) {
            motor.setPower(0);
            return;
        }

        final double out = Math.min(power, Math.max(-power, 0.006 * error));
        setPower(out);
    }

    /**
     * Sets the motor's servo functionality.
     *
     * @param hold Value to set the motor's servo functionality to.
     */
    public void setHold(int hold){
        holdRange = hold;
    }


    /**
     * Checks if the motor has reached its target encoder position.
     *
     * @return True if with 1 degree of target position; false if else
     */
    public boolean atTarget() {
        refreshPosition(false);
        return Math.abs(targetPosition - position) < TICKS_PER_REVOLUTION / 360;
    }

    /**
     * Holds thread until motor reaches target encoder position.
     *
     * @throws InterruptedException If Thread or loop fails.
     */
    public void awaitTarget() throws InterruptedException {
        while (!atTarget()) {
            Thread.sleep(25);
        }
    }

    /**
     * Holds thread until motor reaches target encoder position or until given duration elapses.
     *
     * @param failTime The time, in milliseconds, until it stops holding the thread.
     * @throws InterruptedException If Thread or loop fails.
     */
    public void awaitTarget(int failTime) throws InterruptedException {
        final Stopwatch stopwatch = new Stopwatch(failTime);
        while (!atTarget() && !stopwatch.timerDone()) {
            Thread.sleep(25);
        }
    }

    /**
     * Rotates the motor by a given amount of encoder ticks.
     *
     * @param incrementAmount Amount of encoder ticks to rotate by.
     */
    public void increment(int incrementAmount) {
        refreshPosition(false);
        setFixedRotation(targetPosition + incrementAmount);
    }

    /**
     * Rotates the motor by a given amount of encoder ticks at a given power.
     *
     * @param incrementAmount Amount of encoder ticks to rotate by.
     * @param power           The power to set the motor to.
     */
    public void increment(int incrementAmount, double power) {
        refreshPosition(false);
        setFixedRotation(targetPosition + incrementAmount, power);
    }

    /**
     * Sets the motor's position a certain amount of ticks forward.
     *
     * @param incrementAmount Amount of encoder ticks to rotate by.
     */
    public void incrementPosition(int incrementAmount) {
        refreshPosition(false);
        setPosition(position + incrementAmount);
    }

    /**
     * Sets the motor's position a certain amount of ticks forward at a given power.
     *
     * @param incrementAmount Amount of encoder ticks to rotate by.
     * @param power           The power to set the motor to.
     */
    public void incrementPosition(int incrementAmount, double power) {
        refreshPosition(false);
        setPosition(position + incrementAmount, power);
    }

    /**
     * Sets the minimum position of the motor.
     *
     * @param min The minimum encoder position of the motor.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum position of the motor.
     *
     * @param max The maximum encoder position of the motor.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Sets the minimum and maximum position of the motor.
     *
     * @param min The minimum encoder position of the motor.
     * @param max The maximum encoder position of the motor.
     */
    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the motor to follow the position of another motor.
     *
     * @param motor Motor to follow.
     */
    public void follow(XMotor motor) {
        this.following = motor;
        setFixedRotation(motor.getPosition(), motor.getPower());
    }

    /**
     * Stops following any set motors.
     */
    public void unfollow() {
        this.following = null;
    }

    /**
     * Refreshes the motor's encoder position value.
     *
     * @param milestone Whether or not this is a milestone, where the lastPosition is updated.
     */
    public void refreshPosition(boolean milestone) {
        if (milestone) {
            lastPos = position;
            rpmStopwatch.reset();
        }
        position = motor.getCurrentPosition();
    }

    /**
     * The loop method of the motor;
     * Refreshes the motor's encoder variable.
     * Ensures motor within given encoder range.
     * Stops motors when scheduled to do so.
     * Tunes the rpm coefficient.
     */
    public void loop() {
        /*
        refreshPosition(false);

        if(holdRange != 0){
            hold(holdRange, 0.5);
        }

        if (following != null) {
            final int followingPosition = following.getPosition();
            double newPower = following.getPower();

            if (newPower == 0 && Math.abs(position - followingPosition) > 10) {
                newPower = 0.25;
            }
            setFixedRotation(followingPosition, newPower);
        }

        //Ensures encoder within range
        if (position < min) {
            setFixedRotation(min);
        } else if (position > max) {
            setFixedRotation(max);
        }

        //If timer has elapsed, stop motor
        if (stopWatch.timerDone()) {
            stop();
            return;
        }

        //If motor isn't moving
        if (fixed && !motor.isBusy()) {
            stop();
            return;
        }

        // Calculates RPM
        if (rpmStopwatch.timerDone()) {
            calculateRPM();
            refreshPosition(true);
        }
        */

    }

    /*
    public void setRPM_PID(double target, int index){

        final double TICKS_PER_REVOLUTION = 103.8;
        final double maxRPM = 1620.0;

        double F = 0;

        double kP = 0.0;
        double kI = 0.0;
        double kD = 0.0;


        if(index == 1 || index == 2 || index == 0){
            kP = 0.055;
            kI = 0.001;
            kD = 0.002;
        }else{
            kP = 0.015;
            kI = 0.0003;
            kD = 0.01;
        }


        final double kF = 1.0 / maxRPM;


        op.telemetry.addData("Velocity", motor.getVelocity());
        double currentRPM = Math.abs(motor.getVelocity() * 60.0) / TICKS_PER_REVOLUTION;
        op.telemetry.addData("currentRPM", currentRPM);

        double error = target - currentRPM;

        //free forward: if target is 1000, then the F value is 0.617

        F = target * kF;



        //proportional
        P = kP * error;

        //integral
        //make sure to reset integral when changing the target speed, maybe clamp I

        I += kI * error * 0.04;

        I = Math.min(0.10, I);


        //derivative
        D = kD * ((error - lastError) / 0.04);

        lastError = error;

        op.telemetry.addData("P:", P);
        op.telemetry.addData("I:", I);
        op.telemetry.addData("D:", D);
        op.telemetry.addData("F:", F);
        op.telemetry.addData("Sum", P + I + D + F);
        op.telemetry.addData("error", error);


        double power = P + I + D + F;

        setPower(power);

    }

    public void reset_I(){
        I = 0.0;
    }
    */


    public void setMotorRPM(double targetRPM){
        double targetTPS = (targetRPM * TICKS_PER_REVOLUTION) / 60.0;

        this.motor.setVelocity(targetTPS);

        op.telemetry.addData("Target RPM", targetRPM);
        op.telemetry.addData("Current Data", getCurrentRPM(getCurrentVelocity()));

    }

    public void setPIDFValues(double kP, double kI, double kD, double kF){
        this.motor.setVelocityPIDFCoefficients(
                kP,
                kI,
                kD,
                kF
        );
    }

    public void setMotorMode(Boolean useEncoder){
        if(useEncoder){
            this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void resetEncoder(){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double getMaxVelocity(){
        return this.maxVelocity;
    }

    public double getCurrentVelocity(){
        return this.motor.getVelocity();
    }

    public void setMaxVelocity(double velocity){
        this.maxVelocity = velocity;
    }

    public double getCurrentRPM(double velocity){
        return (velocity * 60) / TICKS_PER_REVOLUTION;
    }

    public double calculateVelocity(double RPM){
        return RPM * TICKS_PER_REVOLUTION / 60;
    }
}