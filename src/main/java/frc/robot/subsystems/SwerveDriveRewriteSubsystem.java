package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Unit.*;

public class SwerveDriveRewriteSubsystem extends SubsystemBase {

    public int testValue = 0;


    private final static int TOP_LEFT_MOTOR_PORT = 6;
    private final static int TOP_RIGHT_MOTOR_PORT = 2;
    private final static int BACK_LEFT_MOTOR_PORT = 4;
    private final static int BACK_RIGHT_MOTOR_PORT = 7;

    private final static int TOP_LEFT_ROTATOR_PORT = 5;
    private final static int TOP_RIGHT_ROTATOR_PORT = 1;
    private final static int BACK_LEFT_ROTATOR_PORT = 3;
    private final static int BACK_RIGHT_ROTATOR_PORT = 8;

    private final static int TOP_LEFT_ENCODER_PORT = 35;
    private final static int TOP_RIGHT_ENCODER_PORT = 31;
    private final static int BACK_LEFT_ENCODER_PORT = 33;
    private final static int BACK_RIGHT_ENCODER_PORT = 37;

    public final WPI_TalonFX[] driveMotors = new WPI_TalonFX[] {
            new WPI_TalonFX(TOP_LEFT_MOTOR_PORT),
            new WPI_TalonFX(TOP_RIGHT_MOTOR_PORT),
            new WPI_TalonFX(BACK_LEFT_MOTOR_PORT),
            new WPI_TalonFX(BACK_RIGHT_MOTOR_PORT)
    };
    public final WPI_TalonFX[] rotationalMotors = new WPI_TalonFX[] {
            new WPI_TalonFX(TOP_LEFT_ROTATOR_PORT),
            new WPI_TalonFX(TOP_RIGHT_ROTATOR_PORT),
            new WPI_TalonFX(BACK_LEFT_ROTATOR_PORT),
            new WPI_TalonFX(BACK_RIGHT_ROTATOR_PORT)
    };
    public final CANCoder[] encoders = new CANCoder[] {
            new CANCoder(TOP_LEFT_ENCODER_PORT),
            new CANCoder(TOP_RIGHT_ENCODER_PORT),
            new CANCoder(BACK_LEFT_ENCODER_PORT),
            new CANCoder(BACK_RIGHT_ENCODER_PORT)
    };

    private final static double LENGTH = 0;
    private final static double WIDTH = 0;
    private final static double WHEEL_RADIUS = 2 * IN;
    private final static double GEAR_RATIO = 8.16; // 1 : 8.16 MAYBE?
    private final static double MAX_WHEEL_SPEED = 10;
    private Translation2d TOP_LEFT_POSITION = new Translation2d(-9.25 * IN, 9.25 * IN);
    private Translation2d TOP_RIGHT_POSITION = new Translation2d(9.25 * IN, 9.25 * IN);
    private Translation2d BACK_LEFT_POSITION = new Translation2d(-9.25 * IN, -9.25 * IN);
    private Translation2d BACK_RIGHT_POSITION = new Translation2d(9.25 * IN, -9.25 * IN);
    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(TOP_LEFT_POSITION, TOP_RIGHT_POSITION, BACK_LEFT_POSITION, BACK_RIGHT_POSITION);

    private static final int TOP_LEFT_OFFSET = 1820-1024; //-208 + 1024;// 908-1024+2048; // -95
    private static final int BACK_LEFT_OFFSET = 1230-1024; //-747 + 1024;// -840;
    private static final int TOP_RIGHT_OFFSET = 203 + 1024; //192 + 1024;// -182+1024+512+1024+2048; // -1200
    private static final int BACK_RIGHT_OFFSET = 2010 + 1024; //1925 + 1024;// 1287-512-1024+2048;
    private static final int[] OFFSETS = new int[] { TOP_LEFT_OFFSET, TOP_RIGHT_OFFSET, BACK_LEFT_OFFSET,
            BACK_RIGHT_OFFSET };

    private final static double METERS_PER_TICKS = WHEEL_RADIUS * 2 * Math.PI / FALCON_CPR / GEAR_RATIO;

    private ChassisSpeeds lastVelocity = new ChassisSpeeds();

    private final TranslationalDrivebase translationalLock = new TranslationalDrivebase() {
        @Override
        public void setTestValue(int newVal) {
            testValue = newVal;
        }

        @Override
        public void setVelocity(Translation2d velocity) {
            updateVelocity(velocity);
        }

        @Override
        public Translation2d getVelocity() {
            ChassisSpeeds speeds = getVelocities();
            return new Translation2d(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond);
        }
    };

    private final RotationalDrivebase rotationalLock = new RotationalDrivebase() {
        @Override
        public void setRotation(double omega) {
            updateRotation(omega);
        }

        @Override
        public double getRate() {
            ChassisSpeeds speeds = getVelocities();
            return speeds.omegaRadiansPerSecond;
        }
    };

    public TranslationalDrivebase getTranslational() {
        return translationalLock;
    }

    public RotationalDrivebase getRotational() {
        return rotationalLock;
    }

