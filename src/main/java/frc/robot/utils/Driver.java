package frc.robot.utils;

import frc.robot.Constants;

public class Driver {
  public double leftStick_x, leftStick_y, rightStick_x, rightStick_y, rt, lt;
  private double mag_right = 0;
  private double mag_left = 0;
  private double seno_left = 0;
  private double seno_right = 0;

  public double mL = 0;
  private double mR = 0;

  public Driver(double leftStickX, double leftStickY,double rightStickX,double rightStickY, double lt, double rt) {
    this.leftStick_x = leftStickX;
    this.leftStick_y = -leftStickY;
    this.rightStick_x = rightStickX;
    this.rightStick_y = -rightStickY;
    this.lt = lt;
    this.rt = rt;
  }

  public double[] drive() {
    attValues();
    movementCalc();
    triggerCalc();
    resetAxis();

    double powers[] = { this.mL, this.mR };
    return powers;
  }


  public void quadCalc() {
    double x = this.leftStick_x;
    double y = this.leftStick_y;
    double mag = this.mag_left;
    // Quadrante 1
    if (y >= 0 && x >= 0) {
      this.mL = mag; // Constante
      this.mR = (2 * seno_left - 1) * mag; // Varia
      // Quadrante 2
    } else if (y >= 0 && x <= 0) {
      this.mL = (2 * seno_left - 1) * mag; // Varia
      this.mR = mag; // Constante
      // Quadrante 3
    } else if (y < 0 && x < 0) {
      this.mL = (2 * seno_left + 1) * mag; // Varia
      this.mR = -mag; // Constante
      // Quadrante 4
    } else if (y < 0 && x >= 0) {
      this.mL = -mag; // Constante
      this.mR = (2 * seno_left + 1) * mag; // Varia
    }
  }

  public void triggerCalc() {
    if (Math.abs(leftStick_x) < 0.04)
      leftStick_x = 0;

    if (rt != 0) {
      if (leftStick_x >= 0) {
        this.mL = rt;
        this.mR = rt * (1 - leftStick_x);
      } else if (leftStick_x < 0) {
        this.mL = rt * (1 + leftStick_x);
        this.mR = rt;
      }
    } else if (lt != 0) {
      if (leftStick_x >= 0) {
        lt = -lt;
        this.mL = lt * (1 - leftStick_x);
        this.mR = lt;
      } else if (leftStick_x < 0) {
        this.mL = lt;
        this.mR = lt * (1 + leftStick_x);
      }
    }
  }

  public void movementCalc() {
    //Verificação do analogico esquerdo
    if (minMethod(mag_left) != 0) {
      //Calculo dos quadrantes
      quadCalc();
    }
    else if(minMethod(mag_right)!=0){
      // Calculo dos quadrantes
      //reverseQuadCalc();
    }
  }

  private void resetAxis() {
    // Verificação de inatividade dos analogicos
    if (mag_left < 0.1) {
      leftStick_x = 0;
      leftStick_y = 0;
      mag_left = 0;
    }
    // Verificação da inatividade de ambos analogicos
    if (mag_left < 0.1 && mag_right < 0.1 && rt == 0 && lt == 0) {
      this.mL = 0;
      this.mR = 0;
    }
  }

  private void attValues() {
    this.mag_left = Math.hypot(leftStick_x, leftStick_y);
    this.seno_left = leftStick_y / mag_left;
    this.mag_right = Math.hypot(rightStick_x, rightStick_y);
    this.seno_right = rightStick_y / mag_right;
  }

  public static double[] povCalc(int pov){
    double[] power = new double[2];

    switch(pov){

      case 0:
        power[0] = 1;
        power[1] = 1;
        break;

      case 45:
        power[0] = 0;
        power[1] = 1;
        break;

      case 90:
        power[0] = -1;
        power[1] = 1;
        break;

      case 135:
        power[0] = -1;
        power[1] = 0;
        break;

      case 180:
        power[0] = -1;
        power[1] = -1;
        break;

      case 225:
        power[0] = 0;
        power[1] = -1;
        break;

      case 270:
        power[0] = 1;
        power[1] = -1;
        break;

      case 315:
        power[0] = 1;
        power[1] = 0;
        break;
 
      default:
        power[0] = 0;
        power[1] = 0;
    }

    power[0]*= Constants.kSlowSpd;
    power[1]*= Constants.kSlowSpd;

    return power;


  }

  public void reverseQuadCalc() {
    seno_right = rightStick_y / mag_right;
    // Quadrante 1
    if (rightStick_y >= 0 && rightStick_x >= 0) {
      mR = -mag_right;
      mL = (-2 * seno_right + 1) * mag_right;
      // Quadrante 2
    } else if (rightStick_y >= 0 && rightStick_x < 0) {
      mR = (-2 * seno_left + 1) * mag_right;
      mL = -mag_right;
      // Quadrante 3
    } else if (rightStick_y < 0 && rightStick_x < 0) {
      mR = (-2 * seno_left - 1) * mag_right;
      mL = mag_right;
      // Quadrante 4
    } else if (rightStick_y < 0 && rightStick_x >= 0) {
      mR = mag_right;
      mL = (-2 * seno_left - 1) * mag_right;
    }
  }
  
  public double minMethod(double a) {
    if(Math.abs(a) < 0.04){
      return 0;
    }  
    return a;
  }
}
