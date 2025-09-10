package com.genymobile.scrcpy;

import android.os.RemoteException;
import android.service.spotlight.Spotlight;
import android.view.Surface;

import java.util.Objects;

public class SpotlightApi {
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

    public static Surface getSurfaceByUserId(int userId) {
        try {
            return Spotlight.getService().getSurfaceByUser(userId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSurfaceByUser(int userId, Surface surface) {
        try {
            Objects.requireNonNull(Spotlight.getService()).setSurfaceByUser(userId, surface);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
