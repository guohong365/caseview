package com.uc.android.widget;

import android.os.Bundle;

public interface OptionControlListener {
    void onOptionChanged(OptionControl sender, String name, Object value);
    void onCommit(OptionControl sender, Bundle Options);
    void onCancel(OptionControl sender);
}
