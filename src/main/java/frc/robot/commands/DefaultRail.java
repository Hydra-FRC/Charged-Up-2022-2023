// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.RailSubsystem;

public class DefaultRail extends CommandBase {
  private final RailSubsystem s_rail;
  private Joystick systemController;
  private double tR, tL;

  public DefaultRail(RailSubsystem subsystem, Joystick controller) {
    s_rail = subsystem;
    this.systemController = controller;
    tR = systemController.getRawAxis(Constants.AXIS_RT);
    tL = systemController.getRawAxis(Constants.AXIS_LT);
    addRequirements(s_rail);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tR = systemController.getRawAxis(Constants.AXIS_RT);
    tL = systemController.getRawAxis(Constants.AXIS_LT);
    s_rail.manageRailR(tR, tL);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
