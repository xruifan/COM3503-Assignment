package gmaths;

/**
* I declare that this code is my own work
*
* @author   Xuan-Rui Fan, lhsu1@sheffiel.ac.uk
* 
*/

public final class Vec3Transform{

    public static Vec3 rotateAroundX(Vec3 vec3, float angle) {   // angle in degrees
        Vec3 vec = vec3;
        angle = (float)(angle*Math.PI/180.0);
        vec.x = vec.x;
        vec.y = -(vec.y*(float)Math.cos(angle) - vec.z*(float)Math.sin(angle));
        vec.z = -(vec.y*(float)Math.sin(angle) + vec.z*(float)Math.cos(angle));
        return vec;
    }

}

