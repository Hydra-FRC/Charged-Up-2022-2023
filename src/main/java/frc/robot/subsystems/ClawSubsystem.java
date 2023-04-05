package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
  CANSparkMax claw;
  Timer time = new Timer();
  boolean clawIsOpen = false;

  public ClawSubsystem() {
    initMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Encoder", claw.getEncoder().getPosition());
    SmartDashboard.putBoolean("Is Opem", clawIsOpen);
  }

  @Override
  public void simulationPeriodic() {

  }

  private void initMotor() {
    this.claw = new CANSparkMax(11, MotorType.kBrushless);
    claw.setInverted(true);
  }

  public void clawActivate() {

    claw.set(0.25);
    clawIsOpen = true;

    // if(!clawIsOpen) claw.set(0.25);

    // if ((claw.getEncoder().getPosition() > 0.2) && (!clawIsOpen)) {
    //   claw.set(0);
    //   clawIsOpen = true;
    // }

  }

  public void clawDesactivate() {
    clawIsOpen = false;
    claw.set(0);
  }

}
