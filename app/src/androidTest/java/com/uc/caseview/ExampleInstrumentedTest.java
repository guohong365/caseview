package com.uc.caseview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.uc.android.drawing.DrawObject;
import com.uc.android.drawing.MatrixUtil;
import com.uc.android.drawing.impl.RectangleImpl;
import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;
import com.uc.caseview.utils.LogUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.uc.caseview.entity.EntityUtils.loadAllCases;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class ExampleInstrumentedTest {
    static final String TAG = "caseview";
    CaseViewApp mApp;

    @Ignore
    @Test
    public void useAppContext() throws Exception {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.uc.caseview", appContext.getPackageName());
        assertEquals(appContext.getApplicationContext().getClass(), CaseViewApp.class);

        CaseViewApp myApp = (CaseViewApp) appContext.getApplicationContext();
        Log.v(TAG, "useAppContext: " + myApp.getClass());

        List<CaseItem> caseItems= loadAllCases();
        for(CaseItem caseItem : caseItems){
            String first=caseItem.getPreviewImage();
            long imageCount=caseItem.getImageCount();
            Log.i(TAG,
                 "id=[" + caseItem.getId() + "]" +
                         " title=[" + caseItem.getTitle() + "]" +
                         " user=[" + caseItem.getUser() + "]" +
                         " firstImage=[" + first + "]["+ caseItem.getPreviewImage()+"]" +
                         " imageCount=[" + imageCount +"][" + caseItem.getImageCount() + "]" +
                         " comment=[" + caseItem.getComment() + "]" +
                         " createTime=[" + caseItem.getCreateTime() + "]" +
                         " createDate=[" + caseItem.getCreateDate() + "]"

            );
        }
        Log.v(TAG, "=============================");
        Log.v(TAG, "to delete all......");
        List<CaseItem> list= EntityUtils.loadAllCases();
        Log.v(TAG, "reload cases with [" + list.size() + "]");
        List<Long> ids=new ArrayList<>();
        for (CaseItem item :list){
            ids.add(item.getId());
        }
        EntityUtils.deleteCases(ids);
        Intent intent = new Intent();
        intent.setClass(myApp, CaseListViewActivity.class);
        myApp.startActivity(intent);

    }
    @Test
    public void test(){
        DrawObject drawObject=new RectangleImpl(100,100, 200, 300);
        drawObject.setPosition(new PointF(0,100));
        PointF rt= drawObject.local2Parent(new PointF(0,0));
        LogUtils.logItem("TEST:", rt);
        drawObject.rotate(90);
        rt= drawObject.local2Parent(new PointF(0,-50));
        LogUtils.logItem("TEST:", rt);
    }
    static final String TAG_MATRIX="TEST_MATRIX";
    @Test
    public void testMatrix(){
        PointF[] src=new PointF[]{new PointF(0,0), new PointF(100,0)};
        Matrix matrix=new Matrix();
        //matrix.setTranslate(100,100);

        Matrix invert=new Matrix();

        Log.v(TAG_MATRIX, String.format("0-[%f,%f][%f,%f]", src[0].x, src[0].y, src[1].x, src[1].y ));
        matrix.postRotate(45);
        PointF[]dest= MatrixUtil.mapPoints(matrix, src);
        Log.v(TAG_MATRIX, String.format("1-[%f,%f][%f,%f]", dest[0].x, dest[0].y, dest[1].x, dest[1].y ));
        matrix.postRotate(90,30, 0);
        dest= MatrixUtil.mapPoints(matrix, src);
        Log.v(TAG_MATRIX, String.format("2-[%f,%f][%f,%f]", dest[0].x, dest[0].y, dest[1].x, dest[1].y ));
        matrix.preTranslate(100,100);
        dest= MatrixUtil.mapPoints(matrix, src);
        Log.v(TAG_MATRIX, String.format("3-[%f,%f][%f,%f]", dest[0].x, dest[0].y, dest[1].x, dest[1].y ));

        matrix.invert(invert);
        src=MatrixUtil.mapPoints(invert, dest);
        Log.v(TAG_MATRIX, String.format("4-[%f,%f][%f,%f]", src[0].x, src[0].y, src[1].x, src[1].y ));


    }
}
