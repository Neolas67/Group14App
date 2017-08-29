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
import au.edu.uts.redylog.redylog.Fragments.EntryFragment;
import au.edu.uts.redylog.redylog.Fragments.JournalFragment;
import au.edu.uts.redylog.redylog.Fragments.LoginFragment;
import au.edu.uts.redylog.redylog.Fragments.RegisterFragment;
import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private FragmentManager _fragmentManager;
    private Fragment _activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _fragmentManager = getSupportFragmentManager();
        UserManager.init(getApplicationContext());
        JournalManager.init(getApplicationContext());
        EntryManager.init(getApplicationContext());

        if (UserManager.getInstance().userExists()) {
            displayFragment(FragmentEnum.LoginFragment);
        } else {
            displayFragment(FragmentEnum.RegisterFragment);
        }
    }

    private void displayFragment(FragmentEnum fragmentEnum) {

        FragmentTransaction fragmentTransaction = _fragmentManager.beginTransaction();
        if (_activeFragment != null) {fragmentTransaction.remove(_activeFragment);}

        switch (fragmentEnum) {
            case RegisterFragment:
                _activeFragment = new RegisterFragment();
                break;
            case LoginFragment:
                _activeFragment = new LoginFragment();
                break;
            case JournalFragment:
                _activeFragment = new JournalFragment();
                break;
            case EntryFragment:
                _activeFragment = new EntryFragment();
                break;
        }

        fragmentTransaction.add(R.id.ll_fragment_holder, _activeFragment);
        fragmentTransaction.commit();
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
    public void onFragmentMessage(FragmentEnum fragmentEnum, Object data) {
        switch(fragmentEnum) {
            case RegisterFragment:
                displayFragment(FragmentEnum.JournalFragment);
                break;
            case LoginFragment:
                displayFragment(FragmentEnum.JournalFragment);
                break;
            case JournalFragment:
                break;
        }
    }
}
