package au.edu.uts.redylog.redylog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DialogFragments.CreateJournalDialogFragment;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;
import au.edu.uts.redylog.redylog.RecyclerViewAdapters.JournalRecyclerViewAdapter;

public class JournalFragment extends Fragment implements SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;
    private TextView _tvError;
    private SearchView _svJournals;
    private RecyclerView mRecyclerView;
    private List<Journal> journals = new ArrayList<>();
    private JournalRecyclerViewAdapter _adapter;

    public JournalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_list, container, false);

        _tvError = view.findViewById(R.id.tv_journal_error);
        _svJournals = view.findViewById(R.id.sv_journals);
        _svJournals.setOnQueryTextListener(this);
        mRecyclerView = view.findViewById(R.id.rv_journals);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        _adapter = new JournalRecyclerViewAdapter(mListener,journals);
        mRecyclerView.setAdapter(_adapter);
        updateList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        _adapter.notifyDataSetChanged();
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

    public void updateList(){
        journals.clear();
        if (JournalManager.getInstance().get_journals().size() > 0) {
            _tvError.setVisibility(View.INVISIBLE);
        } else {
            _tvError.setVisibility(View.VISIBLE);
        }
        journals.addAll(JournalManager.getInstance().get_journals());
        _adapter.notifyDataSetChanged();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.journal_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_journal) {
            displayAddJournalDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayAddJournalDialog() {
        CreateJournalDialogFragment dialogFragment = new CreateJournalDialogFragment();
        dialogFragment.setTargetFragment(this,1);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        _adapter.updateJournals(newText);
        return false;
    }
}
