package au.edu.uts.redylog.redylog.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.DialogFragments.EditEntryDialogFragment;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;

public class ViewEntryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView _tvDate;
    private TextView _tvContent;
    private TextView _tvStatus;
    private Entry _currentEntry;

    public ViewEntryFragment() {

    }

    public Journal getJournal() {
        return JournalManager.getInstance().get_journal(_currentEntry.get_journalId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_entry, container, false);

        _currentEntry = (Entry) getArguments().getSerializable(getString(R.string.bundle_entry_key));

        setupReferences(view);
        updateData();

        return view;
    }

    private void setupReferences(View view){
        _tvContent = view.findViewById(R.id.view_entry_content);
        _tvDate = view.findViewById(R.id.view_entry_date);
        _tvStatus = view.findViewById(R.id.view_entry_status);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.entry_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_entry) {
            displayEditEntryDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayEditEntryDialog() {
        EditEntryDialogFragment dialogFragment = new EditEntryDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.bundle_entry_key), _currentEntry);
        dialogFragment.setArguments(args);
        dialogFragment.setTargetFragment(this,1);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void updateData() {

        if (_currentEntry.get_status() == StatusEnum.Open) {
            _tvStatus.setVisibility(View.GONE);
        } else {
            _tvStatus.setText(_currentEntry.get_status().toString());
        }

        _tvContent.setText(_currentEntry.get_contents());
        _tvDate.setText(HelperMethods.formatDate(_currentEntry.get_createdDate()));
    }
}
