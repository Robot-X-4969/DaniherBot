package robotx.stx_libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

import robotx.stx_libraries.components.XDriverStation;
import robotx.stx_libraries.util.Scheduler;

/**
 * XOpMode Class
 * <p>
 * Custom class by FTC Team 4969 RobotX to better implement XModules into OpModes.
 * <p>
 * Created by Nicholas on 11/3/16.
 */
public abstract class XOpMode extends OpMode {

    public final XRobotContext ctx = new XRobotContext(this);

    /**
     * List of XModules which runs throughout OpMode.
     */
    public final ArrayList<XModule> activeModules = ctx.activeModules;
    /**
     * Modules which run only in the initialization process (i.e. modules which do not call start(), loop(), or stop())
     */
    public final ArrayList<XModule> inactiveModules = ctx.inactiveModules;

    /**
     * XGamepadSystem object for OpMode which combines both gamepads and updates according to dualPLayer setting.
     */
    public final XDriverStation xDS = new XDriverStation();

    /**
     * Scheduling object used throughout OpMode.
     */
    public final Scheduler scheduler = new Scheduler();

    /**
     * Attaches modules to OpMode.
     */
    public void initModules() {
    }

    /**
     * Method called on OpMode initialization.
     * <p>
     * By default, this initializes and attaches schedule and xGamepads to all modules.
     */
    @Override
    public void init() {
        initModules();
        
        xDS.init(gamepad1, gamepad2);
        for (XModule module : ctx.activeModules) {
            module.init();
            module.scheduler = scheduler;
            module.xDS = xDS;
        }
        for (XModule module : ctx.inactiveModules) {
            module.init();
            module.scheduler = scheduler;
            module.xDS = xDS;
        }
    }

    /**
     * Method called while awaiting OpMode start.
     * <p>
     * By default, this calls the init_loop methods of all modules and updates the XGamepad objects.
     */
    @Override
    public void init_loop() {
        scheduler.loop();

        xDS.update();

        for (XModule module : ctx.activeModules) {
            module.init_loop();
        }
        for (XModule module : ctx.inactiveModules) {
            module.init_loop();
        }
    }

    /**
     * Method called on OpMode start.
     * <p>
     * By default, this calls the start methods of all active modules.
     */
    @Override
    public void start() {
        for (XModule module : ctx.activeModules) {
            module.start();
        }
    }

    /**
     * Method called while OpMode running.
     * <p>
     * By default, this calls the loop methods of all active modules and updates the XGamepad objects.
     */
    @Override
    public void loop() {
        scheduler.loop();
        xDS.update();

        for (XModule module : ctx.activeModules) {
            module.loop();
        }
    }

    /**
     * Method called on OpMode stop.
     * <p>
     * By default, this calls the stop methods of all active modules.
     */
    @Override
    public void stop() {
        for (XModule module : ctx.activeModules) {
            module.stop();
        }
    }

    public void registerModule(XModule module){
        ctx.registerModule(module, XRobotContext.ModuleType.ACTIVE);
    }


    public void registerModule(XModule module, XRobotContext.ModuleType type){
        ctx.registerModule(module, type);
    }
}