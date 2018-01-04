package com.uc.caseview;

import android.graphics.PointF;

import com.uc.android.drawing.DrawObject;
import com.uc.android.drawing.impl.RectangleImpl;
import com.uc.caseview.utils.LogUtils;

import org.junit.Test;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        DrawObject drawObject=new RectangleImpl(100,100, 100,100);

        PointF rt= drawObject.local2Parent(drawObject.getPosition());
        LogUtils.logItem("TEST:", rt);
        drawObject.rotate(90);
        rt= drawObject.local2Parent(drawObject.getPosition());
        LogUtils.logItem("TEST:", rt);
    }



}