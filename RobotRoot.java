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
    private static TransformNode moveTranslate, letRotate, neckRotate, leftEarRotate, rightEarRotate, noseRotate;

    private float bodyHeight = 1.5f;
    private float bodyWidth = 0.2f;
    private float bodyDepth = 0.2f;
    private float headScale = 0.5f;
    private float neckScale = 0.2f;
    private float neckLength = 0.5f;
    private float legLength = 0.4f;
    private float legScale = 0.25f;
    private float earLength = 0.6f;
    private float earScale = 0.08f;
    private float noseScale = 0.1f;

    private void setupTree(){
    moveTranslate = new TransformNode("robot transform",Mat4Transform.translate(0,0,0));
    TransformNode robotTranslate = new TransformNode("robot transform",Mat4Transform.translate(0f,0f,0f));
    TransformNode backToOrigin = new TransformNode("back to origin", Mat4Transform.translate(0,-(bodyHeight+neckScale),0));
    TransformNode backToHead = new TransformNode("back to head", Mat4Transform.translate(0,(bodyHeight+neckScale),0));
    TransformNode toLeftEarPos = new TransformNode("Move to left ear pos", Mat4Transform.translate(-0.1f,bodyHeight+((neckScale+headScale)/2),0));
    TransformNode toRightEarPos = new TransformNode("Move to right ear pos", Mat4Transform.translate(0.1f,bodyHeight+((neckScale+headScale)/2),0));
    TransformNode toNosePos = new TransformNode("Move to nose pos", Mat4Transform.translate(0,bodyHeight+((neckScale+(headScale))/2),0.25f));

    Mat4 x = new Mat4(1);
      x = new Mat4(1);
      x = Mat4.multiply(x, Mat4Transform.rotateAroundX(0));
      x = Mat4.multiply(x, Mat4Transform.rotateAroundZ(0));
      x = Mat4.multiply(x, Mat4Transform.rotateAroundY(0));
      letRotate = new TransformNode("With leg rotation", x);

    Mat4 y = new Mat4(1);
      y = Mat4.multiply(y, Mat4Transform.rotateAroundX(0));
      y = Mat4.multiply(y, Mat4Transform.rotateAroundZ(0));
      y = Mat4.multiply(y, Mat4Transform.rotateAroundY(0));
      neckRotate = new TransformNode("With neck rotation", y);

    Mat4 z = new Mat4(1);
    z = Mat4.multiply(z, Mat4Transform.rotateAroundX(0));
    z = Mat4.multiply(z, Mat4Transform.rotateAroundZ(0));
    leftEarRotate = new TransformNode("Left ear rotation", z);

    Mat4 a = new Mat4(1);
    a = Mat4.multiply(a, Mat4Transform.rotateAroundX(0));
    a = Mat4.multiply(a, Mat4Transform.rotateAroundZ(0));
    rightEarRotate = new TransformNode("Right ear rotation", a);

    Mat4 b = new Mat4(1);
    b = Mat4.multiply(b, Mat4Transform.scale(noseScale,noseScale,noseScale*0.1f));
    noseRotate = new TransformNode("Nose Scale", b);
    
    NameNode body = new NameNode("body");
      Mat4 m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight+legScale,0));
      m = Mat4Transform.scale(bodyWidth,bodyHeight,bodyDepth);
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode bodyTransform = new TransformNode("body transform", m);
        ModelNode bodyShape = new ModelNode("Sphere(body)", orb);

    NameNode head = new NameNode("head"); 
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight+neckScale,0));
      m = Mat4.multiply(m, Mat4Transform.scale(headScale,headScale/2,headScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode headTransform = new TransformNode("head transform", m);
        ModelNode headShape = new ModelNode("Sphere(head)", orb);

    NameNode neck = new NameNode("neck");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight,0));
      m = Mat4.multiply(m, Mat4Transform.scale(neckScale,neckScale,neckScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode neckTransform = new TransformNode("neck transform", m);
        ModelNode neckShape = new ModelNode("Sphere(neck)", orb);
    
    NameNode leg = new NameNode("leg");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(legScale,legScale,legScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode legTransform = new TransformNode("leg transform", m);
        ModelNode legShape = new ModelNode("Sphere(leg)", orb);

    NameNode leftEar = new NameNode("leftEar");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(earScale,earLength,earScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode leftEarTransform = new TransformNode("LeftEar transform", m);
        ModelNode leftEarShape = new ModelNode("Sphere(leftEar)", orb);

    NameNode rightEar = new NameNode("rightEar");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(earScale,earLength,earScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode rightEarTransform = new TransformNode("RightEar transform", m);
        ModelNode rightEarShape = new ModelNode("Sphere(rightEar)", orb);
    
    NameNode nose = new NameNode("nose");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode noseTransform = new TransformNode("Nose transform", m);
        ModelNode noseShape = new ModelNode("Sphere(nose)", orb);


    robotRoot.addChild(moveTranslate);
      moveTranslate.addChild(robotTranslate);
        robotTranslate.addChild(letRotate);
        letRotate.addChild(body);
        body.addChild(bodyTransform);
          bodyTransform.addChild(bodyShape);
        body.addChild(leg);
          leg.addChild(legTransform);
          legTransform.addChild(legShape);
        body.addChild(neck);
          neck.addChild(neckTransform);
          neckTransform.addChild(neckShape);
          neck.addChild(backToHead);
          backToHead.addChild(neckRotate);
          neckRotate.addChild(backToOrigin);
          backToOrigin.addChild(head);
            head.addChild(headTransform);
            headTransform.addChild(headShape);
            head.addChild(toLeftEarPos);
            toLeftEarPos.addChild(leftEarRotate);
            leftEarRotate.addChild(leftEar);
            leftEar.addChild(leftEarTransform);
            leftEarTransform.addChild(leftEarShape);
            head.addChild(toRightEarPos);
            toRightEarPos.addChild(rightEarRotate);
            rightEarRotate.addChild(rightEar);
            rightEar.addChild(rightEarTransform);
            rightEarTransform.addChild(rightEarShape);
            head.addChild(toNosePos);
            toNosePos.addChild(noseRotate);
            noseRotate.addChild(nose);
            nose.addChild(noseTransform);
            noseTransform.addChild(noseShape);

    }

    public SGNode getRobotRoot(){
        setupTree();
        robotRoot.update();
        return robotRoot;
    }

    public static void setRobotPose1(){
        moveTranslate.setTransform(Mat4Transform.translate(6,0,-5));
        letRotate.setTransform(Mat4Transform.rotateAroundX(10));
        neckRotate.setTransform(Mat4Transform.rotateAroundX(20));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundX(30));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundX(40));
        noseRotate.setTransform(Mat4Transform.rotateAroundX(40));
        robotRoot.update();
    }

    public static void setRobotPose2(){
        //footRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundZ(10),Mat4Transform.rotateAroundX(-10)),Mat4Transform.rotateAroundY(130)));
        //robotTranslate.setTransform(Mat4Transform.translate(4,0,-4));
        //headRotateTranslate.setTransform(Mat4.multiply(Mat4Transform.rotateAroundZ(30),Mat4Transform.rotateAroundX(-10)));
        //robotRoot.update();
    }

    public static void setRobotPose3(){
        //robotTranslate.setTransform(Mat4Transform.translate(4,0,3));
        //robotRoot.update();
    }

    public static void setRobotPose4(){
        //robotTranslate.setTransform(Mat4Transform.translate(0,0,3));
        //robotRoot.update();
    }

    public static void setRobotPose5(){
        //robotTranslate.setTransform(Mat4Transform.translate(-6,0,0));
        //robotRoot.update();
    }


}