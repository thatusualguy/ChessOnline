package io.github.thatusualguy.chessonline.ui.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.thatusualguy.chessonline.databinding.FragmentHistoryItemBinding;
import io.github.thatusualguy.chessonline.databinding.FragmentWaitingGamesBinding;
import io.github.thatusualguy.chessonline.models.HistoryGame;
import io.github.thatusualguy.chessonline.ui.games.WaitingGameRecyclerViewAdapter;
import io.github.thatusualguy.chessonline.vm.SelectedGameNavigation;

public class HistoryGamesRecyclerViewAdapter extends RecyclerView.Adapter<HistoryGamesRecyclerViewAdapter.ViewHolder> {

	private final LiveData<List<HistoryGame>> historyGames;
	private final SelectedGameNavigation parent;


	@SuppressLint("NotifyDataSetChanged")
	public HistoryGamesRecyclerViewAdapter(LiveData<List<HistoryGame>> historyGames, LifecycleOwner viewLifecycleOwner, SelectedGameNavigation parent) {
		this.parent = parent;
		this.historyGames = historyGames;

		historyGames.observe(viewLifecycleOwner, waitingGamesList -> {
			notifyDataSetChanged();
		});
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(FragmentHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		HistoryGame game = historyGames.getValue().get(position);
		holder.binding.setGame(game);

		holder.binding.historyGameCard.setOnClickListener(x -> {
			parent.navigateToId(game.id);
		});
	}

	@Override
	public int getItemCount() {
		return historyGames.getValue().size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		private final FragmentHistoryItemBinding binding;

		public ViewHolder(FragmentHistoryItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

	}
}
