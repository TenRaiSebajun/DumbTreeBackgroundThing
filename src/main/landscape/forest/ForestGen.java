package main.landscape.forest;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

import java.io.IOException;
import java.util.ArrayList;

import utils.drawing.DrawingUtils;
import utils.gen.*;
import utils.object.manipulation.ColourManipulation;
import utils.object.manipulation.ObjectTransformation;
import utils.gen.MechanicalGen;
import utils.gen.RandomUtils;
import main.landscape.general.Ambience;
import java.nio.file.Files;
import java.nio.file.Paths;

//public class ForestGen{
//
//    private final PApplet p;
//
//    public ForestGen(PApplet p) {
//        this.p = p;
//    }
//
//
//    createForest(20,20,1000,600);
//    drawTree(new PVector(400,900), mechanicalGen.angleOfAccidenceGenerator(radians(270), 10), 100);
//    ArrayList<ArrayList<PVector>> scaledShapeList = objectTrans.scalingFunction(globalShapeList,scaling);
//    objectTrans.centerToAPoint(scaledShapeList,1000,600);
//    drawTreeShadow(globalShadowListCombined,1000,false);
//    draw.drawCollection(scaledShapeList,globalColourList);
//
//}