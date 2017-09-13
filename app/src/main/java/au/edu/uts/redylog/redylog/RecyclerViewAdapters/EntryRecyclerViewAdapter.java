package au.edu.uts.redylog.redylog.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;

public class EntryRecyclerViewAdapter extends RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder> {

    private final List<Entry> mValues;
    private final OnFragmentInteractionListener mListener;
    private Journal _currentJournal;

    public EntryRecyclerViewAdapter(OnFragmentInteractionListener listener, Journal journal) {
        _currentJournal = journal;
        mValues = EntryManager.getInstance().get_entries(journal);
        mListener = listener;
    }

    public void updateEntries(String query) {
        mValues.clear();
        mValues.addAll(EntryManager.getInstance().get_entries(_currentJournal, query));
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Long.toString(mValues.get(position).get_entryId()));
        holder.mDateView.setText(HelperMethods.formatDate(mValues.get(position).get_createdDate()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.displayFragment(FragmentEnum.EntryFragment, holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mDateView;
        public Entry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.entry_title);
            mDateView = view.findViewById(R.id.entry_date);
        }
    }


}

