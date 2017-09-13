package au.edu.uts.redylog.redylog.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DialogFragments.CreateEntryDialogFragment;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;
import au.edu.uts.redylog.redylog.RecyclerViewAdapters.EntryRecyclerViewAdapter;

public class EntryFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private TextView _tvError;
    private SearchView _svEntries;
    private Journal _currentJournal;
    private EntryRecyclerViewAdapter _adapter;

    public EntryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

        _tvError = view.findViewById(R.id.tv_entry_error);
        _svEntries = view.findViewById(R.id.sv_entries);
        _svEntries.setOnQueryTextListener(this);
        _currentJournal = (Journal) getArguments().getSerializable(getString(R.string.bundle_journal_key));

        if (EntryManager.getInstance().get_entries(_currentJournal).size() > 0) {
            _tvError.setVisibility(View.INVISIBLE);
        } else {
            _tvError.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_entries);
        _adapter = new EntryRecyclerViewAdapter(mListener, _currentJournal);
        recyclerView.setAdapter(_adapter);

        return view;
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
        switch(id){
            case R.id.action_create_entry:
                displayAddEntryDialog();
                break;
            case R.id.action_search_entries:
                displaySearchEntryDialog();
                break;
            case R.id.action_close_journal:
                displayCloseJournal();
                break;
            case R.id.action_view_journal:
                displayViewJournalDetails();
                break;
            case R.id.action_delete_journal:
                displayDeleteJournal();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayAddEntryDialog() {
        CreateEntryDialogFragment dialogFragment = new CreateEntryDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.bundle_journal_key), _currentJournal);
        dialogFragment.setArguments(args);

        dialogFragment.show(getFragmentManager(), "dialog");
    }

    private void displaySearchEntryDialog(){

    }

    private void displayViewJournalDetails(){

    }

    private void displayCloseJournal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_close_journal_prompt)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        JournalManager.getInstance().closeJournal(_currentJournal);
                        mListener.displayFragment(FragmentEnum.JournalFragment, null);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    private void displayDeleteJournal(){

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        _adapter.updateEntries(newText);
        return false;
    }
}
