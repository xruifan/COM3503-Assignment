import gmaths.*;

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class EggRoot {
    
    private Model plinth;
    private Model orb;
    private SGNode eggRoot = new NameNode("eggRoot");

    public EggRoot(Model plinth, Model orb){
        this.plinth = plinth;
        this.orb = orb;
    }

    private void setupTree(){
        TransformNode eggTranslate = new TransformNode("egg transform",Mat4Transform.translate(0,0,0));

        NameNode eggPlinthNode = new NameNode("eggPlinth");
        Mat4 m = Mat4Transform.scale(1.0f,0.5f,1.0f);
        m = Mat4.multiply(m, Mat4Transform.translate(0.0f,0.5f,0.0f));
        TransformNode eggPlinthTransform = new TransformNode("egg plinth transform", m);
            ModelNode eggPlinthShape = new ModelNode("Cube(egg plinth)", plinth);

        NameNode eggNode = new NameNode("egg");
        m = Mat4Transform.scale(1.0f,1.5f,1.0f);
        m = Mat4.multiply(m, Mat4Transform.translate(0,0.7f,0));
        TransformNode eggTransform = new TransformNode("egg transform", m);
            ModelNode eggShape = new ModelNode("Sphere(egg)", orb);

        eggRoot.addChild(eggTranslate);
        eggTranslate.addChild(eggPlinthNode);
        eggPlinthNode.addChild(eggPlinthTransform);
        eggPlinthTransform.addChild(eggPlinthShape);
        eggPlinthNode.addChild(eggNode);
            eggNode.addChild(eggTransform);
            eggTransform.addChild(eggShape);
    }


    public SGNode getEggRoot(){
        setupTree();
        eggRoot.update();
        return eggRoot;
    }
}