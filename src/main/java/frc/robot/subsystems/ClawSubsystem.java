package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    VictorSPX claw;
    Timer time = new Timer();
    boolean clawIsOpen = false;
  public ClawSubsystem() {
    initMotor();
  }

  @Override
  public void periodic() {
    if(clawIsOpen){
      periodicClaw();
    }
  }

  @Override
  public void simulationPeriodic() {
    
  }

  private void initMotor(){
    claw = new VictorSPX(Constants.MOTOR_CLAW);
  }

  private void periodicClaw(){
    
      if(time.get() == 0)  time.start();
      claw.set(ControlMode.PercentOutput, 0.5);
      SmartDashboard.putNumber("Timer",time.get());
      SmartDashboard.putBoolean("Garra", true);
      if(time.get() > 2){
        SmartDashboard.putNumber("Timer",time.get());
        time.reset();
        time.stop();
        claw.set(ControlMode.PercentOutput, 0);
        SmartDashboard.putBoolean("Garra", false);
        clawIsOpen = false;
        time.start();
      }
  
  }

  public void clawActivate(){
    clawIsOpen = true;
    time.start();
  }

  public void clawDesactivate(){
    clawIsOpen = false;
    time.stop();
    time.reset();
  }


}

