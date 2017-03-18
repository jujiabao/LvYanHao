package com.lvyanhao.layout;

/**
 * Created by mgc on 2016/7/6.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.lvyanhao.R;


/**
 * Created by SilenceDut on 2015/12/8.
 * �໬ɾ��
 */
public class SwipeBackLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private int mContentWidth;
    private int mMoveLeft;
    private boolean isClose = false;
    private boolean isEdgeDrag=false;
    private CallBack mCallBack;
    private Drawable mShadowLeft;
    private static final int FULL_ALPHA = 255;
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private float mScrimOpacity;
    private float mScrollPercent;
    private Rect mTmpRect = new Rect();

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            //����true��ʾ�����϶�
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mContentView;//���child==mContentView������true��Ҳ����˵mContentView�����ƶ�
            }

            //��¼ֵ�ı仯
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                //��¼��ߵ�ֵ�ı仯����Ϊ����ʵ�ֵ������һ���������ֻ��¼��ߵ�ֵ�Ϳ�����
                mScrollPercent = Math.abs((float) left
                        / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
                mMoveLeft = left;
                if (isClose && (left == mContentWidth)) {
                    //�����ǰ״̬�ǹر�״̬����ߵ�ֵ���ڻ�����View�Ŀ�ȣ�
                    //Ҳ����˵��ǰ�Ľ����Ѿ�������Ļ���ͻص�finish������֪ͨactivity����finish��
                    mCallBack.onFinish();

                }
            }

            //��ָ�ɿ��ᴥ���������������λ�������ڴ˷�����ʵ��
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //һ������дcomputeScroll()��������Ȼû��Ч��
                //����ƶ��ľ�����ڻ���ڵ�ǰ�����1/10���򴥷��ر�
                if (mMoveLeft >= (mContentWidth / 4)) {
                    isClose = true;
                    //���û�����View�ƶ�λ�ã���Ȼ��ǰ�Ľ��滬����Ļ
                    mViewDragHelper.settleCapturedViewAt(mContentWidth, releasedChild.getTop());
                } else {
                    //���û�����View�ƶ�λ�ã����ָ�ԭ����λ��
                    mViewDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
                }
                //֪ͨ�ػ����
                invalidate();
            }

            //���¶�λˮƽ�ƶ���λ��
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //ˮƽ�ƶ�����ķ�Χ��0~��ǰ����Ŀ�ȣ����leftС��0ֱ�ӷ���0��
                // ������ڵ�ǰ����Ŀ��ֱ�ӷ��ص�ǰ������
                //Ҳ���ǿ��Ƶ�ǰ����ֻ�������ƶ�
                return Math.min(mContentWidth, Math.max(left, 0));
            }


            //����ˮƽ�϶��ľ���
            @Override
            public int getViewHorizontalDragRange(View child) {
                //��Ϊ�����ƶ������������棬����ֱ�ӷ�����������Ŀ�ȾͿ�����
                return mContentWidth;
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                isEdgeDrag = true;

            }

        });
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        setShadow();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //SwipeBackFrameLayout����View����ֻ��һ�����������쳣
        if (getChildCount() != 1) {
            throw new IllegalStateException("SwipeBackFrameLayout must host one child.");
        }
        //ȡ�õ�ǰ���ֵĵ�һ����View��Ҳ��Ψһһ����View
        //Ҳ����activity����Ҫ����
        mContentView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //��ȡ��ǰ������
        mContentWidth = mContentView.getWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //���¼����ݸ�ViewDragHelper

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //���¼����ݸ�ViewDragHelper
        mViewDragHelper.processTouchEvent(event);
        invalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        mScrimOpacity=1-mScrollPercent;
        //һ��Ҫ���������������onViewReleased��������

        if (mViewDragHelper!=null&&mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setShadow() {

        mShadowLeft=getResources().getDrawable(R.drawable.shadow_left);

        invalidate();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (drawContent && mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);

    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    //���ûص��ӿ�
    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    //�����Ƴ���Ļʱ�ӿڻص�
    public interface CallBack {
        void onFinish();
    }
}