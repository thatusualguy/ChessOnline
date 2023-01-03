package io.github.thatusualguy.chessonline.ui.games;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
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

/**
 * A fragment representing a list of Items.
 */
public class WaitingGamesFragment extends Fragment {


	private UserViewModel userViewModel;

	// TODO: Customize parameter argument names
	private static final String ARG_COLUMN_COUNT = "column-count";
	// TODO: Customize parameters
	private int mColumnCount = 1;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public WaitingGamesFragment() {
	}

	// TODO: Customize parameter initialization
	@SuppressWarnings("unused")
	public static WaitingGamesFragment newInstance(int columnCount) {
		WaitingGamesFragment fragment = new WaitingGamesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_COLUMN_COUNT, columnCount);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_waiting_games_list, container, false);

		// Set the adapter
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.games_waiting_list);
			if (mColumnCount <= 1) {
				recyclerView.setLayoutManager(new LinearLayoutManager(context));
			} else {
				recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
			}
			recyclerView.setAdapter(new MyWaitingGameRecyclerViewAdapter(PlaceholderContent.ITEMS));
		}
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
		final NavController navController = Navigation.findNavController(view);
		userViewModel.user.observe(getViewLifecycleOwner(), (Observer<User>) user -> {
			if (user != null) {
				showWaitingGames();
			} else {
//				FragmentGames
//				navController.navigate(R.id.lo);
			}
		});

	}

	private void showWaitingGames() {
		// TODO: use vm
	}
}