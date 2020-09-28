package utils.object.manipulation;

import processing.core.PApplet;
import processing.data.IntList;

import java.util.ArrayList;

public class ColourManipulation{

    private final PApplet p;

    public ColourManipulation(final PApplet p) {
        this.p = p ;
    }

    public ArrayList<IntList> pasteliseColourPalette(ArrayList<IntList> arrayIntList)
    {
        ArrayList<IntList> pastelPalette = new ArrayList<IntList>();
        for (IntList intList: arrayIntList)
        {
            IntList colour = new IntList();
            colour.append(intList);
            for (int value: colour)
            {
                colour.set(colour.index(value),value+((255-value)/2));
            }
            pastelPalette.add(colour);
        }
        return pastelPalette;
    }

    public IntList whitenizeColour(IntList colour)
    {
        IntList whiteColour = new IntList();
        for (int number: colour)
        {
            if (number == 255)
            {
                whiteColour.append(number);
            }
            else if (number == 0)
            {
                whiteColour.append(192);
            }
            else
            {
                whiteColour.append(number+(250-number)/2+(250-number)/4);
            }
        }
        return whiteColour;
    }

}
