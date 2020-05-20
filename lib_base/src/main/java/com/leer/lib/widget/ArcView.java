package com.leer.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.leer.lib.R;
import com.leer.lib.utils.ConvertUtils;


public class ArcView extends View {

    private final int DEFAULT_ARC_WIDTH = 5;
    private final int DEFAULT_ARC_COLOR = Color.BLACK;
    private final int DEFAULT_ARC_BACKGROUND_COLOR = Color.BLACK;
    private final int DEFAULT_ARC_PERCENT = 0;
    private final int DEFAULT_CENTER_TEXT_SIZE = 11;


    private String leftLabelText;
    private String rightLabelText;
    //是否是百分数
    private boolean isPercent;

    //标题文本
    private String titleText;
    //中心文本
    private String centerText;
    //是否渐变
    private boolean isGradient;
    private int arcWidth = DEFAULT_ARC_WIDTH;
    private int arcColor = DEFAULT_ARC_COLOR;
    private int arcBackgroundColor = DEFAULT_ARC_BACKGROUND_COLOR;
    private int percent = DEFAULT_ARC_PERCENT;
    private int centerTextSize = DEFAULT_CENTER_TEXT_SIZE;

    private Paint arcPaint;
    private Paint backgroundPaint;
    private Paint textPaint;
    private Paint titlePaint;
    private Paint percentPaint;

    /**
     * 圆弧坐标
     */
    private RectF arcRectF;

    private Rect textBound;
    private Rect titleBound;
    private int baseline;
    private int titleBaseline;

    /**
     * 弧度
     */
    private float radian;


    private String textPercent;

    private int titleTextSize;


    private SweepGradient gradient;
    private Rect percentBound;
    private String percentText;
    private Paint labelPaint;
    private Rect rightLabelBound;
    private Rect leftLabelBound;

    private int arcPadding = ConvertUtils.dp2px(5);

