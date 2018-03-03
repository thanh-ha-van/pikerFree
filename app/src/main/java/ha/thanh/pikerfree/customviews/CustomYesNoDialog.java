package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import ha.thanh.pikerfree.R;

/**
 * Created by HaVan on 8/31/2017.
 */

public class CustomYesNoDialog {

    private Dialog alertDialog;
    private YesNoInterFace interFace;

    public CustomYesNoDialog(Activity activity, YesNoInterFace yesNoInterFace) {
        alertDialog = new Dialog(activity, R.style.PauseDialog);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setContentView(R.layout.view_yes_no_dialog);
        this.interFace = yesNoInterFace;
    }

    public void setListener(YesNoInterFace yesNoInterFace) {
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(String title, String message) {

        CustomTextView tvContent = alertDialog.findViewById(R.id.tv_content);
        CustomTextView tvTitle = alertDialog.findViewById(R.id.tv_title);
        CustomTextView tvCancel = alertDialog.findViewById(R.id.btn_cancel_dialog);
        CustomTextView tvOK = alertDialog.findViewById(R.id.btn_ok_dialog);
        tvTitle.setText(title);
        tvContent.setText(message);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interFace.onNoClicked();
                alertDialog.dismiss();
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interFace.onYesClicked();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public interface YesNoInterFace {

        void onYesClicked();

        void onNoClicked();
    }
}
