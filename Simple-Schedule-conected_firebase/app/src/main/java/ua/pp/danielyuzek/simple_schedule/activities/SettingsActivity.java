package ua.pp.danielyuzek.simple_schedule.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.models.Data;
import ua.pp.danielyuzek.simple_schedule.models.Group;
import ua.pp.danielyuzek.simple_schedule.utilities.UserSettings;

public class SettingsActivity extends AppCompatActivity {

    private TextView mGroupSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.action_settings));

        View groupContainer = findViewById(R.id.group_container);
        mGroupSubtitle = (TextView) findViewById(R.id.group_subtitle);

        String userGroup = UserSettings.getString(UserSettings.USER_GROUP);
        Data data = UserSettings.getData();
        final List<String> groups = new ArrayList<>();
        if (data != null && data.getGroups() != null) {
            for (Group group : data.getGroups()) {
                groups.add(group.getName());
            }
        }

        if (groupContainer != null && mGroupSubtitle != null) {
            groupContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChooseDialog(groups);
                }
            });

            if (userGroup != null && !userGroup.isEmpty()) {
                mGroupSubtitle.setText(userGroup);
            } else {
                mGroupSubtitle.setText(getString(R.string.settings_group_subtitle));
            }
        }
    }

    private void showChooseDialog(final List<String> groups) {
        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(getString(R.string.settings_group_subtitle))
                .setItems(groups.toArray(new CharSequence[groups.size()]),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UserSettings.setString(UserSettings.USER_GROUP, groups.get(which));
                                mGroupSubtitle.setText(groups.get(which));
                    }
                })
                .create()
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(99);
        finish();
    }
}
