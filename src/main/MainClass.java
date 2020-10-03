package main;

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

    //decide on tree scale - i.e. range of number of nodes
    int upperBoundOnNumberOfNodes = (int) random(8 ,10);
    int lowerBoundOnNumberOfNodes = upperBoundOnNumberOfNodes - 2;

    ArrayList<ArrayList<IntList>> palette = colourGen.analogousColourPaletteGenerator(200);
    ArrayList<ArrayList<PVector>> globalShapeList = new ArrayList<ArrayList<PVector>>();
    ArrayList<IntList> globalColourList = new ArrayList<IntList>();
    ArrayList<ArrayList<PVector>> globalShadowList = new ArrayList<ArrayList<PVector>>();
    ArrayList<ArrayList<ArrayList<PVector>>> globalShadowListCombined = new ArrayList<ArrayList<ArrayList<PVector>>>();

    ArrayList<IntList> leafPalette = palette.get(2);
    ArrayList<IntList> branchPalette = colourManipulation.pasteliseColourPalette(palette.get(1));
    ArrayList<IntList> backgroundPalette = colourManipulation.pasteliseColourPalette(palette.get(0));
    ArrayList<IntList> moonColour = new ArrayList<IntList>();
//            moonColour.add(colourManipulation.whitenizeColour(backgroundPalette.get(0)));
    IntList globalShadowColour = new IntList(backgroundPalette.get(1).get(0)/1.5,backgroundPalette.get(1).get(1)/1.5,backgroundPalette.get(1).get(2)/1.5);
    IntList ambientMidColour = colourGen.getShadowMiddleGround(backgroundPalette.get(0));
    Ambience ambience = new Ambience(this,backgroundPalette);
    boolean rightBool = false;



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
        createForest(20,20,1000,600);
        drawTree(new PVector(400,900), mechanicalGen.angleOfAccidenceGenerator(radians(270), 10), 100);
        ArrayList<ArrayList<PVector>> scaledShapeList = objectTrans.scalingFunction(globalShapeList,scaling);
        objectTrans.centerToAPoint(scaledShapeList,rightBool);
        drawTreeShadow(globalShadowListCombined,1000,rightBool);
        draw.drawCollection(scaledShapeList,globalColourList);
