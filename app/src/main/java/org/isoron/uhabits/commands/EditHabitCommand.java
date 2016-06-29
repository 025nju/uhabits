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

import org.isoron.uhabits.*;
import org.isoron.uhabits.models.*;

import javax.inject.*;

/**
 * Command to modify a habit.
 */
public class EditHabitCommand extends Command
{
    @Inject
    HabitList habitList;

    private Habit original;

    private Habit modified;

    private long savedId;

    private boolean hasFrequencyChanged;

    public EditHabitCommand(Habit original, Habit modified)
    {
        HabitsApplication.getComponent().inject(this);

        this.savedId = original.getId();
        this.modified = new Habit();
        this.original = new Habit();

        this.modified.copyFrom(modified);
        this.original.copyFrom(original);

        Frequency originalFreq = this.original.getFrequency();
        Frequency modifiedFreq = this.modified.getFrequency();
        hasFrequencyChanged = (!originalFreq.equals(modifiedFreq));
    }

    @Override
    public void execute()
    {
        copyAttributes(this.modified);
    }

    @Override
    public Integer getExecuteStringId()
    {
        return R.string.toast_habit_changed;
    }

    @Override
    public Integer getUndoStringId()
    {
        return R.string.toast_habit_changed_back;
    }

    @Override
    public void undo()
    {
        copyAttributes(this.original);
    }

    private void copyAttributes(Habit model)
    {
        Habit habit = habitList.getById(savedId);
        if (habit == null) throw new RuntimeException("Habit not found");

        habit.copyFrom(model);
        habitList.update(habit);

        invalidateIfNeeded(habit);
    }

    private void invalidateIfNeeded(Habit habit)
    {
        if (hasFrequencyChanged)
        {
            habit.getCheckmarks().invalidateNewerThan(0);
            habit.getStreaks().invalidateNewerThan(0);
            habit.getScores().invalidateNewerThan(0);
        }
    }
}