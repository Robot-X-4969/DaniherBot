package robotx.opmodes.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import robotx.modules.opmode.CameraSystem;
import robotx.modules.opmode.DriveSystem;
import robotx.modules.opmode.FlyWheel;
import robotx.modules.opmode.IntakeSystem;
import robotx.modules.opmode.PositionTracker;
import robotx.modules.opmode.Spindexer;
import robotx.stx_libraries.XAuton;

enum StateMachine{
    LOCATE, COLLECT, MOVE, SHOOT
}
@Autonomous(name = "Auton2026", group = "Auton")
public class Auton2026 extends XAuton {

    StateMachine currentState = StateMachine.SHOOT;

    ElapsedTime stateTimer = new ElapsedTime();

    boolean moveToNextState = false;

    int shotsFired = 0;

    int ballsCollected = 0;
    int row = 1;
    DriveSystem drive = new DriveSystem(this);
    FlyWheel flyWheel = new FlyWheel(this);
    Spindexer spindexer = new Spindexer(this);
    IntakeSystem intake = new IntakeSystem(this);
    CameraSystem camera = new CameraSystem(this, drive);
    PositionTracker positionTracker = new PositionTracker(this);
    @Override
    public void initModules() {
        super.initModules();

        drive.init();
        flyWheel.init();
        spindexer.init();
        intake.init();
        camera.init();
        positionTracker.init();
    }

    @Override
    public void run(){
        switch(currentState) {
            case LOCATE:
                double targetX = -60 + 24 * row;
                double distance = targetX - positionTracker.getX();
                double kP = 0.03;
               if (Math.abs(distance) > 1.5) {
                    double powerX = kP * distance;
                    drive.drive(powerX);
               } else {
                   drive.drive(0);
               }

               currentState = StateMachine.COLLECT;

                break;
            case COLLECT:

                double targetY = 0.0;
                intake.loop();


                if(ballsCollected < 3){


                }

                intake.stop();
                currentState = StateMachine.MOVE;

                break;
            case MOVE:
                double distanceX = -63.0 - positionTracker.getX();
                double distanceY = 24.0 - positionTracker.getY();
                double k = 0.025;
                if (Math.abs(distanceY) > 1.5) {
                    double powerY = k * distanceY;
                    drive.drive(powerY);
                } else if (Math.abs(distanceX) > 1.5) {
                    double powerX = k * distanceX;
                    drive.drive(powerX);
                } else {
                    drive.drive(0);
                    currentState = StateMachine.SHOOT;
                }
                break;
            case SHOOT:
                camera.setPipeline(2);
                if (!moveToNextState) {

                    camera.autoAlign();
                    moveToNextState = true;
                }

                if (camera.isAligning()) {

                    camera.loop();
                    stateTimer.reset();
                    return;
                }

                double time = stateTimer.seconds();

                if (shotsFired < 3) {
                    if (time < 0.2) {
                        spindexer.autoShoot();
                    } else if (time < 0.6) {
                        spindexer.autoIncrement();
                    } else{
                        stateTimer.reset();
                        shotsFired++;
                    }
                } else {
                    shotsFired = 0;
                    moveToNextState = false;
                    currentState = StateMachine.LOCATE;
                }
                break;

        }
    }


}