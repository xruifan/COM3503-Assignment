import gmaths.*; 

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;

public class Metal {

    private GL3 gl;
    private Camera camera;
    private Light light;
    private Shader shader;
    private Mesh mesh;
    private int[] texture;

    Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), 4.0f);
    Mat4 modelMatrix = Mat4.multiply(Mat4Transform.scale(1.0f,1.0f,1.0f), Mat4Transform.translate(0.0f,0.5f,0.0f));

    public Metal(GL3 gl, Camera camera, Light light, Shader shader, Mesh mesh, int[] texture) {
        this.gl = gl;
        this.camera = camera;
        this.light = light;
        this.shader = shader;
        this.mesh = mesh;
        this.texture = texture;
    }

    public Model getMetal() {
        return new Model(gl, camera, light, shader, material, modelMatrix, mesh, texture);
    }
    
}
