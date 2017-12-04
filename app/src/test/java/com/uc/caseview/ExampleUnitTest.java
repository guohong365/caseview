package com.uc.caseview;

import android.graphics.PointF;

import com.uc.android.drawing.DrawObjectImpl;
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
        DrawObjectImpl drawObject=new DrawObjectImpl();
        drawObject.setPosition(new PointF(100,200));
        PointF rt= drawObject.local2Parent(drawObject.getPosition());
        LogUtils.logItem("TEST:", rt);
        drawObject.rotate(90);
        rt= drawObject.local2Parent(drawObject.getPosition());
        LogUtils.logItem("TEST:", rt);
    }



}