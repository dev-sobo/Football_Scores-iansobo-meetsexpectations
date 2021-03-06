package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CollectionWidgetService extends RemoteViewsService {
    private static final String LOG_TAG = CollectionWidgetService.class.getSimpleName();

    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.SCORES_TABLE + "." + DatabaseContract.scores_table._ID,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.DATE_COL
    };

    private static final int ID_INDEX = 0;
    private static final int HOME_INDEX = 1;
    private static final int HOME_GOALS_INDEX =2;
    private static final int AWAY_INDEX = 3;
    private static final int AWAY_GOALS_INDEX = 4;
    private static final int DATE_INDEX = 5;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

     //   return new CollectionWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
        return new CollectionWidgetRemoteViewsFactory();
    }

    class CollectionWidgetRemoteViewsFactory implements
            RemoteViewsService.RemoteViewsFactory {

        private Cursor data = null;

        //CollectionWidgetRemoteViewsFactory(Context context, Intent intent) {}

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (data != null) {
                data.close();
            }
            Uri todaysScoresUri = DatabaseContract.scores_table.buildScoreWithDate();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            data = getContentResolver().query(todaysScoresUri,
                    SCORE_COLUMNS,
                    null,
                    new String[]{dateFormatter.format(c.getTime())},
                    DatabaseContract.scores_table.DATE_COL + " ASC");

            Log.d(LOG_TAG, "Cursor: " + data);

        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            //return data == null ? 0 : data.getCount();
            return data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION ||
                    data == null || !data.moveToPosition(position)) {
                Log.e(LOG_TAG, "cursor error");
                return null;
            }
            //data.moveToFirst();
            RemoteViews remoteViews = new RemoteViews(getPackageName(),
                    R.layout.collection_widget_item);

            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           // PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
            //remoteViews.setOnClickPendingIntent(R.id.collectionListViewId, pendingIntent);
            // Get all of the strings from the cursor
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("EXTRA_ITEM", position);
            remoteViews.setOnClickFillInIntent(R.id.linearLayoutCollectionWidget, fillInIntent);

             String homeTeam = data.getString(HOME_INDEX);
            Integer homeGoals = data.getInt(HOME_GOALS_INDEX);
            String awayTeam = data.getString(AWAY_INDEX);
            Integer awayGoals = data.getInt(AWAY_GOALS_INDEX);
            String matchDate = data.getString(DATE_INDEX);
            Log.d(LOG_TAG,"Match Date: " +  matchDate + " Home team: " + homeTeam + " Home Score: " + homeGoals);
            Log.d(LOG_TAG, "Match Date: " + matchDate + " Away team: " + awayTeam + " Away score: " + awayGoals);
            remoteViews.setTextViewText(R.id.homeTeamCollectionId, homeTeam);
            if (homeGoals > -1) {
                remoteViews.setTextViewText(R.id.homeGoalsCollectionId, homeGoals.toString());
            } else if (homeGoals <= -1) {
                remoteViews.setTextViewText(R.id.homeGoalsCollectionId, getString(R.string.noScore));
            }

            remoteViews.setTextViewText(R.id.awayTeamCollectionId, awayTeam);
            if (awayGoals > -1) {
                remoteViews.setTextViewText(R.id.awayGoalsCollectionId, awayGoals.toString());
            } else if (awayGoals <= -1) {
                remoteViews.setTextViewText(R.id.awayGoalsCollectionId,  getString(R.string.noScore));
            }


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            if (data.moveToPosition(i)){
                return data.getLong(ID_INDEX);
            }
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
