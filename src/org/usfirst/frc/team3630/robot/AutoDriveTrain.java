package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class AutoDriveTrain extends MecanumDrive {
	
	class PIDfeeder implements PIDSource {
		private PIDSourceType sourceType;
		private double sourceValue;
		
		// filp setting pid soruce
		public void setPIDSourceType(PIDSourceType pidSource) {
			sourceType = pidSource;
		}
		// pid setter source tyupe
		@Override
		public PIDSourceType getPIDSourceType() {
			return sourceType;
		}

		@Override
		public double pidGet() {
			return sourceValue;
		}
		
		public void pidSet(double value) {
			sourceValue = value;
		}
		
	}
	class PIDsetter implements PIDOutput {
		private double out;
		public void pidWrite(double output) {
			out=output;
		}
		public double pidGet(){
			return out;
		}
	}
	
	/**
	 * @author walsl
	 * class which sets up a basic pid subsystem for x,y and themta
	 */
	class PIDSystem {
		public PIDfeeder feeder;
		public PIDsetter setter;
		public PIDController pid;
		
		public PIDSystem (double kp, double ki, double kd){
			pid = new PIDController(kp, ki, kd, feeder, setter);
		}
	}
	
	PIDSystem x;
	PIDSystem y;
	PIDSystem theta;
	// feed in genetic speed controlerds 
	public AutoDriveTrain(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
			SpeedController frontRightMotor, SpeedController rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		x = new PIDSystem(Consts.xKP,Consts.xKI,Consts.xKD);
		y = new PIDSystem(Consts.yKP,Consts.yKI,Consts.yKD);
		theta = new PIDSystem(Consts.thetaKP,Consts.thetaKI,Consts.thetaKD);
	}

	
	/**
	 * drivetrain for autonomus whose pid setpoints are the output of the pid controllers
	 */
	public void autoDrive () {
		
		// auto drive calls the pid controller output from autoDriveTrain
		super.driveCartesian(x.setter.pidGet(), y.setter.pidGet(), theta.setter.pidGet());
	}
}
