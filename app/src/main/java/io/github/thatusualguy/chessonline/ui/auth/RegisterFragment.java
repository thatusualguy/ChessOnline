package io.github.thatusualguy.chessonline.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import io.github.thatusualguy.chessonline.databinding.FragmentRegisterBinding;
import io.github.thatusualguy.chessonline.models.RegisterResult;
import io.github.thatusualguy.chessonline.vm.UserViewModel;

public class RegisterFragment extends Fragment {
	public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

	private UserViewModel userViewModel;
	private SavedStateHandle savedStateHandle;
	private FragmentRegisterBinding binding;

	public RegisterFragment() {
		// Required empty public constructor
	}

	// TODO: get & use safeargs
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentRegisterBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

		savedStateHandle = Objects.requireNonNull(Navigation.findNavController(view)
						.getPreviousBackStackEntry())
				.getSavedStateHandle();

		binding.registrationRegister.setOnClickListener(t -> {
			String email = Objects.requireNonNull(binding.registrationEmail.getText()).toString().trim();
			String name = Objects.requireNonNull(binding.registrationName.getText()).toString().trim();
			String password = Objects.requireNonNull(binding.registrationPassword.getText()).toString().trim();
			View control = binding.registrationRegister;
			register(email, name, password, control);
		});
	}

	private void register(String email, String name, String password, View control) {
		control.setEnabled(false);

		userViewModel.register(email, name, password).observe(getViewLifecycleOwner(), (Observer<RegisterResult>) result -> {
			if (result.Success) {
				savedStateHandle.set(LOGIN_SUCCESSFUL, true);
				navigateBack();
			} else {
				showErrorMessage(result.Message);
			}
			control.setEnabled(true);
		});
	}

	public Boolean navigateBack() {
		return Navigation.findNavController(binding.getRoot()).popBackStack();
	}

	private void showErrorMessage(String message) {
		Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
				.show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}