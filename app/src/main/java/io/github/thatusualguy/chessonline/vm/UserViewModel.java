package io.github.thatusualguy.chessonline.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.github.thatusualguy.chessonline.Grpc;
import io.github.thatusualguy.chessonline.grpc.ChessOnline;
import io.github.thatusualguy.chessonline.grpc.chess_accountGrpc;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.CallStreamObserver;
import io.grpc.stub.StreamObserver;

public class UserViewModel extends ViewModel {
	private final MutableLiveData<User> user = new MutableLiveData<>();

	// TODO: load user from shared prefs
	public LiveData<User> getUser() {
		if (user.getValue() == null) {
			user.postValue(new User());
		}
		return user;
	}

	public void setUser(User _user) {
		user.postValue(_user);
	}

	public LiveData<LoginResult> login(String username, String password) {
		ManagedChannel channel = Grpc.getManagedChannel();
		chess_accountGrpc.chess_accountBlockingStub
				blockingStub = chess_accountGrpc.newBlockingStub(channel);
		chess_accountGrpc.chess_accountStub
				asyncStub = chess_accountGrpc.newStub(channel);

		ChessOnline.login_reqiest loginReqiest = ChessOnline.login_reqiest
				.newBuilder()
				.setEmail(username)
				.setPassword(password)
				.build();

		MutableLiveData<LoginResult> res = new MutableLiveData<>();
		asyncStub.login(loginReqiest, new StreamObserver<ChessOnline.login_reply>() {

			@Override
			public void onNext(ChessOnline.login_reply value) {
				LoginResult loginResult = new LoginResult();
				loginResult.success = value.hasJwt();

				if (loginResult.success) {
					loginResult.jwt = value.getJwt();
				} else {
					loginResult.message = value.getErrorMessage();
				}
				res.postValue(loginResult);

				User logged_user = new User();
				logged_user.Logged_in = true;
				logged_user.Jwt = value.getJwt();
				user.postValue(logged_user);
			}

			@Override
			public void onError(Throwable t) {
				LoginResult loginResult = new LoginResult();
				loginResult.success = false;

				loginResult.message = t.getMessage();
				res.postValue(loginResult);
//				user.postValue(new User());
			}

			@Override
			public void onCompleted() {
			}
		});

		return res;
	}
}
