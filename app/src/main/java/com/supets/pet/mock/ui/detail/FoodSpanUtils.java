package com.supets.pet.mock.ui.detail;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.supets.pet.mockui.R;

/**
 * FoodSpanUtils.getFoodPriceAndSpec(this, "999.99", "/份");
 * FoodSpanUtils.getFoodPrice(this, "999.99");
 * FoodSpanUtils.getFoodNameSpecSpan(this, "小炒肉", "/大份");
 */
public class FoodSpanUtils {


    public static SpannableString getFoodPriceAndSpec(Context context, String price, String spec) {
        String rmb = "¥";

        SpannableString spannableString = new SpannableString(rmb.concat(price).concat(spec));
        //人民币大小
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp26)),
                0, rmb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //价格大小
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp52)),
                rmb.length(), rmb.concat(price).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //人民币和价格颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#222222")), 0, rmb.concat(price).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //规格大小和颜色
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp30)),
                rmb.concat(price).length(), rmb.concat(price).concat(spec).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), rmb.concat(price).length(), rmb.concat(price).concat(spec).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }


    public static SpannableString getFoodPrice(Context context, String price) {
        String rmb = "¥";
        String content = rmb.concat(price);

        SpannableString spannableString = new SpannableString(content);
        //人民币大小
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp24)),
                0, rmb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //价格大小
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp30)),
                rmb.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //文本颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f0625a")), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getFoodNameSpecSpan(Context context, String foodName, String spec) {
        String content = foodName.concat(spec);
        SpannableString spannableString = new SpannableString(content);
        //菜名
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp40)),
                0, foodName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //规格
        spannableString.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.sp26)),
                foodName.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //文本颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
