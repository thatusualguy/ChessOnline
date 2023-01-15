package io.github.thatusualguy.chessonline.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.ui.auth.LoginFragment;

public class MainActivity extends AppCompatActivity {

	public static SharedPreferences preferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferences = getPreferences(0);
	}

	private static final int TIME_INTERVAL = 1000;
	private long mBackPressed = 0;
	private boolean once = true;

	@Override
	public void onBackPressed() {
//		if (Navigation.findNavController(this.getCurrentFocus()).getBackQueue().size() > 1) {
//			super.onBackPressed();
//			return;
//		}


		if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
			super.onBackPressed();
			return;
		} else {
			Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
			super.onBackPressed();
		}

		mBackPressed = System.currentTimeMillis();
	}


}