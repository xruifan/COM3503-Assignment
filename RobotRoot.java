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
    moveTranslate = new TransformNode("robot transform",Mat4Transform.translate(0f,0,0f));
    robotTranslate = new TransformNode("robot transform",Mat4Transform.translate(0,0,0f));
    backToOrigin = new TransformNode("back to origin", Mat4Transform.translate(0,-(bodyHeight+neckSize),0));
    backToHead = new TransformNode("back to head", Mat4Transform.translate(0,(bodyHeight+neckSize),0));
    toLeftEarPos = new TransformNode("move to left ear pos", Mat4Transform.translate(-0.1f,bodyHeight+((neckSize+headSize)/2),0));
    toRightEarPos = new TransformNode("move to right ear pos", Mat4Transform.translate(0.1f,bodyHeight+((neckSize+headSize)/2),0));
    toMidEarPos = new TransformNode("move to mid ear pos", Mat4Transform.translate(0,bodyHeight+((neckSize+(headSize))/2),0.25f));

    Mat4 x = new Mat4(1);
      x = new Mat4(1);
      x = Mat4.multiply(x, Mat4Transform.rotateAroundX(0));
      x = Mat4.multiply(x, Mat4Transform.rotateAroundZ(0));
      x = Mat4.multiply(x, Mat4Transform.rotateAroundY(0));
      footRotate = new TransformNode("with foot rotation", x);

    Mat4 y = new Mat4(1);
      y = Mat4.multiply(y, Mat4Transform.rotateAroundX(0));
      y = Mat4.multiply(y, Mat4Transform.rotateAroundZ(0));
      y = Mat4.multiply(y, Mat4Transform.rotateAroundY(0));
      neckRotate = new TransformNode("with neck rotation", y);

    Mat4 z = new Mat4(1);
    z = Mat4.multiply(z, Mat4Transform.rotateAroundX(0));
    z = Mat4.multiply(z, Mat4Transform.rotateAroundZ(0));
    leftEarRotate = new TransformNode("left ear rotation", z);

    Mat4 a = new Mat4(1);
    a = Mat4.multiply(a, Mat4Transform.rotateAroundX(0));
    a = Mat4.multiply(a, Mat4Transform.rotateAroundZ(0));
    rightEarRotate = new TransformNode("right ear rotation", a);

    Mat4 b = new Mat4(1);
    b = Mat4.multiply(b, Mat4Transform.scale(midEarSize,midEarSize*2.5f,midEarSize));
    midEarRotate = new TransformNode("mid ear Size", b);
  
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
      moveTranslate.setTransform(Mat4Transform.translate(-2,0,-7));
      robotRoot.update();
      return robotRoot;
  }

  // animation
  private static float currentTarX, targetX= -2f;
  private static float currentTarZ, targetZ= -7f;
  private static float currentTarFRX, currentTarFRY, currentTarFRZ, targetFRX, targetFRY, targetFRZ, 
      currentTarNRX, currentTarNRY, currentTarNRZ, targetNRX, targetNRY, targetNRZ, currentTarLERX, currentTarLERY, currentTarLERZ, targetLERX, 
      targetLERY, targetLERZ, currentTarRERX, currentTarRERY, currentTarRERZ, targetRERX, targetRERY, targetRERZ, currentTarMERX, currentTarMERY, 
      currentTarMERZ, targetMERX, targetMERY, targetMERZ = 0;

  private static void setLocation(float x, float z){
    targetX = x;
    targetZ = z;
  }

  private static void setFR(float fRX, float fRY, float fRZ){
    targetFRX = fRX;
    targetFRY = fRY;
    targetFRZ = fRZ;
  }

  private static void setNR(float nRX, float nRY, float nRZ){
    targetNRX = nRX;
    targetNRY = nRY;
    targetNRZ = nRZ;
  }

  private static void setLER(float lERX, float lERY, float lERZ){
    targetLERX = lERX;
    targetLERY = lERY;
    targetLERZ = lERZ;
  }

  private static void setRER(float rERX, float rERY, float rERZ){
    targetRERX = rERX;
    targetRERY = rERY;
    targetRERZ = rERZ;

  }

  private static void setMER(float mERX, float mERY, float mERZ){
    targetMERX = mERX;
    targetMERY = mERY;
    targetMERZ = mERZ;
  }
  
  public static void setRobotPose1(){
    setLocation(-2f,-7f);
    setFR(0f,0f,0f);
    setNR(0f,0f,0f);
    setLER(0f,0f,0f);
    setRER(0f,0f,0f);
    setMER(0f,0f,0f);
  }

  public static void setRobotPose2(){
    setLocation(4f,-4f);
    setFR(-20f,130f,0f);
    setNR(30f,0f,0f);
    setLER(20f,0f,0f);
    setRER(-20f,0f,0f);
    setMER(0f,0f,0f);
  }


  public static void setRobotPose3(){
    setLocation(3f,3f);
    setFR(0f,-0f,-20f);
    setNR(0f,0f,-50f);
    setLER(0f,0f,20f);
    setRER(0f,0f,-20f);
    setMER(10f,0f,0f);
  }

  public static void setRobotPose4(){
    setLocation(0f,4f);
    setFR(-10f,180f,0f);
    setNR(0f,0f,10f);
    setLER(0f,0f,20f);
    setRER(0f,0f,20f);
    setMER(20f,0f,0f);
  }

  public static void setRobotPose5(){
    setLocation(-5f,0f);
    setFR(-10f,270f,0f);
    setNR(-25f,0f,0f);
    setLER(-30f,0f,0f);
    setRER(-20f,0f,0f);
    setMER(-10f,0f,0f);
  }
  
  private static void updateNextL(){
    float lXMovement = Math.abs(currentTarX - targetX)/30;
    float lZMovement = Math.abs(currentTarZ - targetZ)/30;
    if (currentTarX <= targetX) currentTarX += lXMovement;
    if (currentTarX >= targetX) currentTarX -= lXMovement;
    if (currentTarZ <= targetZ) currentTarZ += lZMovement;
    if (currentTarZ >= targetZ) currentTarZ -= lZMovement;
  }

  private static void updateNextFR(){
    float fRXMovement = Math.abs(currentTarFRX - targetFRX)/20;
    float fRYMovement = Math.abs(currentTarFRY - targetFRY)/20;
    float fRZMovement = Math.abs(currentTarFRZ - targetFRZ)/20;
    if (currentTarFRX <= targetFRX) currentTarFRX += fRXMovement;
    if (currentTarFRX >= targetFRX) currentTarFRX -= fRXMovement;
    if (currentTarFRY <= targetFRY) currentTarFRY += fRYMovement;
    if (currentTarFRY >= targetFRY) currentTarFRY -= fRYMovement;
    if (currentTarFRZ <= targetFRZ) currentTarFRZ += fRZMovement;
    if (currentTarFRZ >= targetFRZ) currentTarFRZ -= fRZMovement;
  }

  private static void updateNextNR(){
    float nRXMovement = Math.abs(currentTarNRX - targetNRX)/2;
    float nRYMovement = Math.abs(currentTarNRY - targetNRY)/2;
    float nRZMovement = Math.abs(currentTarNRZ - targetNRZ)/2;
    if (currentTarNRX <= targetNRX) currentTarNRX += nRXMovement;
    if (currentTarNRX >= targetNRX) currentTarNRX -= nRXMovement;
    if (currentTarNRY <= targetNRY) currentTarNRY += nRYMovement;
    if (currentTarNRY >= targetNRY) currentTarNRY -= nRYMovement;
    if (currentTarNRZ <= targetNRZ) currentTarNRZ += nRZMovement;
    if (currentTarNRZ >= targetNRZ) currentTarNRZ -= nRZMovement;
  }

  private static void updateNextLER(){
    float lERXMovement = Math.abs(currentTarLERX - targetLERX)/2;
    float lERYMovement = Math.abs(currentTarLERY - targetLERY)/2;
    float lERZMovement = Math.abs(currentTarLERZ - targetLERZ)/2;
    if (currentTarLERX <= targetLERX) currentTarLERX += lERXMovement;
    if (currentTarLERX >= targetLERX) currentTarLERX -= lERXMovement;
    if (currentTarLERY <= targetLERY) currentTarLERY += lERYMovement;
    if (currentTarLERY >= targetLERY) currentTarLERY -= lERYMovement;
    if (currentTarLERZ <= targetLERZ) currentTarLERZ += lERZMovement;
    if (currentTarLERZ >= targetLERZ) currentTarLERZ -= lERZMovement;
  }

  private static void updateNextRER(){
    float rERXMovement = Math.abs(currentTarRERX - targetRERX)/2;
    float rERYMovement = Math.abs(currentTarRERY - targetRERY)/2;
    float rERZMovement = Math.abs(currentTarRERZ - targetRERZ)/2;
    if (currentTarRERX <= targetRERX) currentTarRERX += rERXMovement;
    if (currentTarRERX >= targetRERX) currentTarRERX -= rERXMovement;
    if (currentTarRERY <= targetRERY) currentTarRERY += rERYMovement;
    if (currentTarRERY >= targetRERY) currentTarRERY -= rERYMovement;
    if (currentTarRERZ <= targetRERZ) currentTarRERZ += rERZMovement;
    if (currentTarRERZ >= targetRERZ) currentTarRERZ -= rERZMovement;
  }

  private static void updateNextMER(){
    float mERXMovement = Math.abs(currentTarMERX - targetMERX)/2;
    float mERYMovement = Math.abs(currentTarMERY - targetMERY)/2;
    float mERZMovement = Math.abs(currentTarMERZ - targetMERZ)/2;
    if (currentTarMERX <= targetMERX) currentTarMERX += mERXMovement;
    if (currentTarMERX >= targetMERX) currentTarMERX -= mERXMovement;
    if (currentTarMERY <= targetMERY) currentTarMERY += mERYMovement;
    if (currentTarMERY >= targetMERY) currentTarMERY -= mERYMovement;
    if (currentTarMERZ <= targetMERZ) currentTarMERZ += mERZMovement;
    if (currentTarMERZ >= targetMERZ) currentTarMERZ -= mERZMovement;
  }

  public static void doRobotTargetPose(){
    updateNextL();
    updateNextFR();
    updateNextNR();
    updateNextLER();
    updateNextRER();
    updateNextMER();

    moveTranslate.setTransform(Mat4Transform.translate(currentTarX,0,currentTarZ));
    footRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundX(currentTarFRX),Mat4Transform.rotateAroundY(currentTarFRY)),Mat4Transform.rotateAroundZ(currentTarFRZ)));
    neckRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundX(currentTarNRX),Mat4Transform.rotateAroundY(currentTarNRY)),Mat4Transform.rotateAroundZ(currentTarNRZ)));
    leftEarRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundX(currentTarLERX),Mat4Transform.rotateAroundY(currentTarNRY)),Mat4Transform.rotateAroundZ(currentTarLERZ)));
    rightEarRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundX(currentTarRERX),Mat4Transform.rotateAroundY(currentTarRERY)),Mat4Transform.rotateAroundZ(currentTarRERZ)));
    midEarRotate.setTransform(Mat4.multiply(Mat4.multiply(Mat4Transform.rotateAroundX(currentTarMERX),Mat4Transform.rotateAroundY(currentTarMERY)),Mat4Transform.rotateAroundZ(currentTarMERZ)));
    robotRoot.update();
  }


}