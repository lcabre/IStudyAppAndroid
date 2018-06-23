package ar.com.hipnos.leo.istudy.api;

import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class ErrorService {

    /**
     * @param errorField
     * @param error_message
     */
    public static void showError(final TextView errorField, String error_message){

        final ViewGroup  parent = (ViewGroup) errorField.getParent();

        TransitionSet set = new TransitionSet()
                .addTransition(new Slide(Gravity.RIGHT))
                .addTransition(new Fade())
                .setDuration(500)
                .setInterpolator(new LinearOutSlowInInterpolator());

        TransitionManager.beginDelayedTransition(parent, set);

        errorField.setText(error_message);
        errorField.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                TransitionSet set = new TransitionSet()
                        .addTransition(new Slide(Gravity.RIGHT))
                        .addTransition(new Fade())
                        .setDuration(500)
                        .setInterpolator(new FastOutSlowInInterpolator());

                TransitionManager.beginDelayedTransition(parent, set);

                errorField.setVisibility(View.INVISIBLE);
            }
        },3000);

    }
}
