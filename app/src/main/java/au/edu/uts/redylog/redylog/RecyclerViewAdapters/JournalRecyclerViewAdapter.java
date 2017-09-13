package au.edu.uts.redylog.redylog.RecyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Journal;
import au.edu.uts.redylog.redylog.R;

public class JournalRecyclerViewAdapter extends RecyclerView.Adapter<JournalRecyclerViewAdapter.ViewHolder> {

    private final List<Journal> mValues;
    private final OnFragmentInteractionListener mListener;

    public JournalRecyclerViewAdapter(OnFragmentInteractionListener listener) {
        mValues = JournalManager.getInstance().get_journals();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitle.setText(mValues.get(position).get_title());
        holder.mStartDate.setText(HelperMethods.formatDate(mValues.get(position).get_startDate()));
        holder.mStatus.setText(mValues.get(position).get_status().toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentMessage(FragmentEnum.JournalFragment, holder.mItem);
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
        public final TextView mTitle;
        public final TextView mStartDate;
        public final TextView mStatus;
        public Journal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.item_journal_title);
            mStartDate = view.findViewById(R.id.item_journal_start_date);
            mStatus = view.findViewById(R.id.item_journal_status);
        }
    }
}
