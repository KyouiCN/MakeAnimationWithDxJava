import jp.ac.tuis.lib.DxJava;

public class Enemy extends GameCharacter{
	
	public Enemy(DxJava dx,int graphicNum,int x,int y) {
		super(dx,graphicNum,x,y);
	}
	
	public void move(int direction) {
		if(direction == 1) {
            if( (x + 5) >= (639-31) ) {
                x = (639-31);
            }
            else {
                x = x + 5;
            }
        }
        if(direction == 2) {
            if( (x - 5) <= 0 ) {
                x = 0;
            }
            else {
                x = x - 5;
            }
        }
        if(direction == 3) {
            if( (y + 5) >= (479 - 31) ) {
                y = (479 - 31);
            }
            else {
                y = y + 5;
            }
        }
        if(direction == 4) {
            if( (y - 5) <= 0 ) {
                y = 0;
            }
            else {
                y = y - 5;
            }
        }
	}
	
	public void draw() {		
		dx.DrawGraph( x, y, graphicNum, DxJava.TRUE );
	}
}