package jp.ac.tuis.lib;

public interface Collidable {

	Collider getCollider(); // 衝突判定に使用する図形を返すメソッド

	// 衝突したときに呼び出されるメソッド。実引数は衝突した相手側のCollidable型オブジェクトを登録した配列。
	// 配列collidedWithには、先頭から衝突相手のCollidable型オブジェクトが登録されているので、
	// for(int i = 0; collidedWith[i] != null; i++) { i番目の衝突相手に関する処理 } として衝突相手に関する
	// 処理を行えばよい。
	void onCollided( Collidable [] collidedWith );

}
