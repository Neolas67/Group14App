package au.edu.uts.redylog.redylog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.HelperMethods;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.Models.User;
import au.edu.uts.redylog.redylog.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText _etFirstName;
    private EditText _etSurname;
    private EditText _etEmail;
    private EditText _etPassword;
    private Button _btnRegister;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        _etFirstName = view.findViewById(R.id.et_register_firstname);
        _etSurname = view.findViewById(R.id.et_register_surname);
        _etEmail = view.findViewById(R.id.et_register_email);
        _etPassword = view.findViewById(R.id.et_register_password);
        _btnRegister = view.findViewById(R.id.btn_register_confirm);

        _btnRegister.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view == _btnRegister) {
            boolean hasError = false;

            if (!HelperMethods.validName(_etFirstName.getText().toString())) {
                _etFirstName.setError(getString(R.string.invalid_name));
                hasError = true;
            }

            if (!HelperMethods.validName(_etSurname.getText().toString())) {
                _etSurname.setError(getString(R.string.invalid_surname));
                hasError = true;
            }

            if (!HelperMethods.validEmail(_etEmail.getText().toString())) {
                _etEmail.setError(getString(R.string.invalid_email));
                hasError = true;
            }

            if (TextUtils.isEmpty(_etPassword.getText().toString())) {
                _etPassword.setError(getString(R.string.empty_password));
                hasError = true;
            }
            else if (!HelperMethods.validPassword(_etPassword.getText().toString())) {
                _etPassword.setError(getString(R.string.invalid_password));
                hasError = true;
            }

            if (!hasError) {
                User user = new User(
                        _etFirstName.getText().toString(),
                        _etSurname.getText().toString(),
                        _etEmail.getText().toString(),
                        _etPassword.getText().toString()
                );

                UserManager.getInstance().register(user);
                Toast.makeText(getContext(), R.string.registration_successful, Toast.LENGTH_SHORT).show();

                if (mListener != null) {
                    mListener.displayFragment(FragmentEnum.JournalListFragment, null);
                }

            }
        }
    }
}
