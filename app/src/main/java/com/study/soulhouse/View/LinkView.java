package com.study.soulhouse.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinkView extends View {
    private Context context;
    private Paint paint;
    public LinkView(Context context) {
        super(context);
        init(context);
    }
    public LinkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public LinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public LinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void init(Context context){
        this.context=context;
        paint=new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rect = new RectF(0, 10, getWidth(), getHeight());
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float bottomLineY = rect.centerY() - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(true);
        canvas.drawText("更多", getWidth()/2, bottomLineY, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rect, Math.max(getHeight(),getWidth())/2,Math.max(getHeight(),getWidth())/2,paint);
        paint.setColor(Color.BLACK);

    }
    /**
     * 点击后显示对应链接内容
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}
