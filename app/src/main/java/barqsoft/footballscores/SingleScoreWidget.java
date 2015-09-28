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
       // Intent clickingIntent = new Intent(context, MainActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickingIntent, 0);
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.single_score_widget);
       // views.setOnClickPendingIntent(R.id.relativeLayoutSingle, pendingIntent);
        context.startService(new Intent(context,SingleItemWidgetService.class));
        Log.v("SingleScoreWidget", "TEST");
    }
}

