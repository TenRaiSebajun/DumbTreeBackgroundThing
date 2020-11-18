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


public class Forest {

    private ArrayList<ArrayList<IntList>> palette;
    private PApplet p;
    private final ArrayList<ArrayList<PVector>> globalShadowList;
    private final ArrayList<ArrayList<ArrayList<PVector>>> globalShadowListCombined;
    private final ArrayList<ArrayList<PVector>> globalShapeList;
    private final ArrayList<IntList> globalColourList;
    ColourGen colourGen;
    private final MechanicalGen mechanicalGen;
    private final DrawingUtils draw;
    private final RandomUtils rand;


    ArrayList<IntList> leafPalette;
    ArrayList<IntList> branchPalette;
    ArrayList<IntList> backgroundPalette;
    IntList globalShadowColour;
    int upperBoundOnNumberOfNodes;
    int lowerBoundOnNumberOfNodes;

    public Forest(PApplet p, ArrayList<ArrayList<PVector>> globalShadowList,ArrayList<ArrayList<ArrayList<PVector>>> globalShadowListCombined, ArrayList<ArrayList<PVector>> globalShapeList, ArrayList<IntList> globalColourList,ArrayList<ArrayList<IntList>> palette) {
        this.p = p;
        this.globalShadowList = globalShadowList;
        this.colourGen = new ColourGen(this.p);
        this.mechanicalGen = new MechanicalGen(this.p);
        this.draw = new DrawingUtils(this.p);
        this.rand = new RandomUtils(this.p);
        ColourManipulation colourManipulation = new ColourManipulation(this.p);
        this.globalShadowListCombined = globalShadowListCombined;
        this.globalShapeList = globalShapeList;
        this.globalColourList = globalColourList;
        this.palette = palette;

        leafPalette = (palette.get(2));
        branchPalette = (colourManipulation.pasteliseColourPalette(palette.get(1)));
        backgroundPalette = (colourManipulation.pasteliseColourPalette(palette.get(0)));
        //decide on tree scale - i.e. range of number of nodes
        upperBoundOnNumberOfNodes = (int) p.random(8, 10);
        lowerBoundOnNumberOfNodes = upperBoundOnNumberOfNodes - 2;
        globalShadowColour = colourGen.getShadow(backgroundPalette.get(1));

    }


    public void createForest(int numberOfTrees, int distanceBetweenTrees, int canvasSize, int yMin)
    {
        PVector treeVector = new PVector((int)p.random(0,canvasSize),yMin);
        for (int i = 0; i < numberOfTrees; i++) {
            if (treeVector.y < canvasSize-100 ^ treeVector.x >canvasSize-100)  {
                treeVector.set((int) p.random(0,canvasSize) + (int) p.random(distanceBetweenTrees,distanceBetweenTrees+50),treeVector.y + (int) p.random(distanceBetweenTrees,distanceBetweenTrees+50));
                drawTree(treeVector, mechanicalGen.angleOfAccidenceGenerator(p.radians(270), 10), (int) p.random(50, 150));
            }
        }
    }


    public void drawTree(PVector treeRoot,float angleOfAccidence,int treeSize) {
        PVector treeTrunkEnd = new PVector(treeRoot.x + p.cos(angleOfAccidence)*treeSize,treeRoot.y + p.sin(angleOfAccidence)*treeSize);
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
            PVector endVertex = new PVector(branchEndVertex.x + lengthOfBranch*p.cos(angleOfBranch),branchEndVertex.y + lengthOfBranch*p.sin(angleOfBranch));
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
        PVector leafPoint1 = new PVector(leafPoint.x + leafSize*p.cos(angleOfBranch - p.radians(30)), leafPoint.y + leafSize*p.sin(angleOfBranch - p.radians(30)));
        PVector leafPoint2 = new PVector(leafPoint1.x + leafSize*p.cos(angleOfBranch + p.radians(30)), leafPoint1.y + leafSize*p.sin(angleOfBranch + p.radians(30)));
        PVector leafPoint3 = new PVector(leafPoint2.x + leafSize*p.cos(angleOfBranch + p.radians(150)), leafPoint2.y + leafSize*p.sin(angleOfBranch + p.radians(150)));
        PVector leafShadowPoint1 = new PVector(leafPoint.x + leafSize* p.cos(angleOfBranch + p.radians(10)), leafPoint3.y);
        IntList leafColour = leafPalette.get(0);
        IntList leafShadow = leafPalette.get(1);
        ArrayList<PVector> leafVertexList = new ArrayList<PVector>();
        leafVertexList.add(leafPoint);
        leafVertexList.add(new PVector(leafPoint.x + leafSize*p.cos(angleOfBranch - p.radians(30)), leafPoint.y + leafSize*p.sin(angleOfBranch - p.radians(30))));
        leafVertexList.add(new PVector(leafPoint1.x + leafSize*p.cos(angleOfBranch + p.radians(30)), leafPoint1.y + leafSize*p.sin(angleOfBranch + p.radians(30))));
        leafVertexList.add(new PVector(leafPoint2.x + leafSize*p.cos(angleOfBranch + p.radians(150)), leafPoint2.y + leafSize*p.sin(angleOfBranch + p.radians(150))));
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


    public void drawTreeShadow(ArrayList<ArrayList<ArrayList<PVector>>> combinedShadows, int canvasSize, boolean shadowRight)
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
                    draw.drawShapeWithColour(vertexList, globalShadowColour,false,0);
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
                    draw.drawShapeWithColour(vertexList, globalShadowColour,false,0);
                }
            }
        }
    }

}
