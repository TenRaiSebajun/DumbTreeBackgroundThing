package utils.object.manipulation;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class ObjectTransformation {

    final PApplet p;

    public ObjectTransformation(final PApplet pa) {
        p =pa;
    }

    public void centerToAPoint(ArrayList<ArrayList<PVector>> shapeList,boolean right){
        if (right){
            float pointX = p.random(400,p.width/2);
            float pointY = 600;
            PVector centreRoot = new PVector(shapeList.get(0).get(0).x,shapeList.get(0).get(0).y);
            PVector displacementVector = new PVector(pointX - centreRoot.x,pointY-centreRoot.y);
            for (ArrayList<PVector> vertexList: shapeList)
            {
                for (PVector vertex: vertexList)
                {
                    vertex.set(vertex.x+displacementVector.x,vertex.y+displacementVector.y);
                }
            }
        }
        else{
            float pointX = p.random(p.width/2,p.width-400);
            float pointY = 600;
            PVector centreRoot = new PVector(shapeList.get(0).get(0).x,shapeList.get(0).get(0).y);
            PVector displacementVector = new PVector(pointX - centreRoot.x,pointY-centreRoot.y);
            for (ArrayList<PVector> vertexList: shapeList)
            {
                for (PVector vertex: vertexList)
                {
                    vertex.set(vertex.x+displacementVector.x,vertex.y+displacementVector.y);
                }
            }
        }

    }

    public ArrayList<ArrayList<PVector>> scalingFunction(ArrayList<ArrayList<PVector>> shapeList ,int scaleBy)
    {
        for (ArrayList<PVector> vertexList:shapeList)
        {
            for (PVector vertex: vertexList)
            {
                PVector newVertex = new PVector(vertex.x/scaleBy,vertex.y/scaleBy);
                vertex.set(newVertex);
            }
        }
        return shapeList;
    }

}
