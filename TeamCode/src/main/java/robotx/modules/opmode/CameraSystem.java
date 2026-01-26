package robotx.modules.opmode;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XCamera;

enum team {
    BLUE, RED
}
public class CameraSystem extends XModule {

    XCamera camera;

    DriveSystem drive;

    boolean isAligning = false;

    boolean hasBeenDown = false;

    team color = team.RED;
    public CameraSystem(OpMode op, DriveSystem drive){
        super(op);
        this.drive = drive;

    }

    @Override
    public void init(){
        this.camera = new XCamera(opMode, "limelight");
        camera.init();
    }

    @Override
    public void loop(){
        camera.loop();

        if(isAligning){
            targetGoal();
        }

    }

    @Override
    public void control_loop(){
        super.control_loop();
        if (xDS.xGamepad1.a.isDown()) {
            if(!hasBeenDown){
                camera.changePipeline(2);
                hasBeenDown = true;
            }
            isAligning = true;

        }
        else if(!xDS.xGamepad1.a.isDown()){
            hasBeenDown = false;
            isAligning = false;
        }
    }

    public void targetGoal(){
        if(color == team.BLUE){
            if(camera.seesAprilTag(20)){
                int index = camera.getAprilTagIndex(20);
                double tx = camera.getAprilTagTX().get(index);

                double k = 0.05;
                //maybe make power proportional to how far away

                if(tx < -1){ // still not centered
                    drive.auto_rotate(Math.min(tx * k, -0.1));
                } else if (tx > 1){
                    drive.auto_rotate(Math.max(tx * k, 0.1));
                }
                else {
                    drive.auto_rotate(0); // stop rotating
                    isAligning = false;
                }

            }
            else{
                drive.auto_rotate(0.25);
            }
        } else if(color == team.RED){
            if(camera.seesAprilTag(24)){
                int index = camera.getAprilTagIndex(24);
                double tx = camera.getAprilTagTX().get(index);

                opMode.telemetry.addData("TX", tx);

                double k = 0.05;

                //maybe make power proportional to how far away
                if(tx < -1){ // still not centered
                    drive.auto_rotate(Math.min(tx * k, -0.1));
                } else if (tx > 1){
                    drive.auto_rotate(Math.max(tx * k, 0.1));
                }
                else {
                    drive.auto_rotate(0); // stop rotating
                    isAligning = false;
                }
            }
            else{
                drive.auto_rotate(0.25);
            }
        }

    }

    public void autoAlign(){
        isAligning = true;
    }

    public boolean isAligning(){
        return isAligning;
    }

    public void setPipeline(int index){
        camera.changePipeline(index);
    }




}
