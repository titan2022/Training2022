package frc.robot.commands;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TranslationalDrivebase;

public class MoveForSeconds extends CommandBase {
    private TranslationalDrivebase drive;
    private WPI_Pigeon2 pigeon;
    private Translation2d offset;
    private double seconds;

    private boolean ended = false;

    public MoveForSeconds(TranslationalDrivebase drive, WPI_Pigeon2 pigeon, Translation2d offset, double seconds) {
        this.drive = drive;
        this.offset = offset;
        this.seconds = seconds;
    }

    @Override
    public void execute() {
        double angle = pigeon.getAngle() * (Math.PI / 180);
        Translation2d newVelocity = new Translation2d();
    }

    @Override
    public void end(boolean interrupted) {
        drive.setVelocity(new Translation2d(0, 0));
        ended = true;
    }

    @Override
    public boolean isFinished() {
        return ended;
    }
}
