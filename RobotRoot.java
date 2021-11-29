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
* @author   Xuan-Rui Fan, lhsu1@sheffiel.ac.uk
* 
*/

public class RobotRoot {

    private Model orb;

    public RobotRoot(Model orb){
        this.orb = orb;
    }

    private static SGNode robotRoot = new NameNode("root");
    private static TransformNode moveTranslate, footRotate, neckRotate, leftEarRotate, rightEarRotate, midEarRotate;
    private TransformNode robotTranslate, backToOrigin, backToHead, toLeftEarPos, toRightEarPos, toMidEarPos, 
        bodyTransform, headTransform, neckTransform, footTransform, leftEarTransform, rightEarTransform, midEarTransform;
    private NameNode body, head, neck, foot, leftEar, rightEar, midEar;
    private ModelNode bodyShape, headShape, neckShape, footShape, leftEarShape, rightEarShape, midEarShape;

    private float bodyHeight = 2.0f;
    private float bodyWidth = 0.4f;
    private float bodyDepth = 0.3f;
    private float headSize = 0.5f;
    private float neckSize = 0.2f;
    private float neckLength = 0.5f;
    private float footLength = 0.4f;
    private float footSize = 0.6f;
    private float earLength = 0.6f;
    private float earSize = 0.08f;
    private float midEarSize = 0.1f;

