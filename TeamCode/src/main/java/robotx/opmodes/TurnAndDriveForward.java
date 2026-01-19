package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robotx.modules.opmode.DriveSystemSimple;
import robotx.stx_libraries.XAuton;

@Autonomous(name = "TurnAndDrive", group = "Drive")
public class TurnAndDriveForward extends XAuton {
    DriveSystemSimple driveSystem;

    @Override
    public void initModules() {
        super.initModules();

        driveSystem = new DriveSystemSimple(this);
        driveSystem.init();
    }

    @Override
    public void run(){
        driveSystem.rotate(1);
        sleep(500);
        driveSystem.stop();
        driveSystem.drive(-0.5);
        sleep(1000);
        stop();
    }
}