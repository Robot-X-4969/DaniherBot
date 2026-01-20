package robotx.stx_libraries.components;

import com.qualcomm.robotcore.hardware.Gamepad;

import robotx.stx_libraries.util.Stats;

public class XDriverStation {

    public final XGamepad xGamepad1 = new XGamepad();
    public final XGamepad xGamepad2 = new XGamepad();
    public final XGamepad xGamepad = new XGamepad();

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    public boolean dualPlayer = true;

    public void init(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        xGamepad1.update(gamepad1);
        xGamepad2.update(gamepad2);
    }

    public void update() {
        xGamepad1.update();
        xGamepad2.update();
        updateGamepad();

        if (xGamepad1.back.wasPressed()) {
            dualPlayer = !dualPlayer;
        }
    }

    private void updateGamepad() {
        //Updates floats
        xGamepad.left_stick_x = Stats.maxAbs(gamepad1.left_stick_x, gamepad2.left_stick_x);
        xGamepad.left_stick_y = Stats.maxAbs(gamepad1.left_stick_y, gamepad2.left_stick_y);
        xGamepad.right_stick_x = Stats.maxAbs(gamepad1.right_stick_x, gamepad2.right_stick_x);
        xGamepad.right_stick_y = Stats.maxAbs(gamepad1.right_stick_y, gamepad2.right_stick_y);
        xGamepad.left_trigger = Math.max(gamepad1.left_trigger, gamepad2.left_trigger);
        xGamepad.right_trigger = Math.max(gamepad1.right_trigger, gamepad2.right_trigger);

        //Updates buttons
        xGamepad.dpad_up.update(gamepad1.dpad_up || gamepad2.dpad_up && dualPlayer);
        xGamepad.dpad_down.update(gamepad1.dpad_down || gamepad2.dpad_down && dualPlayer);
        xGamepad.dpad_left.update(gamepad1.dpad_left || gamepad2.dpad_left && dualPlayer);
        xGamepad.dpad_right.update(gamepad1.dpad_right || gamepad2.dpad_right && dualPlayer);
        xGamepad.a.update(gamepad1.a && !gamepad1.start || gamepad2.a && !gamepad2.start && dualPlayer);
        xGamepad.b.update(gamepad1.b && !gamepad1.start || gamepad2.b && !gamepad2.start && dualPlayer);
        xGamepad.x.update(gamepad1.x || gamepad2.x && dualPlayer);
        xGamepad.y.update(gamepad1.y || gamepad2.y && dualPlayer);
        xGamepad.guide.update(gamepad1.guide || gamepad2.guide && dualPlayer);
        xGamepad.start.update(gamepad1.start || gamepad2.start && dualPlayer);
        xGamepad.back.update(gamepad1.back || gamepad2.back && dualPlayer);
        xGamepad.left_bumper.update(gamepad1.left_bumper || gamepad2.left_bumper && dualPlayer);
        xGamepad.right_bumper.update(gamepad1.right_bumper || gamepad2.right_bumper && dualPlayer);
        xGamepad.left_stick_button.update(gamepad1.left_stick_button || gamepad2.left_stick_button && dualPlayer);
        xGamepad.right_stick_button.update(gamepad1.right_stick_button || gamepad2.right_stick_button && dualPlayer);
    }
}
