import gmaths.*; 
import java.util.*;  

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class Wall {

    private GL3 gl;
    private Camera camera;
    private Light light;
    private Shader shader;
    private Mesh mesh;
    private int[] texture;
    private Mat4 modelMatrix;
    private List<Model> windowWall = new ArrayList<>();
    private List<Model> doorWall = new ArrayList<>();

    private float size = 16f;
    private Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.3f, 0.3f, 0.3f), 32.0f);
    
    public Wall(GL3 gl, Camera camera, Light light, Shader shader, Mesh mesh, int[] texture) {
        this.gl = gl;
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.mesh = mesh;
        this.texture = texture;
    }
    

    public List<Model> getWindowWall() {
        // window wall
        modelMatrix = Mat4Transform.scale(1f,1f,1f);
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    if ((i == 1 && j == 1) || (i == 1 && j == 2)) continue;
                    modelMatrix = Mat4Transform.scale(size/4,1f,size/4);
                    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundY(90), modelMatrix);
                    modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
                    modelMatrix = Mat4.multiply(Mat4Transform.translate(-size*0.5f,i*size/4+(size/4)*0.5f,(j-1)*size/4-(size/4)*0.5f), modelMatrix);
                    windowWall.add(new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture));
                }
            }
        return windowWall;
    }

    public List<Model> getDoorWall() {
        // door wall
        modelMatrix = Mat4Transform.scale(size/4,1f,size/4);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if ((i == 1 && j == 1) || (i == 0 && j == 1)){ continue;};
                modelMatrix = Mat4Transform.scale(size/4,1f,size/4);
                modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
                modelMatrix = Mat4.multiply(Mat4Transform.translate((j-1)*size/4-(size/4)*0.5f,i*size/4+(size/4)*0.5f,-size*0.5f), modelMatrix);
                doorWall.add(new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture));
            }
        }
        return doorWall;
    }

}