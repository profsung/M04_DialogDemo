package edu.uco.hsung.m04_dialogdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class CustomLayoutDialogFragment extends DialogFragment {
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
	public interface CustomLayoutListener {
        void onCustomLayoutDialogPositiveClick(String username, String password, DialogFragment dialog);
        void onCustomLayoutDialogNegativeClick(DialogFragment dialog);
	}
	
	// Use this instance of the interface to deliver action events
	CustomLayoutListener listener;
	
	// to instantiate the NoticeDialogListener
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (CustomLayoutListener) getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement CustomLayoutListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_signin, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
        // Add action buttons
               .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   EditText user = (EditText) view.findViewById(R.id.username);
                	   EditText passwd = (EditText) view.findViewById(R.id.password);
                	   listener.onCustomLayoutDialogPositiveClick(
                			   user.getText().toString(),
                			   passwd.getText().toString(),
                			   CustomLayoutDialogFragment.this);
                	   
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       listener.onCustomLayoutDialogNegativeClick(CustomLayoutDialogFragment.this);
                       CustomLayoutDialogFragment.this.getDialog().cancel();
                   }
               });      
        return builder.create();
    }
}
