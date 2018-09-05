package show.we.lib.widget.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import show.we.lib.utils.DisplayUtils;
import show.we.lib.utils.ShapeXmlUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ll
 * @version 1.0.0
 */
public class GiftShapeEffect {
	
	//礼物动画 ， 暂时不能绘制 ，所以只是按原来的绘制图画 ， 188 ， 888 ， 8888 绘制礼物动画不对 。 
	
	
	 /**
	 * 比翼双飞:300个
     */
    public static final int SHAPE_DOUBLE_HEART = 99;

    /**
     * 笑脸:50个
     */
    public static final int SHAPE_SMILE_FACE = 188;
    /**
     * Love:520个
     */
    public static final int SHAPE_LOVE = 520;
    /**
     * 爱心:100个
     */
    public static final int SHAPE_HEART = 888;
   
    /**
     * 一生一世:1314个
     */
    public static final int SHAPE_1314 = 1314;
    /**
     * 生生世世:3344个
     */
    public static final int SHAPE_3344 = 3344;
    /**
     * 长长久久:9999个
     */
    public static final int SHAPE_9999 = 8888;

    private static final int FRAME_COUNT = 100;
    private static final int ALPHA_FRAME = FRAME_COUNT / 4;
    private static final float DEFAULT_FPS = 8.0f; //铺开速度
    private static final String ASSETS_SMILE_FACE = "shape/smile_face.xml";
    private static final String ASSETS_HEART = "shape/heart.xml";
    private static final String ASSETS_DOUBLE_HEART = "shape/double_heart.xml";
    private static final String ASSETS_LOVE = "shape/love.xml";
    private static final String ASSETS_1314 = "shape/v1314.xml";
    private static final String ASSETS_3344 = "shape/v3344.xml";
    private static final String ASSETS_9999 = "shape/v520.xml";
    private static final float DEFAULT_SCALE = DisplayUtils.getDensity() / 2.0f;

    private List<Point> mDestPoints = new ArrayList<Point>();
    private List<Shape> mShapes = new ArrayList<Shape>();
    private Point mCenterPoint = new Point();
    private float mMinX = -1;
    private float mMaxX;
    private float mMinY = -1;
    private float mMaxY;
    private Bitmap mBitmap;
    private int mBeginFrame;
    private Paint mPaint = new Paint();
    private Matrix mMatrix = new Matrix();
    private float mBitmapScale = DEFAULT_SCALE;
    private float mRatio = 1;
    private float mFps = DEFAULT_FPS;

    /**
     * 设置控件宽度
     * @param width width
     */
    public void setWidth(int width) {
        synchronized (this) {
            mRatio = width / (float) DisplayUtils.getWidthPixels();
            mFps = DEFAULT_FPS * mRatio;
            mBitmapScale = DEFAULT_SCALE * mRatio;
        }
    }

    /**
     * 开始绘制
     *
     * @param context    context
     * @param beginFrame 开始帧
     * @param shapeSize  形状数量
     * @param bitmap     bitmap
     */
    public void start(Context context, int beginFrame, int shapeSize, Bitmap bitmap) {
        synchronized (this) {
            mBeginFrame = beginFrame;
            mBitmap = bitmap;
            try {
                mDestPoints.clear();
                mShapes.clear();
                switch (shapeSize) {
                    case SHAPE_SMILE_FACE:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_SMILE_FACE), mRatio);
                        break;
                    case SHAPE_HEART:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_HEART), mRatio);
                        break;
                    case SHAPE_DOUBLE_HEART:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_DOUBLE_HEART), mRatio);
                        break;
                    case SHAPE_LOVE:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_LOVE), mRatio);
                        break;
                    case SHAPE_1314:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_1314), mRatio);
                        break;
                    case SHAPE_3344:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_3344), mRatio);
                        break;
                    case SHAPE_9999:
                        mDestPoints = ShapeXmlUtils.parse(context.getResources().getAssets().open(ASSETS_9999), mRatio);
                        break;
                    default:
                        break;
                }
                for (Point p : mDestPoints) {
                    if (mMinX == -1) {
                        mMinX = p.x;
                    }
                    mMinX = Math.min(mMinX, p.x);
                    mMaxX = Math.max(mMaxX, p.x);

                    if (mMinY == -1) {
                        mMinY = p.y;
                    }
                    mMinY = Math.min(mMinY, p.y);
                    mMaxY = Math.max(mMaxY, p.y);
                    Shape shape = new Shape();
                    shape.destX = p.x;
                    shape.destY = p.y;
                    mShapes.add(shape);
                }
                mCenterPoint.x = (mMinX + mMaxX) / 2f;
                mCenterPoint.y = (mMinY + mMaxY) / 2f;
                for (Shape shape : mShapes) {
                    shape.x = mCenterPoint.x;
                    shape.y = mCenterPoint.y;
                    float dx = shape.destX - mCenterPoint.x;
                    float dy = shape.destY - mCenterPoint.y;
                    final float half = 180;
                    float degrees = (float) (Math.atan2(dy, dx) * half / Math.PI);
                    shape.sx = (float) (mFps * Math.cos(degrees * Math.PI / half));
                    shape.sy = (float) (mFps * Math.sin(degrees * Math.PI / half));
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 绘制动画
     *
     * @param canvas canvas
     * @param frame  当前帧数
     */
    public void draw(Canvas canvas, int frame) {
        synchronized (this) {
            int delta = frame - mBeginFrame;
            if (mBitmap != null && delta < FRAME_COUNT) {
                for (Shape shape : mShapes) {
                    shape.update();
                    mMatrix.reset();
                    mPaint.reset();
                    final int max = 255;
                    int alpha = (int) (max * Math.min(ALPHA_FRAME, (FRAME_COUNT - delta)) / (float) ALPHA_FRAME);
                    mPaint.setAlpha(alpha);
                    mMatrix.postTranslate(shape.x / mBitmapScale, shape.y / mBitmapScale);
                    mMatrix.postScale(mBitmapScale, mBitmapScale);
                    canvas.drawBitmap(mBitmap, mMatrix, mPaint);
                }
            }
        }
    }
}
