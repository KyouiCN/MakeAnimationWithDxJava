import jp.ac.tuis.lib.DxJava;

public class PlayGame {

	public static void main(String[] args) {
		DxJava dx = new DxJava();		
		int keyInput;
		int bg = dx.LoadGraph( "bg1.jpg" );
		Hero player = new Hero(dx,dx.LoadGraph( "hero.bmp" ),(640/2 - 32/2),(480-100 - 32/2));
		Enemy aEnemy = new Enemy(dx,dx.LoadGraph( "Enemy.bmp" ),(640/2 - 32/2),(100 - 32/2));
		int enemyDirection = 3,moveInterval = 0;
		
		//
		dx.SetFontSize( 30 ); // 字体(フォント)のサイズを30に設定
	    for(int i=0; i<=100; i++) {
	        dx.ClearDrawScreen();
	        dx.DrawFormatString( 0, 0, dx.GetColor( 255, 255, 255 ),"%s","プログラミング応用a長期課題");
	        dx.DrawFormatString( 0, 30, dx.GetColor( 255, 255, 255 ),"%s","loading..."+ i +"%");
	        dx.WaitTimer(10);
	    }
	    
		dx.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

		int bgm_sound = dx.LoadSoundMem(  "ForGame008.mp3" ); // BGM音声データの読み込み
		int shot_sound  = dx.LoadSoundMem( "shot.mp3" ); // 効果音データの読み込み

		dx.PlaySoundMem( bgm_sound, DxJava.DX_PLAYTYPE_LOOP, DxJava.TRUE ); // BGMを繰り返し再生開始

	    while( 0 == dx.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)
	    	
	    	if(moveInterval == 0) {
	    		enemyDirection = (int)(1+Math.random()*(4-1+1));
	    		moveInterval = 10;
	    	}
	    	aEnemy.move(enemyDirection);
	    	moveInterval--;
	    	
	    	keyInput = dx.GetJoypadInputState( DxJava.DX_INPUT_KEY_PAD1 ); // キーボードからの入力を変数keyInputへ保存	        
	    	player.move(keyInput);
	    	
	    	dx.ClearDrawScreen( );
	        dx.DrawGraph( 0, 0,     bg, DxJava.TRUE ); // 座標0,0に背景のグラフィックを描画する

	        if( dx.GetInputChar( DxJava.TRUE ) == ' ' ) { /* GetInputChar(TRUE)はDXライブラリの機能でキーボードで押した文字を入力する */
	        	dx.DrawCircle( player.x+(32/2), player.y+(32/2), 40, dx.GetColor( 255, 0, 0 ), DxJava.TRUE );
	        	dx.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); /* 弾の発射音性を再生 */
	        }

	        
	        player.draw();
	        aEnemy.draw();
	        dx.ScreenFlip( );
	        
	        if( 1 == dx.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
	    }

	}

}