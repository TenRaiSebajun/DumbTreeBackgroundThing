package main.landscape;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;
import utils.drawing.DrawingUtils;
import utils.gen.ColourGen;
import utils.gen.MechanicalGen;
import utils.gen.RandomUtils;
import utils.object.manipulation.ColourManipulation;
import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.util.ArrayList;

public class Mountains {

    private final PApplet p;
    private final DrawingUtils draw;
    private final IntList mountainColour;
    private final ColourManipulation colourManipulation;

    public Mountains(PApplet p, IntList mountainColour){
        this.p = p;
        this.draw = new DrawingUtils(this.p);
        this.colourManipulation = new ColourManipulation(this.p);
        this.mountainColour = mountainColour;
    }

    public void drawMountainRange() {
        int randomX = (int) p.random(0,p.width-300);
        IntList mountainRangeRange =  new IntList(randomX,randomX+500);
        int numberOfMountains = (int) p.random(1,6);
        IntList currentColour = mountainColour.copy();
        for (int i = 0; i < numberOfMountains; i++)
        {
            drawMountain(mountainRangeRange, currentColour);
            currentColour = colourManipulation.lightenColour(currentColour,10);
        }
    }

    public void drawMountain(IntList mountainPosRange, IntList mountainColour) {
        int horizon = p.height/2;
        int heightOfMountain = (int) p.random(0,p.height/2);
        int currentYCord = p.height/2;
        int currentXCord = (int) p.random(mountainPosRange.get(0),mountainPosRange.get(1));
        ArrayList<PVector> mountainVertices = new ArrayList<PVector>();
        PVector initialVertex = new PVector(currentXCord,currentYCord);
        mountainVertices.add(initialVertex);
        while (currentYCord > heightOfMountain){
            int upOrDown = (int) p.random(0,10);
            if (upOrDown == 1) {
                int changeInY = (int) p.random(0,horizon/8);
                currentYCord = currentYCord + changeInY;
                currentXCord = currentXCord + (int) p.random(changeInY/3,changeInY);
                mountainVertices.add(new PVector(currentXCord,currentYCord));
            }
            else {
                int changeInY = (int) p.random(0,horizon/4);
                currentYCord = currentYCord -changeInY;
                currentXCord = currentXCord + (int) p.random(changeInY/3,changeInY);
                mountainVertices.add(new PVector(currentXCord,currentYCord));
            }
        }
        while (currentYCord < horizon){
            int upOrDown = (int) p.random(0,10);
            if (upOrDown == 1) {
                int changeInY = (int) p.random(0,horizon/8);
                currentYCord = currentYCord - changeInY;
                currentXCord = currentXCord + (int) p.random(changeInY/3,changeInY);
                mountainVertices.add(new PVector(currentXCord,currentYCord));
            }
            else {
                int changeInY = (int) p.random(0,horizon/4 );
                currentYCord = currentYCord + changeInY;
                currentXCord = currentXCord + (int) p.random(changeInY/3,changeInY);
                mountainVertices.add(new PVector(currentXCord,currentYCord));
            }
        }
        mountainVertices.add(new PVector(currentXCord,horizon));
        p.stroke(0,0,0);
        draw.drawShapeWithColour(mountainVertices,mountainColour,false,0);


    }
}
