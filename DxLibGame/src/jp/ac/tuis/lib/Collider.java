package jp.ac.tuis.lib;

import java.util.ArrayList;
import java.util.TreeMap;

/*
 * Masanori Ohshiro
 * できるだけ学生が学習済みの文法内容で理解できるように書かれている(やむを得ない一部を除く)。
 */

// 2次元ベクトルを表すクラスVector2D
class Vector2D {
	double x, y;
	Vector2D( double x, double y ) {
		this.x = x; this.y = y;
	}
}

// 2つの double型の値を交換するための便宜的なクラス
class MyDouble {
	double d;
	MyDouble( double d ) {
		this.d = d;
	}
}

// 衝突判定に用いる２次元図形(コライダー)のスーパークラス Collider
public abstract class Collider implements  Comparable<Collider> {

	public int compareTo( Collider c ) {
		return hashCode() - c.hashCode();
	}

	// コライダーオブジェクトをメンバーとして所有する Collidable型オブジェクトへの参照
	Collidable includer;
	// includer のアクセッサ
	public Collidable getIncluder() { return includer; }
	public void setIncluder( Collidable includer ) { this.includer = includer; }

	// 衝突判定可能な状態かどうかを保持しておくフィールド。衝突判定可能な状態のときは true, そうでないときは false にしておく。
	boolean active = false;
	// active のアクセッサ。
	public boolean isActive( ) {
		return active;
	}
	public void setActive( boolean active ) {
		if( this.active == false && active == true  ) { add( includer ); }
		if( this.active == true  && active == false ) remove( index );
		this.active = active;
	}

	// 現在衝突状態にある場合は true，そうでなければ false の値を取るフィールド。
	protected boolean collided = false;
	public boolean getCollided( ) {
		return collided;
	}
	protected void setCollided( boolean collided  ) {
		this.collided = collided;
	}

	// 衝突判定グループを表す文字。同じ文字をもつコライダーオブジェクトは交差しても衝突したとはみなされない。'*'はすべてのグループと一致するとみなされる。
	protected char colliderGroup = '*';
	// collidreGroup のアクセッサ
	public char getColliderGroup() { return colliderGroup; }
	public void setColliderGroup( char colliderGroup ) { this.colliderGroup = colliderGroup; }

	// コライダー図形が囲むキャラクターの種類を表す文字。'*'はすべての種類と一致する。
	protected char type = '*';
	public char getType() { return type; }
	public void setType( char type ) { this.type = type; }

	// 連続衝突抑制用の設定マップ。衝突相手のキャラクター種(文字)と連続衝突しても衝突を無視する時間(ミリ秒)を組で登録できる。
	protected TreeMap<Character, Integer> ignore = new TreeMap<Character, Integer>();
	public  TreeMap<Character, Integer> getIgnore() { return  ignore; }

	// 連続衝突抑制用の記録マップ。交差相手と連続交差している経過時間(ミリ秒)を組で登録できる。
	protected TreeMap<Collider, Integer> rec = new TreeMap<Collider, Integer>();
	public  TreeMap<Collider, Integer> getRec() { return  rec; }


	// コンストラクタ。第1実引数は this を指定すること。第2実引数は衝突判定グループを表す文字。
	Collider( Collidable includer, char colliderGroup ) {
		setIncluder( includer );
		setColliderGroup( colliderGroup );
		setType( '*' );
	}

	// コンストラクタ。第1実引数は this を指定すること。第2実引数は衝突判定グループを表す文字。第3実引数はキャラクターの種類を表す文字。
	Collider( Collidable includer, char colliderGroup, char type ) {
		this( includer,  colliderGroup );
		setType( type  );
	}

	// コライダーの基準点(例1：長方形の場合は左上の頂点，例2：円の場合は中心点)のX座標とY座標の抽象アクセッサ
	public abstract int getX();
	public abstract void setX( int x );
	public abstract int getY();
	public abstract void setY( int y );

	//引数で受け取るもう一つのCollider型オブジェクトと衝突している場合は true を返す抽象メソッド isCollidedWith()。
	abstract public boolean isCollidedWith( Collider c );

	// 衝突判定配列(衝突判定を行う対象の Collidable型オブジェクトを登録しておく配列)と関連する定数・変数・メソッド
	protected static final int maxOfcollidables = 100; // 最大登録数
	protected static int n = 0; // 現在の登録数
	protected static Collidable [ ] collidables = new Collidable [ maxOfcollidables ]; // 衝突判定配列

	// 衝突判定配列に登録されたときの添え字を記録しておくフィールド。
	int index = -1;
	public void setEntryNumber( int index ) { this.index = index; } // 衝突判定配列に登録されたときの添え字をint型フィールドにセットするメソッド。
	public int getEntryNumber() { return index; } // setEntryNumber()メソッドでセットされた値を返すメソッド。

