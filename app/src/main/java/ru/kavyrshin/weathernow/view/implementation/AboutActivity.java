package ru.kavyrshin.weathernow.view.implementation;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

import ru.kavyrshin.weathernow.R;
import ru.kavyrshin.weathernow.view.implementation.adapter.SimpleImageAdapter;

public class AboutActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvToolbar;

    private TextView tvAboutDeveloper;

    private RecyclerView listFreePik;
    private TextView tvReferenceFreePik;
    private int[] freePikIds = {R.drawable.ic_cloud, R.drawable.ic_heavy_rain, R.drawable.ic_moon,
            R.drawable.ic_moon_cloud, R.drawable.ic_rain, R.drawable.ic_shower_rain, R.drawable.ic_snow,
            R.drawable.ic_snow_cloud, R.drawable.ic_storm, R.drawable.ic_sun, R.drawable.ic_sun_cloud};


    private RecyclerView listRoundicons;
    private TextView tvReferenceRoundicons;
    private int[] roundIconsIds = {R.drawable.ic_tornado, R.drawable.ic_wind};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        toolbar = findViewById(R.id.toolbar);
        tvToolbar = toolbar.findViewById(R.id.tvOut);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle(getString(R.string.activity_title_about));

        tvAboutDeveloper = findViewById(R.id.tvAboutDeveloper);
        listFreePik = findViewById(R.id.listFreePik);
        tvReferenceFreePik = findViewById(R.id.tvReferenceFreePik);
        listRoundicons = findViewById(R.id.listRoundicons);
        tvReferenceRoundicons = findViewById(R.id.tvReferenceRoundicons);


        tvAboutDeveloper.setText(getSpannableStringAboutDeveloper());
        tvAboutDeveloper.setMovementMethod(LinkMovementMethod.getInstance());

        tvReferenceFreePik.setText(getSpannableStringForFreepik());
        tvReferenceFreePik.setMovementMethod(LinkMovementMethod.getInstance());
        LinearLayoutManager linearFreePik = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listFreePik.setLayoutManager(linearFreePik);
        listFreePik.setAdapter(new SimpleImageAdapter(freePikIds));

        tvReferenceRoundicons.setText(getSpannableStringForRoundicons());
        tvReferenceRoundicons.setMovementMethod(LinkMovementMethod.getInstance());
        LinearLayoutManager linearRoundicons = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listRoundicons.setLayoutManager(linearRoundicons);
        listRoundicons.setAdapter(new SimpleImageAdapter(roundIconsIds));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (tvToolbar != null) {
            tvToolbar.setText(title);
        }
    }

    private SpannableString getSpannableStringAboutDeveloper() {
        URLSpan urlSpan = new URLSpan(getString(R.string.github_project_reference));

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));

        String aboutDeveloper = getString(R.string.about_developer);
        int start = aboutDeveloper.indexOf("portfolio");
        int end = start + "portfolio".length();

        SpannableString spannableString = new SpannableString(aboutDeveloper);
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private SpannableString getSpannableStringForFreepik() {
        URLSpan urlSpan = new URLSpan(getString(R.string.freepik_reference));
        URLSpan urlFlaticonSpan = new URLSpan("http://www.flaticon.com");

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));

        String madeByFreepik = getString(R.string.made_by_freepik);
        int start = madeByFreepik.indexOf("Freepik");
        int end = start + "Freepik".length();

        SpannableString spannableString = new SpannableString(madeByFreepik);
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = madeByFreepik.indexOf("www.flaticon.com");
        end = start + "www.flaticon.com".length();

        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlFlaticonSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private SpannableString getSpannableStringForRoundicons() {
        URLSpan urlSpan = new URLSpan(getString(R.string.roundicons_reference));
        URLSpan urlFlaticonSpan = new URLSpan("http://www.flaticon.com");

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));

        String madeByRoundicons = getString(R.string.made_by_roundicons);
        int start = madeByRoundicons.indexOf("Roundicons");
        int end = start + "Roundicons".length();

        SpannableString spannableString = new SpannableString(madeByRoundicons);
        spannableString.setSpan(foregroundColorSpan, start, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = madeByRoundicons.indexOf("www.flaticon.com");
        end = start + "www.flaticon.com".length();

        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(urlFlaticonSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}