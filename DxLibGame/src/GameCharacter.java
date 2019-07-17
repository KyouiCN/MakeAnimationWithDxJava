import jp.ac.tuis.lib.DxJava;

public abstract class GameCharacter {
	DxJava dx;
	int graphicNum;
	int x,y;
	
	public GameCharacter(DxJava dx,int graphicNum,int x,int y) {
		this.dx = dx;
		this.graphicNum = graphicNum;
		this.x = x;
		this.y = y;
	}
	
	public abstract void move(int keyInput);
	public abstract void draw();
}
