package frc.robot;

import edu.wpi.first.math.util.Units;

public final class Unit {
    public static final double METERS = 1.0, M = 1.0;
    public static final double CM = 0.01, MM = 0.001;
    public static final double IN = Units.inchesToMeters(1);
    public static final double FT = Units.feetToMeters(1);
    public static final double RAD = 1.0;
    public static final double DEG = Units.degreesToRadians(1);
    public static final double ROT = Math.PI * 2;
    public static final double FALCON_CPR = 2048;
    public static final double CANCODER_CPR = 4096;
    public static final double FALCON_TICKS = ROT / FALCON_CPR;
    public static final double CANCODER_TICKS = ROT / CANCODER_CPR;
    public static final double SECONDS = 1.0, S = 1.0;
    public static final double MIN = 1.0/60;
    public static final double MS = 0.001;
}