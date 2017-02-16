package com.barranquero.downloadfile;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by usuario on 16/02/17
 * DownloadFile
 */

public class DownloadFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int REQUEST_WRITE = 1;
    Button btnDownload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_download, container, false);

        btnDownload = (Button) rootView.findViewById(R.id.btnDownload);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWritePermission()) {
                    onDownload();
                }
            }
        });
    }

    /**
     * Method which checks if the application has permission to write to storage
     * @return True -> has permissions ; False -> doesn't have permissions
     */
    private boolean checkWritePermission() {
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permission);

        boolean permitted = false;

        //return (permissionCheck == PackageManager.PERMISSION_GRANTED);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            permitted = true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            Snackbar.make(getActivity().findViewById(R.id.frameHome), R.string.permission_write_external, Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, REQUEST_WRITE);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, REQUEST_WRITE);
        }
        return permitted;
    }

    /**
     * Method which starts the file download
     */
    public void onDownload() {
        btnDownload.setEnabled(false);
        Intent intent = new Intent(getActivity(), Downloader.class);    // Explicit intent, executes the service
        //intent.setData(Uri.parse("https://commonsware.com/Android/Android-1_0-CC.pdf"));
        intent.setData(Uri.parse("https://bitbits.hopto.org/img/copy.jpg"));
        getActivity().startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE) {
            // Just like Google
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onDownload();
            }
        }
    }
}