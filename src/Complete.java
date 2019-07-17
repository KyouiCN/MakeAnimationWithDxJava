import jp.ac.tuis.lib.DxJava;

//完成版
public class Complete {

    public static void main(String[] args) {

        int x = 640/2 - 32/2, y = 480-100 - 32/2; // 最初の描画位置を画面の真ん中にセット(画面サイズは640x480)
        int keyInput;    // キー入力の結果を保存しておくための変数

        int bg     = 0; // 背景グラフィックのグラフィック番号
        int myShip = 0; // 自機グラフィックのグラフィック番号
        int myLife = 10; // 自機のライフ(0になったら自機は消滅してプレイヤーの負け)
        int i;          // カウントダウンを表示するfor文用の変数

        int shots_Life[ ] = { 0,0,0,0,0,0,0,0,0,0 }; // 自機の弾が未発射(0)か発射済み(1)かを表す変数10個分の配列
        int shots_x[ ]    = { 0,0,0,0,0,0,0,0,0,0 }; // 自機の弾のx座標を表す変数10個分の配列
        int shots_y[ ]    = { 0,0,0,0,0,0,0,0,0,0 }; // 自機の弾のy座標を表す変数10個分の配列
        int j; // 自機の弾を表している配列をfor文で処理するためのカウンタ変数
        int myShot = 0; // 自機弾グラフィックのグラフィック番号を記憶しておく変数

        int ex = 640/2 - 32/2, ey = 100 - 32/2; // 敵機の最初の描画位置を画面の真ん中上部にセット
        int eShip = 0;  // 敵機グラフィックのグラフィック番号を記憶しておく変数
        int eLife = 30; // 敵機のライフ(0になったら敵機は消滅してプレイヤーの勝ち)

        int e_shots_Life[ ] = { 0,0,0,0,0,0,0,0 }; // 敵機の弾が未発射(0)か発射済み(1)かを表す変数10個分の配列
        int e_shots_x[ ]    = { 0,0,0,0,0,0,0,0 }; // 敵機の弾のx座標を表す変数10個分の配列
        int e_shots_y[ ]    = { 0,0,0,0,0,0,0,0 }; // 敵機の弾のy座標を表す変数10個分の配列
        int eShot    = 0; // 敵機弾グラフィックのグラフィック番号を記憶しておく変数
        int eShot_Go = 0; // 敵機が弾を発射できるかどうか(0のとき発射不可能、1の時発射可能)

        int bg_scroll_y = 0; // 背景グラフィックのスクロール位置

        int bgm_sound    = 0; // BGMの音声データ番号を記憶しておく変数
        int shot_sound   = 0; // 弾の発射音声データ番号を記憶しておく変数
        int eshot_sound = 0; // 敵の弾の発射音性データ番号を記憶しておく変数
        int crash_sound = 0; // 爆発の音声データ番号を記憶しておく変数
        int hit_sound     = 0; // 弾のヒット音声データ番号を記憶しておく変数
        int win_sound    = 0; // 勝利BGMの音声データ番号を記憶しておく変数

        int time = DxJava.GetNowCount(); // Windowsが起動してからの時間をミリ秒の単位で得る
    	int lastShotTime = 0;     // 最後に弾を撃った時間を記録しておく変数

        eShip = DxJava.LoadGraph( "enemy.bmp" ); // 敵機グラフィック(32pixels×32pixels)の読み込み
        eShot = DxJava.LoadGraph( "Chars16_orange.bmp" ); // 敵機弾グラフィック(16pixels×16pixels)の読み込み

        myShot= DxJava.LoadGraph( "Chars16_blue.bmp" ); // 自機弾グラフィック(16pixels×16pixels)の読み込み

        bgm_sound   = DxJava.LoadSoundMem(  "ForGame008.mp3" ); // BGM音声データの読み込み
        shot_sound  = DxJava.LoadSoundMem(        "shot.mp3" ); // 弾の発射音声データの読み込み
        eshot_sound = DxJava.LoadSoundMem(       "eshot.mp3" ); // 敵の弾の発射音声データの読み込み
        crash_sound = DxJava.LoadSoundMem(       "crash.mp3" ); // 爆発の音声データの読み込み
        hit_sound   = DxJava.LoadSoundMem(         "hit.mp3" ); // 弾のヒット音声データの読み込み
        win_sound   = DxJava.LoadSoundMem(         "win.mp3" ); // 勝利BGMの音音声データの読み込み

        DxJava.SetFontSize( 30 ); // 字体(フォント)のサイズを30に設定
        for(i=0; i<=100; i++) {
        	DxJava.ClearDrawScreen();
        	DxJava.DrawFormatString( 0, 0, DxJava.GetColor( 255, 255, 255 ),"%s","プログラミング応用a長期課題");
        	DxJava.DrawFormatString( 0, 30, DxJava.GetColor( 255, 255, 255 ),"%s","loading..."+ i +"%");
        	DxJava.WaitTimer(20);
	    }

        DxJava.PlaySoundMem( bgm_sound, DxJava.DX_PLAYTYPE_LOOP, DxJava.TRUE ); // BGMを繰り返し再生開始

        bg     = DxJava.LoadGraph( "bg1.jpg" );       // 背景グラフィック(640pixels×600pixels)の読み込み
        myShip = DxJava.LoadGraph( "hero.bmp" ); // 自機グラフィック(32pixels×32pixels)の読み込み

        DxJava.SetDrawScreen( DxJava.DX_SCREEN_BACK );  // 描画先を裏画面に設定する

        while( 0 == DxJava.ProcessMessage() ) { // Windowが開いている間ずっと繰り返す(事実上の無限ループ)

            DxJava.ClearDrawScreen( ); // 描画先画面をきれいに消去する

            // 背景スクロール処理
            if( bg_scroll_y  < 0 ) bg_scroll_y = bg_scroll_y + 600;
            if( (bg_scroll_y + 480) > 600 ) DxJava.DrawRectGraph( 0, 600 - bg_scroll_y, 0, 0, 640, bg_scroll_y + 480 - 600, bg, DxJava.TRUE, DxJava.FALSE );
            DxJava.DrawRectGraph( 0, 0, 0, bg_scroll_y, 640, 480, bg, DxJava.TRUE, DxJava.FALSE );
            bg_scroll_y = bg_scroll_y - 2;

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

            // キーボードから入力された文字がスペースで、前の弾を打ってから150ミリ秒以上経っているなら自機が弾を撃つ
            if( DxJava.GetInputChar( DxJava.TRUE ) == ' ' && ((DxJava.GetNowCount() - lastShotTime) >=180) ) { // GetInputChar(TRUE)はDxJava.ライブラリの機能でキーボードで押した文字を入力する
                for( j = 0; j < 10; j++ ) {
                    if( shots_Life[j] == 0 ) { // 未発射の弾を見つけたら...
                        shots_Life[j] = 1;     // 発射済みにして
                        shots_x[j] = x + (32/2) - (16/2); // 弾のx座標を自機の鼻先に設定する
                        shots_y[j] = y;                   // 弾のy座標を自機の鼻先に設定する
                        DxJava.PlaySoundMem( shot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 弾の発射音性を再生
    					lastShotTime = DxJava.GetNowCount();
                        break; // 発射する弾は1個だけでいいので、繰り返しを強制的に終了させる
                    }
                }
            }

            for( j = 0; j < 10; j++ ) { // 自機の弾10個に対して次の処理を行う
                if( shots_y[j]+16 < 0 ) shots_Life[j] = 0; // 画面から飛び出した弾は未発射状態にする
                if( shots_Life[j] != 0 ) {              // もし自機のj番目の弾が発射済みなら...
                    DxJava.DrawGraph( shots_x[j], shots_y[j], myShot, DxJava.TRUE ); // 自機のj番目の弾を画面に描画する
                    shots_y[j] = shots_y[j] - 8;        // 次の描画時のためにj番目の弾の位置を前に進める
                }
            }


            eShot_Go = 1; // とりあえず発射可能状態にしておく
            for( j = 0; j < 8; j++ ) {
                if( e_shots_x[j]    < 0   ) e_shots_Life[j] = 0; // 画面から飛び出した弾は未発射状態にする
                if( e_shots_x[j]+16 > 639 ) e_shots_Life[j] = 0; // 画面から飛び出した弾は未発射状態にする
                if( e_shots_y[j]+16 < 0   ) e_shots_Life[j] = 0; // 画面から飛び出した弾は未発射状態にする
                if( e_shots_y[j]    > 479 ) e_shots_Life[j] = 0; // 画面から飛び出した弾は未発射状態にする
                if( e_shots_Life[j] != 0 ) eShot_Go = 0; // 8発の弾のうち1個でも発射済なら発射不可能とする
            }
            if( eShot_Go == 1 ) { // 敵機の弾が発射可能なら全ての弾を発射済みにして位置をセット
                for( j = 0; j < 8; j++ ) {
                    e_shots_Life[j] = 1;
                    e_shots_x[j] = (ex + 16 - 8); e_shots_y[j] = (ey + 16 - 8);
                }
                DxJava.PlaySoundMem( eshot_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 敵の弾の発射音性を再生
            }

            for( j = 0; j < 8; j++ ) { // 敵機の弾のうち、発射済みの弾を描画する。
                if( e_shots_Life[j] != 0 ) {
                    DxJava.DrawGraph( e_shots_x[j], e_shots_y[j], eShot, DxJava.TRUE ); // 敵機弾のグラフィックを描画する
                }
                // 次の描画時のためにj番目の弾の位置を前に進める
                e_shots_x[j] = (int) (e_shots_x[j] + 6 * Math.cos( j * (0.25 * Math.PI) ));
                e_shots_y[j] = (int) (e_shots_y[j] + 6 * Math.sin( j * (0.25 * Math.PI) ));
            }

            DxJava.DrawGraph( ex, ey, eShip, DxJava.TRUE ); // 座標ex,eyに敵機のグラフィックを描画する
            // 敵機の座標をランダムに自機に近づける。なお、GetRand(n)は、0からnまでのでたらめな値を返す
            ex = ex + (x - ex)/( DxJava.GetRand(200) + 15 );  ey = ey + (y - ey)/( DxJava.GetRand(200) + 15 );


            DxJava.DrawGraph( x, y, myShip, DxJava.TRUE ); // 座標x,yに自機のグラフィックを描画する

            // 衝突判定を行う
            // 衝突判定１．「自機と敵機」
            if( (( x <= ex) && ( ex <= (x + 31))) && (( y <= ey) && ( ey <= (y + 31))) ) {
            	DxJava.PlaySoundMem( crash_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 爆発音を再生
                myLife = 0; // 自機のライフを0に設定
            }

            // 衝突判定２．「自機の弾と敵機」
            for( i = 0; i < 10; i++ ) {
                if( shots_Life[i] != 0 ) { // 発射状態の弾に関してだけ敵機との衝突判定をする
                    if( ( (ex <= shots_x[i]) && (shots_x[i] <= (ex + 31) ) && ( (ey <= shots_y[i]) && (shots_y[i] <= (ey + 31) ) )) ) {
                        shots_Life[i] = 0; // 当たった弾を未発射状態にする
                        DxJava.PlaySoundMem( hit_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 弾のヒット音を再生
                        eLife = eLife - 1; // 敵機のライフを1減らす
                    }
                }
            }

            // 衝突判定３．「敵機の弾と自機」
            for( i = 0; i < 8; i++ ) {
                if( e_shots_Life[i] != 0 ) { // 発射状態の弾に関してだけ自機との衝突判定をする
                    if( ( (x <= e_shots_x[i]) && (e_shots_x[i] <= (x + 31) ) && ( (y <= e_shots_y[i]) && (e_shots_y[i] <= (y + 31) ) )) ) {
                        e_shots_Life[i] = 0; // 当たった弾を未発射状態にする
                        DxJava.PlaySoundMem( hit_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 弾のヒット音を再生
                        myLife = myLife - 1; // 自機のライフを1減らす
                    }
                }
            }

            // ライフと経過時間の表示
            DxJava.DrawFormatString( 0, 0, DxJava.GetColor( 20, 20, 255 ), "%02d", myLife );
            DxJava.DrawFormatString( 640 - DxJava.GetDrawFormatStringWidth( "%02d", eLife ), 0, DxJava.GetColor( 255, 0, 0 ), "%02d", eLife );
            DxJava.DrawFormatString( 640/2 - ( DxJava.GetDrawFormatStringWidth( "%03.2f", (DxJava.GetNowCount() - time) / 1000.0 ) ) / 2,
                         2 , DxJava.GetColor( 255, 255, 0 ), "%03.2f", (DxJava.GetNowCount() - time) / 1000.0 );

            DxJava.ScreenFlip( ); // 裏画面に描画したものを表画面に転写する

            // 勝負が決まったら無限ループから抜け出す
            if( eLife <= 0 || myLife <= 0 ) break;

            if( 1 == DxJava.CheckHitKey(DxJava.KEY_INPUT_ESCAPE) ) break; // エスケープキーが押されたら繰り返し処理から出る
        }


        DxJava.SetDrawScreen( DxJava.DX_SCREEN_FRONT ); // 描画先を表画面にする

        // プレイヤーが勝った場合のエンディングを表示
        if( eLife <= 0 ) {
            DxJava.StopSoundMem( bgm_sound ); // BGM再生を停止
            DxJava.PlaySoundMem( win_sound, DxJava.DX_PLAYTYPE_LOOP, DxJava.TRUE ); // 勝利BGMをループ再生
            DxJava.DrawFormatString( 640/2 - (DxJava.GetDrawFormatStringWidth( "You Win!!" ) / 2 ),
                              480/2 - 20, DxJava.GetColor( 20, 20, 255 ), "You Win!!" );
        }

        // プレイヤーが負けた場合のエンディングを表示
        if( myLife <= 0 ) {
            DxJava.PlaySoundMem( crash_sound, DxJava.DX_PLAYTYPE_BACK, DxJava.TRUE ); // 爆発音を再生
            DxJava.DrawFormatString( 640/2 - (DxJava.GetDrawFormatStringWidth( "You Lose!!" ) / 2 ),
                              480/2 - 20, DxJava.GetColor( 255, 100, 100 ), "You Lose!!" );
        }

        while( DxJava.CheckHitKey( DxJava.KEY_INPUT_ESCAPE ) == 0 ); // エスケープキーが押されるのを待つ
    }

}

