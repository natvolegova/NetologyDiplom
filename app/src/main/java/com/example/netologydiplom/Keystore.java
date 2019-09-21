package com.example.netologydiplom;

import android.content.Context;

public interface Keystore {
    boolean hasPin(Context context);
    boolean checkPin(Context context, String pin);
    void saveNew(Context context, String pin);
}
