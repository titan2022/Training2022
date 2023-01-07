package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotationalDrivebase;
import frc.robot.subsystems.TranslationalDrivebase;

public class MoveToCommand extends CommandBase {
    private TranslationalDrivebase drive;

    public MoveToCommand(TranslationalDrivebase drive) {
        this.drive = drive;
    }

    @Override
    public void initialize() {
        drive.setVelocity(new Translation2d(0.5, 0));
    }
}
