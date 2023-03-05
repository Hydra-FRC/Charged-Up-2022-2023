package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class RailSubsystem extends SubsystemBase {
    VictorSPX rail;
  
  public RailSubsystem() {
    initMotor();
  }

  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }

   private void initMotor(){
    rail = new VictorSPX(Constants.MOTOR_RAIL);
  }

    public void manageRailR(double tR){
        if(tR != 0){ 
            rail.set(ControlMode.PercentOutput, tR * Constants.kSlowSpd);
            SmartDashboard.putBoolean("Trilho", true);
        }
        else{
            rail.set(ControlMode.PercentOutput, 0);
            SmartDashboard.putBoolean("Trilho", false);
        }
    }  

    public void manageRailL(double tL){
        if(tL != 0){
          rail.set(ControlMode.PercentOutput, -tL * Constants.kSlowSpd);
            SmartDashboard.putBoolean("Trilho", true);
        }
        else{
            rail.set(ControlMode.PercentOutput, 0);
            SmartDashboard.putBoolean("Trilho", false);
        }
      }

    public void morreEssaDisgrama(){
      rail.set(ControlMode.PercentOutput, 0);
    }

    }


