import gmaths.*;
import java.util.*;  

import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;
  
public class Museum_GLEventListener implements GLEventListener {
  
  private static final boolean DISPLAY_SHADERS = false;
    
  public Museum_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(4f,12f,18f));
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    //floor.dispose(gl);
    for (Model wallPiece : windowWall){
      wallPiece.dispose(gl);
    }
    for (Model wallPiece : doorWall){
      wallPiece.dispose(gl);
    }
    sceneDay.dispose(gl);
    sceneNight.dispose(gl);
    plinth.dispose(gl);
    orb.dispose(gl);
    metal.dispose(gl);
    lightBulb.dispose(gl);
    phoneScreen.dispose(gl);
  }

    
  // ***************************************************
  /* INTERACTION
   *
   *
   */
   
   public void incXPosition() {
     xPosition += 0.5f;
     if (xPosition>5f) xPosition = 5f;
     updateX();
   }
   
   public void decXPosition() {
     xPosition -= 0.5f;
     if (xPosition<-5f) xPosition = -5f;
     updateX();
   }
   
   private void updateX() {
     translateX.setTransform(Mat4Transform.translate(xPosition,0,0));
     translateX.update(); // IMPORTANT  the scene graph has changed
   }
   
   
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */
   
  private Camera camera;
  private Mat4 perspective;
  private Model floor, sceneDay, sceneNight, plinth, orb, metal, phoneScreen, lightBulb;
  private List<Model> windowWall = new ArrayList<>();
  private List<Model> doorWall = new ArrayList<>();
  private Light light;
  private SGNode phoneRoot, eggRoot, spotlightRoot, robotRoot;
  private TransformNode translateX, rotateAll, rotateUpper;
  private float xPosition = 0;
  private float rotateAllAngleStart = 25, rotateAllAngle = rotateAllAngleStart;
  private float rotateUpperAngleStart = -60, rotateUpperAngle = rotateUpperAngleStart;
  
  private void initialise(GL3 gl) {
    createRandomNumbers();
    int[] textureFloor = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
    int[] textureFloorSpecular = TextureLibrary.loadTexture(gl, "textures/floor_specular.jpg");
    int[] textureWall = TextureLibrary.loadTexture(gl, "textures/wall.jpg");
    int[] textureSky = TextureLibrary.loadTexture(gl, "textures/garden.jpg");
    int[] textureNight = TextureLibrary.loadTexture(gl, "textures/night_sky.jpg");
    int[] texturePlinth = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] texturePlinthSpecular = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureEgg = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
    int[] textureEggSpecular = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureMetal = TextureLibrary.loadTexture(gl, "textures/phone_case.jpg");
    int[] texturePhoneScreen = TextureLibrary.loadTexture(gl, "textures/phone_screen.jpg");
    
    light = new Light(gl);
    light.setCamera(camera);
    
    // floor
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");

    floor = new Floor(gl, camera, light, shader, mesh, textureFloor, textureFloorSpecular).getFloor();

    // window wall
    windowWall = new Wall(gl, camera, light, shader, mesh, textureWall).getWindowWall();

    // door wall
    doorWall = new Wall(gl, camera, light, shader, mesh, textureWall).getDoorWall();
      
    // scene
    sceneDay = new Scene(gl, camera, light, shader, mesh, textureSky).getScene();
    sceneNight = new Scene(gl, camera, light, shader, mesh, textureNight).getScene();

    // plinth
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");

    plinth = new Plinth(gl, camera, light, shader, mesh, texturePlinth, texturePlinthSpecular).getPlinth();

    // orb
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_sphere_04.txt", "fs_sphere_04.txt");

    orb = new Orb(gl, camera, light, shader, mesh, textureEgg, textureEggSpecular).getOrb();

    // lightBulb
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_light_01.txt", "fs_light_01.txt");

    lightBulb = new LightBulb(gl, camera, light, shader, mesh).getLightBulb();

    // egg root
    eggRoot = new EggRoot(plinth, orb).getEggRoot();


    ///
    Material material = new Material(new Vec3(1.0f, 1.0f, 1.0f), new Vec3(1.0f, 1.0f, 1.0f), new Vec3(0.1f, 0.1f, 0.1f), 16.0f);
    float size = 16f;
    Mat4 modelMatrix = Mat4Transform.scale(size,1f,size);
    Mat4 m = Mat4Transform.scale(1.0f,0.5f,1.0f);
    ///

    // phone case (metal)
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");

    metal = new Metal(gl, camera, light, shader, mesh, textureMetal).getMetal();

    // phone screen
    phoneScreen = new PhoneScreen(gl, camera, light, shader, mesh, texturePhoneScreen).getPhoneScreen();

    // phone root
    phoneRoot = new PhoneRoot(plinth, metal, phoneScreen).getPhoneRoot();

    // spot light root
    spotlightRoot = new SpotlightRoot(metal, lightBulb).getSpotlightRoot();

    // robot root 
    robotRoot = new RobotRoot(orb).getRobotRoot();

  }
 
  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    light.setPosition(getLightPosition());  // changing light position each frame
    light.render(gl);
    floor.render(gl);
    for (Model wallPiece : windowWall){
      wallPiece.render(gl);
    }
    for (Model wallPiece : doorWall){
      wallPiece.render(gl);
    }
    skyRender(gl);
    eggRoot.draw(gl);
    phoneRoot.draw(gl);
    updateShade();
    spotlightRoot.draw(gl);
    robotRoot.draw(gl);
  }

  /*
  private void updateBranches() {
    double elapsedTime = getSeconds()-startTime;
    rotateAllAngle = rotateAllAngleStart*(float)Math.sin(elapsedTime);
    rotateUpperAngle = rotateUpperAngleStart*(float)Math.sin(elapsedTime*0.7f);
    rotateAll.setTransform(Mat4Transform.rotateAroundZ(rotateAllAngle));
    rotateUpper.setTransform(Mat4Transform.rotateAroundZ(rotateUpperAngle));
    twoBranchRoot.update(); // IMPORTANT  the scene graph has changed
  }
  */

  // The scene's texture changes by time 
  private void skyRender(GL3 gl){
    double elapsedTime = getSeconds()-startTime;
    if (Math.sin(Math.toRadians(elapsedTime*25)) >= 0){
      sceneDay.render(gl);
    } else {
      sceneNight.render(gl);
    }
  }

  // The light's postion is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 5.0f*(float)(Math.sin(Math.toRadians(elapsedTime*50)));
    float y = 2.7f;
    float z = 5.0f*(float)(Math.cos(Math.toRadians(elapsedTime*50)));
    return new Vec3(x,y,z);   
  }

  private void updateShade() {
    double elapsedTime = getSeconds()-startTime;
    float rotateAngle = 180f+45f*(float)Math.sin(elapsedTime);
    SpotlightRoot.updateShadeRotation(rotateAngle);
  }
   
  // ***************************************************
  /* TIME
   */ 
  
  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  // ***************************************************
  /* An array of random numbers
   */ 
  
  private int NUM_RANDOMS = 1000;
  private float[] randoms;
  
  private void createRandomNumbers() {
    randoms = new float[NUM_RANDOMS];
    for (int i=0; i<NUM_RANDOMS; ++i) {
      randoms[i] = (float)Math.random();
    }
  }
  
}