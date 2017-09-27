package com.uc.caseview.utils;

import com.bumptech.glide.request.RequestOptions;
import com.uc.caseview.R;

/**
 * Created by guoho on 2017/5/10.
 */

public class GlobalHolder {
    public static int ImageWidth;
    public static int ColumnWidth;
    public static int Spacing;
    public static int GridViewCutOff;
    public static boolean dynanicColumn;
    public static boolean debug;
    public static int gridColumns;
    private static RequestOptions requestOptions;

    public static RequestOptions getRequestOptions(){
        synchronized (GlobalHolder.class){
            if(requestOptions==null){
                requestOptions=new RequestOptions()
                        .fallback(R.drawable.main_action_gallery_48dp)
                        .error(R.drawable.main_action_gallery_48dp);

            }
        }
        return requestOptions;
    }
}
