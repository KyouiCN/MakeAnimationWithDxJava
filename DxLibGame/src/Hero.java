import jp.ac.tuis.lib.DxJava;

public class Hero extends GameCharacter{
	
	public Hero(DxJava dx,int graphicNum,int x,int y) {
		super(dx,graphicNum,x,y);
	}
	
	public void move(int keyInput) {
		if( keyInput == DxJava.PAD_INPUT_RIGHT ) {
            if( (x + 5) >= (639-31) ) {
                x = (639-31);
            }
            else {
                x = x + 5;
            }
        }
        if( keyInput == DxJava.PAD_INPUT_LEFT  ) {
            if( (x - 5) <= 0 ) {
                x = 0;
            }
            else {
                x = x - 5;
            }
        }
        if( keyInput == DxJava.PAD_INPUT_DOWN  ) {
            if( (y + 5) >= (479 - 31) ) {
                y = (479 - 31);
            }
            else {
                y = y + 5;
            }
        }
        if( keyInput == DxJava.PAD_INPUT_UP    ) {
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
