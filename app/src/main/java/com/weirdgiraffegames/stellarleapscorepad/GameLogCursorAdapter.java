package com.weirdgiraffegames.stellarleapscorepad;

/**
 * Created by sarah on 08/01/2018.
 */


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameLogCursorAdapter extends RecyclerView.Adapter<GameLogCursorAdapter.GameViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    final private GameLogAdapterOnClickHandler mClickHandler;

    public interface GameLogAdapterOnClickHandler {
        void onClick(Long gameId);
    }

    public GameLogCursorAdapter(Context mContext, GameLogAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mClickHandler = clickHandler;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.game_log_item, parent, false);

        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        int gameIDIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry._ID);
        int gameDateIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_TIMESTAMP);
        int tuskadonTotalIndex =mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS);
        int starlingTotalIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS);
        int cosmosaurusTotalIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS);
        int scoutarsTotalIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS);
        int araklithTotalIndex = mCursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS);

        mCursor.moveToPosition(position);

        final long gameID = mCursor.getLong(gameIDIndex);
        String timestamp = mCursor.getString(gameDateIndex);
        String date = parseDate(timestamp);
        int tuskadonTotal = mCursor.getInt(tuskadonTotalIndex);
        int starlingTotal = mCursor.getInt(starlingTotalIndex);
        int cosmosaurusTotal = mCursor.getInt(cosmosaurusTotalIndex);
        int scoutarsTotal = mCursor.getInt(scoutarsTotalIndex);
        int araklithTotal = mCursor.getInt(araklithTotalIndex);

        holder.gameDateTextView.setText(date);
        holder.tuskadonTextView.setText(String.valueOf(tuskadonTotal));
        holder.starlingTextView.setText(String.valueOf(starlingTotal));
        holder.cosmosaurusTextView.setText(String.valueOf(cosmosaurusTotal));
        holder.scoutarsTextView.setText(String.valueOf(scoutarsTotal));
        holder.araklithTextView.setText(String.valueOf(araklithTotal));
        holder.itemView.setTag(gameID);

        Log.d("date",date);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.game_date_tv) TextView gameDateTextView;

        @BindView(R.id.tuskadon_total_tv) TextView tuskadonTextView;
        @BindView(R.id.starling_total_tv) TextView starlingTextView;
        @BindView(R.id.cosmosaurus_total_tv) TextView cosmosaurusTextView;
        @BindView(R.id.scoutars_total_tv) TextView scoutarsTextView;
        @BindView(R.id.araklith_total_tv) TextView araklithTextView;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
            public void onClick(View v) {
            long gameId = (Long) v.getTag();
            mClickHandler.onClick(gameId);
        }
    }

    private String parseDate(String fullTimeStamp) {
        String[] dateParts = fullTimeStamp.split(" ");
        String dateOnly = dateParts[0];
        return dateOnly;
    }
}