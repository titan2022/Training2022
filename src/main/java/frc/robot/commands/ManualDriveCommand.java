package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DifferentialDriveSubsystemTraining;

public class ManualDriveCommand extends CommandBase {
    private DifferentialDriveSubsystemTraining drive;
    private XboxController xbox;
    private static final double MAX_SPEED = 0.5;
    //private static final double MAX_ROTATION = 2 * Math.PI;
    
    public ManualDriveCommand(DifferentialDriveSubsystemTraining drive, XboxController xbox) {
        this.drive = drive;
        this.xbox = xbox;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        return;
    }

    @Override
    public void execute() {
        double left = -xbox.getLeftY() * MAX_SPEED;
        double right = -xbox.getRightY() * MAX_SPEED;
        drive.tank(left, right);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drive.tank(0, 0);
    }
}