    public ArcView(Context context) {
        this(context, null, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView,
                defStyleAttr, 0);
        arcWidth = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcWidth,
                DEFAULT_ARC_WIDTH);
        arcColor = typedArray.getColor(R.styleable.ArcView_arcColor, DEFAULT_ARC_COLOR);
        percent = typedArray.getInt(R.styleable.ArcView_arcPercent, DEFAULT_ARC_PERCENT);
        centerTextSize = typedArray.getDimensionPixelSize(R.styleable.ArcView_centerTextSize,
                DEFAULT_CENTER_TEXT_SIZE);
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.ArcView_titleTextSize,
                ConvertUtils.dp2px(13));
        arcBackgroundColor = typedArray.getColor(R.styleable.ArcView_arcBackgroundColor,
                DEFAULT_ARC_BACKGROUND_COLOR);
        isGradient = typedArray.getBoolean(R.styleable.ArcView_isGradient,
                false);
        isPercent = typedArray.getBoolean(R.styleable.ArcView_isPercent,
                false);
        centerText = typedArray.getString(R.styleable.ArcView_centerText);
        titleText = typedArray.getString(R.styleable.ArcView_titleText);
        leftLabelText = typedArray.getString(R.styleable.ArcView_leftLabelText);
        rightLabelText = typedArray.getString(R.styleable.ArcView_rightLabelText);
        typedArray.recycle();
        init();
    }

    private void init() {
        arcRectF = new RectF();
        textBound = new Rect();
        titleBound = new Rect();

        arcPaint = new Paint();
        arcPaint.setColor(arcColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(arcBackgroundColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(arcWidth);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(centerTextSize);

        titlePaint = new Paint();
        titlePaint.setColor(Color.parseColor("#80FFFFFF"));
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(titleTextSize);
        titlePaint.getTextBounds(titleText, 0, titleText.length(), titleBound);

        labelPaint = new Paint();
        labelPaint.setColor(Color.parseColor("#80FFFFFF"));
        labelPaint.setAntiAlias(true);
        labelPaint.setTextSize(ConvertUtils.dp2px(11));
        leftLabelBound = new Rect();
        labelPaint.getTextBounds(leftLabelText, 0, leftLabelText.length(), leftLabelBound);
        rightLabelBound = new Rect();
        labelPaint.getTextBounds(rightLabelText, 0, rightLabelText.length(), rightLabelBound);

        if (isPercent) {
            percentPaint = new Paint();
            percentPaint.setColor(Color.WHITE);
            percentPaint.setAntiAlias(true);
            percentPaint.setTextSize(ConvertUtils.dp2px(13));
            percentText = "%";
            percentBound = new Rect();
            percentPaint.getTextBounds(percentText, 0, percentText.length(), percentBound);
        }

        setPercent(percent);

    }

    public void setPercent(int p) {
        radian = p * 270 / 100;
        if (TextUtils.isEmpty(centerText)) {
            textPercent = String.valueOf(p);
        } else {
            textPercent = centerText;
        }
        textPaint.getTextBounds(textPercent, 0, textPercent.length(), textBound);
        invalidate();
    }

    public void setPercent(float p) {
        radian = p * 270 / 100;
        if (TextUtils.isEmpty(centerText)) {
            textPercent = String.valueOf(p);
        } else {
            textPercent = centerText;
        }
        textPaint.getTextBounds(textPercent, 0, textPercent.length(), textBound);
        invalidate();
    }

    public void setCenterPercent(float p) {
        if (p != 0) {
            centerText = null;
        } else {
            centerText = "--";
        }
        setPercent(p);
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        arcRectF.left = arcWidth / 2 + arcPadding;
        arcRectF.top = arcWidth / 2 + arcPadding;
        arcRectF.right = width - arcWidth / 2 - arcPadding;
        arcRectF.bottom = height - arcWidth / 2 - arcPadding;

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline =
                (height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top - arcPadding;

        titleBaseline = baseline + titleBound.height() + ConvertUtils.dp2px(10);

        gradient = new SweepGradient(width / 2, height / 2, new int[]{Color.parseColor("#009CF2")
                , Color.parseColor("#FF9500"), Color.parseColor("#D20B00")}
                , new float[]{0.25f, 0.625f, 1f});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(arcRectF, 135, 270, false, backgroundPaint);
        if (isGradient) {
            canvas.save();
            //旋转45 改变渐变起始位
            int rotateDegrees = 45;
            //画笔圆角偏差，若不设置圆角画笔，则不需要
            int offset = 6;
            canvas.rotate(rotateDegrees + offset, getWidth() / 2, getHeight() / 2);

            arcPaint.setShader(gradient);
            canvas.drawArc(arcRectF, 135 - rotateDegrees - offset, radian, false, arcPaint);

            canvas.restore();
        } else {
            canvas.drawArc(arcRectF, 135, radian, false, arcPaint);
        }
        //中心文字
        if (isPercent) {
            //带%
            canvas.drawText(textPercent,
                    getWidth() / 2 - textBound.width() / 2 - percentBound.width() / 2,
                    baseline, textPaint);

            canvas.drawText(percentText, getWidth() / 2 + textBound.width() / 2,
                    baseline, percentPaint);
        } else {
            //不带%
            canvas.drawText(textPercent, getWidth() / 2 - textBound.width() / 2,
                    baseline, textPaint);
        }
        //标题
        canvas.drawText(titleText, getMeasuredWidth() / 2 - titleBound.width() / 2, titleBaseline
                , titlePaint);

        canvas.drawText(leftLabelText, ConvertUtils.dp2px(23),
                getHeight() - leftLabelBound.height(), labelPaint);

        canvas.drawText(rightLabelText,
                getWidth() - ConvertUtils.dp2px(18) - rightLabelBound.width(),
                getHeight() - leftLabelBound.height(), labelPaint);
    }
}
