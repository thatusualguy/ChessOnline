package io.github.thatusualguy.chessonline.ui.games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import io.github.thatusualguy.chessonline.databinding.FragmentWaitingGamesListBinding;
import io.github.thatusualguy.chessonline.vm.User;
import io.github.thatusualguy.chessonline.vm.UserViewModel;
import io.github.thatusualguy.chessonline.vm.WaitingGamesViewModel;

public class WaitingGamesFragment extends Fragment implements SelectedGameNavigation {

	private UserViewModel userViewModel;
	private WaitingGamesViewModel waitingGamesViewModel;

	private FragmentWaitingGamesListBinding binding;
	private NavController navController;

	public WaitingGamesFragment() {
	}

	public static WaitingGamesFragment newInstance() {
		WaitingGamesFragment fragment = new WaitingGamesFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = FragmentWaitingGamesListBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		navController = Navigation.findNavController(view);
		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
		waitingGamesViewModel = new ViewModelProvider(this).get(WaitingGamesViewModel.class);

		userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
			if (user.Logged_in) {
				showWelcomeMessage();
				showWaitingGames();
			} else {
				navController.navigate(WaitingGamesFragmentDirections.actionWaitingGamesFragmentToLoginFragment());
			}
		});
		binding.extendedFab.setOnClickListener(t -> {
			navController.navigate(WaitingGamesFragmentDirections.actionWaitingGamesFragmentToGameCreationFragment());
		});
	}

	private void showWelcomeMessage() {
		Snackbar.make(requireView(), "Let's win some games!", Snackbar.LENGTH_SHORT)
				.show();
	}

	private void showWaitingGames() {
		// Setup  the list
		RecyclerView recyclerView = binding.gamesWaitingList;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(
				new WaitingGameRecyclerViewAdapter(
						waitingGamesViewModel.getWaitingGames(),
						getViewLifecycleOwner(), this));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

	@Override
	public void navigateToId(String id) {
		navController.navigate(WaitingGamesFragmentDirections.actionWaitingGamesFragmentToGameFragment(id));
	}
}