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

public class Orb {

    private GL3 gl;
    private Camera camera;
    private Light light;
    private Shader shader;
    private Mesh mesh;
    private int[] texture;
    private int[] textureSpeculer;

    Material material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(1.0f,1.0f,1.0f), Mat4Transform.translate(0,0.5f,0));

    public Orb(GL3 gl, Camera camera, Light light, Shader shader, Mesh mesh, int[] texture, int[] textureSpeculer) {
        this.gl = gl;
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.mesh = mesh;
        this.texture = texture;
        this.textureSpeculer = textureSpeculer;
    }

    public Model getOrb() {
        return new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture, textureSpeculer);
    }
    
}