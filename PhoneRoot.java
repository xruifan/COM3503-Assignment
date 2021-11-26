import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class PhoneRoot {

    private Model plinth;
    private Model metal;
    private Model phoneScreen;

    public PhoneRoot(Model plinth, Model metal, Model phoneScreen){
        this.plinth = plinth;
        this.metal = metal;
        this.phoneScreen = phoneScreen;
    }


    private SGNode phoneRoot = new NameNode("phoneRoot");

    private void setupTree(){
        TransformNode phoneTranslate = new TransformNode("phone transform",Mat4Transform.translate(6,0,-6));

        NameNode plinthNode = new NameNode("plinth");
        Mat4 m = Mat4Transform.scale(1.0f,0.5f,1.0f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        TransformNode plinthTransform = new TransformNode("plinth transform", m);
            ModelNode plinthShape = new ModelNode("Cube(plinth)", plinth);

        NameNode phoneCaseNode = new NameNode("phoneCase");
        m = Mat4Transform.scale(0.7f,1.7f,0.2f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.6f,0));
        TransformNode phoneCaseTransform = new TransformNode("phoneCase transform", m);
            ModelNode phoneCaseShape = new ModelNode("Cube(phoneCase)", metal);

        NameNode phoneScreenNode = new NameNode("phoneScreen");
        m = Mat4Transform.scale(0.6f,1.1f,0.2f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,1.1f,0.02f));
        TransformNode phoneScreenTransform = new TransformNode("phoneScreen transform", m);
            ModelNode phoneScreenShape = new ModelNode("Cube(phoneScreen)", phoneScreen);

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
        setupTree();
        phoneRoot.update();
        return phoneRoot;
    }

}