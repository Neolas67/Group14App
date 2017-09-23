package au.edu.uts.redylog.redylog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Fragments.EntryListFragment;
import au.edu.uts.redylog.redylog.Fragments.JournalListFragment;
import au.edu.uts.redylog.redylog.Fragments.LoginFragment;
import au.edu.uts.redylog.redylog.Fragments.RegisterFragment;
import au.edu.uts.redylog.redylog.Fragments.ViewEntryFragment;
import au.edu.uts.redylog.redylog.Fragments.ViewHistoryFragment;
import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.History;
import au.edu.uts.redylog.redylog.Models.Journal;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private FragmentManager _fragmentManager;
    private Fragment _activeFragment;
    private Toolbar _toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        _fragmentManager = getSupportFragmentManager();
        UserManager.init(getApplicationContext());
        JournalManager.init(getApplicationContext());
        EntryManager.init(getApplicationContext());

        if (UserManager.getInstance().userExists()) {
            displayFragment(FragmentEnum.LoginFragment, null);
        } else {
            displayFragment(FragmentEnum.RegisterFragment, null);
        }
    }

    private void setupToolbar() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            this.finishAffinity();
            return true;
        } else if (id == R.id.action_clear) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseHelper.clearData();
            this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayFragment(FragmentEnum fragmentEnum, Object data) {
        FragmentTransaction fragmentTransaction = _fragmentManager.beginTransaction();
        if (_activeFragment != null) {fragmentTransaction.remove(_activeFragment);}
        Bundle args = new Bundle();

        switch (fragmentEnum) {
            case RegisterFragment:
                _activeFragment = new RegisterFragment();
                break;
            case LoginFragment:
                _activeFragment = new LoginFragment();
                break;
            case JournalListFragment:
                _activeFragment = new JournalListFragment();
                break;
            case EntryListFragment:
                _activeFragment = new EntryListFragment();
                Journal journal = (Journal)data;
                args.putSerializable(getString(R.string.bundle_journal_key), journal);
                break;
            case ViewEntryFragment:
                _activeFragment = new ViewEntryFragment();
                Entry entry = (Entry)data;
                args.putSerializable(getString(R.string.bundle_entry_key), entry);
                break;
            case ViewHistoryFragment:
                _activeFragment = new ViewHistoryFragment();
                History history = (History)data;
                args.putSerializable(getString(R.string.bundle_history_key),history);
        }

        _activeFragment.setArguments(args);
        fragmentTransaction.add(R.id.ll_fragment_holder, _activeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateTitle(String title) {
        _toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {

        if (_activeFragment instanceof EntryListFragment) {
            displayFragment(FragmentEnum.JournalListFragment, null);
        } else if (_activeFragment instanceof ViewEntryFragment) {
            Journal journal = ((ViewEntryFragment)_activeFragment).getJournal();
            displayFragment(FragmentEnum.EntryListFragment, journal);
        } else if (_activeFragment instanceof ViewHistoryFragment){
            Entry entry = ((ViewHistoryFragment)_activeFragment).getEntry();
            displayFragment(FragmentEnum.ViewEntryFragment,entry);
        }else {
            this.finish();
        }

    }
}
