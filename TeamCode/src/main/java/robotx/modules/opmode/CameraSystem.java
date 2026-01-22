package robotx.modules.opmode;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XCamera;

public class CameraSystem extends XModule {

    XCamera camera;
    public CameraSystem(OpMode op){
        super(op);
    }

    @Override
    public void init(){
        camera.init();
    }

    @Override
    public void loop(){


    }



}
