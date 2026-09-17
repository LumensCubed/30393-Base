package org.firstinspires.ftc.teamcode.opmodes;


import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.PoseSaver;
import org.firstinspires.ftc.teamcode.robot.Robot;

/**
 *This is our base TeleOp class.
 * Red and Blue TeleOps that extend this should be created and put on the driver station.
 */
public class BaseTeleOp extends CommandOpMode {
    protected Robot robot = new Robot();

    /**
     * this is set by the constructor in red/blue teleop classes
     */
    protected boolean isRed;
    protected PoseFactory factory = PoseFactory.degrees();

    public BaseTeleOp(boolean isRed) {
        this.isRed = isRed;
    }

    @Override
    public void init() {
        robot.initialize(isRed, hardwareMap);
        if (!isRed) {
            factory.mirrorX(72);
        }
        if (PoseSaver.autoWasRun) {
            robot.follower.setPose(PoseSaver.endPose);
        } else {
            robot.follower.setPose(factory.of(0,0,0));
        }
        PoseSaver.autoWasRun = false;
        reset();
    }

    @Override
    public void start() {
        super.start();
        robot.update();
    }

    @Override
    public void loop() {
        robot.update();

        if (gamepad2.aWasPressed()){
            robot.slowDrive = !robot.slowDrive;
        }

        super.loop(); //runs CommandOpMode's loop
    }

    public void stop(){
        PoseSaver.autoWasRun = false;
        super.stop();
    }
}
