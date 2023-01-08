package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.TranslationalDrivebase;

public class TestDriveCommand extends CommandBase {

    private int testValue = 0;



    private TranslationalDrivebase drive;
    private XboxController controller;
    private double maxVel;
    private boolean isFieldOriented = false;

    /**
     * Creates a new TranlationalDriveCommand with optional field orientation.
     * 
     * @param drivebase  The drivebase to control.
     * @param xbox  The joystick controller to use.
     * @param localizer  The localization subsystem to use for orientation
     *  correction. This will not be added as a requirement of this command.
     * @param velocity  The translational velocity represented by moving the
     *  translational joystick all the way towards a cardinal direction, in
     *  meters per second.
     */
    public TestDriveCommand(TranslationalDrivebase drivebase, XboxController xbox, double velocity) {
        drive = drivebase;
        controller = xbox;
        maxVel = velocity;
        addRequirements(drivebase);
    }
    /**
     * Creates a new TranlationalDriveCommand using intrinsic orientation.
     * 
     * The maximum velocity in a cardinal direction defaults to 5 meters per second.
     * 
     * @param drivebase  The drivebase to control.
     * @param xbox  The joystick controller to use.
     */
    public TestDriveCommand(TranslationalDrivebase drivebase, XboxController xbox) {
        this(drivebase, xbox, 5.);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("isFieldOriented", isFieldOriented);
    }

    private static double applyDeadband(double joy, double deadband) {
        return Math.abs(joy) < deadband ? 0 : joy;
    }
    
    private double scaleVelocity(double joy) {
        return Math.signum(joy) * joy * joy * maxVel;
    }

    @Override
    public void execute() {
        Translation2d vel = drive.getVelocity();
        SmartDashboard.putNumber("velX", vel.getX());
        SmartDashboard.putNumber("velY", vel.getX());
        SmartDashboard.putNumber("testoffset", testValue);
        if(controller.getBButton()){
            SmartDashboard.putBoolean("contorller ON", isFieldOriented);
            drive.setVelocity(new Translation2d(0, 0.1));
            drive.setTestValue(testValue += 1);
        } else if(controller.getYButton()){
            //SmartDashboard.putBoolean("contorller ON", isFieldOriented);
            drive.setVelocity(new Translation2d(0.1, 0));
            drive.setTestValue(testValue -= 1);
        } else {
            drive.setVelocity(new Translation2d(0, 0));
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setVelocity(new Translation2d(0, 0));
        if(interrupted)
            new StartEndCommand(() -> {
                    controller.setRumble(RumbleType.kLeftRumble, 0.5);
                }, () -> {
                    controller.setRumble(RumbleType.kLeftRumble, 0);
                }).withTimeout(0.5).schedule();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}