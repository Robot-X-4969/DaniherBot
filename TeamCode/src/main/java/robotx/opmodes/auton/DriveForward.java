package robotx.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robotx.modules.opmode.DriveSystem;
import robotx.stx_libraries.XAuton;

@Autonomous(name = "Drive", group = "Drive")
public class DriveForward extends XAuton {
    DriveSystem driveSystem;

    @Override
    public void initModules() {
        super.initModules();

        driveSystem = new DriveSystem(this);
        driveSystem.init();
    }

    @Override
    public void run(){
        driveSystem.drive(-0.5);
        sleep(1000);
        stop();
    }
}