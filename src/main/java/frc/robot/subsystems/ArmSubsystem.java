package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /**Creates a new ExampleSubsystem.*/
  VictorSPX armMotor = new VictorSPX(Constants.MOTOR_ARM);
  public boolean up,down,aM;
  private CANSparkMax sp = new CANSparkMax(10,MotorType.kBrushless);
  private double spEnconderRaw = 0;
  private Timer timer = new Timer();
  private double spdC = 0.5;
  double time;

  Compressor comp = new Compressor(1, PneumaticsModuleType.REVPH);
  Solenoid outP = new Solenoid(PneumaticsModuleType.REVPH,Constants.PISTON1_OUT);
  Solenoid inP = new Solenoid(PneumaticsModuleType.REVPH, Constants.PISTON1_IN); 
  Solenoid inP2 = new Solenoid(PneumaticsModuleType.REVPH, Constants.PISTON2_IN);
  Solenoid outP2 = new Solenoid(PneumaticsModuleType.REVPH, Constants.PISTON2_OUT);
  
  public ArmSubsystem() {
    sp.setInverted(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    armNeutral();
    time = timer.get();
   // spEnconderRaw = sp.getEncoder().getPosition();
    SmartDashboard.putNumber("Encoder Output", spEnconderRaw);
    SmartDashboard.putNumber("Motor Speed", spdC);
  }
 
  @Override
  public void simulationPeriodic() { }

  public void armUp(){
    SmartDashboard.putString("Arm Direction", "Up");
    timer.start();
    // if(time<0.3){
    //   pneuDown();
    // }
    // if(time < 1.1 && (time > 0.3)){
    //   SmartDashboard.putString("MOTOR:","OFF");
    //   motorOn(0.5);
    //   pneuUP();
    // }
    // else{
    //   SmartDashboard.putString("MOTOR:","OFF");
    //   motorOff();
    // }
    motorOn();
    pneuUP();
    SmartDashboard.putNumber("TIME:",time);
    // if(spEnconderRaw > (0.11 * 64)) pneuUP();
    // if(spEnconderRaw < (0.11 * 64))
    //   motorOn(0.5); 
    // else
    //   motorOff(); 
  }

  public void armDown(){
    timer.reset();
    timer.stop();

    SmartDashboard.putString("Arm Direction", "Down");
    pneuDown();
    motorOff();
  } 

  private void armNeutral(){
    if(up == false &&  down == false){ 
    SmartDashboard.putString("Arm Direction", "Neutral");
    pneuNeutral();
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
      inP2.set(false);
      outP2.set(false);
    SmartDashboard.putString("Pneumatics", "NEUTRAL");
  }

  public void motorOn(){
    sp.set(spdC);
    SmartDashboard.putString("Motor Auxiliar", "Ativado");
    aM = true;
  }

  public void motorOff(){
    sp.set(0);
    SmartDashboard.putString("Motor Auxiliar", "Desativado");
    aM = false;
  }

  public double speedConfig(){
    spdC += 0.25;
    if(spdC > 1) spdC = 0;
    return spdC;
  }
}
