package utils.gen;

import processing.core.PApplet;
import processing.data.IntList;
import java.util.ArrayList;


public class MechanicalGen {

    final PApplet p;

    public MechanicalGen(final PApplet pa) {
        p =pa;
    }

    public float angleOfAccidenceGenerator(float passedRadiansAngle, int angleRangeBound)
    {
        if (angleRangeBound > 45)
        {
            float lowerBoundAngle = passedRadiansAngle - PApplet.radians(45);
            float upperBoundAngle = passedRadiansAngle + PApplet.radians(45);
            return p.random(lowerBoundAngle,upperBoundAngle);
        }
        else
        {
            float lowerBoundAngle = passedRadiansAngle - p.radians(angleRangeBound);
            float upperBoundAngle = passedRadiansAngle + p.radians(angleRangeBound);
            return p.random(lowerBoundAngle,upperBoundAngle);
        }
    }

}
