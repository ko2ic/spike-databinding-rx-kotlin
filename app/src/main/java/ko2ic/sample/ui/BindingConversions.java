package ko2ic.sample.ui;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.View;

import io.reactivex.functions.Action;

@BindingMethods({
        @BindingMethod(
                type = NavigationView.class,
                attribute = "onNavigationItemSelected",
                method = "setNavigationItemSelectedListener"
        ),
})
public class BindingConversions {

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.run();
                    } catch (Exception e) {
                        throw new IllegalArgumentException("例外が出ない想定");
                    }
                }
            };
        } else {
            return null;
        }
    }

    @BindingAdapter("android:visibility")
    public static void bindVisibility(@NonNull View view, @Nullable Boolean visible) {
        int visibility = (visible != null && visible) ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}


