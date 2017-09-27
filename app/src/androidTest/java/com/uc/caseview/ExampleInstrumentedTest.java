package com.uc.caseview;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.uc.caseview.entity.CaseItem;
import com.uc.caseview.entity.EntityUtils;

import org.greenrobot.greendao.query.QueryBuilder;
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

}
