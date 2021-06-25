package Deprecate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashdemo.R;

import java.util.List;

/**
 * 一个写完就扔的类(┭┮﹏┭┮)
 */
public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> {
    private Context mContext;
    private List<GameCard> mGameCard;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView image;
        TextView category;
        TextView name;
        TextView intro;
        TextView score;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            image = (ImageView) view.findViewById(R.id.rv_item_game_img);
            category = (TextView) view.findViewById(R.id.rv_item_game_sort);
            name = (TextView) view.findViewById(R.id.rv_item_game_name);
            intro = (TextView) view.findViewById(R.id.rv_item_game_description);
            score = (TextView) view.findViewById(R.id.rv_item_game_score);
        }
    }

    public GameCardAdapter(List<GameCard> gameCardList){
        mGameCard = gameCardList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.game_display_cardview, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position){
        GameCard gameCard = mGameCard.get(position);
        viewHolder.name.setText(gameCard.getGameName());
        viewHolder.category.setText(gameCard.getCategory());
        viewHolder.intro.setText(gameCard.getGameIntro());
        viewHolder.score.setText(gameCard.getScore());
        Glide.with(mContext).load(gameCard.getImage()).into(viewHolder.image);
    }

    public int getItemCount(){
        return mGameCard.size();
    }
}
