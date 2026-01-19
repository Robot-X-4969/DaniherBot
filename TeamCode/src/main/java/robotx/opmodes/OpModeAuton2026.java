package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robotx.modules.opmode.DriveSystem;
import robotx.modules.opmode.FlyWheel;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.LimeLightModule;
import robotx.modules.opmode.Spindexer;
import robotx.stx_libraries.XAuton;

@Autonomous(name = "OpModeAuton2026", group = "Auton")
public class OpModeAuton2026 extends XAuton {
    LimeLightModule llm;
    DriveSystem drive;
    IntakeSystem intake;
    Spindexer spindexer;
    FlyWheel flyWheel;

    @Override
    public void initModules() {

        llm = new LimeLightModule(this);
        modules.add(llm);

        drive = new DriveSystem(this);
        modules.add(drive);

        flyWheel = new FlyWheel(this);
        modules.add(flyWheel);

        spindexer = new Spindexer(this);
        modules.add(spindexer);

        intake = new IntakeSystem(this);
        modules.add(intake);

    }

    @Override
    public void run(){

        int ballCombinationTag = ;
        for(XModule : modules){

        }






    }







}
