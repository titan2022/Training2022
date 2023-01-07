package frc.robot.commands;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotationalDrivebase;

public class RotateToCommand extends CommandBase {
    private final static double maxAcceleration = 1;
    private final static double tolerance = 2 * (Math.PI / 180);

    private RotationalDrivebase drive;
    private WPI_Pigeon2 pigeon;

    private double maxVelocity;
    private Rotation2d targetRotation;

    private double offsetAngle = 360;

    public RotateToCommand(double maxVelocity, Rotation2d targetRotation, RotationalDrivebase drive, WPI_Pigeon2 pigeon) {
        this.maxVelocity = maxVelocity;
        this.targetRotation = targetRotation;
        this.drive = drive;
        this.pigeon = pigeon;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double currentRotation = -(pigeon.getAngle() % 360) * (Math.PI / 180);

        offsetAngle = targetRotation.getRadians() - currentRotation;
        if (offsetAngle > Math.PI) {
            offsetAngle -= 2 * Math.PI;
        } else if (offsetAngle < -Math.PI) {
            offsetAngle += 2 * Math.PI;
        }

        double velocity = Math.min(maxVelocity, Math.sqrt(2 * maxAcceleration * Math.abs(offsetAngle)));
        velocity = Math.copySign(velocity, offsetAngle);
        drive.setRotation(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRotation(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(offsetAngle) < tolerance;
    }
}