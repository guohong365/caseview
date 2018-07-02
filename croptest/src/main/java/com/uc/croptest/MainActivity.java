package com.uc.croptest;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.uc.android.activity.ImageEditActivity;
import com.uc.android.system.FileUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String TAG="MAIN_ACTIVITY";
    final int REQUEST_CAPTURE_IMAGE = 0;
    final int REQUEST_CROP=1;
    Uri imageUri;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.btn_capture);
        button.setOnClickListener(this);
        button=findViewById(R.id.btn_crop);
        button.setOnClickListener(this);
        imageView = findViewById(R.id.image_view);
        imageView.setLongClickable(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_capture:
                imageUri=FileUtil.getExternalFileUri(getApplicationContext(), "capture.jpeg");
                Log.d(TAG, imageUri.getAuthority());
                Log.d(TAG, imageUri.getPath());
                intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_crop:
                Log.d(TAG, "start ImageProcessActivity");
                Uri output=Uri.fromFile(new File(getDataDir(), "output.jpeg"));
                intent=new Intent(this, ImageEditActivity.class);
                intent.putExtra(UCrop.EXTRA_OUTPUT_URI, output);
                intent.putExtra(UCrop.EXTRA_INPUT_URI, imageUri);
                startActivityForResult(intent, REQUEST_CROP);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CAPTURE_IMAGE:
                    imageView.setImageURI(imageUri);
                    break;
                case REQUEST_CROP:
                    imageView.setImageURI(data.getExtras().getParcelable(UCrop.EXTRA_OUTPUT_URI));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
