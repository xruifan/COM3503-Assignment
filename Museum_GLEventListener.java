import gmaths.*;
import java.util.*;  

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

  // my own work
  private boolean dirStatus = true;
  private boolean pointStatus = true;
  private boolean spotlightStatus = true;

  public void dirLightSwitch(){
    if (dirStatus){
      light.offDirLight();
      dirStatus = false;
    } else {
      light.onDirLight();
      dirStatus = true;
    }
  }

  public void pointLightSwitch(){
    if (pointStatus){
      light.offPointLight();
      pointStatus = false;
    } else {
      light.onPointLight();
      pointStatus = true;
    }
  }

  public void spotlightSwitch(){
    if (spotlightStatus){
      light.offSpotlight();
      spotlightStatus = false;
    } else {
      light.onSpotlight();
      spotlightStatus = true;
    }
  }
   
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */
   
  private Camera camera;
  private Mat4 perspective;
  private Model floor, sceneDay, sceneNight, plinth, orb, metal, phoneScreen, lightBulb, venOrb;
  private List<Model> windowWall = new ArrayList<>();
  private List<Model> doorWall = new ArrayList<>();
  public Robot robot;
  public Spotlight spotlight;
  private Light light;
  private SGNode phoneRoot, eggRoot, spotlightRoot, robotRoot;

  
  private void initialise(GL3 gl) {
    
    int[] textureFloor = TextureLibrary.loadTexture(gl, "textures/floor.jpg");
    int[] textureFloorSpecular = TextureLibrary.loadTexture(gl, "textures/floor_specular.jpg");
    int[] textureWall = TextureLibrary.loadTexture(gl, "textures/wall.jpg");
    int[] textureWallSpecular = TextureLibrary.loadTexture(gl, "textures/wall_specular.jpg");
    int[] textureSky = TextureLibrary.loadTexture(gl, "textures/sky.jpg");
    int[] textureNight = TextureLibrary.loadTexture(gl, "textures/night_sky.jpg");
    int[] texturePlinth = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] texturePlinthSpecular = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureEgg = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
    int[] textureEggSpecular = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureMetal = TextureLibrary.loadTexture(gl, "textures/metal.jpg");
    int[] texturePhoneScreen = TextureLibrary.loadTexture(gl, "textures/phone_screen.jpg");
    int[] textureVen = TextureLibrary.loadTexture(gl, "textures/ven0aaa2.jpg");
    int[] textureVenSpecular = TextureLibrary.loadTexture(gl, "textures/ven0aaa2_specular.jpg");
    
    light = new Light(gl);
    light.setCamera(camera);
    
    // floor
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "vs.txt", "fs_2.txt");

    floor = new Floor(gl, camera, light, shader, mesh, textureFloor, textureFloorSpecular).getFloor();

    // window wall
    windowWall = new Wall(gl, camera, light, shader, mesh, textureWall, textureWallSpecular).getWindowWall();

    // door wall
    doorWall = new Wall(gl, camera, light, shader, mesh, textureWall, textureWallSpecular).getDoorWall();
      
    // scene
    shader = new Shader(gl, "vs_tt.txt", "fs.txt");
    sceneDay = new Scene(gl, camera, light, shader, mesh, textureSky).getScene();
    sceneNight = new Scene(gl, camera, light, shader, mesh, textureNight).getScene();

    // plinth
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs.txt", "fs_2.txt");

    plinth = new Plinth(gl, camera, light, shader, mesh, texturePlinth, texturePlinthSpecular).getPlinth();

    // orb
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());

    orb = new Orb(gl, camera, light, shader, mesh, textureEgg, textureEggSpecular).getOrb();
    venOrb = new Orb(gl, camera, light, shader, mesh, textureVen, textureVenSpecular).getOrb();

    // lightBulb
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs.txt", "fs.txt");

    lightBulb = new LightBulb(gl, camera, light, shader, mesh).getLightBulb();

    // egg root
    eggRoot = new Egg(plinth, orb).getEggRoot();

    // phone case (metal)
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());

    metal = new Metal(gl, camera, light, shader, mesh, textureMetal).getMetal();

    // phone screen
    phoneScreen = new PhoneScreen(gl, camera, light, shader, mesh, texturePhoneScreen).getPhoneScreen();

    // phone root
    phoneRoot = new Phone(plinth, metal, phoneScreen).getPhoneRoot();

    // spot light root
    spotlight = new Spotlight(metal, lightBulb);
    spotlightRoot = spotlight.getSpotlightRoot();

    // robot root 
    robot = new Robot(venOrb);
    robotRoot = robot.getRobotRoot();

  }

  private void render(GL3 gl) {
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // change spotlight's position and direction on each frame
    light.setSpotlightPos(spotlight.getLightPos().toVec3());
    light.setSpotlightDirection(spotlight.getShadeDirection());

    light.render(gl);
    floor.render(gl);
    for (Model wallPiece : windowWall){
      wallPiece.render(gl);
    }
    for (Model wallPiece : doorWall){
      wallPiece.render(gl);
    }
    sceneRender(gl);
    eggRoot.draw(gl);
    phoneRoot.draw(gl);
    spotlight.updateShadeRotation();
    spotlightRoot.draw(gl);
    robot.doRobotTargetPose();
    robotRoot.draw(gl);
  }

  // The scene's texture changes by time 
  private void sceneRender(GL3 gl){
    double elapsedTime = getSeconds()-startTime;
    if (Math.sin(Math.toRadians(elapsedTime*25)) >= 0){
      sceneDay.render(gl);
    } else {
      sceneNight.render(gl);
    }
  }

  // ***************************************************
  /* TIME
   */ 
  
  // code from exercise sheets
  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

}