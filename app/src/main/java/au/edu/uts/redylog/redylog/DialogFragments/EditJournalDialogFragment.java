package au.edu.uts.redylog.redylog.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Fragments.EntryListFragment;
import au.edu.uts.redylog.redylog.Fragments.ViewEntryFragment;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;

/**
 * Created by neola on 29-Aug-17.
 */

public class EditJournalDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private Journal _currentJournal;
    private EntryListFragment prevFragment;

    public EditJournalDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _currentJournal = (Journal) getArguments().getSerializable(getString(R.string.bundle_journal_key));

        return new AlertDialog.Builder(getActivity())
                .setView(inflateView())
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private View inflateView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_journal_dialog, null);

        EditText etTitle = view.findViewById(R.id.et_edit_journal_title);
        EditText etContent = view.findViewById(R.id.et_edit_journal_content);

        etTitle.setText(_currentJournal.get_title());
        etContent.setText(_currentJournal.get_description());

        return view;
    }

    //Todo get the journal id from previous fragment
    public void onClick(DialogInterface dialog, int whichButton) {
        EditText etTitle = ((AlertDialog) dialog).findViewById(R.id.et_edit_journal_title);
        EditText etContent = ((AlertDialog) dialog).findViewById(R.id.et_edit_journal_content);

        if (TextUtils.isEmpty(etTitle.getText().toString())) {
            Toast.makeText(getActivity(), "Journal title cannot be empty - failed to edit journal.", Toast.LENGTH_SHORT).show();
        } else {
            _currentJournal.set_title(etTitle.getText().toString());
            _currentJournal.set_description(etContent.getText().toString());
            JournalManager.getInstance().updateJournal(_currentJournal);

            prevFragment = (EntryListFragment) getTargetFragment();
            prevFragment.updateList();

            Toast.makeText(prevFragment.getContext(), R.string.journal_edited_confirmed, Toast.LENGTH_SHORT).show();
        }
    }
}

