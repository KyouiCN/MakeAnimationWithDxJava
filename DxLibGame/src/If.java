import jp.ac.tuis.lib.DxJava; // Java用DXライブラリクラス jp.ac.tuis.lib.DxJava をインポート

public class If {

    public static void main(String[] args) {

        int x = 640/2, y = 480/2; // 最初の描画位置を画面の真ん中にセット(画面サイズは640x480)
        int keyInput;    // キー入力の結果を保存しておくための変数

        DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

        while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)

            DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する

            keyInput = DxJava.GetJoypadInputState( DxJava.DX_INPUT_KEY_PAD1 ); // キーボードからの入力を変数keyInputへ保存
            if( keyInput == DxJava.PAD_INPUT_RIGHT ) x = x + 5;
            if( keyInput == DxJava.PAD_INPUT_LEFT  ) x = x - 5;
            if( keyInput == DxJava.PAD_INPUT_DOWN  ) y = y + 5;
            if( keyInput == DxJava.PAD_INPUT_UP    ) y = y - 5;

            DxJava.DrawCircle( x, y , 25, DxJava.GetColor( 0, 255, 0 ), DxJava.TRUE ); // 座標x,yに半径25の円を描画する

            DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する


            if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
        }

    }

}
