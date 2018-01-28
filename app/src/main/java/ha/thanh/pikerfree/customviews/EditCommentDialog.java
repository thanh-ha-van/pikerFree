package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import ha.thanh.pikerfree.R;

public class EditCommentDialog {

    private Dialog alertDialog;
    private optionInterface interFace;


    public EditCommentDialog(Activity activity, optionInterface yesNoInterFace) {
        alertDialog = new Dialog(activity, R.style.PauseDialog);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_edit_comment_dialog);
        this.interFace = yesNoInterFace;
    }

    public void showAlertDialog(final String userID) {

        final CustomEditText content = alertDialog.findViewById(R.id.et_content);
        content.setText(userID);
        CustomTextView btnOK = alertDialog.findViewById(R.id.btn_ok_dialog);
        CustomTextView btnCancel = alertDialog.findViewById(R.id.btn_cancel_dialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                interFace.onSaveEdit(content.getText().toString());
            }
        });

        alertDialog.show();
    }

    public interface optionInterface {

        void onSaveEdit(String id);

    }
}
