package io.github.thatusualguy.chessonline.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.github.thatusualguy.chessonline.grpc.ChessOnline;
import io.github.thatusualguy.chessonline.grpc.chess_accountGrpc;
import io.github.thatusualguy.chessonline.grpc.chess_gameGrpc;
import io.github.thatusualguy.chessonline.models.HistoryGame;
import io.github.thatusualguy.chessonline.models.Statistics;
import io.github.thatusualguy.chessonline.mygrpc.GrpcChannelRepo;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

public class StatisticViewModel extends ViewModel {


	public LiveData<List<HistoryGame>> getDefaultGameHistory() {
		return getGameHistory(1, 10);
	}

	public LiveData<List<HistoryGame>> getGameHistory(int offset, int length) {
		ChessOnline.history_request request = ChessOnline.history_request.newBuilder()
				.setLength(length)
				.setOffset(offset)
				.build();

		MutableLiveData<List<HistoryGame>> res = new MutableLiveData<>();
		res.setValue(new ArrayList<>());
		getAsyncStub().getHistory(request, new StreamObserver<ChessOnline.history_reply>() {
			@Override
			public void onNext(ChessOnline.history_reply value) {
				res.postValue(value.getGamesList().stream()
						.map(saved_game -> new HistoryGame(
								saved_game.getId().getValue(),
								saved_game.getPlayer1().getName(),
								"",
								saved_game.getOldOpponentElo(),
								saved_game.getEloChange(),
								saved_game.getWinnerId(),
								new Date(saved_game.getCreationTime().getSeconds() * 1000)))
						.collect(Collectors.toList()));
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

			}
		});

		return res;
	}

	public LiveData<Integer> getHistoryLength() {
		MutableLiveData<Integer> res = new MutableLiveData<>();
		getAsyncStub().getHistoryLength(Empty.newBuilder().build(), new StreamObserver<ChessOnline.history_length_reply>() {
			@Override
			public void onNext(ChessOnline.history_length_reply value) {
				res.postValue(value.getCount());
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

			}
		});
		return res;
	}

	public LiveData<Statistics> getStatistics() {
		MutableLiveData<Statistics> res = new MutableLiveData<>();
		getAsyncStub().getStats(Empty.newBuilder().build(), new StreamObserver<ChessOnline.stats_reply>() {
			@Override
			public void onNext(ChessOnline.stats_reply value) {
				Statistics statistics = new Statistics();
				statistics.elo = new Statistics.Stat(value.getElo().getValue(), value.getElo().getAddInfo());
				statistics.wins = new Statistics.Stat(value.getWins().getValue(), value.getWins().getAddInfo());
				res.postValue(statistics);
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onCompleted() {

			}
		});
		return res;
	}

	private chess_accountGrpc.chess_accountStub getAsyncStub() {
		Channel channel = GrpcChannelRepo.getChannel();
		return chess_accountGrpc.newStub(channel);
	}
}
