package io.github.thatusualguy.chessonline.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import io.github.thatusualguy.chessonline.databinding.FragmentProfileBinding;
import io.github.thatusualguy.chessonline.models.UserInfo;
import io.github.thatusualguy.chessonline.vm.UserViewModel;

public class ProfileFragment extends Fragment {

	private FragmentProfileBinding binding;
	private UserViewModel userViewModel;

	public ProfileFragment() {
		// Required empty public constructor
	}

	public static ProfileFragment newInstance() {
		ProfileFragment fragment = new ProfileFragment();
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
		binding = FragmentProfileBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setEnabledButtons(false);

		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

		loadUserInfo();

		binding.profileDiscardChanges.setOnClickListener(l -> loadUserInfo());

		binding.profileSettingsLogout.setOnClickListener(l -> logout());

		binding.profileApplyChanges.setOnClickListener(l -> {
			String email = binding.registrationEmail.getText().toString().trim();
			String name = binding.registrationName.getText().toString().trim();
			String password = binding.registrationPassword.getText().toString().trim();
			sendUserInfo(email, name, password);
		});
	}

	private void logout() {
		userViewModel.logout();
		Navigation.findNavController(binding.getRoot()).popBackStack();
	}

	private void sendUserInfo(String email, String name, String password) {
		setEnabledButtons(false);

		UserInfo new_info = new UserInfo(name, email, password);
		userViewModel.setUserInfo(new_info).observe(getViewLifecycleOwner(), userInfoChange -> {
			if (userInfoChange.success) {
				binding.registrationEmail.setText(userInfoChange.userInfo.email);
				binding.registrationName.setText(userInfoChange.userInfo.username);
				binding.registrationPassword.setText("");
			}
			if (userInfoChange.message != null) {
				showMessage(userInfoChange.message);
			}
			setEnabledButtons(true);
		});
	}

	private void loadUserInfo() {
		setEnabledButtons(false);
		userViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
			binding.registrationEmail.setText(userInfo.email);
			binding.registrationName.setText(userInfo.username);
			binding.registrationPassword.setText("");
			setEnabledButtons(true);
		});
	}

	void setEnabledButtons(boolean state) {
		binding.profileApplyChanges.setEnabled(state);
		binding.profileDiscardChanges.setEnabled(state);
	}

	private void showMessage(String message) {
		Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
				.show();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}