/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.MOVEMENT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/*
 * This OpMode executes a Tank Drive control TeleOp a direct drive robot
 * The code is structured as an Iterative OpMode
 *
 * In this mode, the left and right joysticks control the left and right motors respectively.
 * Pushing a joystick forward will make the attached motor drive forward.
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Robot: Teleop Tank", group="Robot")
//@Disabled
public class EpicMovement2 extends OpMode{

    /* Declare OpMode members. */
    public DcMotor  leftDrive = null;
    public DcMotor  rightDrive = null;
    public DcMotor spoutTake = null;
    public DcMotor leftArm = null;
    public Servo knockeroverer = null;

    //public Servo    rightClaw   = null;

    public TouchSensor slideLimiter;

    //double clawOffset = 0;

    public static final double OUT_KNOCKEROVERER   =  0.3;
    public static final double IN_KNOCKEROVERER = 0.7;
    // public static final double CLAW_SPEED  = 0.02 ;        // sets rate to move servo
    public static final int ARM_UP = 500;   // Run arm motor up at 50% power
    public static final int ARM_DOWN  = 20;   // Run arm motor down at -25% power

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Define and Initialize Motors
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftArm = hardwareMap.get(DcMotor.class, "slides");
        spoutTake = hardwareMap.get(DcMotor.class, "outTake");
        slideLimiter = hardwareMap.get(TouchSensor.class, "limiter");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        spoutTake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftArm.setDirection(DcMotor.Direction.FORWARD);
        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Define and initialize ALL installed servos.
        knockeroverer = hardwareMap.get(Servo.class, "Knockeroverer");
        knockeroverer.setPosition(IN_KNOCKEROVERER);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double leftWheel;
        double rightWheel;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
        leftWheel = gamepad1.left_stick_y;
        rightWheel = gamepad1.right_stick_y;

        leftDrive.setPower(leftWheel * 0.8);
        rightDrive.setPower(rightWheel/0.8);

        // Use gamepad left & right Bumpers to wipe and swipe the knockeroverer
        if (gamepad2.left_bumper) {
            knockeroverer.setPosition(IN_KNOCKEROVERER);
        }
        else if (gamepad2.right_bumper){
            knockeroverer.setPosition(OUT_KNOCKEROVERER);
        }

        if (gamepad1.right_bumper){
            spoutTake.setPower(1);
        }
        else if (gamepad1.b){
            spoutTake.setPower(0);
        }
            //clawOffset -= CLAW_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        //clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        //rightClaw.setPosition(MID_SERVO - clawOffset);

        // Use game pad buttons to move the arm up (Y) and down (A)


        if (gamepad2.dpad_up){
            leftArm.setPower(0.6);
        }
        /*
        else if (gamepad2.dpad_down){
            leftArm.setTargetPosition(ARM_DOWN);
        }
        */
        if (slideLimiter.isPressed()){
            terminateOpModeNow();
        }



        // Send telemetry message to signify robot running;
        //telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("left",  "%.2f", leftWheel);
        telemetry.addData("right", "%.2f", rightWheel);
        telemetry.addData("slidePos:","%.2f", (double) leftArm.getCurrentPosition());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
