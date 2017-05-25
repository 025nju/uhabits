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

package org.isoron.uhabits.commands;

import android.support.annotation.*;

import com.google.gson.*;

import org.isoron.uhabits.*;
import org.isoron.uhabits.models.*;
import org.json.*;

import javax.inject.*;

public class CommandParser
{
    private HabitList habitList;

    private ModelFactory modelFactory;

    @Inject
    public CommandParser(@NonNull HabitList habitList,
                         @NonNull ModelFactory modelFactory)
    {
        this.habitList = habitList;
        this.modelFactory = modelFactory;
    }

    @StringRes
    public Integer getExecuteString(@NonNull Command command)
    {
        if(command instanceof ArchiveHabitsCommand)
            return R.string.toast_habit_archived;

        if(command instanceof ChangeHabitColorCommand)
            return R.string.toast_habit_changed;

        if(command instanceof CreateHabitCommand)
            return R.string.toast_habit_created;

        if(command instanceof DeleteHabitsCommand)
            return R.string.toast_habit_deleted;

        if(command instanceof EditHabitCommand)
            return R.string.toast_habit_changed;

        if(command instanceof UnarchiveHabitsCommand)
            return R.string.toast_habit_unarchived;

        return null;
    }

    @NonNull
    public Command parse(@NonNull String json) throws JSONException
    {
        String event = new JSONObject(json).getString("event");
        Gson gson = new GsonBuilder().create();

        if (event.equals("Archive")) return gson
            .fromJson(json, ArchiveHabitsCommand.Record.class)
            .toCommand(habitList);

        if (event.equals("ChangeColor")) return gson
            .fromJson(json, ChangeHabitColorCommand.Record.class)
            .toCommand(habitList);

        if (event.equals("CreateHabit")) return gson
            .fromJson(json, CreateHabitCommand.Record.class)
            .toCommand(modelFactory, habitList);

        if (event.equals("CreateRep")) return gson
            .fromJson(json, CreateRepetitionCommand.Record.class)
            .toCommand(habitList);

        if (event.equals("DeleteHabit")) return gson
            .fromJson(json, DeleteHabitsCommand.Record.class)
            .toCommand(habitList);

        if (event.equals("EditHabit")) return gson
            .fromJson(json, EditHabitCommand.Record.class)
            .toCommand(modelFactory, habitList);

        if (event.equals("Toggle")) return gson
            .fromJson(json, ToggleRepetitionCommand.Record.class)
            .toCommand(habitList);

        if (event.equals("Unarchive")) return gson
            .fromJson(json, UnarchiveHabitsCommand.Record.class)
            .toCommand(habitList);

        throw new IllegalStateException("Unknown command");
    }
}
