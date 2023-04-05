package frc.robot.utils;

public class SystemDriver {
    
    public static double auxiliarMotorSpdAdjust(double spd){
        if(spd < 1) return spd += 0.25;
        else return spd -= 1;
    }
}