    private void setup(){
    
      moveTranslate = new TransformNode("robot transform",Mat4Transform.translate(0,0,0));
      robotTranslate = new TransformNode("robot transform",Mat4Transform.translate(0f,0f,0f));
      backToOrigin = new TransformNode("back to origin", Mat4Transform.translate(0,-(bodyHeight+neckSize),0));
      backToHead = new TransformNode("back to head", Mat4Transform.translate(0,(bodyHeight+neckSize),0));
      toLeftEarPos = new TransformNode("Move to left ear pos", Mat4Transform.translate(-0.1f,bodyHeight+((neckSize+headSize)/2),0));
      toRightEarPos = new TransformNode("Move to right ear pos", Mat4Transform.translate(0.1f,bodyHeight+((neckSize+headSize)/2),0));
      toMidEarPos = new TransformNode("Move to mid ear pos", Mat4Transform.translate(0,bodyHeight+((neckSize+(headSize))/2),0.25f));

      Mat4 x = new Mat4(1);
        x = new Mat4(1);
        x = Mat4.multiply(x, Mat4Transform.rotateAroundX(0));
        x = Mat4.multiply(x, Mat4Transform.rotateAroundZ(0));
        x = Mat4.multiply(x, Mat4Transform.rotateAroundY(0));
        footRotate = new TransformNode("With foot rotation", x);

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
      b = Mat4.multiply(b, Mat4Transform.scale(midEarSize,midEarSize*2.5f,midEarSize));
      midEarRotate = new TransformNode("Mid ear Size", b);
    
      body = new NameNode("body");
        Mat4 m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight+footSize,0));
        m = Mat4Transform.scale(bodyWidth,bodyHeight,bodyDepth);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
          bodyTransform = new TransformNode("body transform", m);
          bodyShape = new ModelNode("Sphere(body)", orb);

      head = new NameNode("head"); 
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight+neckSize,0));
        m = Mat4.multiply(m, Mat4Transform.scale(headSize*2,headSize*0.5f,headSize*2));
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        headTransform = new TransformNode("head transform", m);
        headShape = new ModelNode("Sphere(head)", orb);

      neck = new NameNode("neck");
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight,0));
        m = Mat4.multiply(m, Mat4Transform.scale(neckSize,neckSize*1.5f,neckSize));
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.3f,0));
        neckTransform = new TransformNode("neck transform", m);
        neckShape = new ModelNode("Sphere(neck)", orb);
      
      foot = new NameNode("foot");
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.scale(footSize,footSize,footSize));
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
        footTransform = new TransformNode("foot transform", m);
        footShape = new ModelNode("Sphere(foot)", orb);

      leftEar = new NameNode("leftEar");
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.scale(earSize,earLength,earSize));
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
        leftEarTransform = new TransformNode("LeftEar transform", m);
          leftEarShape = new ModelNode("Sphere(leftEar)", orb);

      rightEar = new NameNode("rightEar");
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.scale(earSize,earLength,earSize));
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
        rightEarTransform = new TransformNode("RightEar transform", m);
          rightEarShape = new ModelNode("Sphere(rightEar)", orb);
      
      midEar = new NameNode("midEar");
        m = new Mat4(1);
        m = Mat4.multiply(m, Mat4Transform.scale(earSize,earLength*0.5f,earSize));
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,-1));
        midEarTransform = new TransformNode("midEar transform", m);
          midEarShape = new ModelNode("Sphere(midEar)", orb);

    }

    private void buildTree(){
      robotRoot.addChild(moveTranslate);
      moveTranslate.addChild(robotTranslate);
        robotTranslate.addChild(footRotate);
        footRotate.addChild(body);
        body.addChild(bodyTransform);
          bodyTransform.addChild(bodyShape);
        body.addChild(foot);
          foot.addChild(footTransform);
          footTransform.addChild(footShape);
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
            head.addChild(toMidEarPos);
            toMidEarPos.addChild(midEarRotate);
            midEarRotate.addChild(midEar);
            midEar.addChild(midEarTransform);
            midEarTransform.addChild(midEarShape);
    }

    public SGNode getRobotRoot(){
        setup();
        buildTree();
        robotRoot.update();
        setRobotPose1();
        return robotRoot;
    }

    public static void setRobotPose1(){
        moveTranslate.setTransform(Mat4Transform.translate(-2,0,-7));
        footRotate.setTransform(Mat4Transform.rotateAroundX(0));
        neckRotate.setTransform(Mat4Transform.rotateAroundX(0));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundX(0));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundX(0));
        midEarRotate.setTransform(Mat4Transform.rotateAroundX(0));
        robotRoot.update();
    }

    public static void setRobotPose2(){
        moveTranslate.setTransform(Mat4Transform.translate(4,0,-4));
        footRotate.setTransform(Mat4.multiply(Mat4Transform.rotateAroundX(-20),Mat4Transform.rotateAroundY(130)));
        neckRotate.setTransform(Mat4Transform.rotateAroundX(30));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundX(20));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundX(-20));
        midEarRotate.setTransform(Mat4Transform.rotateAroundX(0));
        robotRoot.update();
    }

    public static void setRobotPose3(){
        moveTranslate.setTransform(Mat4Transform.translate(3,0,3));
        footRotate.setTransform(Mat4.multiply(Mat4Transform.rotateAroundZ(-40),Mat4Transform.rotateAroundY(90)));
        neckRotate.setTransform(Mat4Transform.rotateAroundZ(-50));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundZ(20));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundZ(-20));
        midEarRotate.setTransform(Mat4Transform.rotateAroundX(10));
        robotRoot.update();
    }

    public static void setRobotPose4(){
        moveTranslate.setTransform(Mat4Transform.translate(0,0,4));
        footRotate.setTransform(Mat4.multiply(Mat4Transform.rotateAroundX(-10),Mat4Transform.rotateAroundY(180)));
        neckRotate.setTransform(Mat4Transform.rotateAroundZ(10));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundZ(20));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundZ(20));
        midEarRotate.setTransform(Mat4Transform.rotateAroundX(20));
        robotRoot.update();
    }

    public static void setRobotPose5(){
        moveTranslate.setTransform(Mat4Transform.translate(-5,0,0));
        footRotate.setTransform(Mat4.multiply(Mat4Transform.rotateAroundY(-90),Mat4Transform.rotateAroundX(-10)));
        neckRotate.setTransform(Mat4Transform.rotateAroundX(-25));
        leftEarRotate.setTransform(Mat4Transform.rotateAroundX(-30));
        rightEarRotate.setTransform(Mat4Transform.rotateAroundX(-20));
        midEarRotate.setTransform(Mat4Transform.rotateAroundX(-10));
        robotRoot.update();
    }


}