package utils.gen;

import processing.core.PApplet;
import processing.data.IntList;
import java.util.ArrayList;

public class ColourGen{

    private final PApplet p;

    public ColourGen(PApplet p) {
        this.p = p;
    }

    IntList getPrimaryColour()
    {
        int chosenSecondaryValue = (int)p.random(0,256);
        IntList primaryColour = new IntList();
        primaryColour.append(255);
        primaryColour.append(chosenSecondaryValue);
        primaryColour.append(0);
        primaryColour.shuffle();
        return primaryColour;
    }

    int getSecondaryValueOfColour(IntList primaryColour)
    {
        IntList colourList = primaryColour.copy();
        colourList.removeValue(255);
        colourList.removeValue(0);
        return colourList.get(0);
    }

    IntList getUpperBoundColour(IntList medianColour, int colourRange)
    {
        IntList upperBoundColour = medianColour.copy();
        int secondaryValue = getSecondaryValueOfColour(upperBoundColour);
        int secondaryValueIndex = upperBoundColour.index(secondaryValue);
        int primaryValueIndex = upperBoundColour.index(255);
        if (secondaryValue >= 255 - colourRange) {
            upperBoundColour.set(secondaryValueIndex, 255);
            int primaryValueDecreased = 500 - secondaryValue - colourRange;
            upperBoundColour.set(primaryValueIndex, primaryValueDecreased);
        } else {
            upperBoundColour.set(secondaryValueIndex, secondaryValue + colourRange);
        }
        return upperBoundColour;
    }

    IntList getLowerBoundColour(IntList medianColour, int colourRange)
    {
        IntList lowerBoundColour = medianColour.copy();
        int secondaryValue = getSecondaryValueOfColour(lowerBoundColour);
        int secondaryValueIndex = lowerBoundColour.index(secondaryValue);
        int tertiaryValueIndex = lowerBoundColour.index(0);
        if (secondaryValue < colourRange) {
            lowerBoundColour.set(secondaryValueIndex, 0);
            int tertiaryValueIncreased = colourRange - secondaryValue;
            lowerBoundColour.set(tertiaryValueIndex, tertiaryValueIncreased);
        } else {
            lowerBoundColour.set(secondaryValueIndex, secondaryValue - colourRange);
        }
        return lowerBoundColour;
    }

    IntList getShadowOfColour(IntList colour)
    {
        IntList colourShadow = colour.copy();
        int secondaryValue = getSecondaryValueOfColour(colourShadow);
        int primaryValueIndex = colourShadow.index(255);
        int secondaryValueIndex = colourShadow.index(secondaryValue);
        colourShadow.set(primaryValueIndex, 128);
        colourShadow.set(secondaryValueIndex, (int) secondaryValue/2);
        return colourShadow;
    }

    public IntList getShadowMiddleGround(IntList colour)
    {
        IntList colourShadow = colour.copy();
        int secondaryValue = getSecondaryValueOfColour(colourShadow);
        int primaryValueIndex = colourShadow.index(255);
        int secondaryValueIndex = colourShadow.index(secondaryValue);
        colourShadow.set(primaryValueIndex, 192);
        int newSecondaryValue = secondaryValue/2;
        colourShadow.set(secondaryValueIndex,newSecondaryValue + newSecondaryValue/2);
        return colourShadow;
    }


    public ArrayList<ArrayList<IntList>> analogousColourPaletteGenerator(int colourRange) {

        ArrayList<IntList> medianColour = new ArrayList<IntList>();
        IntList primaryColour = getPrimaryColour();
        IntList primaryColourShadow = getShadowOfColour(primaryColour);
        medianColour.add(primaryColour);
        medianColour.add(primaryColourShadow);

        ArrayList<IntList> maxColour = new ArrayList<IntList>();
        IntList upperBoundColour = getUpperBoundColour(primaryColour, colourRange);
        IntList upperBoundColourShadow = getShadowOfColour(upperBoundColour);
        maxColour.add(upperBoundColour);
        maxColour.add(upperBoundColourShadow);

        ArrayList<IntList> minColour = new ArrayList<IntList>();
        IntList lowerBoundColour = getLowerBoundColour(primaryColour, colourRange);
        IntList lowerBoundColourShadow = getShadowOfColour(lowerBoundColour);
        minColour.add(lowerBoundColour);
        minColour.add(lowerBoundColourShadow);

        ArrayList<ArrayList<IntList>> colourPalette = new ArrayList<ArrayList<IntList>>();
        colourPalette.add(minColour);
        colourPalette.add(medianColour);
        colourPalette.add(maxColour);

        return colourPalette;
    }
}
