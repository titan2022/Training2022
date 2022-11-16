package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DifferentialDriveSubsystemTraining;

public class TankDriveCommand extends CommandBase {
    private final DifferentialDriveSubsystemTraining drive;
    private final XboxController xbox;
    private final double maxSpeed;
    //private static final double MAX_ROTATION = 2 * Math.PI;
    
    public TankDriveCommand(DifferentialDriveSubsystemTraining drive, XboxController xbox, double maxSpeed) {
        this.drive = drive;
        this.xbox = xbox;
        this.maxSpeed = maxSpeed;
        addRequirements(drive);
    }
    public TankDriveCommand(DifferentialDriveSubsystemTraining drive, XboxController xbox) {
        this(drive, xbox, 0.5);
    }

    @Override
    public void initialize() {
        return;
    }

    @Override
    public void execute() {
        double left = -xbox.getLeftY() * maxSpeed;
        double right = -xbox.getRightY() * maxSpeed;
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
