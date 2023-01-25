package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DifferentialDriveSubsystemTraining extends SubsystemBase {

    public static final int RIGHT_MOTOR_PORT1 = 0;
    public static final int LEFT_MOTOR_PORT1 = 1;
    public static final int LEFT_MOTOR_PORT2 = 2;
    public static final int RIGHT_MOTOR_PORT2 = 3;
    public static final int TICKS_PER_REVOLUTION = 4096;
    public static final double WHEEL_CIRCUMFRENCE_MM = 70;
    private static final double TRACK_WIDTH_METERS = 1;
    private static final int TICKS_PER_METER = (int) (TICKS_PER_REVOLUTION / WHEEL_CIRCUMFRENCE_MM * 1000);

    private WPI_TalonSRX leftMotor1;
    private WPI_TalonSRX rightMotor1;
    private WPI_VictorSPX leftMotor2;
    private WPI_VictorSPX rightMotor2;
    private DifferentialDriveKinematics kinematics; 

    public DifferentialDriveSubsystemTraining() {
        leftMotor1 = new WPI_TalonSRX(LEFT_MOTOR_PORT1);
        rightMotor1 = new WPI_TalonSRX(RIGHT_MOTOR_PORT1);
        leftMotor2 = new WPI_VictorSPX(LEFT_MOTOR_PORT2);
        rightMotor2 = new WPI_VictorSPX(RIGHT_MOTOR_PORT2);
        kinematics = new DifferentialDriveKinematics(TRACK_WIDTH_METERS);
        leftMotor1.setNeutralMode(NeutralMode.Brake);
        rightMotor1.setNeutralMode(NeutralMode.Brake);
        leftMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor2.setNeutralMode(NeutralMode.Brake);
        rightMotor1.setInverted(true);
        rightMotor2.setInverted(true);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);
    }

    private static double mpsToTphms(double x) {
        return (x * TICKS_PER_METER / 10);
    }

    public void driveVel(double velocity, double angleRad) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(velocity, 0, angleRad);
        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeeds);
        double leftMotorSpeed = mpsToTphms(wheelSpeeds.leftMetersPerSecond);
        double rightMotorSpeed = mpsToTphms(wheelSpeeds.rightMetersPerSecond);
        leftMotor1.set(ControlMode.Velocity, leftMotorSpeed);
        rightMotor1.set(ControlMode.Velocity, rightMotorSpeed);
    }

    public void tank(double left, double right) {
        SmartDashboard.putNumber("left", left);
        SmartDashboard.putNumber("right", right);
        leftMotor1.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
    }

    public void brake() {
        leftMotor1.set(ControlMode.PercentOutput, 0);
        rightMotor1.set(ControlMode.PercentOutput, 0);
    }

}