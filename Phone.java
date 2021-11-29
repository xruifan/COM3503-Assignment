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

public class Phone {

    private Model plinth;
    private Model metal;
    private Model phoneScreen;
    private TransformNode phoneTranslate, plinthTransform, phoneCaseTransform, phoneScreenTransform;
    private NameNode plinthNode, phoneCaseNode, phoneScreenNode;
    private ModelNode plinthShape, phoneCaseShape, phoneScreenShape;

    public Phone(Model plinth, Model metal, Model phoneScreen){
        this.plinth = plinth;
        this.metal = metal;
        this.phoneScreen = phoneScreen;
    }


    private SGNode phoneRoot = new NameNode("phoneRoot");

    private void setup(){
        phoneTranslate = new TransformNode("phone transform",Mat4Transform.translate(6,0,-6));

        plinthNode = new NameNode("plinth");
        Mat4 m = Mat4Transform.scale(1.5f,0.75f,1.5f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        plinthTransform = new TransformNode("plinth transform", m);
            plinthShape = new ModelNode("Cube(plinth)", plinth);

        phoneCaseNode = new NameNode("phoneCase");
        m = Mat4Transform.scale(1.15f,2.55f,0.3f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.6f,0));
        phoneCaseTransform = new TransformNode("phoneCase transform", m);
            phoneCaseShape = new ModelNode("Cube(phoneCase)", metal);

        phoneScreenNode = new NameNode("phoneScreen");
        m = Mat4Transform.scale(0.9f,1.65f,0.3f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,1.1f,0.02f));
        phoneScreenTransform = new TransformNode("phoneScreen transform", m);
            phoneScreenShape = new ModelNode("Cube(phoneScreen)", phoneScreen);

    }

    private void buildTree(){
        phoneRoot.addChild(phoneTranslate);
        phoneTranslate.addChild(plinthNode);
            plinthNode.addChild(plinthTransform);
            plinthTransform.addChild(plinthShape);
            plinthNode.addChild(phoneCaseNode);
            phoneCaseNode.addChild(phoneCaseTransform);
            phoneCaseTransform.addChild(phoneCaseShape);
            phoneCaseNode.addChild(phoneScreenNode);
                phoneScreenNode.addChild(phoneScreenTransform);
                phoneScreenTransform.addChild(phoneScreenShape);
    }

    public SGNode getPhoneRoot(){
        setup();
        buildTree();
        phoneRoot.update();
        return phoneRoot;
    }

}