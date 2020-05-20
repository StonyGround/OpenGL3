package com.leer.lib.widget.dialog;

import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {

    public void setDismissListener(DialogFragmentDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    //dismiss监听
    private DialogFragmentDismissListener dismissListener;

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    public interface DialogFragmentDismissListener {
        void onDismiss(DialogInterface dialog);
    }

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }
}
