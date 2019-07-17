import jp.ac.tuis.lib.DxJava; // Java用DXライブラリクラス jp.ac.tuis.lib.DxJava をインポート

public class Main {

    public static void main(String[] args) {

        int x = 0, y = 0; // 変数の宣言

        DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

        while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)

            DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する

            x = x + 3; y = y + 3; // xとyの値を3づつ増やす

            DxJava.DrawCircle( x, y , 25, DxJava.GetColor( 0, 255, 0 ), DxJava.TRUE ); // 座標x,yに半径25の円を描画する

            DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する


            if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
        }

    }

}
