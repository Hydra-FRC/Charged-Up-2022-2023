// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.stream.DoubleStream;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class BalanceScore extends CommandBase {
  /** Creates a new BalanceScore. */
  private final DriveSubsystem m_subsystem;
  private AHRS navX2;
  private double rawX = 0;

  public BalanceScore(DriveSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    navX2 = m_subsystem.ahrs;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.setPower(DoubleStream.of(Constants.kNormalSpd,Constants.kNormalSpd).toArray());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("Autonomo:", "On");
    rawX = navX2.getRoll();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setPower(DoubleStream.of(0,0).toArray());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(rawX) > 25) return true;
    else return false;
  }
}
