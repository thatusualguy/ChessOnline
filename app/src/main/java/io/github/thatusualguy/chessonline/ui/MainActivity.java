package io.github.thatusualguy.chessonline.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import io.github.thatusualguy.chessonline.R;
import io.github.thatusualguy.chessonline.databinding.ActivityMainBinding;
import io.github.thatusualguy.chessonline.vm.UserViewModel;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);

		NavController navController = Navigation.findNavController(view);
//		navController.set
	}
}