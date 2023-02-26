package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  VictorSPX armMotor;
  public boolean up,down;
  private int i = 0;


  Compressor comp = new Compressor(1, PneumaticsModuleType.REVPH);
  Solenoid outP = new Solenoid(PneumaticsModuleType.REVPH,Constants.PISTON_OUT);
  Solenoid inP = new Solenoid(PneumaticsModuleType.REVPH, Constants.PISTON_IN); 

  
  public ArmSubsystem() {
    armMotor = new VictorSPX(Constants.MOTOR_ARM);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    armNeutral();
    SmartDashboard.putNumber("Arm Control", i);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void armUp(double power){
    if(i < Constants.kMaxExtension){ 
      armMotor.set(ControlMode.PercentOutput, power);
      SmartDashboard.putString("Arm Direction", "Up");
      pneuUP();
      i++;
    } else {
      SmartDashboard.putString("Arm Direction", "Up Locked");
      armMotor.set(ControlMode.PercentOutput, 0);
      pneuNeutral();
    }
  }

  public void armDown(double power){
    if(i > 0){ 
      power = -power;
      armMotor.set(ControlMode.PercentOutput, power);
      SmartDashboard.putString("Arm Direction", "Down");
      i--;
      pneuDown();
    } else {
      SmartDashboard.putString("Arm Direction", "Down Locked");
      armMotor.set(ControlMode.PercentOutput, 0);
      pneuNeutral();
    }
  }

  private void armNeutral(){
    if(up == false &&  down == false){ 
    SmartDashboard.putString("Arm Direction", "Neutral");
    armMotor.set(ControlMode.PercentOutput, 0);
    }
  }

  private void pneuUP(){
    inP.set(true);
    outP.set(false);
    SmartDashboard.putString("Pneumatics", "UP");
  }
  private void pneuDown(){
    inP.set(false);
    outP.set(true);
    SmartDashboard.putString("Pneumatics", "DOWN");
  }
  private void pneuNeutral(){
    inP.set(false);
    outP.set(false);
    SmartDashboard.putString("Pneumatics", "NEUTRAL");
  }
}
