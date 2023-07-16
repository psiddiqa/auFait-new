package com.example.aufait;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment {

    // Required empty public constructor
    public ShareFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the "Share App" button by its ID
        Button shareButton = view.findViewById(R.id.shareButton);

        // Set a click listener for the "Share App" button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code here to handle the app sharing functionality

                // Example: Share the app link with other apps
                String appLink = "https://www.example.com/myapp"; // Replace with your app's link

                // Create a new sharing Intent
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome app!");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, appLink);

                // Start the sharing activity
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }
}