	// 配列に Collidable型オブジェクト c を登録するメソッド
	static void add( Collidable c ) {
		if( n < maxOfcollidables ) {
			for( int i = 0; i < maxOfcollidables; i++ ) {
				if( collidables[ i ] == null ) {
					 collidables[ i ] = c;
					 c.getCollider().setEntryNumber( i ); // 登録された Collidable型オブジェクト c にも何番目の要素に登録されたか記録しておく。
					 n++;
					 break;
				}
			}
		}
	}

	// 配列から Collidable型オブジェクト c を削除するメソッド
	static void remove( Collidable c ) {
		for( int i = 0; i < maxOfcollidables; i++ ) {
			if( collidables[ i ] == c ) {
				if( c != null ) {
					collidables[ i ] = null;
					c.getCollider().setCollided( false );
					c.getCollider().setEntryNumber( -1 );
					n--;
				}
				 break;
			}
		}
	}

	// 配列から t番目に登録されているCollidable型オブジェクトを削除するメソッド
	static void remove( int t ) {
		if( (0 <= t) && (t < maxOfcollidables) ) {
			Collidable c = collidables[ t ];
			if( c != null ) {
				collidables[ t ] = null;
				c.getCollider().setCollided( false );
				c.getCollider().setEntryNumber( -1 );
				n--;
			}
		}
	}

	protected static TreeMap<Collider, Integer> crossedWithMap = new TreeMap<Collider, Integer>(); // 交差した相手と交差時刻を一時的に登録するマップ
	protected static Collidable[] collidedWith = new Collidable[ maxOfcollidables ]; // 衝突した相手を一時的に登録する配列
	protected static int numOfCrossedWith  = 0; // 配列crossedWithに登録されている Collidable型オブジェクトの数
	protected static int numOfCollidedWith = 0; // 配列collidedWithに登録されている Collidable型オブジェクトの数
	protected static ArrayList<Collider> dusts = new ArrayList<Collider>();

	// 衝突判定を行うメソッド
	public static void checkCollision() {
		int collisionTime =  DxJava.dx.GetNowCount();
		for( int i = 0; i < maxOfcollidables; i++ ) {

			Collidable theCollidable = collidables[ i ]; // 現在注目している Collidable型オブジェクト

			if( theCollidable != null) {

				Collider theCollider = theCollidable.getCollider();
				 TreeMap<Character, Integer> ignore = theCollider.getIgnore();
				 TreeMap<Collider, Integer> rec = theCollider.getRec();

				 // デフォルトの衝突無視期間を得る。
				Integer wildcardIgnoreTimeInteger = ignore.get( '*' );
				int wildcardIgnoreTime = 0;
				if( wildcardIgnoreTimeInteger != null ) {
					wildcardIgnoreTime = wildcardIgnoreTimeInteger;
				}
				else {
					wildcardIgnoreTime = 0;
				}

				//マップ crossedWithMap と 配列 collidedWith を初期化する。
				crossedWithMap.clear();
				for( int j = 0; j < collidedWith.length; j++ ) collidedWith[i] = null;
				numOfCrossedWith = numOfCollidedWith = 0;

				int checked = 0; // 衝突判定済みの Collidable型オブジェクト数
				boolean collided = false; // 衝突していたら true、そうでなければ falseの値をとる変数

				for( int j = 0; j < maxOfcollidables; j++ ) {
					Collidable target = collidables[ j ]; // 衝突判定の相手

					if(  target != null ) {
						char theGroup = theCollider.getColliderGroup();
						char anothersGroup = target.getCollider().getColliderGroup();
						if(  theCollidable != target && ( theGroup != '*' &&  anothersGroup != '*' ) ) { // 相手が「自分ではない」かつ「自分も相手も'*'グループではない」ときだけ衝突判定を行う
							if( ( theCollider.isActive() && target.getCollider().isActive() ) && theGroup != anothersGroup ) { // 「自分も相手も衝突可能な状態」かつ「異なる衝突判定グループ所属」のときだけ衝突判定を行う
								// theCollider と target との衝突判定を行う。衝突していたら collided に true を代入する。
								collided = theCollider.isCollidedWith( target.getCollider() );
								 // 交差していたら、衝突相手と時刻を collidedWithMapに登録し，衝突相手を配列 collidedWith に登録。
								if( collided ) {
									crossedWithMap.put( target.getCollider(), collisionTime );
									Integer startTime = rec.get( target.getCollider() );
									if( startTime != null ) { // 前回登録されている
										Integer ignoreTimeInteger = ignore.get( target.getCollider().getType() );
										int ignoreTime = wildcardIgnoreTime;
										if( ignoreTimeInteger != null ) ignoreTime = ignoreTimeInteger;
										if( collisionTime <= ( startTime + ignoreTime ) ) {
											collided = false;
										}
										else {
											rec.put ( target.getCollider(), collisionTime );
										}
									}
									else { // 新規衝突
										rec.put ( target.getCollider(), collisionTime );
									}
								}
								if( collided ) {
									collidedWith[ numOfCollidedWith++ ] = target;
								}
							}
							checked++;
						}
					}

					if( (n-1) <= checked ) break; // すでに登録されている自分以外のCollidable型オブジェクトと衝突判定をしていたらループを抜ける
				}

				dusts.clear();

				for (Collider key : rec.keySet()) {
					Integer elementInteger = crossedWithMap.get(key);
					if( elementInteger == null ) dusts.add(key);
			    }

				for(  Collider c : dusts ) {
					rec.remove( c );
				}

				theCollider.setCollided( collided );
				if( collided ) theCollidable.onCollided( collidedWith ); // 衝突していたら、衝突相手の入った配列を実引数に onCollided()メソッドを呼ぶ。
				if( ! theCollider.isActive() ) remove( theCollider.getEntryNumber() ); // 衝突した結果、衝突不可能になったら配列から削除する。
			}
		}
	}

