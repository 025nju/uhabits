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

import android.app.*;
import android.content.*;

import org.isoron.androidbase.*;
import org.isoron.uhabits.core.database.*;
import org.isoron.uhabits.core.preferences.*;
import org.isoron.uhabits.core.reminders.*;
import org.isoron.uhabits.core.tasks.*;
import org.isoron.uhabits.core.ui.*;
import org.isoron.uhabits.utils.*;
import org.isoron.uhabits.widgets.*;

import java.io.*;

/**
 * The Android application for Loop Habit Tracker.
 */
public class HabitsApplication extends Application
{
    private Context context;

    private static HabitsApplicationComponent component;

    private WidgetUpdater widgetUpdater;

    private ReminderScheduler reminderScheduler;

    private NotificationTray notificationTray;

    public HabitsApplicationComponent getComponent()
    {
        return component;
    }

    public static void setComponent(HabitsApplicationComponent component)
    {
        HabitsApplication.component = component;
    }

    public static boolean isTestMode()
    {
        try
        {
            Class.forName("org.isoron.uhabits.BaseAndroidTest");
            return true;
        }
        catch (final ClassNotFoundException e)
        {
            return false;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;

        component = DaggerHabitsApplicationComponent
            .builder()
            .appContextModule(new AppContextModule(context))
            .build();

        if (isTestMode())
        {
            File db = DatabaseUtils.getDatabaseFile(context);
            if (db.exists()) db.delete();
        }

        try
        {
            DatabaseUtils.initializeDatabase(context);
        }
        catch (UnsupportedDatabaseVersionException e)
        {
            File db = DatabaseUtils.getDatabaseFile(context);
            db.renameTo(new File(db.getAbsolutePath() + ".invalid"));
            DatabaseUtils.initializeDatabase(context);
        }

        widgetUpdater = component.getWidgetUpdater();
        widgetUpdater.startListening();

        reminderScheduler = component.getReminderScheduler();
        reminderScheduler.startListening();

        notificationTray = component.getNotificationTray();
        notificationTray.startListening();

        Preferences prefs = component.getPreferences();
        prefs.setLastAppVersion(BuildConfig.VERSION_CODE);

        TaskRunner taskRunner = component.getTaskRunner();
        taskRunner.execute(() -> {
            reminderScheduler.scheduleAll();
            widgetUpdater.updateWidgets();
        });
    }

    @Override
    public void onTerminate()
    {
        context = null;
        reminderScheduler.stopListening();
        widgetUpdater.stopListening();
        notificationTray.stopListening();
        super.onTerminate();
    }
}
