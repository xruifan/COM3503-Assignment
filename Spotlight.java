import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

/**
* I declare that this code is my own work
*
* @author   Xuan-Rui Fan, lhsu1@sheffield.ac.uk
* 
*/

public class Spotlight {

    private  double startTime = getSeconds();
    
    private Model metal, lightBulb;
    private TransformNode shadeRotateTransform, lightRotateTransform, lightTransform, spotlightTranslate, baseTransform, lowerPostTransform, 
            upperPostTransform, shadeTransform;
    private NameNode baseNode, lowerPostNode, upperPostNode, shadeNode, lightNode;
    private ModelNode baseShape, lowerPostShape, upperPostShape, shadeShape, lightShape;
    public Vec4 lightPos;
    private SGNode spotlightRoot = new NameNode("spotlightRoot");

    public Vec3 shadeDirection = new Vec3(0,-1,0);

    public Spotlight(Model metal, Model lightBulb){
        this.metal = metal;
        this.lightBulb = lightBulb;
    }

    private void setup(){
        spotlightTranslate = new TransformNode("spotlight transform",Mat4Transform.translate(7.5f,0.0f,3.0f));

        baseNode = new NameNode("spotlightBase");
        Mat4 m = Mat4Transform.scale(0.5f,0.25f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.0f,0.0f));
        baseTransform = new TransformNode("base transform", m);
            baseShape = new ModelNode("Cube(Spotlight Base)", metal);

        lowerPostNode = new NameNode("spotlight lower post");
        m = Mat4Transform.scale(0.25f,5.0f,0.25f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        lowerPostTransform = new TransformNode("lower post transform", m);
            lowerPostShape = new ModelNode("Cube(Spotlight lower post)", metal);

        upperPostNode = new NameNode("spotlight upper post");
        m = Mat4Transform.scale(1.0f,0.25f,0.25f);
        m = Mat4.multiply(m, Mat4Transform.translate(-0.4f,20.0f,0.0f));
        upperPostTransform = new TransformNode("upper post transform", m);
            upperPostShape = new ModelNode("Cube(Spotlight upper post)", metal);    

        shadeNode = new NameNode("spotlight shade");
        m = Mat4Transform.scale(0.5f,0.7f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(-2.0f,7.0f,0.0f));
        shadeTransform = new TransformNode("shade transform", m);
            shadeShape = new ModelNode("Cube(Spotlight shade)", metal);  

        shadeRotateTransform = new TransformNode("shade transform", m);
        lightRotateTransform = shadeRotateTransform;

        lightNode = new NameNode("spotlight light");
        m = Mat4Transform.scale(0.5f,0.5f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,1.0f,0.0f));
        lightTransform = new TransformNode("light transform", m);
            lightShape = new ModelNode("Sphere(Spotlight light)", lightBulb);  

    }

    private void buildTree(){
        spotlightRoot.addChild(spotlightTranslate);
        spotlightTranslate.addChild(baseNode);
        baseNode.addChild(baseTransform);
        baseTransform.addChild(baseShape);
        baseNode.addChild(lowerPostNode);
            lowerPostNode.addChild(lowerPostTransform);
            lowerPostTransform.addChild(lowerPostShape);
            lowerPostNode.addChild(upperPostNode);
            upperPostNode.addChild(upperPostTransform);
            upperPostTransform.addChild(upperPostShape);
            upperPostNode.addChild(shadeNode);
                shadeNode.addChild(shadeTransform);
                shadeTransform.addChild(shadeRotateTransform);
                shadeRotateTransform.addChild(shadeShape);
                shadeTransform.addChild(lightRotateTransform);
                lightRotateTransform.addChild(lightTransform);
                lightTransform.addChild(lightShape);
    }

    public  void updateShadeRotation(){
        double elapsedTime = getSeconds()-startTime;
        float rotateAngle = 180f+45f*(float)Math.sin(elapsedTime);
        shadeRotateTransform.setTransform(Mat4Transform.rotateAroundX(rotateAngle));
        spotlightRoot.update();
        lightPos = Mat4.multiply(lightTransform.worldTransform, new Vec4(0f,0.5f,0f,1.0f));
        shadeDirection = Mat4.multiply(Mat4Transform.rotateAroundX(rotateAngle), new Vec4(0,1,0,0)).toVec3();
    }

    public SGNode getSpotlightRoot(){
        setup();
        buildTree();
        spotlightRoot.update();
        lightPos = new Vec4(0,0,0,0);
        return spotlightRoot;
    }

    public Vec4 getLightPos(){
        return lightPos;
    }

    public Vec3 getShadeDirection(){
        return shadeDirection;
    }

    private  double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }

}