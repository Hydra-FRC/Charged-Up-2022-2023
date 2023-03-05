package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  //VictorSPX armMotor = new VictorSPX(Constants.MOTOR_ARM);
  public boolean up,down,aM;
  private CANSparkMax sp = new CANSparkMax(10,MotorType.kBrushless);
  private RelativeEncoder spEncoder = sp.getEncoder();



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
    SmartDashboard.putNumber("Encoder Output",spEncoder.getPosition());
  }
 
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void armUp(){
    SmartDashboard.putString("Arm Direction", "Up");
    pneuUP();
  }

  public void armDown(){
    SmartDashboard.putString("Arm Direction", "Down");
    pneuDown();
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
    inP2.set(true);
    outP2.set(false);
    SmartDashboard.putString("Pneumatics", "UP");
  }
  private void pneuDown(){
    inP.set(false);
    outP.set(true);
    inP2.set(false);
    outP2.set(true);
    SmartDashboard.putString("Pneumatics", "DOWN");
  }
  private void pneuNeutral(){
      inP.set(false);
      outP.set(false);
      inP2.set(false);
      outP2.set(false);
    SmartDashboard.putString("Pneumatics", "NEUTRAL");
  }

  public void motorOn(double spd){
    sp.set(spd);
    SmartDashboard.putString("Motor Auxiliar", "Ativado");
    aM = true;
  }

  public void motorOff(){
    sp.set(0);
    SmartDashboard.putString("Motor Auxiliar", "Desativado");
    aM = false;
  }
}
