package com.android.goodslistgambit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.android.goodslistgambit.OnItemClickListener;
import com.android.goodslistgambit.R;
import com.android.goodslistgambit.database.App;
import com.android.goodslistgambit.pojo.Goods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> implements OnItemClickListener {
    private ArrayList<Goods> mGoods;
    private Context mContext;
    private App mApp = new App();
    Goods goods;
    private final int BUTTON_MINUS_ID = 6000;
    private final int BUTTON_PLUS_ID = 6001;
    private final int GOODS_COUNT_ID = 6002;

    public GoodsAdapter(ArrayList<Goods> goods) {
        this.mGoods = goods;
    }


    @NonNull
    @Override
    public GoodsAdapter.GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        return new GoodsViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, int position) {

        holder.bind(goods = mGoods.get(position));
        mContext = holder.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    @Override
    public void onItemClicked(Goods goods) {

    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mGoodsImage;
        private TextView mGoodsTitle;
        private TextView mGoodsCount;
        private Button mBtnPlus;
        private Button mBtnMinus;
        private TextView mPrice;
        private Button mAdd_to_store;
        private ConstraintLayout mConstraintLayout;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            mGoodsImage = itemView.findViewById(R.id.goods_image);
            mGoodsTitle = itemView.findViewById(R.id.goods_title);
            mGoodsCount = itemView.findViewById(R.id.goods_count);
            mPrice = itemView.findViewById(R.id.price);
            mAdd_to_store = itemView.findViewById(R.id.btn_add_to_store);
            mConstraintLayout = itemView.findViewById(R.id.constraint);

        }


        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        private void bind(Goods goods) {

            mGoodsTitle.setText(goods.getName());
            mPrice.setText(goods.getPrice() + mGoodsImage.getContext().getString(R.string.rub_sign));

            Glide.with(mGoodsImage.getContext())
                    .load(goods.getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_baseline_fastfood_24)
                            .centerCrop()
                            .error(R.drawable.ic_launcher_foreground))
                    .into(mGoodsImage);


            mAdd_to_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    notifyItemChanged(getAdapterPosition());
                    mConstraintLayout.removeView(mAdd_to_store);
                    mGoodsCount = new TextView(mContext);
                    mGoodsCount.setTextSize(spToPx(8, mContext));
                    mGoodsCount.setId(GOODS_COUNT_ID);
                    mGoodsCount.setText("1");
                    mGoodsCount.setTextColor(R.color.black);
//                    ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mGoodsCount.getLayoutParams();
//                    mGoodsCount.setGravity();
                    mGoodsCount.setLayoutParams(new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));


                    mBtnMinus = new Button(mContext);
                    mBtnMinus.setTextSize(14);
                    mBtnMinus.setId(BUTTON_MINUS_ID);
                    mBtnMinus.setBackgroundResource(R.drawable.ic_button_minus);
                    mBtnMinus.setLayoutParams(new ConstraintLayout.LayoutParams(
                            (int) convertDpToPixels(mContext, 32),
                            (int) convertDpToPixels(mContext, 32)));

                    mBtnPlus = new Button(mContext);
                    mBtnPlus.setTextSize(14);
                    mBtnPlus.setId(BUTTON_PLUS_ID);
                    mBtnPlus.setBackgroundResource(R.drawable.ic_button_plus);
                    mBtnPlus.setLayoutParams(new ConstraintLayout.LayoutParams(
                            (int) convertDpToPixels(mContext, 32),
                            (int) convertDpToPixels(mContext, 32)));

                    mConstraintLayout.addView(mBtnMinus);
                    mConstraintLayout.addView(mBtnPlus);
                    mConstraintLayout.addView(mGoodsCount);

                    ConstraintSet mConstraintSet = new ConstraintSet();
                    mConstraintSet.clone(mConstraintLayout);
                    changeConstraints(mConstraintSet);
                    mConstraintSet.applyTo(mConstraintLayout);

                }
            });
        }

    }

    private void createViews() {

    }

    float convertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }

    float convertDpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    private void changeConstraints(ConstraintSet mConstraintSet) {
//        mConstraintSet.clear(R.id.btn_minus, ConstraintSet.LEFT);
//        mConstraintSet.clear(R.id.btn_minus, ConstraintSet.TOP);

        mConstraintSet.connect(BUTTON_MINUS_ID, ConstraintSet.LEFT, R.id.price, ConstraintSet.RIGHT, (int) (convertDpToPixels(mContext, 10)));
        mConstraintSet.connect(BUTTON_MINUS_ID, ConstraintSet.BOTTOM, R.id.price, ConstraintSet.BOTTOM, 0);

        mConstraintSet.connect(GOODS_COUNT_ID, ConstraintSet.TOP, BUTTON_MINUS_ID, ConstraintSet.TOP, 0);
        mConstraintSet.connect(GOODS_COUNT_ID, ConstraintSet.LEFT, BUTTON_MINUS_ID, ConstraintSet.RIGHT, (int) (convertDpToPixels(mContext, 13)));

        mConstraintSet.connect(BUTTON_PLUS_ID, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, (int) (convertDpToPixels(mContext, 16)));
        mConstraintSet.connect(BUTTON_PLUS_ID, ConstraintSet.LEFT, GOODS_COUNT_ID, ConstraintSet.RIGHT, (int) (convertDpToPixels(mContext, 14)));
        mConstraintSet.connect(BUTTON_PLUS_ID, ConstraintSet.TOP, GOODS_COUNT_ID, ConstraintSet.TOP, 0);
    }

}
