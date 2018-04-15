package com.csci150.newsapp.entirenews;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.csci150.newsapp.entirenews.utils.Utils;

import java.lang.reflect.Field;

/**
 * Created by Shifatul Islam (Denocyte) on 4/15/2018 1:56 PM.
 * A listing app, where you can find everything in one place.
 */
public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Utils.print("BottomNav", "Unable to get shift mode field", Log.ERROR);
        } catch (IllegalAccessException e) {
            Utils.print("BottomNav", "Unable to change value of shift mode", Log.ERROR);
        }
    }
}
