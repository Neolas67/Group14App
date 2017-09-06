package au.edu.uts.redylog.redylog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Helpers.FragmentEnum;
import au.edu.uts.redylog.redylog.Helpers.OnFragmentInteractionListener;
import au.edu.uts.redylog.redylog.R;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText _etPassword;
    private Button _btnLogin;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        _etPassword = view.findViewById(R.id.et_login_Password);
        _btnLogin = view.findViewById(R.id.btn_login_confirm);

        _btnLogin.setOnClickListener(this);

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
        if (view == _btnLogin) {
            if (UserManager.getInstance().login(_etPassword.getText().toString())) {
                Toast.makeText(getContext(), "Login successful.", Toast.LENGTH_SHORT).show();

                if (mListener != null) {
                    mListener.onFragmentMessage(FragmentEnum.LoginFragment, null);
                }

            } else {
                _etPassword.setError("Password incorrect.");
                Toast.makeText(getContext(), "Login failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
