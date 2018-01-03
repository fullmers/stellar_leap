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

    public GameLogAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
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

        holder.tuskadonTextView.setText(String.valueOf(tuskadonTotal));
        holder.starlingTextView.setText(String.valueOf(starlingTotal));
        holder.cosmosaurusTextView.setText(String.valueOf(cosmosaurusTotal));
        holder.scoutarsTextView.setText(String.valueOf(scoutarsTotal));
        holder.araklithTextView.setText(String.valueOf(araklithTotal));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    //Inner class to hold the views needed to display a single item in the recycler-view

    class GameViewHolder extends RecyclerView.ViewHolder {

        // Will display the total points for given species
        TextView tuskadonTextView;
        TextView starlingTextView;
        TextView cosmosaurusTextView;
        TextView scoutarsTextView;
        TextView araklithTextView;

        public GameViewHolder(View itemView) {
            super(itemView);
            tuskadonTextView = (TextView) itemView.findViewById(R.id.tuskadon_total_tv);
            starlingTextView = (TextView) itemView.findViewById(R.id.starling_total_tv);
            cosmosaurusTextView = (TextView) itemView.findViewById(R.id.cosmosaurus_total_tv);
            scoutarsTextView = (TextView) itemView.findViewById(R.id.scoutars_total_tv);
            araklithTextView = (TextView) itemView.findViewById(R.id.araklith_total_tv);
        }
    }


}
