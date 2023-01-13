package io.github.thatusualguy.chessonline.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import io.github.thatusualguy.chessonline.databinding.FragmentLoginBinding;
import io.github.thatusualguy.chessonline.ui.games.WaitingGamesFragmentDirections;
import io.github.thatusualguy.chessonline.vm.LoginResult;
import io.github.thatusualguy.chessonline.vm.UserViewModel;


public class LoginFragment extends Fragment {
	public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

	private UserViewModel userViewModel;
	private SavedStateHandle savedStateHandle;

	private FragmentLoginBinding binding;

	public LoginFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentLoginBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

		savedStateHandle = Navigation.findNavController(view)
				.getPreviousBackStackEntry()
				.getSavedStateHandle();
		savedStateHandle.set(LOGIN_SUCCESSFUL, false);

		binding.loginLoginButton.setOnClickListener(v -> {
			String username = binding.registrationEmail.getText().toString();
			String password = binding.registrationPassword.getText().toString();
			View control = binding.loginLoginButton;
			login(username, password, control);
		});

		binding.loginRegister.setOnClickListener(v -> {
			final NavController navController = Navigation.findNavController(view);
			navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment(
					binding.registrationEmail.getText().toString(),
					binding.registrationPassword.getText().toString()
			));
		});
	}

	private void login(String username, String password, View control) {
		control.setEnabled(false);

		userViewModel.login(username, password).observe(getViewLifecycleOwner(), (Observer<LoginResult>) result -> {
			if (result.success) {
				savedStateHandle.set(LOGIN_SUCCESSFUL, true);
				NavHostFragment.findNavController(this).popBackStack();
			} else {
				showErrorMessage(result);
			}
			control.setEnabled(true);
		});
	}

	private void showErrorMessage(LoginResult result) {
		Snackbar.make(requireView(), result.message, Snackbar.LENGTH_LONG)
				.setBackgroundTint(com.google.android.material.R.attr.colorPrimaryVariant)
				.setTextColor(com.google.android.material.R.attr.colorOnPrimary)
				.show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}