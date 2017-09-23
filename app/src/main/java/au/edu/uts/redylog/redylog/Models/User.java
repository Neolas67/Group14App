package au.edu.uts.redylog.redylog.Models;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import au.edu.uts.redylog.redylog.Helpers.HelperMethods;

/**
 * Created by Hayden on 23-Aug-17.
 */

public class User {

    long _userId;
    String _firstName;
    String _surname;
    String _email;
    byte[] _password;

    public User(long _userId, String _firstName, String _surname, String _email, byte[] _password) {
        this._userId = _userId;
        this._firstName = _firstName;
        this._surname = _surname;
        this._email = _email;
        this._password = _password;
    }

    public User(String _firstName, String _surname, String _email, String _password) {
        this._firstName = _firstName;
        this._surname = _surname;
        this._email = _email;
        this._password = HelperMethods.encryptMsg(_password);
    }

    public User(String _firstName, String _surname, String _email, byte[] _password) {
        this._firstName = _firstName;
        this._surname = _surname;
        this._email = _email;
        this._password = _password;
    }

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public byte[] get_password() {
        return _password;
    }

    public void set_password(byte[] _password) {
        this._password = _password;
    }
}
