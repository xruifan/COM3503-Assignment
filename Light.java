import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
  
public class Light {
  
  private Material material, dirLight, pointLight, spotlight;
  private Vec3[] position = new Vec3[3];
  private Mat4 model;
  private Shader shader;
  private Camera camera;
    
  public Light(GL3 gl) {
    material = new Material();
    material.setAmbient(0.3f, 0.3f, 0.3f);
    material.setDiffuse(0.3f, 0.3f, 0.3f);
    material.setSpecular(0.8f, 0.8f, 0.8f);

    dirLight = new Material();
    onDirLight();
    
    pointLight = new Material();
    onPointLight();

    spotlight = new Material();
    onSpotlight();

    position[0] = new Vec3(0f,15f,0f);
    position[1] = new Vec3(-7f,5f,-7f);
    position[2] = new Vec3(0f,0f,0f);

    model = new Mat4(1);
    shader = new Shader(gl, "vs_light_01.txt", "fs_light.txt");
    fillBuffers(gl);
  }

  public void onDirLight(){
      dirLight.setAmbient(0.6f, 0.6f, 0.6f);
      dirLight.setDiffuse(0.6f, 0.6f, 0.6f);
      dirLight.setSpecular(1.0f, 1.0f, 1.0f);
  }

  public void offDirLight(){
      dirLight.setAmbient(0.0f, 0.0f, 0.0f);
      dirLight.setDiffuse(0.0f, 0.0f, 0.0f);
      dirLight.setSpecular(0.0f, 0.0f, 0.0f);
  }

  public void onPointLight(){
      pointLight.setAmbient(1.0f, 1.0f, 1.0f);
      pointLight.setDiffuse(1.0f, 1.0f, 1.0f);
      pointLight.setSpecular(1.0f, 1.0f, 1.0f);
  }

  public void offPointLight(){
      pointLight.setAmbient(0.0f, 0.0f, 0.0f);
      pointLight.setDiffuse(0.0f, 0.0f, 0.0f);
      pointLight.setSpecular(0.0f, 0.0f, 0.0f);
  } 
  
  public void onSpotlight(){
      spotlight.setAmbient(1.0f, 1.0f, 1.0f);
      spotlight.setDiffuse(1.0f, 1.0f, 1.0f);
      spotlight.setSpecular(1.0f, 1.0f, 1.0f);
  }
  
  public void offSpotlight(){
      spotlight.setAmbient(0.0f, 0.0f, 0.0f);
      spotlight.setDiffuse(0.0f, 0.0f, 0.0f);
      spotlight.setSpecular(0.0f, 0.0f, 0.0f);
  } 

  public void setPosition(Vec3 v, int index) {
    position[index].x = v.x;
    position[index].y = v.y;
    position[index].z = v.z;
  }
  
  public void setPosition(float x, float y, float z, int index) {
    position[index].x = x;
    position[index].y = y;
    position[index].z = z;
  }
  
  public Vec3 getPosition(int index) {
    return position[index];
  }
  
  public void setMaterial(Material m) {
    material = m;
  }

  public void setDirLight(Material m) {
    dirLight = m;
  }

  public void setPointLight(Material m) {
    pointLight = m;
  }

  public void setSpotlight(Material m) {
    spotlight = m;
  }
  
  public Material getMaterial() {
    return material;
  }

  public Material getDirLight() {
    return dirLight;
  }

  public Material getPointLight() {
    return pointLight;
  }

  public Material getSpotlight() {
    return spotlight;
  }
  
  public void setCamera(Camera camera) {
    this.camera = camera;
  }
  
