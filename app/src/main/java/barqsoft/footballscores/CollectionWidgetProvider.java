package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by ian on 9/23/2015.
 */
public class CollectionWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = CollectionWidgetProvider.class.getSimpleName();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // go thru all of the app widgets to be updated with remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Log.v(LOG_TAG, "collection");
            // This intent will start the CollectionWidgetService
            // this intent will have the appwidgetId for an extra
            Intent intent = new Intent(context, CollectionWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // RemoteViews object for the collection widget's layout
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.collection_widget );
            // Now connect the remoteviews object to use a remoteviews adapter.
            // this connects to a RemoteViewsService, described by the intent above.
            remoteViews.setRemoteAdapter(R.id.collectionListViewId, intent);
            // set the empty view
            remoteViews.setEmptyView(R.id.collectionListViewId, R.id.emptyView);

            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);

        }
    }
}
