package au.edu.uts.redylog.redylog.DialogFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import au.edu.uts.redylog.redylog.Fragments.EntryListFragment;
import au.edu.uts.redylog.redylog.Fragments.JournalListFragment;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.SearchFilter;
import au.edu.uts.redylog.redylog.Helpers.StatusEnum;
import au.edu.uts.redylog.redylog.R;

/**
 * Created by neola on 29-Aug-17.
 */

public class SearchDialogFragment extends DialogFragment implements DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnClickListener, Spinner.OnItemSelectedListener {

    EditText etStartDate;
    EditText etEndDate;
    EditText etPromptingDate;
    SearchFilter _searchFilter;
    Spinner spStatus;
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
                .setNegativeButton(R.string.clear_filters, this)
                .create();
    }

    private View setupView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_dialog, null);

        spStatus = view.findViewById(R.id.search_dialog_status);
        spStatus.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, StatusEnum.values()));
        spStatus.setOnItemSelectedListener(this);

        etStartDate = view.findViewById(R.id.search_dialog_startdate);
        etStartDate.setShowSoftInputOnFocus(false);
        etStartDate.setOnClickListener(this);

        etEndDate = view.findViewById(R.id.search_dialog_enddate);
        etEndDate.setShowSoftInputOnFocus(false);
        etEndDate.setOnClickListener(this);

        if (_searchFilter.get_startDate() != null) { etStartDate.setText(HelperMethods.formatDateNoTime(_searchFilter.get_startDate())); }
        if (_searchFilter.get_endDate() != null) { etEndDate.setText(HelperMethods.formatDateNoTime(_searchFilter.get_endDate())); }
        if (_searchFilter.get_status() != null) { spStatus.setSelection(_searchFilter.get_status().ordinal()); }

        return view;
    }

    //Todo get the journal id from previous fragment
    public void onClick(DialogInterface dialog, int whichButton) {
        if (whichButton == -2) {
            _searchFilter.set_query(null);
            _searchFilter.set_startDate(null);
            _searchFilter.set_endDate(null);
            _searchFilter.set_status(null);
        }

        prevFragment = getTargetFragment();

        if (prevFragment instanceof EntryListFragment) {
            EntryListFragment entryListFragment = (EntryListFragment) prevFragment;
            entryListFragment.updateList();
        } else if (prevFragment instanceof JournalListFragment) {
            JournalListFragment journalListFragment = (JournalListFragment) prevFragment;
            journalListFragment.updateList();
        }
    }

    @Override
    public void onClick(View view) {

        Calendar calendar = Calendar.getInstance();

        if (view == etStartDate) {
            if (_searchFilter.get_startDate() != null) { calendar.setTime(_searchFilter.get_startDate()); }
        } else if (view == etEndDate) {
            if (_searchFilter.get_endDate() != null) { calendar.setTime(_searchFilter.get_endDate()); }
        }

        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(this);
        datePickerDialog.updateDate(year, month, day);

        etPromptingDate = (EditText) view;
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (etPromptingDate == etStartDate) {
            _searchFilter.set_startDate(calendar.getTime());
            etStartDate.setText(HelperMethods.formatDateNoTime(calendar.getTime()));
        } else if (etPromptingDate == etEndDate) {
            _searchFilter.set_endDate(calendar.getTime());
            etEndDate.setText(HelperMethods.formatDateNoTime(calendar.getTime()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        StatusEnum status = (StatusEnum) adapterView.getItemAtPosition(i);
        _searchFilter.set_status(status);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        _searchFilter.set_status(StatusEnum.None);
    }
}

