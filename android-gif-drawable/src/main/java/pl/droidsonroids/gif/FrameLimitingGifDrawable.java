package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifInfoHandle;

/**
 * Project: two-halves
 * Created by danielwoolf on 02/07/2018.
 */
public class FrameLimitingGifDrawable extends GifDrawable {

    int lastFrameIndex;

    public FrameLimitingGifDrawable(@NonNull Resources res, @RawRes @DrawableRes int id) throws Resources.NotFoundException, IOException {
        super(res, id);
        lastFrameIndex = getNumberOfFrames() - 1;
    }
    
    public FrameLimitingGifDrawable(@NonNull byte[] bytes) throws IOException {
		super(new GifInfoHandle(bytes), null, null, true);
	}

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        if (getCurrentFrameIndex() >= lastFrameIndex) {
            stop();
        }
    }

    public void setLastFrameIndex(int lastFrameIndex) {
        this.lastFrameIndex = lastFrameIndex;
    }
}
