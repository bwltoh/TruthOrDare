package com.example.truthordare;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Random;

public class SpinnerBoard extends View {


    private Paint                       arcPaint;
    private Paint                       textPaint;
    private ArrayList<String>           names;
    private int[]                       colorsList;
    private float[]                     anglesRanges;
    private RectF                       rectF;
    private int                         value;
    private boolean                     isInit = false;
    private Matrix                      matrix;
    private ValueAnimator               animator;
    private SpinnerBoard.OnNameChoosing onNameChoosing;
    private Bitmap                      bitmap;

    public SpinnerBoard(Context context) {
        super(context);
        init();
    }

    public SpinnerBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpinnerBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(5f);
        arcPaint.setShadowLayer(20, 0, 0, Color.BLACK);//add shadow

        setLayerType(LAYER_TYPE_SOFTWARE, arcPaint);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(2f);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(getResources().getColor(android.R.color.white));
        names = new ArrayList<>();
        matrix = new Matrix();
        colorsList = new int[]{R.color.colorAccent, R.color.colorPrimary,
                R.color.yellow, R.color.red, R.color.blue,
                R.color.grass, R.color.orange, R.color.gray,
                R.color.pink, R.color.green, R.color.purpel, R.color.colorPrimaryDark};

        isInit = true;


    }

    void setUpAngels() {
        float temp = (float) 360 / names.size();


        anglesRanges = new float[names.size()];
        anglesRanges[0] = 0;
        for (int angle = 1; angle < names.size(); angle++) {


            anglesRanges[angle] = temp;

            temp = temp + (float) 360 / names.size();

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) pxToDp(400);
        int desiredHeight = (int) pxToDp(400);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;


        if (widthMode == MeasureSpec.EXACTLY) {

            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {

            width = Math.min(desiredWidth, widthSize);
        } else {

            width = desiredWidth;
        }


        if (heightMode == MeasureSpec.EXACTLY) {

            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {

            height = Math.min(desiredHeight, heightSize);
        } else {

            height = desiredHeight;
        }


        int dim = Math.min(width, height);
        setMeasuredDimension(dim, dim);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit) {
            init();
        }

        float minDim = Math.min(getWidth(), getHeight());
        rectF = new RectF(0 + getPaddingLeft(), 0 + getPaddingTop(),
                minDim - getPaddingRight(), minDim - getPaddingBottom());

        for (int i = 0; i < names.size(); i++) {
            arcPaint.setColor(getResources().getColor(colorsList[i]));
            canvas.drawArc(rectF, 0, (float) 360 / names.size(), true, arcPaint);

            canvas.rotate((float) ((float) 360 / names.size()) / 2, (float) getWidth() / 2, (float) getWidth() / 2);

            canvas.drawText(names.get(i), (float) (minDim / 2 + minDim / 4), (float) (minDim / 2 + 0), textPaint);
            canvas.rotate((float) ((float) 360 / names.size()) / 2, (float) getWidth() / 2, (float) getWidth() / 2);

        }


        canvas.translate((float) rectF.width() / 2 + (getPaddingLeft()) - ((float) bitmap.getWidth() / 2),
                (float) rectF.height() / 2 + (getPaddingTop()) - ((float) bitmap.getHeight() / 2));

        matrix.setRotate(value, (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);


        canvas.drawBitmap(bitmap, matrix, null);
        canvas.restore();

    }

    private Bitmap getBitmap() {

        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.red_arrow);
        return Bitmap.createScaledBitmap(bitmap, getWidth() / 2, getHeight() / 4, true);

    }


    public void startAnimation() {
        Random randomAngle = new Random();
        int angle = randomAngle.nextInt(360);

        animator = ValueAnimator.ofInt(0, angle + 360 * 5);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (int) animation.getAnimatedValue();
                invalidate();
            }

        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int v = getValue() - +360 * 5;

                for (int i = 0; i < anglesRanges.length; i++) {
                    if (i == anglesRanges.length - 1) {
                        onNameChoosing.getName(names.get(i));
                        break;
                    }
                    if (v < anglesRanges[i + 1] && v > anglesRanges[i]) {
                        onNameChoosing.getName(names.get(i));
                        break;
                    }
                }
            }
        });
        if (animator.isRunning()) {
            animator.cancel();
            animator.start();

        } else {
            animator.start();
        }
    }


    public int getValue() {
        return value;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInit)
            init();
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
        setUpAngels();
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null)
            animator.removeAllListeners();
    }

    public void setOnNameChoosing(SpinnerBoard.OnNameChoosing onNameChoosing) {
        this.onNameChoosing = onNameChoosing;
    }

    private float pxToDp(float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());

    }

    private float dpToPx(float dp) {

        return dp / getResources().getDisplayMetrics().density;
    }

    public interface OnNameChoosing {
        void getName(String name);
    }
}