//        String chromeBackroundPath = "C:/Users/UserHere/AppData/Local/Google/Chrome/User Data/Default";
//        save(chromeBackroundPath + "/background.png");
//        try {
//            Files.delete(Paths.get(chromeBackroundPath + "/background.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Files.move(Paths.get(chromeBackroundPath + "/background.png"),Paths.get(chromeBackroundPath + "/background.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }





    void createForest(int numberOfTrees, int distanceBetweenTrees, int canvasSize, int yMin)
    {
        PVector treeVector = new PVector((int)random(0,canvasSize),yMin);
        for (int i = 0; i < numberOfTrees; i++) {
            if (treeVector.y < canvasSize-100 ^ treeVector.x >canvasSize-100)  {
                treeVector.set((int) random(0,canvasSize) + (int) random(distanceBetweenTrees,distanceBetweenTrees+50),treeVector.y + (int) random(distanceBetweenTrees,distanceBetweenTrees+50));
                drawTree(treeVector, mechanicalGen.angleOfAccidenceGenerator(radians(270), 10), (int) random(50, 150));
            }
        }
    }


    public void drawTree(PVector treeRoot,float angleOfAccidence,int treeSize) {
        PVector treeTrunkEnd = new PVector(treeRoot.x + cos(angleOfAccidence)*treeSize,treeRoot.y + sin(angleOfAccidence)*treeSize);
        int currentNodeDegree = 1;
        drawBranch(treeRoot,treeTrunkEnd, 1,true);
        drawAtNode(treeTrunkEnd, angleOfAccidence, currentNodeDegree, treeSize);
        globalShadowListCombined.add(new ArrayList<ArrayList<PVector>>(globalShadowList));
        globalShadowList.removeAll(globalShadowList);
    }

    void drawAtNode(PVector branchEndVertex, float originalAngleOfAccidence, int currentNodeDegree, int treeSize)
    {
        int numberOfBranches = rand.numberOfBranches(currentNodeDegree, 5,2);
        while (numberOfBranches != 0)
        {
            int lengthOfBranch = rand.currentBranchLength(treeSize,currentNodeDegree,2);
            float angleOfBranch = mechanicalGen.angleOfAccidenceGenerator(originalAngleOfAccidence, 20 + currentNodeDegree*10);
            PVector endVertex = new PVector(branchEndVertex.x + lengthOfBranch*cos(angleOfBranch),branchEndVertex.y + lengthOfBranch*sin(angleOfBranch));
            drawBranch(branchEndVertex, endVertex, currentNodeDegree+1,false);
            drawLeaf(endVertex, angleOfBranch, 10);
            if (rand.continueDrawingYesOrNo(currentNodeDegree, upperBoundOnNumberOfNodes, lowerBoundOnNumberOfNodes))
            {
                drawAtNode(endVertex, angleOfBranch, currentNodeDegree + 1, treeSize);
            }
            else
            {
                return;
            }
            numberOfBranches--;
        }
    }



    void drawLeaf(PVector leafPoint, float angleOfBranch, int leafSize)
    {
        PVector leafPoint1 = new PVector(leafPoint.x + leafSize*cos(angleOfBranch - radians(30)), leafPoint.y + leafSize*sin(angleOfBranch - radians(30)));
        PVector leafPoint2 = new PVector(leafPoint1.x + leafSize*cos(angleOfBranch + radians(30)), leafPoint1.y + leafSize*sin(angleOfBranch + radians(30)));
        PVector leafPoint3 = new PVector(leafPoint2.x + leafSize*cos(angleOfBranch + radians(150)), leafPoint2.y + leafSize*sin(angleOfBranch + radians(150)));
        PVector leafShadowPoint1 = new PVector(leafPoint.x + leafSize* cos(angleOfBranch + radians(10)), leafPoint3.y);
        IntList leafColour = leafPalette.get(0);
        IntList leafShadow = leafPalette.get(1);
        ArrayList<PVector> leafVertexList = new ArrayList<PVector>();
        leafVertexList.add(leafPoint);
        leafVertexList.add(new PVector(leafPoint.x + leafSize*cos(angleOfBranch - radians(30)), leafPoint.y + leafSize*sin(angleOfBranch - radians(30))));
        leafVertexList.add(new PVector(leafPoint1.x + leafSize*cos(angleOfBranch + radians(30)), leafPoint1.y + leafSize*sin(angleOfBranch + radians(30))));
        leafVertexList.add(new PVector(leafPoint2.x + leafSize*cos(angleOfBranch + radians(150)), leafPoint2.y + leafSize*sin(angleOfBranch + radians(150))));
        globalShadowList.add(new ArrayList<PVector>(leafVertexList));
        globalShapeList.add(leafVertexList);
        globalColourList.add(leafPalette.get(1));
        drawShadowOfLeaf(leafVertexList,leafShadowPoint1);
    }

    void drawShadowOfLeaf(ArrayList<PVector> lightLeaf, PVector shadowVertex)
    {
        ArrayList<PVector> shadowVertexList = new ArrayList<PVector>();
        shadowVertexList.add(new PVector(lightLeaf.get(0).x, lightLeaf.get(0).y));
        shadowVertexList.add(new PVector(shadowVertex.x, shadowVertex.y));
        shadowVertexList.add(new PVector(lightLeaf.get(2).x, lightLeaf.get(2).y));
        shadowVertexList.add(new PVector(lightLeaf.get(1).x, lightLeaf.get(1).y));
        globalShapeList.add(shadowVertexList);
        globalColourList.add(leafPalette.get(0));
    }

    void drawBranch(PVector branchRoot, PVector branchEnd, int currentNode, boolean trunk)
    {
        if (trunk)
        {
            int initialThickness = 20/currentNode;
            int latterThickness = 10/currentNode;
            ArrayList<PVector> vertexList = new ArrayList<PVector>();
            vertexList.add(new PVector(branchRoot.x - initialThickness, branchRoot.y));
            vertexList.add(new PVector(branchRoot.x, branchRoot.y + initialThickness));
            vertexList.add(new PVector(branchRoot.x + initialThickness, branchRoot.y));
            vertexList.add(new PVector(branchEnd.x + latterThickness, branchEnd.y));
            vertexList.add(new PVector(branchEnd.x - latterThickness, branchEnd.y));
            globalShadowList.add(new ArrayList<PVector>(vertexList));
            globalShapeList.add(vertexList);
            globalColourList.add(branchPalette.get(0));
            drawShadowOfBranch(branchRoot,branchEnd, initialThickness, latterThickness, true);
        } else {
            int initialThickness = 20 / currentNode;
            int latterThickness = 10 / currentNode;
            ArrayList<PVector> vertexList = new ArrayList<PVector>();
            vertexList.add(new PVector(branchRoot.x - initialThickness, branchRoot.y));
            vertexList.add(new PVector(branchRoot.x + initialThickness, branchRoot.y));
            vertexList.add(new PVector(branchEnd.x + latterThickness, branchEnd.y));
            vertexList.add(new PVector(branchEnd.x - latterThickness, branchEnd.y));
            globalShadowList.add(new ArrayList<PVector>(vertexList));
            globalShapeList.add(vertexList);
            globalColourList.add(branchPalette.get(0));
            drawShadowOfBranch(branchRoot, branchEnd, initialThickness, latterThickness, false);
        }
    }

    void drawShadowOfBranch(PVector branchRoot, PVector branchEnd, int initialThickness, int latterThickness, boolean trunk)
    {
        ArrayList<PVector> shadowVertexList = new ArrayList<PVector>();
        shadowVertexList.add(new PVector(branchRoot.x, branchRoot.y));
        shadowVertexList.add(new PVector(branchRoot.x + initialThickness, branchRoot.y));
        shadowVertexList.add(new PVector(branchEnd.x + latterThickness, branchEnd.y));
        shadowVertexList.add(new PVector(branchEnd.x, branchEnd.y));
        if (trunk){
            shadowVertexList.set(0, new PVector(branchRoot.x,branchRoot.y + initialThickness));
        }
        globalShadowList.add(new ArrayList<PVector>(shadowVertexList));
        globalShapeList.add(shadowVertexList);
        globalColourList.add(branchPalette.get(1));
    }


    void drawTreeShadow(ArrayList<ArrayList<ArrayList<PVector>>> combinedShadows, int canvasSize,boolean shadowRight)
    {
        if (shadowRight)
        {
            for (ArrayList<ArrayList<PVector>> treeShadow : combinedShadows) {
                PVector trunkRoot = treeShadow.get(0).get(0);
                PVector shadowRoot = treeShadow.get(1).get(0);
                PVector shadowRoot2 = treeShadow.get(1).get(1);
                PVector newShadowVertex = new PVector(shadowRoot2.y, shadowRoot2.x / 2);
                PVector vectorDisplacement = new PVector(shadowRoot.x - newShadowVertex.x, shadowRoot.y - newShadowVertex.y);
                treeShadow.get(0).set(1, trunkRoot);
                treeShadow.get(1).set(0, trunkRoot);
                for (ArrayList<PVector> vertexList : treeShadow) {
                    for (PVector vector : vertexList) {
                        vertexList.set(vertexList.indexOf(vector), new PVector(vector.y + vectorDisplacement.x, vector.x / 2 + vectorDisplacement.y));
                    }
                    draw.drawShapeWithColour(vertexList, globalShadowColour);
                }
            }
        }
        else
        {
            for (ArrayList<ArrayList<PVector>> treeShadow : combinedShadows) {
                PVector trunkRoot = treeShadow.get(0).get(0);
                PVector shadowRoot = treeShadow.get(1).get(0);
                PVector shadowRoot2 = treeShadow.get(1).get(1);
                PVector newShadowVertex = new PVector(canvasSize - shadowRoot2.y, shadowRoot2.x / 2);
                PVector vectorDisplacement = new PVector(shadowRoot.x - newShadowVertex.x, shadowRoot.y - newShadowVertex.y);
                treeShadow.get(0).set(1, trunkRoot);
                treeShadow.get(1).set(0, trunkRoot);
                for (ArrayList<PVector> vertexList : treeShadow) {
                    for (PVector vector : vertexList) {
                        vertexList.set(vertexList.indexOf(vector), new PVector(canvasSize - vector.y + vectorDisplacement.x, vector.x / 2 + vectorDisplacement.y));
                    }
                    draw.drawShapeWithColour(vertexList, globalShadowColour);
                }
            }
        }
    }


}
