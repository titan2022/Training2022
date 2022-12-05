package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Attack3;
import frc.robot.subsystems.DifferentialDriveSubsystemTraining;

public class Attack3DriveCommand extends CommandBase {
    private final DifferentialDriveSubsystemTraining drive;
    private final Attack3 joy;
    private final double maxSpd;

    public Attack3DriveCommand(DifferentialDriveSubsystemTraining drive, Attack3 joy, double maxSpd) {
        this.drive = drive;
        this.joy = joy;
        this.maxSpd = maxSpd;
        addRequirements(drive);
    }
    public Attack3DriveCommand(DifferentialDriveSubsystemTraining drive, Attack3 joy) {
        this(drive, joy, 1);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double throttle = joy.getYAxis();
        double turn = joy.getXAxis();
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
