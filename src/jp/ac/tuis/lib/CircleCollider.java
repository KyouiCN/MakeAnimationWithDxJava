package jp.ac.tuis.lib;

public class CircleCollider extends Collider {

	int x, y, r; // 円の基準点である円の中心点のX座標，Y座標，および半径


	// オーバライドされた基準点の座標に関するアクセッサ
	public int getX() { return x; }
	public void setX( int x ) { this.x = x; }
	public int getY() { return y; }
	public void setY( int y ) { this.y = y; }
	// 半径のアクセッサ
	public int getR() { return r; }
	public void setR( int r ) { if( r >= 0 ) this.r = r; }

	// コンストラクタ
	public CircleCollider( Collidable includer, char colliderGroup, int x, int y, int r ) {
		super( includer, colliderGroup );
		setX( x ); setY( y ); setR( r );
	}

	// 衝突判定
	public boolean isCollidedWith( Collider c ) {
		if( c instanceof CircleCollider ) {
			return isCollidedWith( (CircleCollider) c );
		}
		else if( c instanceof RectangleCollider ) {
			return isCollidedWith( (RectangleCollider) c );
		}
		else return false;
	}


	// 円どうしの衝突判定
	public boolean isCollidedWith( CircleCollider c ) {

		double dx = c.getX() - x;
		double dy = c.getY() - y;
		double r2 = c.getR() + r;

		return ((dx * dx) + (dy * dy)) <= r2;
	}

	// 円(自分)と長方形の衝突判定
	public boolean isCollidedWith( RectangleCollider c ) {

		double leftTopX = c.getX();
		double leftTopY = c.getY();
		double rightBottomX = c.getX() + c.getWidth();
		double rightBottomY = c.getY() + c.getHeight();

		// 円(自分)の中に長方形cの各頂点が含まれているなら衝突している。
		if( isInnerTheCircle( leftTopX,     leftTopY,     this) ) return true; // 長方形の左上の頂点について
		if( isInnerTheCircle( rightBottomX, leftTopY,     this) ) return true; // 長方形の右上の頂点について
		if( isInnerTheCircle( leftTopX,     rightBottomY, this) ) return true; // 長方形の左下の頂点について
		if( isInnerTheCircle( rightBottomX, rightBottomY, this) ) return true; // 長方形の右下の頂点について

		// 円(自分)が長方形の4辺のいずれかと交差しているなら衝突している。
		if( collideWithTheLineSegment( leftTopX, leftTopY, rightBottomX, leftTopY, this ) ) return true;
		if( collideWithTheLineSegment( rightBottomX, leftTopY, rightBottomX, rightBottomY, this ) ) return true;
		if( collideWithTheLineSegment( rightBottomX, rightBottomY, leftTopX, rightBottomY, this ) ) return true;
		if( collideWithTheLineSegment( leftTopX, rightBottomY, leftTopX, leftTopY, this ) ) return true;

		// 円(自分)が長方形cの中にいる(円の中心-左上頂点-右上頂点，円の中心-右下頂点-左下頂点の角度がいずれも0度以上かつ90度以下)なら true、そうでないなら false を返す。
		double x0 = leftTopX,     y0 = leftTopY;
		double x1 = rightBottomX, y1 = leftTopY;
		double x2 = rightBottomX, y2 = rightBottomY;
		double x3 = leftTopX,     y3 = rightBottomY;
		return checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, x, y );
	}

	public void draw() {
		if( DxJava.dx != null  ) DxJava.dx.DrawCircle( x, y, r, getColliderColor(), DxJava.FALSE );
	}

}
