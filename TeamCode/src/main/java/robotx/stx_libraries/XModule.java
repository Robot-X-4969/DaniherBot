package robotx.stx_libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

import robotx.stx_libraries.components.XMotor;
import robotx.stx_libraries.components.XDriverStation;
import robotx.stx_libraries.util.Scheduler;

/**
 * XModule Class
 * <p>
 * Custom class by FTC Team 4969 RobotX for better access to opMode, XGamepad, and Scheduling instances.
 * <p>
 * Created by Nicholas on 11/3/16.
 */
public abstract class XModule {
    /// OpMode's instance.
    public final OpMode opMode;
    /// OpMode's scheduling object.
    public Scheduler scheduler;
    public XDriverStation xDS = new XDriverStation();

    /// List of module's motors to be called every loop.
    public final List<XMotor> loopMotors = new ArrayList<>();

    /**
     * XModule Constructor
     * <p>
     * Implements opMode, schedule, and xGamepad variables automatically into modules.
     *
     * @param op The OpMode's instance.
     */
    public XModule(OpMode op) {
        opMode = op;
    }

    /// Update method which refreshes xGamepad objects.
    public void update() {
        scheduler.loop();
        xDS.update();
    }

    /// Method called on OpMode initialization.
    public void init() {
        for(XMotor motor : loopMotors){
            motor.init();
        }
    }

    /// Method called while awaiting OpMode start.
    public void init_loop() {
        for (XMotor motor : loopMotors) {
            motor.loop();
        }
    }

    /// Method called on OpMode start.
    public void start() {
    }

    /// Method which handles inputs.
    public void control_loop() {
    }

    /**
     * Method called while OpMode running.
     * <p>
     * By default, this loops all loopMotors.
     */
    public void loop() {
        control_loop();
        for (XMotor motor : loopMotors) {
            motor.loop();
        }
    }


    /// Method called on OpMode stop.
    public void stop() {
    }
}
