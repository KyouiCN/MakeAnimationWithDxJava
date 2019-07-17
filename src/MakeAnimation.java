import jp.ac.tuis.lib.DxJava;

public class MakeAnimation {

	public static void main(String[] args) {

        int xPlayer = 0, yPlayer = 0;
        int x = 0, y = 0;
        int keyInput;
        int ichirou = 0;
        ichirou = DxJava.LoadGraph( "ichirou.png" ); 

        DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する
        
        dx.SetFontSize( 30 ); // 字体(フォント)のサイズを30に設定
	    for(int i=0; i<=100; i++) {
	        dx.ClearDrawScreen();
	        dx.DrawFormatString( 0, 0, dx.GetColor( 255, 255, 255 ),"%s","プログラミング応用a長期課題");
	        dx.DrawFormatString( 0, 30, dx.GetColor( 255, 255, 255 ),"%s","loading..."+ i +"%");
	        dx.WaitTimer(10);
	    }

        while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)
        	keyInput = DxJava.GetJoypadInputState( DxJava.DX_INPUT_KEY_PAD1 ); // キーボードからの入力を変数keyInputへ保存
        	if( keyInput == DxJava.PAD_INPUT_RIGHT ) {
                if( (xPlayer + 5) >= (639-50) ) {
                	xPlayer = (639-50);
                }
                else {
                	xPlayer = xPlayer + 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_LEFT  ) {
                if( (xPlayer - 5) <= 0 ) {
                	xPlayer = 0;
                }
                else {
                	xPlayer = xPlayer - 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_DOWN  ) {
                if( (yPlayer + 5) >= (479 - 59) ) {
                	yPlayer = (479 - 59);
                }
                else {
                	yPlayer = yPlayer + 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_UP    ) {
                if( (yPlayer - 5) <= 0 ) {
                	yPlayer = 0;
                }
                else {
                	yPlayer = yPlayer - 5;
                }
            }
            
            DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する
            DxJava.DrawGraph( xPlayer, yPlayer, ichirou, DxJava.TRUE );
            
            
            
            
            
            

            x = x + 3; y = y + 3; // xとyの値を3づつ増やす

            DxJava.DrawCircle( x, y , 25, DxJava.GetColor( 0, 255, 0 ), DxJava.TRUE ); // 座標x,yに半径25の円を描画する

            DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する


            if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
        }

    }

}
