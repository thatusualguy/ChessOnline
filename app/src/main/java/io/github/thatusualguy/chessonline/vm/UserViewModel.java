package io.github.thatusualguy.chessonline.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.github.thatusualguy.chessonline.Grpc;
import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.grpc.ChessOnline;
import io.github.thatusualguy.chessonline.grpc.chess_accountGrpc;
import io.grpc.ManagedChannel;
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

	private chess_accountGrpc.chess_accountStub getAsyncStub() {
		ManagedChannel channel = Grpc.getManagedChannel();
		return chess_accountGrpc.newStub(channel);
	}

	public LiveData<LoginResult> login(String username, String password) {
		chess_accountGrpc.chess_accountStub asyncStub = getAsyncStub();

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

	public LiveData<RegisterResult> register(String email, String name, String password) {
		chess_accountGrpc.chess_accountStub asyncStub = getAsyncStub();

		MutableLiveData<RegisterResult> res = new MutableLiveData<>();
		ChessOnline.register_request registerRequest = ChessOnline.register_request.newBuilder()
				.setEmail(email)
				.setName(name)
				.setPassword(password)
				.build();

		asyncStub.register(registerRequest, new StreamObserver<ChessOnline.register_reply>() {
			@Override
			public void onNext(ChessOnline.register_reply value) {
				RegisterResult registerResult = new RegisterResult();
				registerResult.Success = value.hasSuccess() && value.getSuccess();

				if (registerResult.Success) {

				} else {
					registerResult.Message = value.getErrorMessage();
				}
				res.postValue(registerResult);
			}

			@Override
			public void onError(Throwable t) {
				RegisterResult registerResult = new RegisterResult();
				registerResult.Success = false;
				registerResult.Message = t.getMessage();
				res.postValue(registerResult);
			}

			@Override
			public void onCompleted() {
			}
		});

		return res;
	}
}
