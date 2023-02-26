// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.RailSubsystem;
import frc.robot.utils.Driver;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public Joystick driverController = new Joystick(Constants.CONTROLE1_ID);
  public Joystick systemsController = new Joystick(Constants.CONTROLE2_ID);
  private static DriveSubsystem robotDrive = new DriveSubsystem();
  private static ClawSubsystem claw = new ClawSubsystem();
  private static RailSubsystem rail = new RailSubsystem();
  private static ArmSubsystem arm = new ArmSubsystem();
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  JoystickButton lb = new JoystickButton(driverController, 5);
  double spd = 1;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    lb.whileTrue(new RunCommand(() -> robotDrive.setPower(Driver.povCalc(driverController.getPOV()))));
    robotDrive.setDefaultCommand(new DefaultDrive(robotDrive, driverController));
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link Joystick}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // CONFIGURAR O QUE CADA BOTÃO FAZ
    //double[] powers = {0,1};
    // new JoystickButton(driverController, Constants.BUTTON_Y)
    //   .whenActive(new RunCommand(() ->{
    //     robotDrive.setPower(powers);
    //   }, robotDrive), false);

    new JoystickButton(systemsController, Constants.BUTTON_Y)
      .onTrue((Commands.runOnce(() -> claw.clawActivate())))
      .onFalse(Commands.runOnce(() -> claw.clawDesactivate()));
    
    new JoystickButton(systemsController, Constants.BUTTON_B)
      .toggleOnTrue(Commands.run(() -> {
        rail.manageRailL(systemsController.getRawAxis(Constants.RT));
        rail.manageRailR(systemsController.getRawAxis(Constants.LT));
      }));

    new JoystickButton(systemsController, Constants.RB)
      .whileTrue(Commands.run(() ->{
        arm.armUp(spd); 
        arm.up = true;
      }))
      .onFalse(Commands.run(() -> arm.up = false));
    new JoystickButton(systemsController, Constants.LB)
      .whileTrue(Commands.run(() -> {
        arm.armDown(spd);
        arm.down = false;
      }))
      .onFalse(Commands.run(() -> arm.down = false));
    
    
    // new JoystickButton(systemsController, Constants.BUTTON_B)
    //   .whenPressed(()-> robotShooter.setPower(1))
    //   .whenReleased(()-> robotShooter.setPower(0));

    // new JoystickButton(systemsController, Constants.BUTTON_A)
    //   .whenPressed(()-> robotShooter.setPower(0.5))
    //   .whenReleased(()-> robotShooter.setPower(0));
    
    // if(driverController.getPOV() == 90){
    //   new RunCommand(robotDrive, () ->{
    //     robotDrive.setPower(powers)
    //   });
    //}

    // .whenPressed() para o comando só ser executado com o botão pressionado
    // .whenActive() para o comando continuar executando mesmo após soltar o botão
    // TAMBÉM TEM O .while(Active,etc...) TESTAR
  }

  public Command getAutonomousCommand(){
    return m_chooser.getSelected();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An ExampleCommand will run in autonomous
  // }
}

// new DefaultDrive(robotDrive, driverController.getRawAxis(Constants.STICK_X), driverController.getRawAxis(Constants.STICK_Y), driverController.getRawAxis(Constants.LT), driverController.getRawAxis(Constants.RT), driverController.getRawButton(Constants.BUTTON_A), driverController.getRawButton(Constants.BUTTON_B), driverController.getRawButton(Constants.BUTTON_Y))


