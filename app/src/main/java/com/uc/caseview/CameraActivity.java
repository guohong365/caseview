package com.uc.caseview;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.uc.android.fragment.Camera2BasicFragment;
import com.uc.android.util.camera.FileSaver;
import com.uc.android.util.camera.NameGenerator;
import com.uc.android.util.camera.SaveCallback;
import com.uc.android.view.camera.CameraPreviewView;
import com.uc.caseview.fragment.CameraFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class CameraActivity extends ActivityBase implements CameraFragment.OnFragmentInteractionListener{
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);
        if(null==savedInstanceState){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance("", ""))
                    .commit();
        }
         setFullScreenMode(FULL_SCREEN_MODE.FULL_SCREEN_HIDE_ALL);
    }
    NameGenerator mNameGenerator=new NameGenerator() {
        @Override
        public String getNewFullPathName() {
            File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if(dir==null){
                //Log.e(TAG, "onPictureTaken: get dir failed");
                return null;
            }
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String name=dateFormat.format(new Date())+"jpg";
            return dir.getAbsolutePath() + File.separator + name;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
