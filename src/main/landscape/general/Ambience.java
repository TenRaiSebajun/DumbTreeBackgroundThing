package main.landscape.general;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;
import java.util.ArrayList;

public class Ambience {

    private final PApplet p;
    private final ArrayList<IntList> backgroundPalette;

    public Ambience(PApplet p, ArrayList<IntList> backgroundPalette) {
        this.p = p;
        this.backgroundPalette = backgroundPalette;
    }

    public void sunHalo(int sunPositionX, int horizonPoint, int sunColour)
    {
        int b1 = p.color(255,255,255,0);
        int newSunColour = p.color(sunColour,200);
        setGradient(sunPositionX, 0, p.width, horizonPoint , newSunColour, b1, 2);
        setGradient(sunPositionX-p.width, 0,p.width-1, horizonPoint , b1, newSunColour, 2);
    }

    public void makeSunMoon(IntList moonSunColour, boolean right){
        int moonSunColourInt = p.color(moonSunColour.get(0),moonSunColour.get(1),moonSunColour.get(2));
        int sizeOfMoon = (int) p.random(50,500);
        PVector moonPosition = getMoonPosition(right);
        p.fill(moonSunColourInt);
        p.circle(moonPosition.x,moonPosition.y,sizeOfMoon);
        p.noStroke();
        sunHalo((int) moonPosition.x,p.height/2,moonSunColourInt);
    }

    PVector getMoonPosition(boolean decider) {
        if (decider){
            return new PVector(p.random(p.width/2+200,p.width),p.random(0,p.height/2));
        }
        else{
            return new PVector(p.random(0,p.width/2-200),p.random(0,p.height/2));
        }
    }



    public void createBackground()
    {
        int b2 = p.color(backgroundPalette.get(0).get(0),backgroundPalette.get(0).get(1),backgroundPalette.get(0).get(2));
        int b1 = p.color(backgroundPalette.get(1).get(0),backgroundPalette.get(1).get(1),backgroundPalette.get(1).get(2));
        setGradient(0, 0, p.width, p.height, b1, b2, 1);
    }

    public void reEstablishHorizon(IntList midColour)
    {
        int b2 = p.color(midColour.get(0),midColour.get(1),midColour.get(2));
        int b1 = p.color(backgroundPalette.get(1).get(0),backgroundPalette.get(1).get(1),backgroundPalette.get(1).get(2));
        setGradient(0, p.height/2, p.width, p.height, b1, b2, 1);
    }

    public void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

        p.noFill();

        if (axis == 1) {  // Top to bottom gradient
            for (int i = y; i <= y+h; i++) {
                float inter = p.map(i, y, y+h, 0, 1);
                int c = p.lerpColor(c1, c2, inter);
                p.stroke(c);
                p.line(x, i, x+w, i);
            }
        }
        else if (axis == 2) {  // Left to right gradient
            for (int i = x; i <= x+w; i++) {
                float inter = p.map(i, x, x+w, 0, 1);
                int c = p.lerpColor(c1, c2, inter);
                p.stroke(c);
                p.line(i, y, i, y+h);
            }
        }
    }

}
