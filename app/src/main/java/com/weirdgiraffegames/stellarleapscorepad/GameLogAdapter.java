package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

/**
 * Created by sarah on 03/01/2018.
 */

public class GameLogAdapter extends RecyclerView.Adapter<GameLogAdapter.GameViewHolder>  {

    private Context mContext;
    private Cursor mCursor;

    final private GameLogAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface GameLogAdapterOnClickHandler {
        void onClick(String gameId);
    }

    public GameLogAdapter(Context context, Cursor cursor, GameLogAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mClickHandler = clickHandler;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.game_log_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) return;

        int tuskadonTotal = mCursor.getInt(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS));
        int starlingTotal = mCursor.getInt(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS));
        int cosmosaurusTotal = mCursor.getInt(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS));
        int scoutarsTotal = mCursor.getInt(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS));
        int araklithTotal = mCursor.getInt(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS));

        String gameID = mCursor.getString(mCursor.getColumnIndexOrThrow(GameLogContract.GameLogEntry._ID));

        holder.tuskadonTextView.setText(String.valueOf(tuskadonTotal));
        holder.starlingTextView.setText(String.valueOf(starlingTotal));
        holder.cosmosaurusTextView.setText(String.valueOf(cosmosaurusTotal));
        holder.scoutarsTextView.setText(String.valueOf(scoutarsTotal));
        holder.araklithTextView.setText(String.valueOf(araklithTotal));
        holder.gameIdTextView.setText(gameID);

        holder.itemView.setTag(gameID);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    //Inner class to hold the views needed to display a single item in the recycler-view

    class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Will display the total points for given species
        TextView tuskadonTextView;
        TextView starlingTextView;
        TextView cosmosaurusTextView;
        TextView scoutarsTextView;
        TextView araklithTextView;
        TextView gameIdTextView;

        public GameViewHolder(View itemView) {
            super(itemView);
            gameIdTextView = (TextView) itemView.findViewById(R.id.game_id_tv);

            tuskadonTextView = (TextView) itemView.findViewById(R.id.tuskadon_total_tv);
            starlingTextView = (TextView) itemView.findViewById(R.id.starling_total_tv);
            cosmosaurusTextView = (TextView) itemView.findViewById(R.id.cosmosaurus_total_tv);
            scoutarsTextView = (TextView) itemView.findViewById(R.id.scoutars_total_tv);
            araklithTextView = (TextView) itemView.findViewById(R.id.araklith_total_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String gameId = (String) v.getTag();
            mClickHandler.onClick(gameId);
        }
    }

    //Swaps the Cursor currently held in the adapter with a new one and triggers a UI refresh
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}
