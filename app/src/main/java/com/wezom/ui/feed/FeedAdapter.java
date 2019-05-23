package com.wezom.ui.feed;

import android.util.Log;

import com.wezom.common.adapters.VideosRecyclerViewAdapter;
import com.wezom.net.models.FoundedVideo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class FeedAdapter extends VideosRecyclerViewAdapter<FoundedVideo> {

    // example 2018-02-20T11:25:57.000Z
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ROOT);

    @Override
    public void refreshRecyclerView() {
        sortItems();
        super.refreshRecyclerView();
    }

    private void sortItems() {
        Collections.sort(items, (first, second) -> {
            String rawDate1 = first.snippet.publishedAt;
            String rawDate2 = second.snippet.publishedAt;
            try {
                Date date1 = formatter.parse(rawDate1);
                Date date2 = formatter.parse(rawDate2);
                boolean answer = date1.getTime() < date2.getTime();
                return answer ? 1 : -1;
            } catch (ParseException e) {
                Log.e("error", e.getMessage());
            }
            return 0;
        });
    }
}
