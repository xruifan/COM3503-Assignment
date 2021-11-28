import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class RobotRoot {

    private Model orb;

    public RobotRoot(Model orb){
        this.orb = orb;
    }

    private static SGNode robotRoot = new NameNode("root");
    private static TransformNode robotTranslate, robotRotateTranslate, bodyTransform;


    private void setupTree(){
        robotTranslate = new TransformNode("robot transform",Mat4Transform.translate(-2,0,-7));
        robotRotateTranslate = new TransformNode("robot rotate transform",Mat4Transform.rotateAroundX(0));

        NameNode body = new NameNode("body");
        Mat4 m = Mat4Transform.scale(0.7f,1.96f,0.7f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.85f,0));
        bodyTransform = new TransformNode("body transform", m);
            ModelNode bodyShape = new ModelNode("Sphere(body)", orb);

        NameNode neck = new NameNode("neck");
        m = Mat4Transform.scale(0.21f,0.21f,0.21f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,13.0f,0));
        TransformNode neckTransform = new TransformNode("neck transform", m);
            ModelNode neckShape = new ModelNode("Sphere(neck)", orb);

        NameNode head = new NameNode("head");
        m = Mat4Transform.scale(1.82f,0.7f,0.7f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,4.5f,0));
        TransformNode headTransform = new TransformNode("head transform", m);
            ModelNode headShape = new ModelNode("Sphere(head)", orb);

        NameNode foot = new NameNode("foot");
        m = Mat4Transform.scale(0.7f,0.7f,0.7f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
        TransformNode footTransform = new TransformNode("foot transform", m);
            ModelNode footShape = new ModelNode("Sphere(foot)", orb);

        NameNode ear0 = new NameNode("ear0");
        m = Mat4Transform.scale(0.14f,0.7f,0.14f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,5.5f,0));
        TransformNode ear0Transform = new TransformNode("ear0 transform", m);
            ModelNode ear0Shape = new ModelNode("Sphere(ear0)", orb);

        NameNode ear1 = new NameNode("ear1");
        m = Mat4Transform.scale(0.14f,0.98f,0.14f);
        m = Mat4.multiply(m, Mat4Transform.translate(5.5f,3.2f,0.0f));
        TransformNode ear1Transform = new TransformNode("ear1 transform", m);
            ModelNode ear1Shape = new ModelNode("Sphere(ear1)", orb);

        NameNode ear2 = new NameNode("ear2");
        m = Mat4Transform.scale(0.14f,0.98f,0.14f);
        m = Mat4.multiply(m, Mat4Transform.translate(-5.5f,3.2f,0.0f));
        TransformNode ear2Transform = new TransformNode("ear2 transform", m);
            ModelNode ear2Shape = new ModelNode("Sphere(ear2)", orb);

        NameNode eye0 = new NameNode("eye0");
        m = Mat4Transform.scale(0.21f,0.21f,0.07f);
        m = Mat4.multiply(m, Mat4Transform.translate(2.0f,15.0f,4.3f));
        TransformNode eye0Transform = new TransformNode("eye0 transform", m);
            ModelNode eye0Shape = new ModelNode("Sphere(eye0)", orb);

        NameNode eye1 = new NameNode("eye1");
        m = Mat4Transform.scale(0.49f,0.49f,0.2f);
        m = Mat4.multiply(m, Mat4Transform.translate(-0.6f,6.5f,1.5f));
        TransformNode eye1Transform = new TransformNode("eye1 transform", m);
            ModelNode eye1Shape = new ModelNode("Sphere(eye1)", orb);

        robotRoot.addChild(robotTranslate);
        robotTranslate.addChild(robotRotateTranslate);
        robotRotateTranslate.addChild(body);
        body.addChild(bodyTransform);
        bodyTransform.addChild(bodyShape);
        body.addChild(foot);
            foot.addChild(footTransform);
            footTransform.addChild(footShape);
        body.addChild(neck);
            neck.addChild(neckTransform);
            neckTransform.addChild(neckShape);
            neck.addChild(head);
            head.addChild(headTransform);
            headTransform.addChild(headShape);
            head.addChild(ear0);
                ear0.addChild(ear0Transform);
                ear0Transform.addChild(ear0Shape);
            head.addChild(ear1);
                ear1.addChild(ear1Transform);
                ear1Transform.addChild(ear1Shape);
            head.addChild(ear2);
                ear2.addChild(ear2Transform);
                ear2Transform.addChild(ear2Shape);
            head.addChild(eye0);
                eye0.addChild(eye0Transform);
                eye0Transform.addChild(eye0Shape);
            head.addChild(eye1);
                eye1.addChild(eye1Transform);
                eye1Transform.addChild(eye1Shape);
    }

    public SGNode getRobotRoot(){
        setupTree();
        robotRoot.update();
        return robotRoot;
    }

    public static void setRobotPose1(){
        robotTranslate.setTransform(Mat4Transform.translate(-2,0,-7));
        robotRoot.update();
    }

    public static void setRobotPose2(){
        robotRotateTranslate.setTransform(Mat4Transform.rotateAroundZ(60));
        robotRotateTranslate.setTransform(Mat4Transform.rotateAroundY(130));
        robotTranslate.setTransform(Mat4Transform.translate(4,0,-4));
        robotRoot.update();
    }

    public static void setRobotPose3(){
        robotTranslate.setTransform(Mat4Transform.translate(4,0,3));
        robotRoot.update();
    }

    public static void setRobotPose4(){
        robotTranslate.setTransform(Mat4Transform.translate(0,0,3));
        robotRoot.update();
    }

    public static void setRobotPose5(){
        robotTranslate.setTransform(Mat4Transform.translate(-6,0,0));
        robotRoot.update();
    }

    private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

}