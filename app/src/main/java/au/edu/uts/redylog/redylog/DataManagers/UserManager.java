package au.edu.uts.redylog.redylog.DataManagers;

import android.content.Context;

import java.util.List;

import au.edu.uts.redylog.redylog.Helpers.DatabaseHelper;
import au.edu.uts.redylog.redylog.Models.User;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class UserManager {

    private static UserManager ourInstance;
    public static UserManager getInstance() {
        return ourInstance;
    }

    List<User> _users;
    DatabaseHelper _db;
    private static Context _context;

    public static void init(Context context){
        _context = context;
         ourInstance = new UserManager();
    }

    public UserManager() {
        _db = new DatabaseHelper(_context);
        _users = _db.getAllUsers();
    }

    public void addUser(User user) {
        _db.addUser(user);
        _users.add(user);
    }

    public boolean login(String password) {
        for (User user : _users) {
            if (user.get_password().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean userExists() {
        return _users.size() > 0;
    }

}
