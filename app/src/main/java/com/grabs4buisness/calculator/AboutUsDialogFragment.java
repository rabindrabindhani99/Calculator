package com.grabs4buisness.calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AboutUsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About Us")
                .setMessage("Created by Rabindra Bindhani\nMaster of Computer Application (MCA)\nIGIT, Sarang\nLinkedIn: https://www.linkedin.com/in/rabindrabindhani99/")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dismiss the dialog when OK is clicked
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Write to us", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open Gmail with a new email to the developer
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:grabs4buisness+calculator@gmail.com"));
                        startActivity(emailIntent);
                    }
                });
        return builder.create();
    }
}
