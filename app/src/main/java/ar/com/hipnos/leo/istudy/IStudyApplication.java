package ar.com.hipnos.leo.istudy;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class IStudyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
