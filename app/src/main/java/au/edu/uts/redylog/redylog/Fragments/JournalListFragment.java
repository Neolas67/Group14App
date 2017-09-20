package au.edu.uts.redylog.redylog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DialogFragments.CreateJournalDialogFragment;
import au.edu.uts.redylog.redylog.DialogFragments.SearchDialogFragment;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Helpers.SearchFilter;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;
import au.edu.uts.redylog.redylog.RecyclerViewAdapters.JournalRecyclerViewAdapter;

public class JournalListFragment extends Fragment implements SearchView.OnQueryTextListener, FloatingActionButton.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private TextView _tvError;
    private SearchView _svJournals;
    private FloatingActionButton _fabJournal;
    private RecyclerView mRecyclerView;
    private ImageButton _ibFilter;

    private List<Journal> _journals = new ArrayList<>();
    private JournalRecyclerViewAdapter _adapter;
    private SearchFilter _searchFilter = new SearchFilter();

    public JournalListFragment() {

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

        setupReferences(view);
        setupModel();
        setupRecyclerView(view);

        return view;
    }

    private void setupReferences(View view) {
        _tvError = view.findViewById(R.id.tv_journal_error);
        _fabJournal = view.findViewById(R.id.fab_journal_list);
        _svJournals = view.findViewById(R.id.sv_journals);
        _ibFilter = view.findViewById(R.id.ib_journal_list_filter);

        _ibFilter.setOnClickListener(this);
        _fabJournal.setOnClickListener(this);
        _svJournals.setOnQueryTextListener(this);
    }

    private void setupModel() {
        if (JournalManager.getInstance().get_journals().size() > 0) {
            _tvError.setVisibility(View.GONE);
        } else {
            _tvError.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_journals);
        _journals.addAll(JournalManager.getInstance().get_journals());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        _adapter = new JournalRecyclerViewAdapter(mListener, _journals);
        mRecyclerView.setAdapter(_adapter);
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
        _journals.clear();
        _journals.addAll(JournalManager.getInstance().get_journals());
        setupModel();
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

    private void displayFilterDialog() {
        SearchDialogFragment dialogFragment = new SearchDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(getString(R.string.bundle_filter_key), _searchFilter);
        dialogFragment.setArguments(args);
        dialogFragment.setTargetFragment(this,1);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        _journals.clear();
        _journals.addAll(JournalManager.getInstance().get_journals(newText));
        _adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view == _fabJournal) {
            displayAddJournalDialog();
        } else {
            displayFilterDialog();
        }

    }
}
