import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class SpotlightRoot {

    private static double startTime = getSeconds();
    
    private Model metal;
    private Model lightBulb;
    private static TransformNode shadeRotateTransform, lightRotateTransform;
    private static SGNode spotlightRoot = new NameNode("spotlightRoot");

    public SpotlightRoot(Model metal, Model lightBulb){
        this.metal = metal;
        this.lightBulb = lightBulb;
    }

    private void setupTree(){
        TransformNode spotlightTranslate = new TransformNode("spotlight transform",Mat4Transform.translate(7.5f,0.0f,3.0f));

        NameNode baseNode = new NameNode("spotlightBase");
        Mat4 m = Mat4Transform.scale(0.5f,0.25f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.0f,0.0f));
        TransformNode baseTransform = new TransformNode("base transform", m);
            ModelNode baseShape = new ModelNode("Cube(Spotlight Base)", metal);

        NameNode lowerPostNode = new NameNode("spotlight lower post");
        m = Mat4Transform.scale(0.25f,5.0f,0.25f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        TransformNode lowerPostTransform = new TransformNode("lower post transform", m);
            ModelNode lowerPostShape = new ModelNode("Cube(Spotlight lower post)", metal);

        NameNode upperPostNode = new NameNode("spotlight upper post");
        m = Mat4Transform.scale(1.0f,0.25f,0.25f);
        m = Mat4.multiply(m, Mat4Transform.translate(-0.4f,20.0f,0.0f));
        TransformNode upperPostTransform = new TransformNode("upper post transform", m);
            ModelNode upperPostShape = new ModelNode("Cube(Spotlight upper post)", metal);    

        NameNode shadeNode = new NameNode("spotlight shade");
        m = Mat4Transform.scale(0.5f,0.7f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(-2.0f,7.0f,0.0f));
        TransformNode shadeTransform = new TransformNode("shade transform", m);
            ModelNode shadeShape = new ModelNode("Cube(Spotlight shade)", metal);  

        shadeRotateTransform = new TransformNode("shade transform", m);
        lightRotateTransform = shadeRotateTransform;

        NameNode lightNode = new NameNode("spotlight light");
        m = Mat4Transform.scale(0.5f,0.5f,0.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,1.0f,0.0f));
        TransformNode lightTransform = new TransformNode("light transform", m);
            ModelNode lightShape = new ModelNode("Sphere(Spotlight light)", lightBulb);  

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

    public SGNode getSpotlightRoot(){
        setupTree();
        spotlightRoot.update();
        return spotlightRoot;
    }

    public static void updateShadeRotation(){
        double elapsedTime = getSeconds()-startTime;
        float rotateAngle = 180f+45f*(float)Math.sin(elapsedTime);
        shadeRotateTransform.setTransform(Mat4Transform.rotateAroundX(rotateAngle));
        spotlightRoot.update();
    }

    private static double getSeconds() {
        return System.currentTimeMillis()/1000.0;
    }


    // The light's postion is continually being changed, so needs to be calculated for each frame.
    private Vec3 getLightPosition() {
        float x = 0;
        float y = 0;
        float z = 0;
        return new Vec3(x,y,z);   
    }

}