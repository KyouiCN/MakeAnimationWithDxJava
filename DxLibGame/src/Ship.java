import jp.ac.tuis.lib.Collidable;
import jp.ac.tuis.lib.Collider;
import jp.ac.tuis.lib.DxJava;
import jp.ac.tuis.lib.RectangleCollider;

public class Ship implements Collidable {

	RectangleCollider c;   // 矩形の衝突判定用図形。この中のx,yがこのオブジェクトの座標。

	int hp;         // ライフ
	int graphic;    // グラフィック番号
	int shot_sound; // 効果音の音声データ番号

	public int getHp() { return hp; } // hpのゲッタ

	Ship( char colliderGroup, int x, int y, int width, int height, int hp, int graphicNum, int soundNum ) {
		c = new RectangleCollider( this, colliderGroup, x, y, width, height );
		this.hp = hp;
		graphic = graphicNum;
		shot_sound = soundNum; // 効果音データの読み込み
	}

	// getCollider()のオーバライド。自分の衝突判定用図形オブジェクトへの参照を返す。
	public Collider getCollider() { return c; }


	// onCollided()メソッドのオーバライド。衝突したときの処理。ここでは引数は無視する。
	public void onCollided( Collidable [] collidedWith ) {
		hp--; // ライフを減らす。
		if( hp <= 0 ) { // ライフが0以下になってしまった場合
			hp = 0; // 念のため」ライフを改めて0に設定する。
			c.setActive(false); // 衝突判定不可能な状態に設定。
			DxJava.DrawCircle( c.getX()+(32/2), c.getY()+(32/2), 40, DxJava.GetColor( 255, 0, 0 ), DxJava.TRUE );
        	DxJava.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); /* 弾の発射音性を再生 */
		}
	}

	// 移動するメソッド move()
	public void move() {
    	int x = c.getX(), y = c.getY();

    	int keyInput = DxJava.GetJoypadInputState( DxJava.DX_INPUT_KEY_PAD1 ); // キーボードからの入力を変数keyInputへ保存
        if( keyInput == DxJava.PAD_INPUT_RIGHT ) { /* 右矢印キーを押し続けても画面の右側に自機が出ないようにする */
            if( (x + 5) >= (639-31) ) {
                x = (639-31);
            }
            else {
                x = x + 5;
            }
        }
        if( keyInput == DxJava.PAD_INPUT_LEFT  ) { /* 左矢印キーを押し続けても画面の左側に自機が出ないようにする */
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

        // 自機に新しい座標をセット。
        c.setX( x );c.setY( y );

	}

	// 	自分を表示するメソッド
	public void draw() {
		DxJava.DrawGraph( c.getX(), c.getY(), graphic, DxJava.TRUE );
	}

}
