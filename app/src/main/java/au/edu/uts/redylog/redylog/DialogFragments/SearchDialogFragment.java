package au.edu.uts.redylog.redylog.DialogFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.SearchFilter;
import au.edu.uts.redylog.redylog.MainActivity;
import au.edu.uts.redylog.redylog.R;

/**
 * Created by neola on 29-Aug-17.
 */

public class SearchDialogFragment extends DialogFragment implements DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    EditText etStartDate;
    EditText etEndDate;
    EditText etPromptingDate;
    SearchFilter _searchFilter;
    Fragment prevFragment;

    public SearchDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        _searchFilter = (SearchFilter) getArguments().getSerializable(getString(R.string.bundle_filter_key));

        return new AlertDialog.Builder(getActivity())
                .setView(setupView())
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private View setupView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_dialog, null);

        etStartDate = view.findViewById(R.id.search_dialog_startdate);
        etStartDate.setShowSoftInputOnFocus(false);
        etStartDate.setOnClickListener(this);

        etEndDate = view.findViewById(R.id.search_dialog_enddate);
        etEndDate.setShowSoftInputOnFocus(false);
        etEndDate.setOnClickListener(this);

        return view;
    }

    //Todo get the journal id from previous fragment
    public void onClick(DialogInterface dialog, int whichButton) {
        //prevFragment = (EntryListFragment) getTargetFragment();
        //prevFragment.updateList();
    }

    @Override
    public void onClick(View view) {

        Date date = new Date();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), SearchDialogFragment.this, date.getYear(), date.getMonth(), date.getDay()
        );
        datePickerDialog.setOnDateSetListener(this);

        etPromptingDate = (EditText) view;
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        if (etPromptingDate == etStartDate) {
            _searchFilter.set_startDate(calendar.getTime());
            etStartDate.setText(HelperMethods.formatDate(calendar.getTime()));
        } else if (etPromptingDate == etEndDate) {
            _searchFilter.set_endDate(calendar.getTime());
            etEndDate.setText(HelperMethods.formatDate(calendar.getTime()));
        }
    }
}

