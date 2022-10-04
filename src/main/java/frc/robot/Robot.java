// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  WPI_TalonFX motor = new WPI_TalonFX(2);
  XboxController  xbox = new XboxController(0);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {}

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    /**This can be used for movement */
    motor.set(ControlMode.PercentOutput, 0.5);
    //**OR This can be used for movement*/
    motor.set(ControlMode.Position , 1024);


    //**Makes the motors invert, so that forward is forward. */
    motor.setInverted(true);
    //**Another way to set the correct way of forward This and the code above do the same thing*/
    motor.setSensorPhase(false);
    //**This would set the neutral space, when the velocity is 0. In this case, the neutral position of the motor is BRAKE, but you can also set it to coast, which just leaves the motor in neutral and lets it follow gravity. */
    motor.setNeutralMode(NeutralMode. Brake);
    //**Every motor has a true 0, a true north that is set at the factory. With this code we can set whatever we want the motor to be when we turn it on; this changes the true 0 momentarily, for your sake. */
    motor.configIntegratedSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
    //**Sets Feedback Sensor name to one that's in the system. */
    motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    //**OR this can be used for movement*/ //**This sets the speed of the motor to 0, or the NeutralMode */
    motor.set(ControlMode.Velocity , 0);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //**If b button on the controller is pressed*/
    if(xbox.getXButtonPressed()){
      //**This command runs the motor */
      motor.set(ControlMode.PercentOutput, 0.55);//**If x button is pressed, go forward 25% */
    }
    else if(xbox.getBButtonReleased()){ //**If the b button is released, go backwards 25% */
      motor.set(ControlMode.PercentOutput, 0.80);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {

  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
