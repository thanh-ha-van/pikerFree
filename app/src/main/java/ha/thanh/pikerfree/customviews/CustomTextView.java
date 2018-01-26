package ha.thanh.pikerfree.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import ha.thanh.pikerfree.R;


public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyAttributes(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {

        Typeface fontDefault = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        this.setTypeface(fontDefault);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTextView_fontAssetName:
                    try {
                        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/" + a.getString(attr));
                        if (font != null) {
                            this.setTypeface(font);
                        }
                    } catch (RuntimeException e) {
                        e.getMessage();
                    }
            }
        }
        a.recycle();
    }

}
