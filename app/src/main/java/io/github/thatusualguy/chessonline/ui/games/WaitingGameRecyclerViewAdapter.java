package io.github.thatusualguy.chessonline.ui.games;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.thatusualguy.chessonline.databinding.FragmentWaitingGamesBinding;
import io.github.thatusualguy.chessonline.vm.WaitingGame;

import java.util.List;

public class WaitingGameRecyclerViewAdapter extends RecyclerView.Adapter<WaitingGameRecyclerViewAdapter.ViewHolder> {

	private final LiveData<List<WaitingGame>> waitingGames;
	private final SelectedGameNavigation parent;

	@SuppressLint("NotifyDataSetChanged")
	public WaitingGameRecyclerViewAdapter(LiveData<List<WaitingGame>> waitingGames, LifecycleOwner viewLifecycleOwner, SelectedGameNavigation parent) {
		this.waitingGames = waitingGames;
		this.parent = parent;

		waitingGames.observe(viewLifecycleOwner, waitingGamesList -> {
			notifyDataSetChanged();
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(FragmentWaitingGamesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		WaitingGame game = waitingGames.getValue().get(position);
		holder.binding.setGame(game);

		holder.binding.gamesWaitingItemJoinButton.setOnClickListener(x->{
			parent.navigateToId(game.id);
		});
	}

	@Override
	public int getItemCount() {
		return waitingGames.getValue().size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final io.github.thatusualguy.chessonline.databinding.FragmentWaitingGamesBinding binding;

		public ViewHolder(FragmentWaitingGamesBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

	}
}