    public SwerveDriveRewriteSubsystem() {
        TalonFXConfiguration mainConfig = new TalonFXConfiguration();
        TalonFXConfiguration rotatorConfig = new TalonFXConfiguration();

        rotatorConfig.supplyCurrLimit.currentLimit = 12;
        rotatorConfig.supplyCurrLimit.enable = true;
        rotatorConfig.supplyCurrLimit.triggerThresholdCurrent = 12;
        rotatorConfig.supplyCurrLimit.triggerThresholdTime = 0.0;
        mainConfig.supplyCurrLimit.currentLimit = 20;
        mainConfig.supplyCurrLimit.enable = true;
        mainConfig.supplyCurrLimit.triggerThresholdCurrent = 20;
        mainConfig.supplyCurrLimit.triggerThresholdTime = 0.00;

        rotatorConfig.slot0.kP = 0.319;
        rotatorConfig.slot0.allowableClosedloopError = 5;

        mainConfig.slot0.kP = 0.06;
        mainConfig.slot0.kD = 0.2;
        mainConfig.slot0.kF = 0.045;
        mainConfig.slot0.allowableClosedloopError = 20;

        rotatorConfig.slot0.kP = 0.319;
        rotatorConfig.slot0.kD = 0;
        rotatorConfig.slot0.kF = 0;

        for (int i = 0; i <= 3; i++) {
            driveMotors[i].configFactoryDefault();
            driveMotors[i].configAllSettings(mainConfig);
            driveMotors[i].setInverted(false);
            driveMotors[i].setSensorPhase(false);
            driveMotors[i].selectProfileSlot(0, 0);
            rotationalMotors[i].configFactoryDefault();
            rotationalMotors[i].configAllSettings(rotatorConfig);
            rotationalMotors[i].configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, -0);
            rotationalMotors[i].configRemoteFeedbackFilter(encoders[i], 0);
            rotationalMotors[i].setInverted(true);
            rotationalMotors[i].setSensorPhase(false);
            rotationalMotors[i].selectProfileSlot(0, 0);

            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 20);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 1000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_6_Misc, 60);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_7_CommStatus, 50);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 20);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_11_UartGadgeteer, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 5000);
            driveMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_15_FirmwareApiStatus, 5000);

            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 20);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat, 1000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_6_Misc, 60);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_7_CommStatus, 50);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_10_Targets, 20);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_11_UartGadgeteer, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_12_Feedback1, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_14_Turn_PIDF1, 5000);
            rotationalMotors[i].setStatusFramePeriod(StatusFrameEnhanced.Status_15_FirmwareApiStatus, 5000);

            encoders[i].configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition, 0);
            encoders[i].setStatusFramePeriod(CANCoderStatusFrame.SensorData, 5000);
            encoders[i].setStatusFramePeriod(CANCoderStatusFrame.VbatAndFaults, 5000);
        }
    }

    public double getRotatorEncoderCount(int module) {
        return rotationalMotors[module].getSelectedSensorVelocity() - OFFSETS[module];
    }

    private void applyModuleState(SwerveModuleState state, int module) {
        double velTicks = state.speedMetersPerSecond / (10 * METERS_PER_TICKS);
        if (velTicks == 0) {
            driveMotors[module].set(ControlMode.Velocity, 0);
            return;
        }
        double currTicks = getRotatorEncoderCount(module);
        double targetTicks = CANCODER_CPR / 2 - state.angle.getRadians() * RAD / CANCODER_TICKS;
        double deltaTicks = (targetTicks - currTicks) % CANCODER_CPR;
        if (deltaTicks >= CANCODER_CPR / 2)
            deltaTicks -= CANCODER_CPR;
        else if (deltaTicks <= -CANCODER_CPR / 2)
            deltaTicks += CANCODER_CPR;
        if (deltaTicks >= CANCODER_CPR / 4) {
            deltaTicks -= CANCODER_CPR / 2;
            velTicks *= -1;
        } else if (deltaTicks <= -CANCODER_CPR / 4) {
            deltaTicks += CANCODER_CPR / 2;
            velTicks *= -1;
        }

        driveMotors[module].set(ControlMode.Velocity, velTicks);
        rotationalMotors[module].set(ControlMode.Position, currTicks + deltaTicks + OFFSETS[module]);
    }

    private SwerveModuleState[] getSwerveModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (int i = 0; i < 4; i++) {
            double encoderVelocity = driveMotors[i].getSelectedSensorVelocity() * METERS_PER_TICKS * 10;
            double rotatorEncoderPosition = getRotatorEncoderCount(i) * CANCODER_TICKS / RAD;
            states[i] = new SwerveModuleState(encoderVelocity, new Rotation2d(rotatorEncoderPosition));
        }
        return states;
    }

    private void setVelocities(ChassisSpeeds inputChassisSpeeds) {
        lastVelocity = inputChassisSpeeds;
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(inputChassisSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_WHEEL_SPEED);
        for (int i = 0; i < 4; i++) {
            applyModuleState(states[i], i);
        }
    }

    public ChassisSpeeds getVelocities() {
        SwerveModuleState[] states = getSwerveModuleStates();
        return kinematics.toChassisSpeeds(states);
    }

    public void updateVelocity(Translation2d velocity) {
        lastVelocity.vxMetersPerSecond = velocity.getX();
        lastVelocity.vyMetersPerSecond = velocity.getY();
        setVelocities(lastVelocity);
    }

    public void updateRotation(double omega) {
        lastVelocity.omegaRadiansPerSecond = omega;
        setVelocities(lastVelocity);
    }

    public void move(Translation2d velocity, double rotationalVelocity) {
        ChassisSpeeds speed = new ChassisSpeeds(velocity.getX(), velocity.getY(), rotationalVelocity);
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speed);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_WHEEL_SPEED);
        for (int i = 0; i < 4; i++) {
            applyModuleState(states[i], i);
        }
    }

    public void coast() {
        for (WPI_TalonFX rotator : rotationalMotors)
            rotator.setNeutralMode(NeutralMode.Coast);
    }

    public void brake() {
        for (WPI_TalonFX rotator : rotationalMotors)
            rotator.setNeutralMode(NeutralMode.Brake);
    }
}
