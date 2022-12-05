package frc.robot.commands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DifferentialDriveSubsystemTraining;

public class PS4ArcadeDriveCommand extends CommandBase {
    private final DifferentialDriveSubsystemTraining drive;
    private final PS4Controller xbox;
    private final double maxSpd;

    public PS4ArcadeDriveCommand(DifferentialDriveSubsystemTraining drive, PS4Controller xbox, double maxSpd) {
        this.drive = drive;
        this.xbox = xbox;
        this.maxSpd = maxSpd;
        addRequirements(drive);
    }
    public PS4ArcadeDriveCommand(DifferentialDriveSubsystemTraining drive, PS4Controller xbox) {
        this(drive, xbox, 1);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double throttle = - (xbox.getRightY() + xbox.getLeftY());
        double turn = xbox.getRightX() + xbox.getLeftX();
        double left = throttle + turn;
        double right = throttle - turn;
        double scale = Math.max(Math.abs(left), Math.abs(right));
        scale = scale > 1 ? scale : 1;
        left /= scale;
        right /= scale;
        drive.tank(left*maxSpd, right*maxSpd);
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
