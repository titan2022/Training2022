# Titan Robotics 2022 Preseason Training Repository

Welcome to the Titan Robotics programming subteam.

## Contents

* [Homework](#homework)
* [Links and Resources](#links-and-resources)
* [Quick Reference](#quick-reference)

## Homework

### VSCode and Git

1. Checkout Training2022/git-lesson
2. Create a branch off of that branch called `<yourname>-git-lesson`
3. Push a commit that adds a file named \<yourname>.txt to your new branch
4. Merge your branch into Training2022/git-lesson and make sure this change is pushed

### Introduction to Motors and Controllers

In a branch called `yourname-motors` off of the `motors` branch, write a program that uses some sort of input from an `XboxController` to control a `WPI_TalonFX` motor. Explicitly set the neutral mode of the motor. Don't forget to push your code (you don't need to merge the branch). For an extra (optional) challenge, create multiple motors and control them using a joystick, a button, and a trigger.

## Links and Resources

Here are some useful links that will be useful during training.
You aren't expected to go check them out right now, but they're here for anyone who wants a preview of the kinds of things we'll go over.

### VSCode and Git

* [Git Download](https://git-scm.com/downloads)
* [GitHub](https://github.com/)
* [WPI VSCode Download Instructions](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)

### Introduction to Motors and Controllers

* [FRC Game Tools Download Instructions](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html)

### Command Based Programming: Subsystems

* [What is Command Based Programming](https://docs.wpilib.org/en/stable/docs/software/commandbased/what-is-command-based.html)
* [Subsystems](https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html)
* [Commands](https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html)

### Command Based Programming: Commands

* [Command Groups](https://docs.wpilib.org/en/stable/docs/software/commandbased/command-groups.html)
* [Binding Commands to Triggers](https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html)
* [Convenience Features](https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html)

### NetworkTables and the FRC Control System

* [NetworkTables](https://docs.wpilib.org/en/stable/docs/software/networktables/index.html)
* [Shuffleboard](https://docs.wpilib.org/en/stable/docs/software/dashboards/shuffleboard/getting-started/index.html)
* [Driver Station Tour](https://docs.wpilib.org/en/stable/docs/software/driverstation/driver-station.html)
* [Driver Station Log Viewer](https://docs.wpilib.org/en/stable/docs/software/driverstation/driver-station-log-viewer.html)

### CAN for Programming

* [CTRE Phoenix Framework Download](https://store.ctr-electronics.com/software/)
* [Falcon500 User Manual](https://www.vexrobotics.com/217-6515.html#attr-vex_docs_downloads)

### Introduction to Advanced Topics

* [`WPI_TalonFX` Documentation](https://store.ctr-electronics.com/content/api/java/html/classcom_1_1ctre_1_1phoenix_1_1motorcontrol_1_1can_1_1_w_p_i___talon_f_x.html)

### Limelight Tuning and Other Sensors

* [Limelight Documentation](https://docs.limelightvision.io/en/latest/vision_pipeline_tuning.html)

## Quick Reference

### Git Review

Create a branch: `git checkout -b your-branch-name`

Stage changes: `git add .`

Commit staged changes: `git commit -m "a short description of your changes"`

Publish a new branch: `git push -u origin your-branch-name`

Switch to another existing branch: `git checkout other-branch-name`

Check for updates: `git pull`

Merge a branch into the branch you are on: `git merge your-branch-name`

### Motors

`WPI_TalonFX` constructor: `WPI_TalonFX(int id)` (`id` is the CAN id of the motor controller)

Control Modes:
* `motor.set(ControlMode.PercentOutput, pct)` - -1.0 < `pct` < 1.0
* `motor.set(ControlMode.Velocity, vel)` - `vel` is in ticks per 100ms (requires an encoder and a PID index to be selected)
* `motor.set(ControlMode.Position, pos)` - `pos` is in ticks (requires an encoder and a PID index to be selected)

Neutral Modes:
* `motor.setNeutralMode(NeutralMode.Brake)` motor provides resistance to turning
* `motor.setNeutralMode(NeutralMode.Coast)` motor coasts to a stop without providing resistance

Inverting Directions:
* `motor.setInverted(true/false)`
* `motor.setSensorPhase(true/false)`

Encoder Initialization:
* `motor.configSensorInitializationStrategy(SensorInitializationStrategy.BootToZero, 0)` zeros the integrated encoder on startup
* `motor.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition, 0)` uses absolute orientation for the integrated encoder

Selecting an Encoder and PID index:
* `motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, idx, 0)` selects the integrated encoder for PID index `idx` (usually 0)
* `motor.selectProfileSlot(idx, 0)` tells the motor to use PID index `idx` (usually 0)

### Controllers / Gamepads

`XboxController` constructor: `XboxController(int port)` (`port` is the USB port the controller is plugged into)

Joysticks: `xbox.getLeftX()`, `xbox.getLeftY()`, `xbox.getRightX()`, and `xbox.getRightY()` return values from -1.0 to +1.0. The left Y stick is reversed.

Buttons:
* `xbox.getXButton()` returns `true` if the X button is currently pressed
* `xbox.getXButtonPressed()` returns `true` if the X button was pressed since the last time it was checked
* `xbox.getXButtonReleased()` returns `true` if the X button was released since the last time it was checked
* `xbox.getLeftTrigger()`, `xbox.getRightTrigger()`, `xbox.getLeftStickButton()`, and `xbox.getRightStickButton()` work the same way, with the same three variants.

Triggers: `xbox.getLeftTriggerAxis()` and `xbox.getRightTriggerAxis()` return values from 0.0 to 1.0.
