package com.uc.android.widget;

import android.os.Bundle;

public interface OptionControl {
    void setOptionControlListener(OptionControlListener optionControlListener);
    void setOption(String name, Object value);
    void commit();
    void cancel();
}