	// 点(x,y)が円cと接するか円cの内部にあるなら true を返し，そうでないなら false を返すユーティリティメソッド
	public static boolean isInnerTheCircle( double x, double y, CircleCollider theCircle ) {
		double dx = theCircle.getX() - x;
		double dy = theCircle.getY() - y;
		double  r = theCircle.getR();
		return (( dx * dx ) + ( dy * dy )) <= (r * r) ;
	}

	// ベクトル(x1, y1)，(x2, y2)の内積を返すユーティリティメソッド
	public static double innerProduct( double x1, double y1, double x2, double y2 ) {
		return (x1 * x2) + (y1 * y2);
	}

	// ベクトル(x1, y1)，(x2, y2)の外積を返すユーティリティメソッド
	public static double outerProduct( double x1, double y1, double x2, double y2 ) {
		return (x1 * y2) - (y1 * x2 );
	}

	// ベクトル(x, y)のノルム(ベクトルの長さ)の二乗を返すユーティリティメソッド
	public static double Norm2( double x, double y ) {
		return innerProduct( x, y, x, y );
	}

	// 点(x1, y1)と点(x2, y2)を端点とする線分と円cが交われば true、そうでなければ false を返すユーティリティメソッド
	public static boolean collideWithTheLineSegment( double x1, double y1, double x2,double y2, CircleCollider c ) {
		double x0 = c.getX();
		double y0 = c.getY();
		double r0 = c.getR();
		Vector2D v1 = new Vector2D( x2 - x1, y2 - y1 );
		Vector2D v2 = new Vector2D( x0 - x1, y0 - y1 );
		double ip = innerProduct( v1.x, v1.y, v2.x, v2.y  );
		double v1n2 = Norm2( v1.x, v1.y );
		double v2n2 = Norm2( v2.x, v2.y );
		double t = ip / v1n2;
		if( 0.0 <= t && t <= 1.0 ) {
			double distance2 = v2n2 - ( ip * t );
			return distance2 < (r0 * r0);
		}
		else {
			return false;
		}
	}

	// 第0～3頂点からなる長方形内に点(x, y)が含まれていれば true を返し、そうでない場合は falseを返すユーティリティメソッド
	public static boolean checkInnerRectangle( double x0, double y0, double x1, double y1, double x2, double y2,double x3, double y3, double x, double y) {
		double v1x = x1 - x0, v1y = y1 - y0;
		double v2x = x  - x0, v2y = y  - y0;
		double ip = innerProduct( v1x, v1y, v2x, v2y );
		double op = outerProduct( v1x, v1y, v2x, v2y );
		double theta1 = Math.atan2( op, ip ) * (180.0/Math.PI);
		double v3x = x3 - x2, v3y = y3 - y2;
		double v4x = x  - x2, v4y = y  - y2;
		ip = innerProduct( v3x, v3y, v4x, v4y );
		op = outerProduct( v3x, v3y, v4x, v4y );
		double theta2 = Math.atan2( op, ip ) * (180.0/Math.PI);

		if( 0.0 <= theta1 && theta1 <= 90.0 && 0.0 <= theta2 && theta2 <= 90.0 ) {
			return true;
		}
		else {
			return false;
		}
	}

	// double型の値を交換するメソッド
	public static void swap( MyDouble d1, MyDouble d2 ) {
		double t = d1.d;
		d1.d = d2.d;
		d2.d = t;
	}

	// コライダー描写色を返すメソッド
	protected int getColliderColor() {
		if( getCollided() ) {
			return DxJava.dx.GetColor( 255, 0, 0 );
		}
		else {
			return DxJava.dx.GetColor( 0, 255, 0 );
		}
	}

	// 自分自身(コライダー図形)を描写する抽象メソッド
	public abstract void draw();

	// 登録されているコライダー図形をすべて描写するメソッド
	public static void drawAll() {
		int drawn = 0;
		for( int i = 0; i < collidables.length; i++ ) {
			Collidable theCollidable = collidables[ i ];
			if( theCollidable != null ) {
				Collider c = theCollidable.getCollider();
				if( c != null ) c.draw();
				drawn++;
			}
			if( drawn == n ) break;
		}
	}
}
