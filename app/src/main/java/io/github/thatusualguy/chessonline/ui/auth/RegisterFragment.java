package io.github.thatusualguy.chessonline.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.databinding.FragmentLoginBinding;
import io.github.thatusualguy.chessonline.vm.UserViewModel;

public class RegisterFragment extends Fragment {
	public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

	private UserViewModel userViewModel;
	private SavedStateHandle savedStateHandle;
	private FragmentLoginBinding binding;

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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_register, container, false);
	}
}