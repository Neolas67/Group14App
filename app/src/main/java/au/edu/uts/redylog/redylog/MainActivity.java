package au.edu.uts.redylog.redylog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import au.edu.uts.redylog.redylog.DataManagers.EntryManager;
import au.edu.uts.redylog.redylog.DataManagers.JournalManager;
import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Fragments.EntryListFragment;
import au.edu.uts.redylog.redylog.Fragments.JournalListFragment;
import au.edu.uts.redylog.redylog.Fragments.LoginFragment;
import au.edu.uts.redylog.redylog.Fragments.RegisterFragment;
import au.edu.uts.redylog.redylog.Fragments.ViewEntryFragment;
import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.Entry;
import au.edu.uts.redylog.redylog.Models.Journal;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private FragmentManager _fragmentManager;
    private Fragment _activeFragment;
    //test
    private Toolbar _toolbar;

    private int _currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

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
                _currentFragment = 0;
                break;
            case LoginFragment:
                _activeFragment = new LoginFragment();
                _currentFragment = 1;
                break;
            case JournalListFragment:
                _activeFragment = new JournalListFragment();
                _toolbar.setTitle(R.string.title_journals);
                _currentFragment = 2;
                break;
            case EntryListFragment:
                _activeFragment = new EntryListFragment();
                Journal journal = (Journal)data;
                _toolbar.setTitle(journal.get_title());
                args.putSerializable(getString(R.string.bundle_journal_key), journal);
                _currentFragment = 3;
                break;
            case ViewEntryFragment:
                _activeFragment = new ViewEntryFragment();
                Entry entry = (Entry)data;
                _toolbar.setTitle(entry.get_title());
                args.putSerializable(getString(R.string.bundle_entry_key), entry);
                _currentFragment = 4;
        }

        _activeFragment.setArguments(args);
        fragmentTransaction.add(R.id.ll_fragment_holder, _activeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (_currentFragment == 3) {
            displayFragment(FragmentEnum.JournalListFragment, null);
        } else if (_currentFragment == 4) {
            displayFragment(FragmentEnum.EntryListFragment, null);
        } else {
            super.onBackPressed();
        }

    }
}
