package au.edu.uts.redylog.redylog.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.Fragments.EntryListFragment;
import au.edu.uts.redylog.redylog.Fragments.ViewEntryFragment;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;

/**
 * Created by neola on 29-Aug-17.
 */

public class EditEntryDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private Entry _currentEntry;
    private ViewEntryFragment prevFragment;

    public EditEntryDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _currentEntry = (Entry) getArguments().getSerializable(getString(R.string.bundle_entry_key));

        return new AlertDialog.Builder(getActivity())
                .setView(inflateView())
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private View inflateView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_entry_dialog, null);

        EditText etTitle = view.findViewById(R.id.et_edit_entry_title);
        EditText etContent = view.findViewById(R.id.et_edit_entry_content);

        etTitle.setText(_currentEntry.get_title());
        etContent.setText(_currentEntry.get_contents());

        return view;
    }

    //Todo get the journal id from previous fragment
    public void onClick(DialogInterface dialog, int whichButton) {
        EditText etTitle = ((AlertDialog) dialog).findViewById(R.id.et_edit_entry_title);
        EditText etContent = ((AlertDialog) dialog).findViewById(R.id.et_edit_entry_content);

        History history = new History(
                _currentEntry.get_entryId(),
                _currentEntry.get_title(),
                _currentEntry.get_contents(),
                new Date()
        );

        EntryManager.getInstance().addHistory(history);

        _currentEntry.set_title(etTitle.getText().toString());
        _currentEntry.set_contents(etContent.getText().toString());
        EntryManager.getInstance().updateEntry(_currentEntry);

        prevFragment = (ViewEntryFragment) getTargetFragment();

        prevFragment.get_history().add(history);
        prevFragment.updateData();
        Toast.makeText(prevFragment.getContext(), R.string.entry_edited_confirmed, Toast.LENGTH_SHORT).show();
    }
}

