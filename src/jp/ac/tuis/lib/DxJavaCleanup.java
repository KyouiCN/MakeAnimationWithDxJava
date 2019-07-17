package jp.ac.tuis.lib;

/*
DxJava では、 ゲーム用ウィンドウが強制的に閉じられた時、プログラムは自動的に終了します。
 その際の処理の流れは、以下のようになっています。
    (1) DxJavaクラスの static  void setCleanup( DxJavaCleanup  cleanupwork ) メソッドによって設定された DxJavaCleanup型オブジェクトの cleanup()メソッドを呼び出す。
    (2) DxLib_End() を呼び出す。
    (3) System.exit( 0 );を呼び出す。
 ですので、ウィンドウが強制的に閉じられた時に実行したい後始末処理がある場合は、以下のようにこのクラスのサブクラスを定義し、cleanup()メソッドをオーバライドしておいて、
 そのサブクラスのオブジェクトを setCleanup()メソッドに渡してください。

例：
	class MyDxJavaCleanup extends DxJavaCleanup {
		public void cleanup() {
			// 必要な後処理をここに書く
		}
	}

としておき，
	DxJava.setCleanup( new  MyDxJavaCleanup() );
とする。

 */
public class DxJavaCleanup {
	public void cleanup() {

	}
}
