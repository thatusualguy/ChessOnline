package io.github.thatusualguy.chessonline.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.databinding.BottomNavigationLayoutBinding;
import io.github.thatusualguy.chessonline.databinding.FragmentBottomNavigationBinding;
import io.github.thatusualguy.chessonline.databinding.FragmentProfileBinding;

public class BottomNavigationFragment extends Fragment {


	private FragmentBottomNavigationBinding binding;

	public BottomNavigationFragment() {
		// Required empty public constructor
	}

	public static BottomNavigationFragment newInstance() {
		BottomNavigationFragment fragment = new BottomNavigationFragment();
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
		binding = FragmentBottomNavigationBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		NavigationUI.setupWithNavController(
				binding.profileBottomNavigation,
				Navigation.findNavController(binding.getRoot())
		);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}