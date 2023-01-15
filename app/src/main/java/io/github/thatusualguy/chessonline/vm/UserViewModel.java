package io.github.thatusualguy.chessonline.vm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.protobuf.Empty;

import io.github.thatusualguy.chessonline.models.LoginResult;
import io.github.thatusualguy.chessonline.models.RegisterResult;
import io.github.thatusualguy.chessonline.models.User;
import io.github.thatusualguy.chessonline.models.UserInfo;
import io.github.thatusualguy.chessonline.models.UserInfoChange;
import io.github.thatusualguy.chessonline.mygrpc.GrpcChannelRepo;
import io.github.thatusualguy.chessonline.grpc.ChessOnline;
import io.github.thatusualguy.chessonline.grpc.chess_accountGrpc;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

public class UserViewModel extends ViewModel {
	@NonNull
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
		Channel channel = GrpcChannelRepo.getChannel();
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
					GrpcChannelRepo.setJwt(loginResult.jwt);
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

	public LiveData<UserInfo> getUserInfo() {
		MutableLiveData<UserInfo> res = new MutableLiveData<>();

		getAsyncStub().getInfo(Empty.newBuilder().build(), new StreamObserver<ChessOnline.account_info>() {
			@Override
			public void onNext(ChessOnline.account_info value) {
				UserInfo userInfo = new UserInfo(value.getFullname(), value.getEmail());
				res.postValue(userInfo);
			}

			@Override
			public void onError(Throwable t) {
				Log.d("ChessOnline", t.getMessage());
			}

			@Override
			public void onCompleted() {

			}
		});

		return res;
	}

	public LiveData<UserInfoChange> setUserInfo(UserInfo new_info) {
		ChessOnline.change_info_request request = ChessOnline.change_info_request
				.newBuilder()
				.setNewInfo(ChessOnline.account_info
						.newBuilder()
						.setEmail(new_info.email)
						.setPassword(new_info.password)
						.setFullname(new_info.username)
						.build())
				.build();

		MutableLiveData<UserInfoChange> res = new MutableLiveData<>();
		getAsyncStub().setInfo(request, new StreamObserver<ChessOnline.change_info_reply>() {
			@Override
			public void onNext(ChessOnline.change_info_reply value) {
				UserInfoChange userInfoChange = new UserInfoChange();
				userInfoChange.success = value.getSuccess();

				if (value.hasErrorMessage())
					userInfoChange.message = value.getErrorMessage();
				else {
					userInfoChange.userInfo = new UserInfo(value.getCurrentInfo().getFullname(), value.getCurrentInfo().getEmail());
				}

				res.postValue(userInfoChange);
			}

			@Override
			public void onError(Throwable t) {
				UserInfoChange userInfoChange = new UserInfoChange();
				userInfoChange.success = false;
				userInfoChange.message = t.getMessage();

				res.postValue(userInfoChange);
			}

			@Override
			public void onCompleted() {

			}
		});

		return res;
	}

	public void logout() {
		GrpcChannelRepo.setJwt("");
		user.postValue(new User());
	}
}
