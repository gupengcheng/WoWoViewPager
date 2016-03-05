package com.nightonke.wowoviewpager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.View;

import com.nightonke.wowoviewpager.Color.ColorChangeType;
import com.nightonke.wowoviewpager.Eases.EaseType;

/**
 * Created by Weiping on 2016/3/3.
 */

/**
 * animation to change the color of layer-list-drawable of view
 * notice that the background of the view must be:
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
 *     <item .../>
 *     <item .../>
 * </layer-list>
 *
 */
public class WoWoLayerListColorAnimation extends PageAnimation {

    private EaseType easeType;
    private boolean useSameEaseTypeBack = true;

    private ColorChangeType colorChangeType;

    private int[] items;

    private int[] targetColor;
    private int[] fromColor;

    private int[] targetA;
    private int[] targetR;
    private int[] targetG;
    private int[] targetB;
    private float[][] targetHSV;
    private int[] fromA;
    private int[] fromR;
    private int[] fromG;
    private int[] fromB;
    private float[][] fromHSV;

    public WoWoLayerListColorAnimation(int page, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType) {
        setPage(page);
        setStartOffset(0);
        setEndOffset(1);

        this.easeType = EaseType.Linear;
        this.useSameEaseTypeBack = true;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, EaseType easeType) {
        setPage(page);
        setStartOffset(0);
        setEndOffset(1);

        this.easeType = easeType;
        this.useSameEaseTypeBack = true;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, boolean useSameEaseTypeBack) {
        setPage(page);
        setStartOffset(0);
        setEndOffset(1);

        this.easeType = EaseType.Linear;
        this.useSameEaseTypeBack = useSameEaseTypeBack;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, EaseType easeType, boolean useSameEaseTypeBack) {
        setPage(page);
        setStartOffset(0);
        setEndOffset(1);

        this.easeType = easeType;
        this.useSameEaseTypeBack = useSameEaseTypeBack;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, float startOffset, float endOffset, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType) {
        setPage(page);
        setStartOffset(startOffset);
        setEndOffset(endOffset);

        this.easeType = EaseType.Linear;
        this.useSameEaseTypeBack = true;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, float startOffset, float endOffset, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, EaseType easeType) {
        setPage(page);
        setStartOffset(startOffset);
        setEndOffset(endOffset);

        this.easeType = easeType;
        this.useSameEaseTypeBack = true;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, float startOffset, float endOffset, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, boolean useSameEaseTypeBack) {
        setPage(page);
        setStartOffset(startOffset);
        setEndOffset(endOffset);

        this.easeType = EaseType.Linear;
        this.useSameEaseTypeBack = useSameEaseTypeBack;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    public WoWoLayerListColorAnimation(int page, float startOffset, float endOffset, int[] fromColor, int[] targetColor, int[] items, ColorChangeType colorChangeType, EaseType easeType, boolean useSameEaseTypeBack) {
        setPage(page);
        setStartOffset(startOffset);
        setEndOffset(endOffset);

        this.easeType = easeType;
        this.useSameEaseTypeBack = useSameEaseTypeBack;
        this.fromColor = fromColor;
        this.targetColor = targetColor;
        this.items = items;
        setARGBandHSV();

        this.colorChangeType = colorChangeType;
    }

    private float lastPositionOffset = -1;

    private boolean lastTimeIsExceed = false;

    @Override
    public void play(View onView, float positionOffset) {

        if (positionOffset < getStartOffset()) {
            return;
        }

        if (positionOffset >= getEndOffset()) {
            // if the positionOffset exceeds the endOffset,
            // we should set onView to target color
            // otherwise there may be offsets between target color and actually color
            if (lastTimeIsExceed) return;
            // if the last time we do this, just return
            LayerDrawable layerDrawable = (LayerDrawable) onView.getBackground();
            int length = targetColor.length;
            for (int i = 0; i < length; i++) {
                ((GradientDrawable)layerDrawable.findDrawableByLayerId(items[i])).setColor(targetColor[i]);
            }
            lastTimeIsExceed = true;
            return;
        }
        lastTimeIsExceed = false;

        // get the true offset
        positionOffset = (positionOffset - getStartOffset()) / (getEndOffset() - getStartOffset());
        float movementOffset;

        if (lastPositionOffset == -1) {
            // first movement
            movementOffset = easeType.getOffset(positionOffset);
        } else {
            if (positionOffset < lastPositionOffset) {
                // back
                if (useSameEaseTypeBack) {
                    movementOffset = 1 - easeType.getOffset(1 - positionOffset);
                } else {
                    movementOffset = easeType.getOffset(positionOffset);
                }
            } else {
                // forward
                movementOffset = easeType.getOffset(positionOffset);
            }
        }
        lastPositionOffset = positionOffset;

        LayerDrawable layerDrawable = (LayerDrawable) onView.getBackground();
        int length = targetColor.length;
        for (int i = 0; i < length; i++) {
            if (colorChangeType == ColorChangeType.RGB) {
                ((GradientDrawable)layerDrawable.findDrawableByLayerId(items[i])).setColor(
                        Color.argb(
                                fromA[i] + (int)((targetA[i] - fromA[i]) * movementOffset),
                                fromR[i] + (int)((targetR[i] - fromR[i]) * movementOffset),
                                fromG[i] + (int)((targetG[i] - fromG[i]) * movementOffset),
                                fromB[i] + (int)((targetB[i] - fromB[i]) * movementOffset))
                );
            } else {
                ((GradientDrawable)layerDrawable.findDrawableByLayerId(items[i])).setColor(Color.HSVToColor(new float[]{
                        fromHSV[i][0] + (targetHSV[i][0] - fromHSV[i][0]) * movementOffset,
                        fromHSV[i][1] + (targetHSV[i][1] - fromHSV[i][1]) * movementOffset,
                        fromHSV[i][2] + (targetHSV[i][2] - fromHSV[i][2]) * movementOffset
                }));
            }
        }
    }

    private void setARGBandHSV() {
        int length = targetColor.length;
        targetA = new int[length];
        targetR = new int[length];
        targetG = new int[length];
        targetB = new int[length];
        targetHSV = new float[length][3];
        fromA = new int[length];
        fromR = new int[length];
        fromG = new int[length];
        fromB = new int[length];
        fromHSV = new float[length][3];
        for (int i = 0; i < length; i++) {
            targetA[i] = Color.alpha(targetColor[i]);
            targetR[i] = Color.red(targetColor[i]);
            targetG[i] = Color.green(targetColor[i]);
            targetB[i] = Color.blue(targetColor[i]);
            Color.colorToHSV(targetColor[i], targetHSV[i]);

            fromA[i] = Color.alpha(fromColor[i]);
            fromR[i] = Color.red(fromColor[i]);
            fromG[i] = Color.green(fromColor[i]);
            fromB[i] = Color.blue(fromColor[i]);
            if (colorChangeType == ColorChangeType.RGB) Log.d("WoWo", fromA[i] + " " + fromR[i] + " " + fromG[i] + " " + fromB[i]);
            Color.colorToHSV(fromColor[i], fromHSV[i]);
        }
    }
}