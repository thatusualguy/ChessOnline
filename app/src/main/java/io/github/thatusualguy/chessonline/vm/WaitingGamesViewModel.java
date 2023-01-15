package io.github.thatusualguy.chessonline.vm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.github.thatusualguy.chessonline.Grpc;
import io.github.thatusualguy.chessonline.grpc.ChessOnline;
import io.github.thatusualguy.chessonline.grpc.chess_gameGrpc;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

public class WaitingGamesViewModel extends ViewModel {

	private final MutableLiveData<List<WaitingGame>> waitingGames = new MutableLiveData<>();


	private long last_update = 0;
	private final static long MAX_UPDATE_AGE = 2000;

	public LiveData<List<WaitingGame>> getWaitingGames() {
		if (waitingGames.getValue() == null)
			waitingGames.setValue(new ArrayList<>());

		long current_time = (new Date().getTime());
		if (current_time - last_update < MAX_UPDATE_AGE)
			return waitingGames;

		refresh();
		last_update = current_time;
		return waitingGames;
	}

	private chess_gameGrpc.chess_gameStub getAsyncStub() {
		Channel channel = Grpc.getChannel();
		return chess_gameGrpc.newStub(channel);
	}

	public void refresh() {
		getAsyncStub().getWaitingGames(Empty.newBuilder().build(), new StreamObserver<ChessOnline.waiting_games_reply>() {
			@Override
			public void onNext(ChessOnline.waiting_games_reply value) {
				List<ChessOnline.waiting_game> values = value.getGamesList();
				List<WaitingGame> updated_waitingGames = values.stream()
						.map(game ->
								new WaitingGame(
										game.getId().getValue(),
										game.getCreationTime().getNanos() / 1000,
										game.getCreator().getName(),
										game.getElo(),
										ChessColor.fromGrpc(game.getChosedColor())))
						.collect(Collectors.toList());

				waitingGames.postValue(updated_waitingGames);
			}

			@Override
			public void onError(Throwable t) {
				Log.d("Chess", "Error loading waiting games:\n" + t.getMessage());
			}

			@Override
			public void onCompleted() {

			}
		});
	}
}
