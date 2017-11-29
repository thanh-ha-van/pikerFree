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

public class UserInforDialog {

    private Dialog alertDialog;
    private YesNoInterFace interFace;


    public interface YesNoInterFace {

        void onOption1Clicked();

        void onOption2Clicked();

        void onOption3Clicked();
    }

    public UserInforDialog(Activity activity, YesNoInterFace yesNoInterFace) {
        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setContentView(R.layout.view_multiple_dialog);
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(String title, String message) {

        CustomTextView tvTitle = (CustomTextView) alertDialog.findViewById(R.id.tv_title);
        CustomTextView btnViewProfile = (CustomTextView) alertDialog.findViewById(R.id.btn_view_profile);
        CustomTextView btnSendMess = (CustomTextView) alertDialog.findViewById(R.id.btn_send_mes);
        CustomTextView btnChoose = (CustomTextView) alertDialog.findViewById(R.id.btn_choose);
        tvTitle.setText(title);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onOption1Clicked();
            }
        });


        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onOption2Clicked();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onOption3Clicked();
            }
        });
        alertDialog.show();
    }
}
