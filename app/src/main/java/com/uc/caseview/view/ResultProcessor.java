package com.uc.caseview.view;

import android.content.Intent;

public interface ResultProcessor {
    boolean process(int requestCode, Intent intent);
}
