package au.edu.uts.redylog.redylog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.R;
import au.edu.uts.redylog.redylog.RecyclerViewAdapters.HistoryRecyclerViewAdapter;

/**
 * Created by neola on 23-Sep-17.
 */

public class ViewHistoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView _tvDate;
    private TextView _tvContent;

    private HistoryRecyclerViewAdapter _adapter;
    private History _currentHistory;

    public ViewHistoryFragment() {

    }

    public Entry getEntry() {
        return EntryManager.getInstance().getEntry(_currentHistory.get_entryId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_history, container, false);

        _currentHistory = (History) getArguments().getSerializable(getString(R.string.bundle_history_key));

        setupReferences(view);
        mListener.updateTitle(_currentHistory.get_title());

        return view;
    }

    private void setupReferences(View view){
        _tvContent = view.findViewById(R.id.view_history_content);
        _tvContent.setText(_currentHistory.get_content());
        _tvDate = view.findViewById(R.id.view_history_date);
        _tvDate.setText(HelperMethods.formatDate(_currentHistory.get_changedDate()));
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
}
