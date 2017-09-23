package au.edu.uts.redylog.redylog.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.DialogFragments.EditEntryDialogFragment;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;
import au.edu.uts.redylog.redylog.RecyclerViewAdapters.HistoryRecyclerViewAdapter;

public class ViewEntryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView _tvDate;
    private TextView _tvContent;
    private TextView _tvStatus;
    private RecyclerView mRecyclerView;

    private List<History> _history = new ArrayList<>();
    private HistoryRecyclerViewAdapter _adapter;
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
        setupRecyclerView(view);
        updateData();

        mListener.updateTitle(_currentEntry.get_title());

        return view;
    }

    private void setupReferences(View view){
        _tvContent = view.findViewById(R.id.view_history_content);
        _tvDate = view.findViewById(R.id.view_entry_date);
        _tvStatus = view.findViewById(R.id.view_entry_status);
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_versions);
        _adapter = new HistoryRecyclerViewAdapter(mListener, _history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(_adapter);

        _history.addAll(EntryManager.getInstance().get_history(_currentEntry));
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

        switch (_currentEntry.get_status()) {
            case Open:
                menu.findItem(R.id.action_unhide_entry).setVisible(false);
                break;
            case Hidden:
                menu.findItem(R.id.action_hide_entry).setVisible(false);
                break;
            case Deleted:
                menu.findItem(R.id.action_hide_entry).setVisible(false);
                menu.findItem(R.id.action_unhide_entry).setVisible(false);
                menu.findItem(R.id.action_delete_entry).setVisible(false);
                menu.findItem(R.id.action_edit_entry).setVisible(false);
                break;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit_entry:
                displayEditEntryDialog();
                break;
            case R.id.action_hide_entry:
                displayHideEntry();
                break;
            case R.id.action_unhide_entry:
                displayUnhideEntry();
                break;
            case R.id.action_delete_entry:
                displayDeleteEntryDialog();
                break;
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

    private void displayHideEntry(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_hide_entry_prompt)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EntryManager.getInstance().hideEntry(_currentEntry);
                        mListener.displayFragment(FragmentEnum.EntryListFragment, getJournal());
                        Toast.makeText(getContext(), R.string.entry_hidden_confirmed, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    private void displayUnhideEntry() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_unhide_entry_prompt)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EntryManager.getInstance().unhideEntry(_currentEntry);
                        mListener.displayFragment(FragmentEnum.EntryListFragment, getJournal());
                        Toast.makeText(getContext(), R.string.entry_unhidden_confirmed, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    private void displayDeleteEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_entry_prompt)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _currentEntry.set_status(StatusEnum.Deleted);
                        EntryManager.getInstance().updateEntry(_currentEntry);
                        Journal journal = JournalManager.getInstance().get_journal(_currentEntry.get_journalId());
                        mListener.displayFragment(FragmentEnum.EntryListFragment, journal);
                        Toast.makeText(getContext(), R.string.entry_deleted_confirmed, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    public List<History> get_history() {
        return _history;
    }

    public void updateData() {
        if (_currentEntry.get_status() == StatusEnum.Open) {
            _tvStatus.setVisibility(View.GONE);
        } else {
            _tvStatus.setText(_currentEntry.get_status().toString());
        }
        _history.clear();
        _history.addAll(EntryManager.getInstance().get_history(_currentEntry));
        _adapter.notifyDataSetChanged();
        _tvContent.setText(_currentEntry.get_contents());
        _tvDate.setText(HelperMethods.formatDate(_currentEntry.get_createdDate()));
        mListener.updateTitle(_currentEntry.get_title());
    }
}
