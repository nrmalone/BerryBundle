package com.cis4280.berrybundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayout layout = (LinearLayout) view;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,10);

        Button shortGameScores = new Button(getContext());
        shortGameScores.setLayoutParams(layoutParams);
        shortGameScores.setText("Short Game Scores");
        shortGameScores.setOnClickListener(shortGameClickListener);
        layout.addView(shortGameScores);

        Button gameScores = new Button(getContext());
        gameScores.setLayoutParams(layoutParams);
        gameScores.setText("Game Scores");
        gameScores.setOnClickListener(gameClickListener);
        layout.addView(gameScores);

        return view;
    }

    private final View.OnClickListener shortGameClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), ShortGameHistory.class);
            //intent.putExtra(DetailsActivity.database, "Short Game");
            startActivity(intent);
        }
    };

    private final View.OnClickListener gameClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), GameHistory.class);
            //intent.putExtra(DetailsActivity.database, "Normal Game");
            startActivity(intent);
        }
    };
}
