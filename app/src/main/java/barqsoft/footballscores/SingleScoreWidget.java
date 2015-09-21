package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Implementation of App Widget functionality.
 */
public class SingleScoreWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        //for (int appWidgetId : appWidgetIds) {
          //  updateAppWidget(context, appWidgetManager, appWidgetId);
        //}
        context.startService(new Intent(context,SingleItemWidgetService.class));
        Log.v("SingleScoreWidget", "TEST");
    }

/*
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        context.startService(new Intent(context,SingleItemWidgetService.class));
    }*/

    // TODO: refactor this code into a service?
    // Update the textViews with home teams, away teams, and scores.
    // From Database?
    /*static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.single_score_widget);
        views.setTextViewText(R.id.scoreTextWidgetId, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }*/
}

