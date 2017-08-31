package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;

/**
 * Created by HaVan on 8/31/2017.
 */

public class CustomYesNoDialog {

    private Dialog alertDialog;
    private YesNoInterFace interFace;

    @BindView(R.id.tv_title)
    private TextView tvTitle;
    @BindView(R.id.tv_content)
    private TextView tvContent;


    interface YesNoInterFace {
        void onYesClicked();

        void onNoClicked();
    }


    public CustomYesNoDialog(Activity activity, YesNoInterFace yesNoInterFace) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_yes_no_dialog);
        this.interFace = yesNoInterFace;
        ButterKnife.bind(activity);
    }

    public void showAlertDialog(String title, String message) {

        tvTitle.setText(title);
        tvContent.setText(message);
        alertDialog.show();

    }

    @OnClick(R.id.btn_ok_dialog)
    public void onOkClicked(){
        alertDialog.dismiss();
        interFace.onYesClicked();
    }
    @OnClick(R.id.btn_cancel_dialog)
    public void onCancelClicked(){
        alertDialog.dismiss();
        interFace.onNoClicked();
    }
}
