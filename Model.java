import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;

public class Model {
  
  private Mesh mesh;
  private int[] textureId1; 
  private int[] textureId2; 
  private Material material;
  private Shader shader;
  private Mat4 modelMatrix;
  private Camera camera;
  private Light light;
  
  public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2) {
    this.mesh = mesh;
    this.material = material;
    this.modelMatrix = modelMatrix;
    this.shader = shader;
    this.camera = camera;
    this.light = light;
    this.textureId1 = textureId1;
    this.textureId2 = textureId2;
  }
  
  public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1) {
    this(gl, camera, light, shader, material, modelMatrix, mesh, textureId1, null);
  }
  
  public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
    this(gl, camera, light, shader, material, modelMatrix, mesh, null, null);
  }
  
  public void setModelMatrix(Mat4 m) {
    modelMatrix = m;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public void setLight(Light light) {
    this.light = light;
  }

  public void render(GL3 gl, Mat4 modelMatrix) {
    Vec3 ambient = new Vec3(0.6f, 0.6f, 0.6f);
    Vec3 diffuse = new Vec3(0.3f, 0.3f, 0.3f);
    Vec3 specular = new Vec3(0.8f, 0.8f, 0.8f);

    Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
    shader.use(gl);
    shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
    shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
    
    shader.setVec3(gl, "viewPos", camera.getPosition());

    shader.setVec3(gl, "dirLight.position", light.getPosition(0));
    shader.setVec3(gl, "dirLight.ambient", light.getDirLight().getAmbient());
    shader.setVec3(gl, "dirLight.diffuse", light.getDirLight().getDiffuse());
    shader.setVec3(gl, "dirLight.specular", light.getDirLight().getSpecular());

    shader.setVec3(gl, "pointLight.position", light.getPosition(1));
    shader.setVec3(gl, "pointLight.ambient", light.getPointLight().getAmbient());
    shader.setVec3(gl, "pointLight.diffuse", light.getPointLight().getDiffuse());
    shader.setVec3(gl, "pointLight.specular", light.getPointLight().getSpecular());
    shader.setFloat(gl, "pointLight.constant", 1.0f);
    shader.setFloat(gl, "pointLight.linear", 0.09f);
    shader.setFloat(gl, "pointLight.quadratic", 0.032f);
    
    shader.setVec3(gl, "spotLight.position", light.getPosition(2));
    shader.setVec3(gl, "spotLight.ambient", light.getSpotlight().getAmbient());
    shader.setVec3(gl, "spotLight.diffuse", light.getSpotlight().getDiffuse());
    shader.setVec3(gl, "spotLight.specular", light.getSpotlight().getSpecular());
    shader.setFloat(gl, "spotLight.constant", 1.0f);
    shader.setFloat(gl, "spotLight.linear", 0.09f);
    shader.setFloat(gl, "spotLight.quadratic", 0.032f);
    shader.setVec3(gl, "spotLight.direction", SpotlightRoot.shadeDirection);
    shader.setFloat(gl, "spotLight.cutOff", (float)Math.cos(Math.toRadians(12.5)));
    shader.setFloat(gl, "spotLight.outerCutOff", (float)Math.cos(Math.toRadians(15)));
    
    shader.setVec3(gl, "material.ambient", material.getAmbient());
    shader.setVec3(gl, "material.diffuse", material.getDiffuse());
    shader.setVec3(gl, "material.specular", material.getSpecular());
    shader.setFloat(gl, "material.shininess", material.getShininess());  

    if (textureId1!=null) {
      shader.setInt(gl, "first_texture", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
      gl.glActiveTexture(GL.GL_TEXTURE0);
      gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
    }
    if (textureId2!=null) {
      shader.setInt(gl, "second_texture", 1);
      gl.glActiveTexture(GL.GL_TEXTURE1);
      gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
    }
    mesh.render(gl);
  } 
  
  public void render(GL3 gl) {
    render(gl, modelMatrix);
  }
  
  public void dispose(GL3 gl) {
    mesh.dispose(gl);
    if (textureId1!=null) gl.glDeleteBuffers(1, textureId1, 0);
    if (textureId2!=null) gl.glDeleteBuffers(1, textureId2, 0);
  }

}