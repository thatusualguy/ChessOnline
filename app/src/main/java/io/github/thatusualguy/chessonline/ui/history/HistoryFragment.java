package io.github.thatusualguy.chessonline.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.databinding.FragmentHistoryBinding;
import io.github.thatusualguy.chessonline.databinding.FragmentWaitingGamesListBinding;
import io.github.thatusualguy.chessonline.ui.games.WaitingGameRecyclerViewAdapter;
import io.github.thatusualguy.chessonline.ui.games.WaitingGamesFragmentDirections;
import io.github.thatusualguy.chessonline.vm.SelectedGameNavigation;
import io.github.thatusualguy.chessonline.vm.StatisticViewModel;

public class HistoryFragment extends Fragment implements SelectedGameNavigation {

	StatisticViewModel statisticViewModel;
	private FragmentHistoryBinding binding;
	private NavController navController;

	public HistoryFragment() {
		// Required empty public constructor
	}

	public static HistoryFragment newInstance() {
		HistoryFragment fragment = new HistoryFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = FragmentHistoryBinding.inflate(inflater, container, false);
		View view = binding.getRoot();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		navController = Navigation.findNavController(view);
		statisticViewModel = new ViewModelProvider(requireActivity()).get(StatisticViewModel.class);

		statisticViewModel.getStatistics().observe(getViewLifecycleOwner(), statistics -> {
			binding.setStats(statistics);
		});

		binding.historyGamesPlayed.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.historyGamesPlayed.setAdapter(
				new HistoryGamesRecyclerViewAdapter(
						statisticViewModel.getDefaultGameHistory(),
						getViewLifecycleOwner(), this));
	}

	@Override
	public void navigateToId(String id) {
		navController.navigate(HistoryFragmentDirections.actionHistoryFragmentToGameHistoryFragment(id));
	}
}