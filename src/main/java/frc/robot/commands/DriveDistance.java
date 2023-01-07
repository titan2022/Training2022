package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TranslationalDrivebase;

import static frc.robot.Unit.*;

public class DriveDistance extends CommandBase {
    private TranslationalDrivebase drive;
    private Translation2d offset;
    private Translation2d lastPos = new Translation2d(0, 0);
    private double velocity, acceleration, step;
    private Translation2d lastVel = new Translation2d(0, 0);

    public DriveDistance(TranslationalDrivebase drive, Translation2d offset, double velocity, double acceleration, double step) {
        this.drive = drive;
        this.offset = offset;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.step = step;
        addRequirements(drive);
    }
    public DriveDistance(TranslationalDrivebase drive, double distance, double velocity, double acceleration, double step) {
        this(drive, new Translation2d(0, distance), velocity, acceleration, step);
    }
    public DriveDistance(TranslationalDrivebase drive, Translation2d offset, double velocity, double acceleration) {
        this(drive, offset, velocity, acceleration, 0.02);
    }
    public DriveDistance(TranslationalDrivebase drive, double distance, double velocity, double acceleration) {
        this(drive, new Translation2d(0, distance), velocity, acceleration, 0.02);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Move Task", String.format("Rel (%.2f, %.2f)", offset.getX(), offset.getY()));
        lastPos = new Translation2d(0, 0);
        lastVel = new Translation2d(0, 0);
    }

    @Override
    public void execute() {
        Translation2d vel = drive.getVelocity();
        lastPos = lastPos.plus(vel.times(step));
        Translation2d off = offset.minus(lastPos);
        double v = Math.min(velocity, Math.sqrt(2 * acceleration * off.getNorm()));
        lastVel = off.times(v / off.getNorm());
        drive.setVelocity(lastVel);
        SmartDashboard.putNumber("Move Pos X", lastPos.getX());
        SmartDashboard.putNumber("Move Pos Y", lastPos.getY());
        SmartDashboard.putNumber("Move Off X", off.getX());
        SmartDashboard.putNumber("Move Off Y", off.getY());
        SmartDashboard.putNumber("Move Velocity X", lastVel.getX());
        SmartDashboard.putNumber("Move Velocity Y", lastVel.getY());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setVelocity(new Translation2d(0, 0));
        SmartDashboard.putNumber("Move Velocity X", 0);
        SmartDashboard.putNumber("Move Velocity Y", 0);
        SmartDashboard.putString("Move Task", String.format("[End] Rel (%.2f, %.2f)", offset.getX(), offset.getY()));
    }

    @Override
    public boolean isFinished() {
        double pre = lastPos.minus(offset).getNorm();
        double post = lastPos.plus(lastVel.times(2 * step)).minus(offset).getNorm();
        double dist = lastVel.getNorm() * 2 * step;
        return pre - post < Math.max(dist - 0.5*IN, 0.5*IN);
    }
}