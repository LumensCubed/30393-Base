package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.Placeholder;
import org.firstinspires.ftc.teamcode.misc.PlaceholderReporter;

import java.util.concurrent.TimeUnit;

/**
 * This is the base class that all OpModes we make should extend.
 * It automatically logs loop times and runs the scheduler.
 * All overridden methods (init(), loop(), etc.) should be put as super.method() at the end in
 * OpModes that extend this.
 */
public class BaseOpMode extends OpMode {
    protected double loops = 0;
    protected double secondLoops = 0;
    protected double storedLoops = 0;
    protected ElapsedTime loopTimer = new ElapsedTime();
    protected Timing.Timer secTimer = new Timing.Timer(1000, TimeUnit.MILLISECONDS);
    public void reset() {
        Scheduler.reset();
    }

    /**
     * Schedules objects to the scheduler
     */
    public void schedule(Command... commands) {
        Scheduler.schedule(commands);
    }

    /**
     * resets scheduler
     */
    @Override
    public void init() {
        reset();
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);
        telemetry.setNumDecimalPlaces(0, 5);
        telemetry.setCaptionValueSeparator(": ");
        PlaceholderReporter.reportPlaceholderFields(telemetry, this);
        telemetry.update();
    }

    /**
     * starts timers for loop time logging and runs loop once
     */
    @Override
    public void start() {
        loopTimer.reset();
        secTimer.start();
        loop();
    }

    /**
     * adds loop time to telemetry, updates it, and runs scheduler
     */
    @Override
    public void loop() {
        secondLoops++;
        loops++;
        if (secTimer.done()){
            storedLoops = secondLoops;
            secondLoops = 0;
            secTimer.start();
        }
        telemetry.addData("average loop time (ms): ", (loopTimer.milliseconds()/loops));
        telemetry.addData("loops per second (approximate)", storedLoops);
        telemetry.update();
        Scheduler.execute();
    }

    /**
     * resets scheduler
     */
    public void stop() {
        reset();
    }
}
