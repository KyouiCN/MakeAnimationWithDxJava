import jp.ac.tuis.lib.Collidable;
import jp.ac.tuis.lib.Collider;
import jp.ac.tuis.lib.DxJava;
import jp.ac.tuis.lib.RectangleCollider;

public class MyShot  implements Collidable {

	RectangleCollider c;   // 矩形の衝突判定用図形。この中のx,yがこのオブジェクトの座標。

	int hp = 0;
	int graphic;    // グラフィック番号
	int shot_sound; // 効果音の音声データ番号

	MyShot( char colliderGroup, int x, int y, int width, int height, int graphicNum, int soundNum ) {
		c = new RectangleCollider( this, colliderGroup, x, y, width, height );
		graphic  = graphicNum;    // グラフィック番号
		shot_sound = soundNum;  // 効果音データの番号をセットする
	}

	// getCollider()のオーバライド。自分の衝突判定用図形オブジェクトへの参照を返す。
	public Collider getCollider() { return c; }

	// onCollided()メソッドのオーバライド。衝突したときの処理。ここでは引数は無視する。
	public void onCollided( Collidable [] collidedWith ) {
		hp--;
		if( hp <= 0 ) {
			c.setActive(false); // 衝突判定不可能な状態に設定。
	    	DxJava.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); /* 弾の爆発音を再生 */
		}
	}


	// 移動するメソッド move()
	public void move() {
		c.setY( c.getY() - 6  ); // 画面の上方向へ移動する。
	}

	// 	自分を表示するメソッド
	public void draw() {
		DxJava.DrawGraph( c.getX(), c.getY(), graphic, DxJava.TRUE );
	}

}
