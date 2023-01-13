package io.github.thatusualguy.chessonline.ui.games;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.placeholder.PlaceholderContent;
import io.github.thatusualguy.chessonline.vm.User;
import io.github.thatusualguy.chessonline.vm.UserViewModel;
import io.github.thatusualguy.chessonline.vm.WaitingGamesViewModel;

/**
 * A fragment representing a list of Items.
 */
public class WaitingGamesFragment extends Fragment {

	private UserViewModel userViewModel;
	private WaitingGamesViewModel waitingGamesViewModel;

	public WaitingGamesFragment() {
	}

	public static WaitingGamesFragment newInstance(int columnCount) {
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
		View view = inflater.inflate(R.layout.fragment_waiting_games_list, container, false);

		// Set the adapter
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.games_waiting_list);
			recyclerView.setLayoutManager(new LinearLayoutManager(context));
			recyclerView.setAdapter(new MyWaitingGameRecyclerViewAdapter(PlaceholderContent.ITEMS));
		}
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
		final NavController navController = Navigation.findNavController(view);
		userViewModel.getUser().observe(getViewLifecycleOwner(), (Observer<User>) user -> {
			if (user != null) {
				showWelcomeMessage();
				showWaitingGames();
			} else {
				navController.navigate(R.id.action_waitingGamesFragment_to_loginFragment);
			}
		});
	}

	private void showWelcomeMessage() {

	}

	private void showWaitingGames() {
		// TODO: use vm
	}
}