package utils.gen;

import processing.core.PApplet;
import processing.data.IntList;
import java.util.ArrayList;

public class RandomUtils {

    final PApplet p;

    public RandomUtils(PApplet p) {
        this.p = p;
    }
    public boolean continueDrawingYesOrNo(int currentNodeDegree, int upperBoundOnNumberOfNodes, int lowerBoundOnNumberOfNodes) {
        if (currentNodeDegree == upperBoundOnNumberOfNodes)
        {
            return false;
        }
        else if (currentNodeDegree < lowerBoundOnNumberOfNodes)
        {
            return true;
        }
        else
        {
            if ((int) p.random(upperBoundOnNumberOfNodes - currentNodeDegree) > 0)
            {
                return true;
            }
            else return false;
        }
    }

    public int currentBranchLength(int treeSize,int currentNodeDegree, int multiplier)
    {
        return (treeSize/currentNodeDegree)*multiplier;
    }

    public int numberOfBranches(int currentNodeDegree, int terminalBushinessBound, int initialBranchBound)
    {
        int branches = (int) p.random(1, initialBranchBound + currentNodeDegree);
        return Math.min(branches, terminalBushinessBound);
    }

}
