/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.timezone;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.SystemApi;

import com.android.internal.annotations.GuardedBy;

import java.util.Objects;

/**
 * A class that can find time zone-related information about telephony networks.
 *
 * @hide
 */
@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
public class TelephonyLookup {

    private static Object sLock = new Object();
    @GuardedBy("sLock")
    private static TelephonyLookup sInstance;

    @NonNull
    private final libcore.timezone.TelephonyLookup mDelegate;

    /**
     * Obtains an instance for use when resolving telephony time zone information. This method never
     * returns {@code null}.
     */
    @NonNull
    public static TelephonyLookup getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new TelephonyLookup(libcore.timezone.TelephonyLookup.getInstance());
            }
            return sInstance;
        }
    }

    private TelephonyLookup(@NonNull libcore.timezone.TelephonyLookup delegate) {
        mDelegate = Objects.requireNonNull(delegate);
    }

    /**
     * Returns an object capable of querying telephony network information. This method can return
     * {@code null} in the event of an error while reading the underlying data files.
     */
    @Nullable
    public TelephonyNetworkFinder getTelephonyNetworkFinder() {
        libcore.timezone.TelephonyNetworkFinder telephonyNetworkFinderDelegate =
                mDelegate.getTelephonyNetworkFinder();
        return telephonyNetworkFinderDelegate != null
                ? new TelephonyNetworkFinder(telephonyNetworkFinderDelegate) : null;
    }
}
