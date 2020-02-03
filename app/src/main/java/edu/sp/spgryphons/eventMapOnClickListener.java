/*
 * Copyright (C) 2016 Google Inc.
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

package edu.sp.spgryphons;

import android.view.View;

/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
public class eventMapOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    double lat;
    double longi;

    public eventMapOnClickListener(double lat, double longi) {
        this.lat = lat;
        this.longi = longi;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }
}
