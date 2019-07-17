import jp.ac.tuis.lib.Collider;
import jp.ac.tuis.lib.DxJava;

public class For2 {

	public static void main(String[] args) {

		int graphicNum = DxJava.LoadGraph( "MyShip32.bmp" );
		int myShotGraphicNum = DxJava.LoadGraph( "Chars16_orange.bmp" );


		int bgm_sound  = DxJava.LoadSoundMem( "ForGame008.mp3" ); // BGM音声データの読み込み
		int shot_sound = DxJava.LoadSoundMem( "shot.mp3" ); // 効果音データの読み込み


	    // 自機と敵機を衝突判定可能な状態で生成する。
	    Ship      myShip = new Ship( 'f', 640/2 - 32/2, 480/2 - 32/2, 32, 32, 20, graphicNum, shot_sound );
	    EnemyShip eShip  = new EnemyShip( 'e', 320, 40, 30, DxJava.GetColor( 255, 255, 0 ), shot_sound );

	    // 自機の弾の用意
	    MyShot [] myShots = new MyShot[ 10 ];
	    for( int i = 0; i < myShots.length; i++ )  {
	    	myShots[i] = new MyShot( 'f', 0,  0, 16, 16, myShotGraphicNum, shot_sound );
	    	myShots[i].getCollider().setActive( false ); // 初期状態では衝突判定の対象外にしておく
	    }
	    // 自機と敵機を衝突可能な状態にする。
	    myShip.getCollider().setActive( true );
	    eShip.getCollider().setActive( true );


		int bg     = 0;  // 背景グラフィックのグラフィック番号
		int i;           // カウントダウンを表示するfor文用の変数

	    DxJava.SetFontSize( 30 ); // 字体(フォント)のサイズを30に設定
	    for( i = 3; i >= 0; i-- ) {
	        DxJava.ClearDrawScreen(); // 描画先画面をきれいに消去する
	        // 数値iを画面のだいたい中央あたり(640/2, 480/2)に青色で表示
	        DxJava.DrawFormatString( 640/2, 480/2, DxJava.GetColor( 50, 50, 255 ), "%d", i );
	        DxJava.WaitTimer( 700 ); // 700ミリ秒動作をとめる
	    }

		bg = DxJava.LoadGraph( "bg.jpg" ); // 背景グラフィック(640pixels×600pixels)の読み込み

		DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

		DxJava.PlaySoundMem( bgm_sound, DxJava.DX_PLAYTYPE_LOOP, DxJava.TRUE ); // BGMを繰り返し再生開始

	    while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)

	    	DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する

	        DxJava.DrawGraph( 0, 0, bg, DxJava.TRUE ); // 座標0,0に背景のグラフィックを描画する

	        // 画面の上へ出て行ってしまった弾を再利用する。
        	for( i = 0; i < myShots.length; i++  ) {
    			if( (myShots[i].getCollider().getY() + 16) < 0) {
    				myShots[i].hp = 0;
    				myShots[i].getCollider().setActive( false ); // 衝突判定の対象外に戻しておく
    			}
        	}

	        if( DxJava.GetInputChar( DxJava.TRUE ) == ' ' ) { /* GetInputChar(TRUE)はDXライブラリの機能でキーボードで押した文字を入力する */
	        	for( i = 0; i < myShots.length; i++  ) {
	        		if( myShots[i].hp == 0 ) {
	        			myShots[i].hp = 1;
	        			myShots[i].getCollider().setX(  myShip.getCollider().getX() + (32/2 - 16/2) );
	        			myShots[i].getCollider().setY(  myShip.getCollider().getY() );
	        			myShots[i].getCollider().setActive( true );
	        			DxJava.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 弾の発射音性を再生
	        			break;
	        		}
	        	}
	        }

	        myShip.draw(); // 自機を描写する。
	        eShip.draw();  // 敵機を描写する。

        	for( i = 0; i < myShots.length; i++  ) {
        		if( myShots[i].hp != 0 ) {
        			 myShots[i].draw();
        		}
        	}

	        Collider.drawAll(); // コライダー図形を描写する(デバッグ用)

	        Collider.checkCollision(); // 自動衝突判定を行う

	        myShip.move(); // 自機を移動させる
	        eShip.move();  // 敵機を移動させる

        	for( i = 0; i < myShots.length; i++  ) {
        		if( myShots[i].hp != 0 ) {
        			 myShots[i].move();
        		}
        	}
	        DxJava.DrawFormatString( 0, 0, DxJava.GetColor( 50, 50, 255 ), "HP:%d", myShip.getHp() );

	        DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する

	        if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
	    }

	}

}

