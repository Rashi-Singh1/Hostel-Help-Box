package com.example.hostelhelpbox;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private Context mcontext = null;

    public Context getMcontext() {
        return mcontext;
    }

    public void setMcontext(Context mcontext) {
        this.mcontext = mcontext;
    }
}
