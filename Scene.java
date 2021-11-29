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

public class Scene {

    private GL3 gl;
    private Camera camera;
    private Light light;
    private Shader shader;
    private Mesh mesh;
    private int[] texture;

    Material material = new Material(new Vec3(1.0f,1.0f,1.0f), new Vec3(1.0f,1.0f,1.0f), new Vec3(1.0f,1.0f,1.0f), 16.0f);
    float size = 16f;
    Mat4 modelMatrix = Mat4Transform.scale(size*3,1f,size*3);

    public Scene(GL3 gl, Camera camera, Light light, Shader shader, Mesh mesh, int[] texture) {
        this.gl = gl;
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.mesh = mesh;
        this.texture = texture;
    }

    public Model getScene() {
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(-size*1.0f,size*0.5f,0), modelMatrix);
        return new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture);
    }
    
}
