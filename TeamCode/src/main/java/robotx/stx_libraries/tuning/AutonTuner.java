package robotx.stx_libraries.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.drive.MecanumDrive;

/**
 * AutonTuner class
 * <p>
 * Custom class by FTC Team 4969 RobotX for tuning auton drive distances.
 * <p>
 * Created by John Daniher on 2/6/2025.
 */
public class AutonTuner extends MecanumDrive {
    /// The time, in milliseconds, being tested for the robot to drive the length of 1 tile.
    public int tileTime = 300;

    /// The time, in milliseconds, being tested for the robot to turn 360 degrees.
    public int turnTime = 300;

    /// Toggle for turning mode.
    public boolean turning = false;

    /// The unit at which the tileTime scales by on change.
    public int unit = 10;

    /**
     * Basic AutonTuner Module.
     *
     * @param op The OpMode the AutonTuner Module is created in.
     */
    public AutonTuner(OpMode op) {
        super(op);
    }

    /**
     * Method which runs while opMode is running.
     * <p>
     * Updates telemetry to display values.
     */
    @Override
    public void loop() {
        if (turning) {
            opMode.telemetry.addData("Controls", "\nB: Toggle Drive\nA: Turn\nD-Pad Left: Unit / 10\nD-Pad Right: Unit * 10\nD-Pad Up: TurnTime+\nD-Pad Down: TurnTime-\n");
        } else {
            opMode.telemetry.addData("Controls", "\nB: Toggle Turn\nA: Drive\nD-Pad Left: Unit / 10\nD-Pad Right: Unit * 10\nD-Pad Up: TileTime+\nD-Pad Down: TileTime-\n");
        }
        if (turning) {
            opMode.telemetry.addData("TurnTime: ", turnTime + " ms");
        } else {
            opMode.telemetry.addData("TileTime: ", tileTime + " ms");

        }
        opMode.telemetry.addData("Unit: ", unit + " ms");

        super.loop();
    }

    /**
     * Method which runs to check controller inputs.
     * <p>
     * Checks dpad for value changes, checks A button for run command.
     */
    @Override
    public void control_loop() {
        // Checks controls for tileTime changes.
        if (xDS.xGamepad1.dpad_up.wasPressed()) {
            if (turning) {
                turnTime += unit;
            } else {
                tileTime += unit;
            }
        }
        if (xDS.xGamepad1.dpad_down.wasPressed()) {
            if (turning) {
                turnTime -= unit;
                if (turnTime < 0) {
                    turnTime = 1;
                }
            } else {
                tileTime -= unit;
                if (tileTime < 0) {
                    tileTime = 1;
                }
            }
        }

        // Checks controls for unit changes.
        if (xDS.xGamepad1.dpad_right.wasPressed()) {
            unit *= 10;
            if (unit > 1000) {
                unit = 1000;
            }
        }
        if (xDS.xGamepad1.dpad_left.wasPressed()) {
            unit /= 10;
            if (unit < 1) {
                unit = 1;
            }
        }

        if (xDS.xGamepad1.b.wasPressed()) {
            turning = !turning;
        }

        // Check controls for run start.
        if (xDS.xGamepad1.a.wasPressed()) {
            if (turning) {
                rotate(1);
            } else {
                drive(1);
            }
            // Schedules stop in tileTime milliseconds
            scheduler.schedule(tileTime, this::stopMotors);
        }
    }
}
