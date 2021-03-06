package tracer;

import java.io.IOException;

/**
 * 3D flat, unbounded, traceable thingy, represented by normal vector and distance from the origin
 */
public class Plane extends Traceable {

	Vec3 normal;
	float offset;
	
	public Plane( Vec3 n, float o ) {
		normal = new Vec3(n);
		normal.normalize();
		offset = o;
		material = new Material();
	}
	public Plane() {
		normal = new Vec3(0,1,0);
		offset = 0.0f;
		material = new Material();
	}
	
	public void parse( Parser p ) throws IOException {
		p.parseKeyword( "{" );
		while( !p.tryKeyword("}") && !p.endOfFile() ) {
			
			if( p.tryKeyword("normal") ) {
				normal.parse( p );
			} else if( p.tryKeyword("offset") ) {
				offset = p.parseFloat();
			} else if( p.tryKeyword("material") ) {
				material.parse( p );
			} else {
				System.out.println( p.tokenWasUnexpected() );	
			}
			
		}
	}
	
	public IntersectionInfo intersect( Ray r ) {
        if(hit(r)){
            return new IntersectionInfo(new Vec3(), new Vec3(), 0, this);
        } else {
            return new IntersectionInfo(false);
        }
	}
	
	public boolean hit( Ray r ) {
	    return -(this.offset - this.normal.dot(r.origin)) / (this.normal.dot(r.direction)) > 0;
	}
	
}