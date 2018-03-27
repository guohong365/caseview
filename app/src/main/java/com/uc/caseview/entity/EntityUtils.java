package com.uc.caseview.entity;

import android.content.Context;
import android.util.Log;

import com.uc.caseview.CaseViewApp;
import com.uc.caseview.utils.FileUtils;
import com.uc.caseview.utils.LogUtils;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import static com.uc.caseview.CaseViewApp.APP_DB;

public class EntityUtils {
    static String TAG=EntityUtils.class.getSimpleName();
    /*
    private static Query<ImageItem> sql_selectImageItemByCaseId;
    private static CountQuery<CaseItem> sql_selectCaseItemCountByTitle;
    private static CountQuery<ImageItem> sql_selectImageItemCountByCaseId;
    private static Query<CaseItem> sql_selectAllCaseItems;
    private static Query<ImageItem> sql_selectAllImageItems;
    */
    private static DaoSession _session;
    public static DaoSession getNewSession(Context context){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, APP_DB);
        Database db = openHelper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
    public static DaoSession getSession(){
        synchronized (EntityUtils.class){
            if(_session==null){
                _session= getNewSession(CaseViewApp.App);
            }
            _session.clear();
            Log.v(TAG, "session cleared.");
            return _session;
        }
    }
/*
    private static DeleteQuery<CaseItem> _deleteCaseItemInCaseIds(Object[]ids){
                return getSession().getCaseItemDao().queryBuilder()
                        .where(CaseItemDao.Properties.Id.in(ids))
                        .buildDelete();
    }
    private static Query<ImageItem> _selectImageItemInCaseIds(Object[] ids){
        return getSession().getImageItemDao().queryBuilder()
                .where(ImageItemDao.Properties.CaseId.in(ids))
                .build();
    }
    private static DeleteQuery<ImageItem> _deleteImageItemInCaseIds(Object[] ids){
                return getSession().getImageItemDao().queryBuilder()
                        .where(ImageItemDao.Properties.CaseId.in(ids))
                        .buildDelete();
    }
    private static Query<ImageItem> _selectImageItemByCaseId(){
        synchronized (EntityUtils.class){
            if(sql_selectImageItemByCaseId==null){
                sql_selectImageItemByCaseId= getSession().getImageItemDao().queryBuilder()
                        .where(ImageItemDao.Properties.CaseId.eq(-1))
                        .build();
            }
        }
        return sql_selectImageItemByCaseId;
    }
    private static CountQuery<CaseItem> _selectCaseItemCountByTitle(){
        synchronized (EntityUtils.class) {
            if (sql_selectCaseItemCountByTitle == null) {
                sql_selectCaseItemCountByTitle = getSession().getCaseItemDao().queryBuilder()
                        .where(CaseItemDao.Properties.Title.eq(""))
                        .buildCount();
            }
        }
        return sql_selectCaseItemCountByTitle;
    }
    private static CountQuery<ImageItem> _selectImageItemCountByCaseId(){
        synchronized (EntityUtils.class){
            if(sql_selectImageItemCountByCaseId==null){
                sql_selectImageItemCountByCaseId= getSession().getImageItemDao().queryBuilder()
                        .where(ImageItemDao.Properties.CaseId.eq(-1))
                        .buildCount();
            }
        }
        return sql_selectImageItemCountByCaseId;
    }
    private static Query<CaseItem> _selectAllCaseItems(){
        synchronized (EntityUtils.class){
            if(sql_selectAllCaseItems==null){
                sql_selectAllCaseItems= getSession().getCaseItemDao().queryBuilder()
                        .orderDesc(CaseItemDao.Properties.CreateTime).build();
            }
        }
        return sql_selectAllCaseItems;
    }
    private static Query<ImageItem> _selectAllImageItems(){
        synchronized (EntityUtils.class){
            if(sql_selectAllImageItems==null){
                sql_selectAllImageItems= getSession().getImageItemDao().queryBuilder()
                        .orderDesc(ImageItemDao.Properties.TakeTime).build();
            }
        }
        return sql_selectAllImageItems;
    }
    private static Query<ImageItem> _selectImageItemsInIds(Object[] ids){
        return getSession().getImageItemDao().queryBuilder().where(ImageItemDao.Properties.Id.in(ids)).build();
    }
    */
    public static int deleteCases(Long[] ids) {
        return deleteCases(Arrays.asList(ids));
    }
    public static int deleteCases(final Collection<Long> ids) {
        Log.v(TAG, "prepare delete " + ids.size());
        DaoSession session = EntityUtils.getSession();
        try {
            session.callInTx(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Log.v(TAG, "prepare load images in case ids " + ids.size() + "["+ ids.toString()+"]");
                    List<ImageItem> images =getSession()
                            .getImageItemDao()
                            .queryBuilder()
                            .where(ImageItemDao.Properties.Id.in(ids.toArray())).list();
                    Log.v(TAG, "image related loaded [" + images.size() + "]");
                    for (ImageItem item : images) {
                        File file = FileUtils.getImageFile(CaseViewApp.App, item.getName());
                        Log.v(TAG, "delete image file [" + file.getName() +"]");
                        file.delete();
                    }
                    getSession().getImageItemDao().deleteInTx(images);
                    Log.v(TAG,"images deleted....");
                    getSession().getCaseItemDao().deleteByKeyInTx(ids);
                    Log.v(TAG, "cases deleted....");
                    return true;
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return 0;
        }
        return ids.size();
    }
    public static List<CaseItem> loadAllCases(){
        List<CaseItem> list=getSession()
                .getCaseItemDao()
                .queryBuilder()
                .orderDesc(CaseItemDao.Properties.CreateTime)
                .list();
        Log.v(TAG, "load all cases.");
        LogUtils.logItems(TAG,list);
        return list;
    }
    public static List<ImageItem> loadAllImages(){
        List<ImageItem> list=getSession()
                .getImageItemDao()
                .queryBuilder()
                .orderDesc(ImageItemDao.Properties.TakeTime)
                .list();
        LogUtils.logItems(TAG,list);
        return list;
    }
    public static List<ImageItem> loadImagesByCase(long caseId){
        List<ImageItem> list = getSession()
                .getImageItemDao()
                .queryBuilder()
                .where(ImageItemDao.Properties.CategoryId.eq(caseId))
                .orderDesc(ImageItemDao.Properties.TakeTime).list();
        LogUtils.logItems(TAG, list);
        return list;
    }
    public static boolean isCaseExistsByTitle(Long id, String title){
        return id==null ?
                getSession()
                        .getCaseItemDao()
                        .queryBuilder()
                        .where(CaseItemDao.Properties.Title.eq(title))
                        .buildCount().count() > 0 :
                getSession()
                        .getCaseItemDao().
                        queryBuilder()
                        .where(CaseItemDao.Properties.Title.eq(title), CaseItemDao.Properties.Id.notEq(id))
                        .buildCount().count()>0;
    }
    public static long getCaseImageCount(long caseId){
        return getSession()
                .getImageItemDao()
                .queryBuilder()
                .where(ImageItemDao.Properties.CategoryId.eq(caseId))
                .buildCount().count();
    }

    public static List<ImageGroupItem> buildDateGroupedImageList(SortedMap<String, List<ImageItem>> map, CaseItem caseItem){
        List<ImageGroupItem> list=new ArrayList<>();
        for (Map.Entry<String, List<ImageItem>> entry : map.entrySet()){
            list.add(new ImageGroupItem(entry.getKey(), entry.getValue(),caseItem));
        }
        LogUtils.logItems(TAG, list);
        return list;
    }

    public static SortedMap<String, List<ImageItem>> buildDateGroupedImageItem(List<ImageItem> list){
        SortedMap<String, List<ImageItem>> groups = new TreeMap<>();
        for (ImageItem item : list) {
            if (!groups.containsKey(item.getDate())) {
                groups.put(item.getDate(), new ArrayList<ImageItem>());
            }
            groups.get(item.getDate()).add(item);
        }
        return groups;
    }

    public static List<ImageItem> loadImagesByInIds(List<Long> imageIds) {
        List<ImageItem> list=getSession()
                .getImageItemDao()
                .queryBuilder()
                .where(ImageItemDao.Properties.Id.in(imageIds.toArray()))
                .orderDesc(ImageItemDao.Properties.TakeTime)
                .list();
        LogUtils.logItems(TAG,list);
        return list;
    }
    public static List<ImageItem> loadImagesByDateAndCaseId(@NotNull String date, Long caseId){
        List<ImageItem> list;
        if(caseId==null) {
            list= getSession().getImageItemDao().queryBuilder()
                    .where(ImageItemDao.Properties.Date.eq(date))
                    .orderDesc(ImageItemDao.Properties.TakeTime)
                    .build().list();
        } else {
            list= getSession().getImageItemDao().queryBuilder()
                    .where(ImageItemDao.Properties.Date.eq(date), ImageItemDao.Properties.CategoryId.eq(caseId))
                    .orderDesc(ImageItemDao.Properties.TakeTime)
                    .build().list();
        }
        LogUtils.logItems(TAG,list);
        return list;
    }

}
