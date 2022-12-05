package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Attack3 extends Joystick {
    public JoystickButton trigger, up, down, left, right;
    public JoystickButton leftFront, leftBack, rightFront, rightBack;
    public JoystickButton backLeft, backRight;
    
    public Attack3(int port) {
        super(port);
        trigger = new JoystickButton(this, 1);
        up = new JoystickButton(this, 3);
        down = new JoystickButton(this, 2);
        left = new JoystickButton(this, 4);
        right = new JoystickButton(this, 5);
        leftFront = new JoystickButton(this, 6);
        leftBack = new JoystickButton(this, 7);
        rightFront = new JoystickButton(this, 11);
        rightBack = new JoystickButton(this, 10);
        backLeft = new JoystickButton(this, 8);
        backRight = new JoystickButton(this, 9);
    }

    public double getXAxis() {
        return getRawAxis(0);
    }
    
    public double getYAxis() {
        return -getRawAxis(1);
    }
    
    public double getZAxis() {
        return -getRawAxis(2);
    }
}
