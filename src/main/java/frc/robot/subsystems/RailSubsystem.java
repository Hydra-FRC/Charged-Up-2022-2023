package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.Driver;

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
    this.rail.configNeutralDeadband(0.04);
    this.rail.setNeutralMode(NeutralMode.Brake);;
    
  }

    public void manageRailR(double tL, double tR){
        if((Driver.minMethod(tR) != 0) && (Driver.minMethod(tL) != 0)){
          rail.set(ControlMode.PercentOutput,0);
        }
        else if(Driver.minMethod(tR) != 0){ 
            rail.set(ControlMode.PercentOutput, tR);
            SmartDashboard.putBoolean("Trilho frente", true);
        }
        else if(Driver.minMethod(tL) != 0){
          rail.set(ControlMode.PercentOutput, -tL);
            SmartDashboard.putBoolean("Trilho", true);
        }
        else{
          rail.set(ControlMode.PercentOutput, 0);
          SmartDashboard.putBoolean("Trilho", false);
        }
        SmartDashboard.putNumber("Forca RT", tR);
        SmartDashboard.putNumber("Forca LT", tL);
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


