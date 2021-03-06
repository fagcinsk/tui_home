package ru.fagci.tuihome.utils;

import android.graphics.*;
import android.media.ThumbnailUtils;
import android.os.Build;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtils {
    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private static Rect setTextSizeForWidth(Paint paint, float maxW, float maxH, String text) {
        final float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float tsByW = testTextSize * maxW / bounds.width();
        float tsByH = testTextSize * maxH / bounds.height();
        paint.setTextSize(Math.min(tsByW, tsByH));
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds;
    }

    public static Bitmap getStringBitmap(String t) {
        if (null == t) return null;
        final int W = 96;
        final int H = 96;

        Bitmap b = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);

        Paint p = new Paint();
        Rect bounds = setTextSizeForWidth(p, W - 16, H - 16, t);

        p.setTextAlign(Paint.Align.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p.setColor(Color.valueOf(0, 0, 0, 128).toArgb());
        }
        canvas.drawCircle(W / 2, H / 2, H / 2, p);
        p.setColor(Color.WHITE);
        canvas.drawText(t, W / 2, H / 2 + bounds.height() / 2, p);
        return b;
    }

    public static Bitmap getThumbnailSimple(File file) {
        final int THUMB_SIZE = 96;

        return ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeFile(file.getAbsolutePath()), THUMB_SIZE, THUMB_SIZE);
    }
}
