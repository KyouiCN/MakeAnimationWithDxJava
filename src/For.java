import jp.ac.tuis.lib.DxJava; // Java用DXライブラリクラス jp.ac.tuis.lib.DxJava をインポート

public class For {

    public static void main(String[] args) {

        int x = 640/2 - 32/2, y = 480/2 - 32/2; // 最初の描画位置を画面の真ん中にセット(画面サイズは640x480)
        int keyInput;    // キー入力の結果を保存しておくための変数

        int bg     = 0; // 背景グラフィックのグラフィック番号
        int myShip = 0; // 自機グラフィックのグラフィック番号
        int i;          // カウントダウンを表示するfor文用の変数

        DxJava.SetFontSize( 30 ); // 字体(フォント)のサイズを30に設定
        for( i = 3; i >= 0; i-- ) {
            DxJava.ClearDrawScreen(); // 描画先画面をきれいに消去する
            // 数値iを画面のだいたい中央あたり(640/2, 480/2)に青色で表示
            DxJava.DrawFormatString( 640/2, 480/2, DxJava.GetColor( 50, 50, 255 ), "%d", i );
            DxJava.WaitTimer( 700 ); // 700ミリ秒動作をとめる
        }

        bg     = DxJava.LoadGraph( "bg.jpg" );       // 背景グラフィック(640pixels×600pixels)の読み込み
        myShip = DxJava.LoadGraph( "MyShip32.bmp" ); // 自機グラフィック(32pixels×32pixels)の読み込み

        DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

        while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)

            DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する

            keyInput = DxJava.GetJoypadInputState( DxJava.DX_INPUT_KEY_PAD1 ); // キーボードからの入力を変数keyInputへ保存
            if( keyInput == DxJava.PAD_INPUT_RIGHT ) { // 右矢印キーを押し続けても画面の右側に自機が出ないようにする
                if( (x + 5) >= (639-31) ) {
                    x = (639-31);
                }
                else {
                    x = x + 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_LEFT  ) { // 左矢印キーを押し続けても画面の左側に自機が出ないようにする
                if( (x - 5) <= 0 ) {
                    x = 0;
                }
                else {
                    x = x - 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_DOWN  ) { // 下矢印キーを押し続けても画面の下側に自機が出ないようにする
                if( (y + 5) >= (479 - 31) ) {
                    y = (479 - 31);
                }
                else {
                    y = y + 5;
                }
            }
            if( keyInput == DxJava.PAD_INPUT_UP    ) { // 上矢印キーを押し続けても画面の上側に自機が出ないようにする
                if( (y - 5) <= 0 ) {
                    y = 0;
                }
                else {
                    y = y - 5;
                }
            }

            DxJava.DrawGraph( 0, 0,     bg, DxJava.TRUE ); // 座標0,0に背景のグラフィックを描画する
            DxJava.DrawGraph( x, y, myShip, DxJava.TRUE ); // 座標x,yに自機のグラフィックを描画する

            DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する


            if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
        }

    }

}

