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

package org.isoron.uhabits.widgets;

import android.content.*;
import android.support.annotation.*;

import org.apache.commons.lang3.*;
import org.isoron.uhabits.ui.widgets.*;

public class FrequencyWidgetProvider extends BaseWidgetProvider
{
    @NonNull
    @Override
    protected BaseWidget getWidgetFromId(@NonNull Context context, int id)
    {
        throw new NotImplementedException("");
    }

//    @NonNull
//    @Override
//    protected BaseWidget getWidgetFromId(int id)
//    {
//        throw new NotImplementedException("");
//    }
//
//    @Override
//    protected View buildCustomView(Context context, Habit habit)
//    {
//        FrequencyChart chart = new FrequencyChart(context);
//        GraphWidgetView view = new GraphWidgetView(context, chart);
//        view.setTitle(habit.getName());
//        return view;
//    }
//
//    @Override
//    protected int getDefaultHeight()
//    {
//        return 200;
//    }
//
//    @Override
//    protected int getDefaultWidth()
//    {
//        return 200;
//    }
//
//    @Override
//    protected int getLayoutId()
//    {
//        return R.layout.widget_wrapper;
//    }
//
//    @Override
//    protected PendingIntent getOnClickPendingIntent(Context context,
//                                                    Habit habit)
//    {
//        return HabitBroadcastReceiver.buildViewHabitIntent(context, habit);
//    }
//
//    @Override
//    protected void refreshCustomViewData(Context context,
//                                         View view,
//                                         Habit habit)
//    {
//        GraphWidgetView widgetView = (GraphWidgetView) view;
//        FrequencyChart chart = (FrequencyChart) widgetView.getDataView();
//
//        int color = ColorUtils.getColor(context, habit.getColor());
//
//        chart.setColor(color);
//        chart.setFrequency(habit.getRepetitions().getWeekdayFrequency());
//    }
}
