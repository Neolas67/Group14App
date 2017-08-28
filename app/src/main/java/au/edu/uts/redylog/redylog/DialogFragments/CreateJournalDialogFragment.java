package au.edu.uts.redylog.redylog.DialogFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.MainActivity;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.Models.User;
import au.edu.uts.redylog.redylog.R;

public class CreateJournalDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public CreateJournalDialogFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setView(getActivity().getLayoutInflater().inflate(R.layout.fragment_create_journal_dialog, null))
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    public void onClick(DialogInterface dialog, int whichButton) {
        EditText etTitle = ((AlertDialog) dialog).findViewById(R.id.et_create_journal_title);
        Journal journal = new Journal(etTitle.getText().toString());

        JournalManager.getInstance().addJournal(journal);
    }

}
