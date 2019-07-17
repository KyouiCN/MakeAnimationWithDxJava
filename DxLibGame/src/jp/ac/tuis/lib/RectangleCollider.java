package jp.ac.tuis.lib;

public class RectangleCollider extends Collider { // 衝突判定に使う(各辺が画面の枠と水平または垂直の)長方形

	int x, y, width, height; // 長方形の基準点である長方形の左上頂点のX座標，Y座標，および横幅，高さ

	// オーバライドされた基準点の座標に関するアクセッサ
	public int getX() { return x; }
	public void setX( int x ) { this.x = x; }
	public int getY() { return y; }
	public void setY( int y ) { this.y = y; }
	// 幅と高さのアクセッサ
	public int getWidth() { return width; }
	public void setWidth( int width ) { if( width >= 0 ) this.width = width; }
	public int getHeight() { return height; }
	public void setHeight( int height ) { if( height >= 0 ) this.height = height; }

	// コンストラクタ
	public RectangleCollider( Collidable includer, char colliderGroup, int x, int y, int width, int height ) {
		super( includer, colliderGroup );
		setX( x ); setY( y ); setWidth( width ); setHeight( height );
	}

	// 衝突判定を行うメソッド
	public boolean isCollidedWith( Collider c ) {
		if( c instanceof CircleCollider ) {
			return isCollidedWith( (CircleCollider) c );
		}
		else if( c instanceof RectangleCollider ) {
			return isCollidedWith( (RectangleCollider) c );
		}
		else return false;
	}

	// 長方形どうしの衝突判定
	public boolean isCollidedWith( RectangleCollider r ) {

		double x0 = x,         y0 = y;
		double x1 = x + width, y1 = y;
		double x2 = x + width, y2 = y + height;
		double x3 = x,         y3 = y + height;

		double rx0 = r.getX(),                ry0 = r.getY();
		double rx1 = r.getX() + r.getWidth(), ry1 = r.getY();
		double rx2 = r.getX() + r.getWidth(), ry2 = r.getY() + r.getHeight();
		double rx3 = r.getX(),                ry3 = r.getY() + r.getHeight();

		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx0, ry0 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx1, ry1 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx2, ry2 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx3, ry3 ) ) return true;;

		double t;

		t = x0; x0 = rx0; rx0 = t; t = y0; y0 = ry0; ry0 = t;
		t = x1; x1 = rx1; rx1 = t; t = y1; y1 = ry1; ry1 = t;
		t = x2; x2 = rx2; rx2 = t; t = y2; y2 = ry2; ry2 = t;
		t = x3; x3 = rx3; rx3 = t; t = y3; y3 = ry3; ry3 = t;

		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx0, ry0 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx1, ry1 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx2, ry2 ) ) return true;;
		if( checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, rx3, ry3 ) ) return true;;

		return false;
	}


	// 長方形(自分)と円の衝突判定
	public boolean isCollidedWith( CircleCollider c ) {

		double leftTopX = x;
		double leftTopY = y;
		double rightBottomX = x + width;
		double rightBottomY = y + height;

		// 円cの中に長方形(自分)の各頂点が含まれているなら衝突している。
		if( isInnerTheCircle( leftTopX,     leftTopY,     c) ) return true; // 長方形の左上の頂点について
		if( isInnerTheCircle( rightBottomX, leftTopY,     c) ) return true; // 長方形の右上の頂点について
		if( isInnerTheCircle( leftTopX,     rightBottomY, c) ) return true; // 長方形の左下の頂点について
		if( isInnerTheCircle( rightBottomX, rightBottomY, c) ) return true; // 長方形の右下の頂点について

		// 円cが長方形(自分)の4辺のいずれかと交差しているなら衝突している。
		if( collideWithTheLineSegment( leftTopX, leftTopY, rightBottomX, leftTopY, c ) ) return true;
		if( collideWithTheLineSegment( rightBottomX, leftTopY, rightBottomX, rightBottomY, c ) ) return true;
		if( collideWithTheLineSegment( rightBottomX, rightBottomY, leftTopX, rightBottomY, c ) ) return true;
		if( collideWithTheLineSegment( leftTopX, rightBottomY, leftTopX, leftTopY, c ) ) return true;

		// 円cが長方形(自分)の中にいる(円の中心-左上頂点-右上頂点，円の中心-右下頂点-左下頂点の角度がいずれも0度以上かつ90度以下)なら true、そうでないなら false を返す。
		double x0 = leftTopX,     y0 = leftTopY;
		double x1 = rightBottomX, y1 = leftTopY;
		double x2 = rightBottomX, y2 = rightBottomY;
		double x3 = leftTopX,     y3 = rightBottomY;
		return checkInnerRectangle( x0, y0, x1, y1, x2, y2, x3, y3, c.getX(), c.getY() );
	}

	public void draw() {
		if( DxJava.dx != null  ) DxJava.dx.DrawBox( x, y, x + width, y + height, getColliderColor(), DxJava.FALSE );
	}

}
