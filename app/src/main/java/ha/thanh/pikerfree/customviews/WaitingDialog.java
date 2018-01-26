package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import ha.thanh.pikerfree.R;

/**
 * Created by HaVan on 8/30/2017.
 */

public class WaitingDialog {

    private Dialog alertDialog;

    public WaitingDialog(final Activity activity) {

        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setContentView(R.layout.view_waiting_dialog);
        final CustomTextView title = (CustomTextView) alertDialog.findViewById(R.id.tv_title);
        alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    if (title.getText().toString().equalsIgnoreCase("Loading")) {
                        title.setText("Tab outside to cancel process");
                        return true;
                    }
                }
                return true;
            }
        });
    }

    public void showDialog() {
        alertDialog.show();
    }

    public void hideDialog() {
        alertDialog.dismiss();
    }

}
