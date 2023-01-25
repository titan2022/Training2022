package frc.robot.commands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DifferentialDriveSubsystemTraining;

public class PS4TankDriveCommand extends CommandBase {
    private final DifferentialDriveSubsystemTraining drive;
    private final PS4Controller xbox;
    private final double maxSpeed;
    
    public PS4TankDriveCommand(DifferentialDriveSubsystemTraining drive, PS4Controller ps4, double maxSpeed) {
        this.drive = drive;
        this.xbox = ps4;
        this.maxSpeed = maxSpeed;
        addRequirements(drive);
    }
    public PS4TankDriveCommand(DifferentialDriveSubsystemTraining drive, PS4Controller xbox) {
        this(drive, xbox, 0.5);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Drive mode", "Tank - PS4");
    }

    @Override
    public void execute() {
        double rawLeft = -xbox.getLeftY();
        double rawRight = -xbox.getRightY();
        double left = Math.abs(rawLeft) > 0.1 ? rawLeft * maxSpeed : 0;
        double right = Math.abs(rawRight) > 0.1 ? rawRight * maxSpeed : 0;
        drive.tank(left, right);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drive.tank(0, 0);
        SmartDashboard.putString("Drive mode", "None");
    }
}
