/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.isoron.uhabits;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

import org.isoron.uhabits.helpers.DatabaseHelper;

public class HabitsApplication extends Application
{
    private static Context context;

    private boolean isTestMode()
    {
        try
        {
            getClassLoader().loadClass("org.isoron.uhabits.unit.models.HabitTest");
            return true;
        }
        catch (final Exception e)
        {
            return false;
        }
    }

    public static Context getContext()
    {
        return context;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        HabitsApplication.context = this;

        String databaseFilename = BuildConfig.databaseFilename;

        if (isTestMode())
        {
            databaseFilename = "test.db";
            DatabaseHelper.deleteDatabase(this, databaseFilename);
        }

        DatabaseHelper.initializeActiveAndroid(this, databaseFilename);
    }

    @Override
    public void onTerminate()
    {
        HabitsApplication.context = null;
        ActiveAndroid.dispose();
        super.onTerminate();
    }
}
