package com.genymobile.scrcpy;

import android.os.RemoteException;
import android.service.spotlight.Spotlight;
import android.view.Surface;

import com.genymobile.scrcpy.util.Ln;

import java.io.IOException;
import java.util.Objects;

public class SpotlightApi {

    public static int systemVersion;

    static {
        try {
            systemVersion = Spotlight.SYSTEM.VERSION;
        } catch (Exception e) {
            systemVersion = -1;
        }
        Ln.i("Spotlight Version: " + systemVersion);
    }

    public static int getDisplayIdFromUserId(int userId) {
        if (userId == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(Spotlight.getService().getEnvData(userId).executeInfo.displayId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static Surface getSurfaceByUserId(int userId) throws IOException {
        if (systemVersion < 1) {
            throw new IOException("系统版本过老，不支持");
        }
        try {
            return Spotlight.getService().getSurfaceByUser(userId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSurfaceByUser(int userId, Surface surface) throws IOException {
        if (systemVersion < 1) {
            throw new IOException("系统版本过老，不支持");
        }
        try {
            Spotlight.getService().setSurfaceByUser(userId, surface);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearSurfaceByUser(int userId) {
        if (userId <= 0) {
            return;
        }
        try {
            SpotlightApi.setSurfaceByUser(userId, null);
            Ln.i("Spotlight surface cleaned up for user " + userId);
        } catch (Exception e) {
            Ln.e("Failed to clean up Spotlight surface for user " + userId, e);
        }
    }
}
