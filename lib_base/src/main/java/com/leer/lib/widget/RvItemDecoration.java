package com.leer.lib.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RvItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint;

    public RvItemDecoration() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#E3E4E5"));
    }

    /**
     * 复写onDraw方法，从而达到在每隔条目的被绘制之前（或之后），让他先帮我们画上去几根线吧
     */
    @Override
    public void onDraw(@NotNull Canvas c, RecyclerView parent, @NotNull RecyclerView.State state) {
        //先初始化一个Paint来简单指定一下Canvas的颜色，就黑的吧！


        //获得RecyclerView中总条目数量
        int childCount = parent.getChildCount();

        //遍历一下
        for (int i = 0; i < childCount; i++) {
            //获得子View，也就是一个条目的View，准备给他画上边框
            View childView = parent.getChildAt(i);

            //先获得子View的长宽，以及在屏幕上的位置，方便我们得到边框的具体坐标
            float x = childView.getX();
            float y = childView.getY();
            int width = childView.getWidth();
            int height = childView.getHeight();

            //根据这些点画条目的四周的线
//            c.drawRect(x, y, x + width, y + mDividerHeight, paint);//top
//            c.drawRect(x, y + height, x + width, y + height + mDividerHeight, paint);//bottom
//
//            c.drawRect(x, y, x + mDividerHeight, y + height, paint);//left
//            c.drawRect(x + width, y, x + width + mDividerHeight, y + height, paint);//right

//            c.drawLine(x, y, x + width, y, paint);//top
//            c.drawLine(x, y, x, y + height, paint);//left
//            c.drawLine(x + width, y, x + width, y + height, paint);//right
            c.drawLine(x, y + height, x + width, y + height, paint);//bottom

        }
        super.onDraw(c, parent, state);
    }

    /**
     * 是否是最后一行
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            int childCount = parent.getAdapter().getItemCount();

            double count = Math.ceil((double) childCount / (double) spanCount);//总行数
            double currentCount = Math.ceil((double) (itemPosition + 1) / spanCount);//当前行数

            //最后当前数量小于总的
            if (currentCount < count) {
                return false;
            }
        }
        return true;
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastColumn(int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //有多少列
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = getSpanCount(parent);
            if ((itemPosition + 1) % spanCount == 0) {//因为是从0可以所以要将ItemPosition先加1
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}