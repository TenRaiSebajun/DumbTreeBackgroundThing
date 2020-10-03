package main;

import main.landscape.Forest;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

import java.util.ArrayList;

import utils.drawing.DrawingUtils;
import utils.gen.*;
import utils.object.manipulation.ColourManipulation;
import utils.object.manipulation.ObjectTransformation;
import utils.gen.MechanicalGen;
import utils.gen.RandomUtils;
import main.landscape.general.Ambience;

public class MainClass extends PApplet {

    public static void main(String[] args)
    {
        PApplet.main("main.MainClass",args);
    }

    ColourGen colourGen = new ColourGen(this);
    ObjectTransformation objectTrans = new ObjectTransformation(this);
    ColourManipulation colourManipulation = new ColourManipulation(this);
    MechanicalGen mechanicalGen = new MechanicalGen(this);
    RandomUtils rand = new RandomUtils(this);
    DrawingUtils draw = new DrawingUtils(this);

    ArrayList<ArrayList<IntList>> palette = colourGen.analogousColourPaletteGenerator(200);
    ArrayList<ArrayList<PVector>> globalShapeList = new ArrayList<ArrayList<PVector>>();
    ArrayList<IntList> globalColourList = new ArrayList<IntList>();
    ArrayList<ArrayList<PVector>> globalShadowList = new ArrayList<ArrayList<PVector>>();
    ArrayList<ArrayList<ArrayList<PVector>>> globalShadowListCombined = new ArrayList<ArrayList<ArrayList<PVector>>>();

    ArrayList<IntList> leafPalette = palette.get(2);
    ArrayList<IntList> branchPalette = colourManipulation.pasteliseColourPalette(palette.get(1));
    ArrayList<IntList> backgroundPalette = colourManipulation.pasteliseColourPalette(palette.get(0));
    ArrayList<IntList> moonColour = new ArrayList<IntList>();
    IntList globalShadowColour = new IntList(backgroundPalette.get(1).get(0)/1.5,backgroundPalette.get(1).get(1)/1.5,backgroundPalette.get(1).get(2)/1.5);
    IntList ambientMidColour = colourGen.getShadowMiddleGround(backgroundPalette.get(0));
    Ambience ambience = new Ambience(this,backgroundPalette);
    boolean rightBool = false;


    Forest forest = new Forest(this,globalShadowList,globalShadowListCombined,globalShapeList,globalColourList, palette);


    public void setup()
    {
        frameRate(2);
    }

    public void settings()
    {
        size(1600,900);
        looping = false;
    }



    public void draw()
    {
        int scaling = 2;
        ambience.createBackground();
        moonColour.add(colourManipulation.whitenizeColour(backgroundPalette.get(0)));
        moonColour.add(colourGen.getShadow(moonColour.get(0)));
        ambience.makeSunMoon(moonColour,rightBool,backgroundPalette);
        ambience.reEstablishHorizon(ambientMidColour);

        forest.createForest(20,20,1000,600);
        forest.drawTree(new PVector(400,900), mechanicalGen.angleOfAccidenceGenerator(radians(270), 10), 100);

        ArrayList<ArrayList<PVector>> scaledShapeList = objectTrans.scalingFunction(globalShapeList,scaling);
        objectTrans.centerToAPoint(scaledShapeList,rightBool);
        forest.drawTreeShadow(globalShadowListCombined,1000,rightBool);
        draw.drawCollection(scaledShapeList,globalColourList);
//        String chromeBackroundPath = "C:/Users/UserHere/AppData/Local/Google/Chrome/User Data/Default";
//        save(chromeBackroundPath + "/background.png");

    }
}
