package utils.drawing;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

import java.util.ArrayList;

public class DrawingUtils {

    final PApplet p;

    public DrawingUtils(final PApplet pa) {
        p =pa;
    }

    public void drawShapeWithColour(ArrayList<PVector> vertexList, IntList colourValues, boolean stroke, int strokeColour)
    {
        if (stroke) p.stroke(strokeColour); else p.noStroke();
        p.beginShape();
        for (PVector currentVertex : vertexList) {
            p.vertex(currentVertex.x, currentVertex.y);
        }
        p.fill(colourValues.get(0), colourValues.get(1), colourValues.get(2));
        p.endShape(p.CLOSE);
    }

    public void drawCollection(ArrayList<ArrayList<PVector>> shapeCollection, ArrayList<IntList> paletteCollection){
        for (int i = 0; i <  shapeCollection.size(); i++)
        {
            drawShapeWithColour(shapeCollection.get(i),paletteCollection.get(i),false,0);
        }
    }

    public void fillWithColour(IntList colour)
    {
        p.fill(colour.get(0),colour.get(1),colour.get(2));
    }

    public boolean rightDecider()
    {
        int right = (int) p.random(0,2);
        if (right == 1){
            return true;
        }
        else{
            return false;
        }
    }

}
