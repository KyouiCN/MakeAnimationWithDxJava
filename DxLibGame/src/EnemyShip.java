import jp.ac.tuis.lib.CircleCollider;
import jp.ac.tuis.lib.Collidable;
import jp.ac.tuis.lib.Collider;
import jp.ac.tuis.lib.DxJava;

public class EnemyShip implements Collidable {

	CircleCollider c;       // 円形の衝突判定用図形。この中のx,yがこのオブジェクトの座標。

	int hp;         // ライフ
	int color;      // 円の色。
	int shot_sound; // 効果音の音声データ番号

	EnemyShip( char colliderGroup, int x, int y, int hp, int color, int soundNum ) {
		c = new CircleCollider( this, colliderGroup, x, y, hp );
		this.hp = c.getR(); // ライフを半径とする。
		this.color = color; // 	色をセットする。
		shot_sound = soundNum; // 効果音データの番号をセットする
	}

	// getCollider()のオーバライド。自分の衝突判定用図形オブジェクトへの参照を返す。
	public Collider getCollider() { return c; }

	// onCollided()メソッドのオーバライド。衝突したときの処理。ここでは引数は無視する。
	public void onCollided( Collidable [] collidedWith ) {
		hp = hp - 1; // ライフを減らす。
		c.setR( hp );
		if( hp <= 0 ) { // ライフが0以下になってしまった場合
			hp = 0; // 念のため」ライフを改めて0に設定する。
			c.setActive(false); // 衝突判定不可能な状態に設定。
			DxJava.DrawCircle( c.getX()+(32/2), c.getY()+(32/2), 40, DxJava.GetColor( 255, 0, 0 ), DxJava.TRUE );
        	DxJava.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); /* 弾の発射音性を再生 */
		}
	}


	// 移動するメソッド move()
	public void move() {
		c.setX( ( (c.getX() + 7) % 640 ) ); // 画面の上から下に繰り返し移動させる。
		c.setY( ( (c.getY() + 1) % 480 ) ); // 画面の上から下に繰り返し移動させる。
	}

	// 	自分を表示するメソッド
	public void draw() {
		DxJava.DrawCircle( c.getX(), c.getY(), c.getR(), color, DxJava.TRUE );
	}

}
