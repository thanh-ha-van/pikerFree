package ha.thanh.pikerfree.fragments.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;


public class IntroFragment3 extends IntroFragment {

    @BindView(R.id.iv_character)
    View viewCharacter;

    @BindViews({R.id.iv_image_1, R.id.iv_image_2, R.id.iv_image_3, R.id.iv_image_4})
    View[] viewImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void animationViewPage() {

        viewImages[0].animate().setStartDelay(1200).alpha(1).setDuration(500);
        viewImages[1].animate().setStartDelay(1500).alpha(1).setDuration(500);
        viewImages[2].animate().setStartDelay(1200).alpha(1).setDuration(500);
        viewImages[3].animate().setStartDelay(1500).alpha(1).setDuration(500);
        viewCharacter.animate().alpha(1).setDuration(1000);
    }

    @Override
    public void disableAnimationViewPage() {
        if (viewCharacter != null) {
            viewCharacter.clearAnimation();
            viewCharacter.setAlpha(0);
            for (View viewImage : viewImages) {
                viewImage.clearAnimation();
                viewImage.setAlpha(0);
            }
        }
    }

}
