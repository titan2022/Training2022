package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Unit.*;

public class PoseEstimatorSubsystem extends SubsystemBase {
    private PhotonCamera[] cameras;
    private SwerveDriveRewriteSubsystem drive;
    private WPI_Pigeon2 pigeon;

    private final Pose3d[] aprilTags = new Pose3d[] {
        new Pose3d(596.51 * IN, 12.91 * IN, 269.18 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(596.51 * IN, 12.91 * IN, 203.18 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(596.51 * IN, 12.91 * IN, 137.18 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(622.7 * IN, 22.13 * IN, 45.63 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(0, 22.13 * IN, 45.63 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(26.19 * IN, 12.91 * IN, 137.18 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(26.19 * IN, 12.91 * IN, 203.18 * IN, new Rotation3d(0, 0, 0)),
        new Pose3d(26.19 * IN, 12.91 * IN, 269.18 * IN, new Rotation3d(0, 0, 0))
    };

    private final SwerveDrivePoseEstimator poseEstimator;

    private final Field2d field2d = new Field2d();

    private double[] previousPipelineTimestamp;

    public PoseEstimatorSubsystem(PhotonCamera[] cameras, SwerveDriveRewriteSubsystem drive, WPI_Pigeon2 pigeon) {
        this.cameras = cameras;
        this.drive = drive;
        this.pigeon = pigeon;

        previousPipelineTimestamp = new double[cameras.length];

        for (int i = 0; i < cameras.length; i++) {
            previousPipelineTimestamp[i] = 0;
        }

        ShuffleboardTab tab = Shuffleboard.getTab("Vision");

        poseEstimator = new SwerveDrivePoseEstimator(
            drive.kinematics,
            pigeon.getRotation2d(),
            drive.modulePositions,
            new Pose2d(0, 0, new Rotation2d(0))
        );

        tab.addString("Pose", this::getFormattedPose).withPosition(0, 0);
        tab.add("Field", field2d).withPosition(0, 0);
    }

    @Override
    public void periodic() {
        for (int i = 0; i < cameras.length; i++) {
            var pipelineResult = cameras[i].getLatestResult();
            var resultTimestamp = pipelineResult.getTimestampSeconds();
            if (resultTimestamp != previousPipelineTimestamp[i] && pipelineResult.hasTargets()) {
                previousPipelineTimestamp[i] = resultTimestamp;
                var target = pipelineResult.getBestTarget();
                var fiducialId = target.getFiducialId();
                if (target.getPoseAmbiguity() <= 0.2 && fiducialId > 0 && fiducialId < aprilTags.length) {
                    var targetPose = aprilTags[fiducialId - 1];
                    Transform3d camToTarget = target.getBestCameraToTarget();
                    Pose3d camPose = targetPose.transformBy(camToTarget.inverse());

                    // TODO:
                    //var visionMeasurement = camPose.transformBy("distance from camera to robot")
                    //poseEstimator.addVisionMeasurement(visionMeasurement.toPose2d(), resultTimestamp);
                    poseEstimator.addVisionMeasurement(camPose.toPose2d(), resultTimestamp);
                }
            }
        }

        poseEstimator.update(
            pigeon.getRotation2d(),
            drive.modulePositions
        );

        field2d.setRobotPose(getCurrentPose());
    }

    private String getFormattedPose() {
        var pose = getCurrentPose();
        return String.format(
            "(%.2f, %.2f) %.2f degrees",
            pose.getX(),
            pose.getY(),
            pose.getRotation().getDegrees()
        );
    }

    public Pose2d getCurrentPose() {
        return poseEstimator.getEstimatedPosition();
    }
}
