package au.edu.uts.redylog.redylog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.uts.redylog.redylog.DataManagers.UserManager;
import au.edu.uts.redylog.redylog.Models.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        _etFirstName = view.findViewById(R.id.etFirstName);
        _etSurname = view.findViewById(R.id.etSurname);
        _etEmail = view.findViewById(R.id.etEmail);
        _etPassword = view.findViewById(R.id.etPassword);
        _btnRegister = view.findViewById(R.id.btnRegister);

        _btnRegister.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
            if (TextUtils.isEmpty(_etFirstName.getText())) {
                displayInvalidTextToast(getString(R.string.first_name));
            } else if (TextUtils.isEmpty(_etSurname.getText())) {
                displayInvalidTextToast(getString(R.string.surname));
            } else if (TextUtils.isEmpty(_etEmail.getText())) {
                displayInvalidTextToast(getString(R.string.email));
            } else if (TextUtils.isEmpty(_etPassword.getText())) {
                displayInvalidTextToast(getString(R.string.password));
            } else {
                User user = new User(
                        _etFirstName.getText().toString(),
                        _etSurname.getText().toString(),
                        _etEmail.getText().toString(),
                        _etPassword.getText().toString()
                );
                UserManager.getInstance().addUser(user);
            }
        }
    }

    private void displayInvalidTextToast(String missingValue) {
        Toast.makeText(
                getContext(),
                "Please ensure you have entered a valid " + missingValue,
                Toast.LENGTH_SHORT
        ).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
