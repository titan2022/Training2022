package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DifferentialDriveSubsystemTraining extends SubsystemBase {

    public static final int LEFT_MOTOR_PORT = 0;
    public static final int RIGHT_MOTOR_PORT = 1;
    public static final int TICKS_PER_REVOLUTION = 4096;
    public static final double WHEEL_CIRCUMFRENCE_MM = 70;
    private static final double TRACK_WIDTH_METERS = 1;
    private static final int TICKS_PER_METER = (int) (TICKS_PER_REVOLUTION / WHEEL_CIRCUMFRENCE_MM * 1000);

    private WPI_TalonFX leftMotor;
    private WPI_TalonFX rightMotor;
    private DifferentialDriveKinematics kinematics;

    public DifferentialDriveSubsystemTraining() {
        leftMotor = new WPI_TalonFX(LEFT_MOTOR_PORT);
        rightMotor = new WPI_TalonFX(RIGHT_MOTOR_PORT);
        kinematics = new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
    }

    private static double mpsToTphms(double x) {
        return (x * TICKS_PER_METER / 10) / (WHEEL_CIRCUMFRENCE_MM / 1000);
    }

    public void driveVel(double velocity, double angleDeg) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(velocity, 0, angleDeg);
        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
        double leftMotorSpeed = mpsToTphms(wheelSpeeds.leftMetersPerSecond);
        double rightMotorSpeed = mpsToTphms(wheelSpeeds.rightMetersPerSecond);
        leftMotor.set(ControlMode.Velocity, leftMotorSpeed);
        rightMotor.set(ControlMode.Velocity, rightMotorSpeed);
    }

    public void brake() {
        leftMotor.set(ControlMode.PercentOutput, 0);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

}
