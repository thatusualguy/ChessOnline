package io.github.thatusualguy.chessonline.databinding;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class Adapters {
	@BindingAdapter(value = "app:drawableTint") //customise your name here
	public static void setTextColor(TextView view, int color) {
		Drawable a = view.getCompoundDrawables()[0];
		if (a != null)
			a.setTint(color);
	}

}
