package main.landscape.general;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;
import utils.gen.ColourGen;

import java.util.ArrayList;

public class Ambience {

    private PApplet p;
    private final ArrayList<IntList> backgroundPalette;
    private ColourGen colourGen;

    public Ambience(PApplet p, ArrayList<IntList> backgroundPalette) {
        this.p = p;
        this.backgroundPalette = backgroundPalette;
        this.colourGen = new ColourGen(this.p);
    }

    public void sunHalo(int sunPositionX, int horizonPoint, int sunColour)
    {
        int b1 = p.color(255,255,255,0);
        int newSunColour = p.color(sunColour,200);
        setGradient(sunPositionX, 0, p.width, horizonPoint , newSunColour, b1, 2);
        setGradient(sunPositionX-p.width, 0,p.width-1, horizonPoint , b1, newSunColour, 2);
    }

    public void makeSunMoon(ArrayList<IntList> moonSunColour, boolean right,ArrayList<IntList> backgroundPalette){
        int decider = (int) p.random(0,2);
        if (decider == 1) {
            int nightOrDay = (int) p.random(0,2);
            if (nightOrDay == 1) {
                IntList nightTimeColuor = colourGen.getShadow(backgroundPalette.get(1));
                IntList nightTimeColour2 = colourGen.getShadow(nightTimeColuor);
                p.noStroke();
                p.fill(nightTimeColour2.get(0), nightTimeColour2.get(1), nightTimeColour2.get(2));
                p.rect(0, 0, p.width, p.height / 2);
                int numberOfMoons = (int) p.random(0, 5);
                float previousMoonPositionX = 0;
                for (int i = 0; i < numberOfMoons; i++) {
                    int moonSunColourInt = p.color(moonSunColour.get(0).get(0), moonSunColour.get(0).get(1), moonSunColour.get(0).get(2));
                    int sizeOfMoon = (int) p.random(100, 500);
                    PVector moonPosition = getSunMoonPosition(right, true, previousMoonPositionX);
                    p.fill(moonSunColourInt);
                    p.circle(moonPosition.x, moonPosition.y, sizeOfMoon);
                    p.noStroke();
                    drawDetailsOfAMoon(moonPosition, sizeOfMoon, nightTimeColour2);
                }
            }
            else {
                int numberOfMoons = (int) p.random(0, 5);
                float previousMoonPositionX = 0;
                for (int i = 0; i < numberOfMoons; i++) {
                    int moonSunColourInt = p.color(moonSunColour.get(0).get(0), moonSunColour.get(0).get(1), moonSunColour.get(0).get(2));
                    int sizeOfMoon = (int) p.random(100, 500);
                    PVector moonPosition = getSunMoonPosition(right, true, previousMoonPositionX);
                    p.fill(moonSunColourInt);
                    p.circle(moonPosition.x, moonPosition.y, sizeOfMoon);
                    p.noStroke();
                    drawDetailsOfAMoon(moonPosition, sizeOfMoon, backgroundPalette.get(1));
                }
            }
        }
        else {
            int moonSunColourInt = p.color(moonSunColour.get(0).get(0), moonSunColour.get(0).get(1), moonSunColour.get(0).get(2));
            int sizeOfSun = (int) p.random(250,500);
            PVector sunPosition = getSunMoonPosition(right,false, 0);
            p.fill(moonSunColourInt);
            p.circle(sunPosition.x, sunPosition.y, sizeOfSun);
            sunHalo((int) sunPosition.x,p.height/2,moonSunColourInt);
        }
    }

    void drawDetailsOfAMoon(PVector centreOfCircle, int sizeOfCircle,IntList moonShadow) {
        int moonShadowColour = p.color(moonShadow.get(0),moonShadow.get(1),moonShadow.get(2));
        int sizeOfMoonShadow = sizeOfCircle-sizeOfCircle/3;
        p.noStroke();
        p.fill(moonShadowColour);
        p.arc(centreOfCircle.x,centreOfCircle.y,sizeOfCircle,sizeOfCircle,-p.PI/2,p.PI/2);
        p.arc(centreOfCircle.x+1,centreOfCircle.y,sizeOfMoonShadow,sizeOfCircle,p.PI/2,p.PI+p.PI/2);
    }

    void makeCrater(PVector centreOfCircle, int sizeOfCircle, IntList moonColour) {

        int sizeOfCrater = sizeOfCircle/4;
        int sizeOfShadow = sizeOfCrater/8;
        p.noStroke();
        p.fill(0, 0, 0, 100);
        p.arc(centreOfCircle.x,centreOfCircle.y,sizeOfCrater+30,sizeOfCrater,-p.PI/2,p.PI/2);
        p.noStroke();
        p.fill(moonColour.get(0), moonColour.get(1), moonColour.get(2));
        p.arc(centreOfCircle.x-1,centreOfCircle.y,sizeOfCrater,sizeOfCrater,-p.PI/2,p.PI/2);


        p.fill(0, 0, 0,100);
        p.arc(centreOfCircle.x,centreOfCircle.y,sizeOfCrater,sizeOfCrater,p.PI/2,p.PI+p.PI/2);
        p.fill(moonColour.get(0), moonColour.get(1), moonColour.get(2));
        p.arc(centreOfCircle.x+1,centreOfCircle.y,sizeOfCrater-30,sizeOfCrater,p.PI/2,p.PI+p.PI/2);

    }

    PVector getSunMoonPosition(boolean decider, boolean moon, float previousPositionX) {
        if (moon) {
            float xCord = previousPositionX;
            while (xCord > previousPositionX - 100 && xCord < previousPositionX + 100 ) {
                xCord = p.random(0,p.width);
            }
            return new PVector(xCord, p.random(0, p.height / 2));
            }
        else {
            if (decider) {
                return new PVector(p.random(p.width / 2 + 200, p.width), p.random(0, p.height / 2));
            } else {
                return new PVector(p.random(0, p.width / 2 - 200), p.random(0, p.height / 2));
            }
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
