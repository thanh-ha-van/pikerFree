package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import ha.thanh.pikerfree.R;

public class UserInforDialog {

    private Dialog alertDialog;
    private optionInterface interFace;


    public UserInforDialog(Activity activity, optionInterface yesNoInterFace) {
        alertDialog = new Dialog(activity, R.style.PauseDialog);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_multiple_dialog);
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(String title, final String userID) {

        CustomTextView tvTitle = alertDialog.findViewById(R.id.tv_title);
        CustomTextView btnViewProfile = alertDialog.findViewById(R.id.btn_view_profile);
        CustomTextView btnSendMess = alertDialog.findViewById(R.id.btn_send_mes);
        CustomTextView btnChoose = alertDialog.findViewById(R.id.btn_choose);
        tvTitle.setText(title);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onViewProfile(userID);
            }
        });


        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onSendMess(userID);
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onChoose(userID);
            }
        });
        alertDialog.show();
    }

    public interface optionInterface {

        void onViewProfile(String id);

        void onSendMess(String id);

        void onChoose(String id);
    }
}
