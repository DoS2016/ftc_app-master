package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Matrix34F;
import com.vuforia.Tool;
import com.vuforia.Vec2F;
import com.vuforia.Vec3F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Arrays;

/**
 * Created by main2 on 9/29/2017.
 */
@Autonomous(name="fancy", group ="Concept")
public class VuforiaOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "ARP7n43/////AAAAGdGoThGE8k4YowI9EbuCTToRW5VvobacImha3msx7xPnmPUDGiTKRUxcagzehV6BEX4iIdhDpwbWDbKUxKCDvITmAR3E13KNy0uBnk61DpT4IrxJRDbLs3GkrxwSbrCK088QC2XY02KmMxY9kKMSNvg0HuNtB8IuFyo3wgIdkQhZOsDUpKM210oaIpRzyiz/XPez6hWYlfKJEUSGicvyhj1oR6++ey9mk6zKQDxRnSNJJqr6qTkdzs2aj0dV+7xZRpbFVTPO9Q4i955vzjdmS7Z6+FYVIqy8PGFKyT5YejHg/EYG+AJ4VHggr/+zS8YDZDgPpC0xgvTIh7uDz0idF7u3Jj0o68aPCCnqoGtaogTd";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizerImplSubclass vuforia = new VuforiaLocalizerImplSubclass(params);

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        waitForStart();

        relicTrackables.activate();

        while(opModeIsActive()){

            if(vuforia.rgb != null){

                Bitmap bm = Bitmap.createBitmap(vuforia.rgb.getWidth(), vuforia.rgb.getHeight(), Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(vuforia.rgb.getPixels());



            }



            for(VuforiaTrackable track : relicTrackables){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) track.getListener()).getRawPose();

                if(pose != null){
                    Matrix34F rawPose = new Matrix34F();
                    float[] poseData = Arrays.copyOfRange(pose.transposed().getData(), 0, 12);
                    rawPose.setData(poseData);

                    Vec2F point = Tool.projectPoint(vuforia.getCameraCalibration(), rawPose, new Vec3F(0,0,0));


                }


            }
            telemetry.update();

        }


    }
}
