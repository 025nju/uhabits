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

package org.isoron.uhabits.receivers;

import android.content.*;
import android.support.annotation.*;
import android.util.*;

import org.isoron.uhabits.*;
import org.isoron.uhabits.intents.*;
import org.isoron.uhabits.models.*;

/**
 * The Android BroadcastReceiver for Loop Habit Tracker.
 * <p>
 * All broadcast messages are received and processed by this class.
 */
public class WidgetReceiver extends BroadcastReceiver
{
    public static final String ACTION_ADD_REPETITION =
        "org.isoron.uhabits.ACTION_ADD_REPETITION";

    public static final String ACTION_DISMISS_REMINDER =
        "org.isoron.uhabits.ACTION_DISMISS_REMINDER";

    public static final String ACTION_REMOVE_REPETITION =
        "org.isoron.uhabits.ACTION_REMOVE_REPETITION";

    public static final String ACTION_TOGGLE_REPETITION =
        "org.isoron.uhabits.ACTION_TOGGLE_REPETITION";

    @NonNull
    private HabitList habits;

    @NonNull
    private IntentParser parser;

    @NonNull
    private ReceiverActions actions;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        HabitsApplication app =
            (HabitsApplication) context.getApplicationContext();

        habits = app.getComponent().getHabitList();
        actions = app.getComponent().getReceiverActions();
        parser = new IntentParser(habits);

        Log.d("WidgetReceiver",
            String.format("Received intent: %s", intent.toString()));
        try
        {
            switch (intent.getAction())
            {
                case ACTION_ADD_REPETITION:
                    onActionAddRepetition(intent);
                    break;

                case ACTION_TOGGLE_REPETITION:
                    onActionToggleRepetition(intent);
                    break;

                case ACTION_REMOVE_REPETITION:
                    onActionRemoveRepetition(intent);
                    break;
            }
        }
        catch (RuntimeException e)
        {
            Log.e("WidgetReceiver", "could not process intent", e);
        }
    }

    private void onActionAddRepetition(Intent intent)
    {
        IntentParser.CheckmarkIntentData data;
        data = parser.parseCheckmarkIntent(intent);
        actions.addRepetition(data.habit, data.timestamp);
    }

    private void onActionRemoveRepetition(Intent intent)
    {
        IntentParser.CheckmarkIntentData data;
        data = parser.parseCheckmarkIntent(intent);
        actions.removeRepetition(data.habit, data.timestamp);
    }

    private void onActionToggleRepetition(Intent intent)
    {
        IntentParser.CheckmarkIntentData data;
        data = parser.parseCheckmarkIntent(intent);
        actions.toggleRepetition(data.habit, data.timestamp);
    }
}