  public void render(GL3 gl) {

    gl.glBindVertexArray(vertexArrayId[0]);
    position[2] = SpotlightRoot.lightPos.toVec3();
    //position[2] = new Vec3(5,7,5);

    for (int i = 0; i < position.length; i++){
      if (i == 0) continue;
      Mat4 model = new Mat4(1);
      model = Mat4.multiply(Mat4Transform.scale(0.4f,0.4f,0.4f), model);
      model = Mat4.multiply(Mat4Transform.translate(position[i]), model);
       
      Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), model));
      shader.use(gl);
      shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());
      gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
    }

    gl.glBindVertexArray(0);
  }

  public void dispose(GL3 gl) {
    gl.glDeleteBuffers(1, vertexBufferId, 0);
    gl.glDeleteVertexArrays(1, vertexArrayId, 0);
    gl.glDeleteBuffers(1, elementBufferId, 0);
  }

    // ***************************************************
  /* THE DATA
   */
  // anticlockwise/counterclockwise ordering
  
  private static final int XLONG = 30;
  private static final int YLAT = 30;

  public static final float[] vertices = createVertices();
  public static final int[] indices = createIndices();

  private static float[] createVertices() {
    double r = 0.5;
    int step = 8;
    //float[] 
    float[] vertices = new float[XLONG*YLAT*step];
    for (int j = 0; j<YLAT; ++j) {
      double b = Math.toRadians(-90+180*(double)(j)/(YLAT-1));
      for (int i = 0; i<XLONG; ++i) {
        double a = Math.toRadians(360*(double)(i)/(XLONG-1));
        double z = Math.cos(b) * Math.cos(a);
        double x = Math.cos(b) * Math.sin(a);
        double y = Math.sin(b);
        int base = j*XLONG*step;
        vertices[base + i*step+0] = (float)(r*x);
        vertices[base + i*step+1] = (float)(r*y);
        vertices[base + i*step+2] = (float)(r*z); 
        vertices[base + i*step+3] = (float)x;
        vertices[base + i*step+4] = (float)y;
        vertices[base + i*step+5] = (float)z;
        vertices[base + i*step+6] = (float)(i)/(float)(XLONG-1);
        vertices[base + i*step+7] = (float)(j)/(float)(YLAT-1);
      }
    }
    return vertices;
    
    //debugging code:
    //for (int i=0; i<vertices.length; i+=step) {
    //  System.out.println(vertices[i]+", "+vertices[i+1]+", "+vertices[i+2]);
    //}
  }
  
  private static int[] createIndices() {
    int[] indices = new int[(XLONG-1)*(YLAT-1)*6];
    for (int j = 0; j<YLAT-1; ++j) {
      for (int i = 0; i<XLONG-1; ++i) {
        int base = j*(XLONG-1)*6;
        indices[base + i*6+0] = j*XLONG+i;
        indices[base + i*6+1] = j*XLONG+i+1;
        indices[base + i*6+2] = (j+1)*XLONG+i+1;
        indices[base + i*6+3] = j*XLONG+i;
        indices[base + i*6+4] = (j+1)*XLONG+i+1;
        indices[base + i*6+5] = (j+1)*XLONG+i;
      }
    }
    return indices;
    
    //debugging code:
    //for (int i=0; i<indices.length; i+=3) {
    //  System.out.println(indices[i]+", "+indices[i+1]+", "+indices[i+2]);
    //}
  }
    
  private int vertexStride = 8;
  private int vertexXYZFloats = 3;
  
  // ***************************************************
  /* THE LIGHT BUFFERS
   */

  private int[] vertexBufferId = new int[1];
  private int[] vertexArrayId = new int[1];
  private int[] elementBufferId = new int[1];
    
  private void fillBuffers(GL3 gl) {
    gl.glGenVertexArrays(1, vertexArrayId, 0);
    gl.glBindVertexArray(vertexArrayId[0]);
    gl.glGenBuffers(1, vertexBufferId, 0);
    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferId[0]);
    FloatBuffer fb = Buffers.newDirectFloatBuffer(vertices);
    
    gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, fb, GL.GL_STATIC_DRAW);
    
    int stride = vertexStride;
    int numXYZFloats = vertexXYZFloats;
    int offset = 0;
    gl.glVertexAttribPointer(0, numXYZFloats, GL.GL_FLOAT, false, stride*Float.BYTES, offset);
    gl.glEnableVertexAttribArray(0);
     
    gl.glGenBuffers(1, elementBufferId, 0);
    IntBuffer ib = Buffers.newDirectIntBuffer(indices);
    gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, elementBufferId[0]);
    gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, ib, GL.GL_STATIC_DRAW);
    gl.glBindVertexArray(0);
  } 

}