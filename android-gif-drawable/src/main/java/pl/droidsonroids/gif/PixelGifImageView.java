package pl.droidsonroids.gif;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;


public class PixelGifImageView extends GifImageView {

	private boolean mFreezesAnimation;

	/**
	 * A corresponding superclass constructor wrapper.
	 *
	 * @param context
	 * @see ImageView#ImageView(Context)
	 */
	public PixelGifImageView(Context context) {
		super(context);

		if (this.getDrawable() != null) {
			this.getDrawable().setFilterBitmap(false);
		}
	}

	/**
	 * Like equivalent from superclass but also try to interpret src and background
	 * attributes as {@link GifDrawable}.
	 *
	 * @param context
	 * @param attrs
	 * @see ImageView#ImageView(Context, AttributeSet)
	 */
	public PixelGifImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (this.getDrawable() != null) {
			this.getDrawable().setFilterBitmap(false);
		}
		postInit(GifViewUtils.initImageView(this, attrs, 0, 0));
	}

	/**
	 * Like equivalent from superclass but also try to interpret src and background
	 * attributes as GIFs.
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 * @see ImageView#ImageView(Context, AttributeSet, int)
	 */
	public PixelGifImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (this.getDrawable() != null) {
			this.getDrawable().setFilterBitmap(false);
		}
		postInit(GifViewUtils.initImageView(this, attrs, defStyle, 0));
	}

	/**
	 * Like equivalent from superclass but also try to interpret src and background
	 * attributes as GIFs.
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 * @param defStyleRes
	 * @see ImageView#ImageView(Context, AttributeSet, int, int)
	 */
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	public PixelGifImageView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
		super(context, attrs, defStyle, defStyleRes);
		if (this.getDrawable() != null) {
			this.getDrawable().setFilterBitmap(false);
		}
		postInit(GifViewUtils.initImageView(this, attrs, defStyle, defStyleRes));
	}

	private void postInit(GifViewUtils.GifImageViewAttributes result) {
		mFreezesAnimation = result.freezesAnimation;
		if (result.mSourceResId > 0) {
			super.setImageResource(result.mSourceResId);
		}
		if (result.mBackgroundResId > 0) {
			super.setBackgroundResource(result.mBackgroundResId);
		}
	}

	/**
	 * Sets the content of this GifImageView to the specified Uri.
	 * If uri destination is not a GIF then {@link ImageView#setImageURI(Uri)}
	 * is called as fallback.
	 * For supported URI schemes see: {@link android.content.ContentResolver#openAssetFileDescriptor(Uri, String)}.
	 *
	 * @param uri The Uri of an image
	 */
	@Override
	public void setImageURI(Uri uri) {
		if (!GifViewUtils.setGifImageUri(this, uri)) {
			super.setImageURI(uri);
		}
		if (getDrawable() != null) {
            getDrawable().setFilterBitmap(false);
		}
	}

	@Override
	public void setImageResource(int resId) {
		if (!GifViewUtils.setResource(this, true, resId)) {
			super.setImageResource(resId);
		}
		if (getDrawable() != null) {
            getDrawable().setFilterBitmap(false);
		}
	}
	


	@Override
	public void setBackgroundResource(int resId) {
		if (!GifViewUtils.setResource(this, false, resId)) {
			super.setBackgroundResource(resId);
		}
		if (getDrawable() != null) {
            getDrawable().setFilterBitmap(false);
		}
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Drawable source = mFreezesAnimation ? getDrawable() : null;
		Drawable background = mFreezesAnimation ? getBackground() : null;
		return new GifViewSavedState(super.onSaveInstanceState(), source, background);
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof GifViewSavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		GifViewSavedState ss = (GifViewSavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		ss.restoreState(getDrawable(), 0);
		ss.restoreState(getBackground(), 1);
	}

	/**
	 * Sets whether animation position is saved in {@link #onSaveInstanceState()} and restored
	 * in {@link #onRestoreInstanceState(Parcelable)}
	 *
	 * @param freezesAnimation whether animation position is saved
	 */
	public void setFreezesAnimation(boolean freezesAnimation) {
		mFreezesAnimation = freezesAnimation;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (getBackground() != null){
			this.getBackground().setFilterBitmap(false);
		}
		super.onDraw(canvas);
	}
}
