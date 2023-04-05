// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.stream.DoubleStream;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TaxiScore extends CommandBase {
  /** Creates a new TaxiScore. */
  private final DriveSubsystem m_Subsystem;
  private Timer clock;
  boolean back = true;
  public TaxiScore(DriveSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Subsystem = subsystem;
    addRequirements(m_Subsystem);
    clock = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    clock.start();
    SmartDashboard.putString("State", "Initializing");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(back)
      m_Subsystem.setPower(DoubleStream.of(-Constants.kNormalSpd,-Constants.kNormalSpd).toArray());
    else if(back == false){
      m_Subsystem.setPower(DoubleStream.of(Constants.kNormalSpd,Constants.kNormalSpd).toArray());
      SmartDashboard.putString("State", "Executing");
      SmartDashboard.putNumber("Clock", clock.get());

    }
    if(clock.get() > 1)
      back = false;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Subsystem.setPower(DoubleStream.of(0,0).toArray());
    clock.stop();
    clock.reset();
    SmartDashboard.putString("State", "Ended");
    SmartDashboard.putNumber("Clock", clock.get());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(clock.get() > 4) return true;
    else return false;
  }
}
