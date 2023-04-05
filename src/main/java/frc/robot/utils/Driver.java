package frc.robot.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Driver {
  public double leftStick_x, leftStick_y, rightStick_x, rightStick_y, rt, lt;
  private double mag_right = 0;
  private double mag_left = 0;
  private double seno_left = 0;
  private double seno_right = 0;

  public double mL = 0;
  private double mR = 0;

  public Driver(double leftStickX, double leftStickY, double rightStickX, double rightStickY, double lt, double rt) {
    this.leftStick_x = leftStickX;
    this.leftStick_y = -leftStickY;
    this.rightStick_x = rightStickX;
    this.rightStick_y = -rightStickY;
    this.lt = lt;
    this.rt = rt;
  }

  public double[] drive() {
    attValues();
    resetAxis();
    SmartDashboard.putNumber("x2:", rightStick_x);
    SmartDashboard.putNumber("y2:", rightStick_y);
    SmartDashboard.putNumber("mag2:", mag_right);
    SmartDashboard.putNumber("mag:", mag_left);
    SmartDashboard.putNumber("sin2:", seno_right);
    if (rt != 0) {
      if (leftStick_x >= 0.1) {
        if (leftStick_x <= 0.04) {
          leftStick_x = 0;
        }
        SmartDashboard.putString("Condicao", "rt!=0 && leftStick_x>=0");
        mR = (1 - leftStick_x) * rt;
        mL = rt;
      } else if (leftStick_x < 0) {
        if (leftStick_x > -0.04) {
          leftStick_x = 0;
        }
        SmartDashboard.putString("Condicao", "rt!=0 && leftStick_x<0");
        mR = rt;
        mL = (1 + leftStick_x) * rt;
      } else if (rightStick_x >= 0.1) {
        if (rightStick_x <= 0.04) {
          rightStick_x = 0;
        }
        mL = (1 - rightStick_x) * rt;
        mR = rt;
      } else {
        if (rightStick_x > -0.04) {
          rightStick_x = 0;
        }
        mL = rt;
        mR = (1 + rightStick_x) * rt;
      }
    } else if (lt != 0) {
      if (leftStick_x >= 0.1) {
        if (leftStick_x < 0.04) {
          leftStick_x = 0;
        }
        SmartDashboard.putString("Condicao", "lt!=0 && leftStick_x>=0");
        mL = lt;
        mR = (1 - leftStick_x) * lt;
      } else if (leftStick_x < 0) {
        if (leftStick_x > -0.04) {
          leftStick_x = 0;
        }
        SmartDashboard.putString("Condicao", "lt!=0 && leftStick_x<0");
        mL = (1 + leftStick_x) * lt;
        mR = lt;
      } else if (rightStick_x >= 0.1) {
        SmartDashboard.putString("quadrante:", "reverse");
        if (rightStick_x <= 0.04) {
          rightStick_x = 0;
        }
        mR = lt;
        mL = (1 - rightStick_x) * lt;
      } else {
        if (rightStick_x > -0.04) {
          rightStick_x = 0;
        }
        mR = (1 + rightStick_x) * lt;
        mL = lt;
      }
    } else if (mag_left != 0) {
      if (leftStick_x >= 0 && leftStick_y >= 0) {
        SmartDashboard.putString("Condicao", "leftStick_x>=0 && leftStick_y>=0");
        mR = seno_left * mag_left;
        mL = mag_left;
      } else if (leftStick_x < 0 && leftStick_y >= 0) {
        SmartDashboard.putString("Condicao", "leftStick_x<0 && leftStick_y>=0");
        mR = mag_left;
        mL = seno_left * mag_left;
      } else if (leftStick_x < 0 && leftStick_y < 0) {
        SmartDashboard.putString("Condicao", "leftStick_x<0 && leftStick_y<0");
        mR = seno_left * mag_left;
        mL = -mag_left;
      } else if (leftStick_x >= 0 && leftStick_y < 0) {
        SmartDashboard.putString("Condicao", "leftStick_x>=0 && leftStick_y<0");
        mR = -mag_left;
        mL = seno_left * mag_left;
      }
    } else if (mag_right != 0) {
      if (rightStick_x >= 0 && rightStick_y >= 0) {
        SmartDashboard.putString("Condicao", "rightStick_x>=0 && rightStick_y>=0");
        mR = -mag_right;
        mL = -seno_right * mag_right;
      } else if (rightStick_x < 0 && rightStick_y >= 0) {
        SmartDashboard.putString("Condicao", "rightStick_x<0 && rightStick_y>=0");
        mR = -seno_right * mag_right;
        mL = -mag_right;
      } else if (rightStick_x < 0 && rightStick_y < 0) {
        SmartDashboard.putString("Condicao", "rightStick_x<0 && rightStick_y<0");
        mR = mag_right;
        mL = -seno_right * mag_right;
      } else if (rightStick_x >= 0 && rightStick_y < 0) {
        SmartDashboard.putString("Condicao", "rightStick_x<0 && rightStick_y<0");
        mR = -seno_right * mag_right;
        mL = mag_right;
      }
    }

    double powers[] = { this.mL, this.mR };
    return powers;
  }

  // public void quadCalc() {
  // double x = this.leftStick_x;
  // double y = this.leftStick_y;
  // double mag_left = this.mag_left;
  // // Quadrante 1
  // if (y >= 0 && x >= 0) {
  // this.mL = mag_left; // Constante
  // this.mR = (2 * seno_left - 1) * mag_left; // Varia
  // // Quadrante 2
  // } else if (y >= 0 && x <= 0) {
  // this.mL = (2 * seno_left - 1) * mag_left; // Varia
  // this.mR = mag_left; // Constante
  // // Quadrante 3
  // } else if (y < 0 && x < 0) {
  // this.mL = (2 * seno_left + 1) * mag_left; // Varia
  // this.mR = -mag_left; // Constante
  // // Quadrante 4
  // } else if (y < 0 && x >= 0) {
  // this.mL = -mag_left; // Constante
  // this.mR = (2 * seno_left + 1) * mag_left; // Varia
  // }
  // }

  // public void triggerCalc() {
  // if (Math.abs(leftStick_x) < 0.04)
  // leftStick_x = 0;

  // if (rt != 0) {
  // if (leftStick_x >= 0) {
  // this.mL = rt;
  // this.mR = rt * (1 - leftStick_x);
  // } else if (leftStick_x < 0) {
  // this.mL = rt * (1 + leftStick_x);
  // this.mR = rt;
  // }
  // } else if (lt != 0) {
  // if (leftStick_x >= 0) {
  // lt = -lt;
  // this.mL = lt * (1 - leftStick_x);
  // this.mR = lt;
  // } else if (leftStick_x < 0) {
  // this.mL = lt;
  // this.mR = lt * (1 + leftStick_x);
  // }
  // }
  // }

  // public void movementCalc() {
  // // Verificação do analogico esquerdo
  // if (minMethod(mag_left) != 0) {
  // // Calculo dos quadrantes
  // quadCalc();
  // } else if (minMethod(mag_right) != 0) {
  // // Calculo dos quadrantes
  // // reverseQuadCalc();
  // }
  // }

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
    this.lt = -lt;
    this.mag_left = Math.hypot(leftStick_x, leftStick_y);
    this.seno_left = leftStick_y / mag_left;
    this.mag_right = Math.hypot(rightStick_x, rightStick_y);
    this.seno_right = rightStick_y / mag_right;
  }

  public static double[] povCalc(int pov, double spd) {
    double[] power = new double[2];

    switch (pov) {

      case 0:
        power[0] = 1;
        power[1] = 1;
        break;

      case 45:
        power[0] = 1;
        power[1] = 0;
        break;

      case 90:
        power[0] = 1;
        power[1] = -1;
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
        power[0] = -1;
        power[1] = 1;
        break;

      case 315:
        power[0] = 0;
        power[1] = 1;
        break;

      default:
        power[0] = 0;
        power[1] = 0;
    }

    power[0] *= spd;
    power[1] *= spd;

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

  public static double minMethod(double a) {
    if (Math.abs(a) < 0.05) {
      return 0;
    }
    return a;
  }
}