package com.barranquero.downloadfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by usuario on 16/02/17
 * DownloadFile
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Resources resources = context.getResources();
        String downloaded = String.format(resources.getString(R.string.download_file), intent.getExtras().getString("file"));
        Toast.makeText(context, downloaded, Toast.LENGTH_SHORT).show();
    }
}
