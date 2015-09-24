package barqsoft.footballscores;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

// TODO: ADD A BUTTON TO CHANGE THE GAME.
// TODO: ADD A COLLECTIONS WIDGET WITH CONFIG ACTIVITY TO CHOOSE THE DAY

/**
 * Created by ian on 9/13/2015.
 */
public class SingleItemWidgetService extends IntentService {
   private static final String LOG_TAG = SingleItemWidgetService.class.getSimpleName();

    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.DATE_COL
    };

    private static final int HOME_INDEX = 0;
    private static final int HOME_GOALS_INDEX = 1;
    private static final int AWAY_INDEX = 2;
    private static final int AWAY_GOALS_INDEX = 3;
    private static final int DATE_INDEX = 4;


    public SingleItemWidgetService() {
        super("SingleItemWidgetService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, SingleScoreWidget.class));
        Log.v(LOG_TAG, "SERVICE TEST");
        int appWidget = appWidgetIds[0];

        // Get scores data from ContentProvider (get a Cursor)
        Cursor scoresData = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,
                SCORE_COLUMNS, null, null, DatabaseContract.scores_table.HOME_COL + " ASC");

        if (scoresData == null) {
            Log.e(LOG_TAG, getString(R.string.NullCursorException));
            return;
        }
        if (!scoresData.moveToFirst()) {
            Log.e(LOG_TAG, getString(R.string.CursorCouldNotGoToFirst));
            scoresData.close();
            return;
        }
        scoresData.moveToLast();
        // Get the specific data in the columns in the Cursor

        String homeTeam = scoresData.getString(HOME_INDEX);
        Integer homeGoals = scoresData.getInt(HOME_GOALS_INDEX);
        String awayTeam = scoresData.getString(AWAY_INDEX);
        Integer awayGoals = scoresData.getInt(AWAY_GOALS_INDEX);
        int databaseDate = scoresData.getInt(DATE_INDEX);
        scoresData.close();
        Log.v(LOG_TAG, "HOME TEAM: " + homeTeam);
        Log.v(LOG_TAG, "HOME SCORE: " + homeGoals);
        Log.v(LOG_TAG, "away team: " + awayTeam);
        Log.v(LOG_TAG, "awayh score: " + awayGoals);
        Log.v(LOG_TAG, "date" + databaseDate);


        RemoteViews views = new RemoteViews(getPackageName(), R.layout.single_score_widget);

        views.setTextViewText(R.id.homeTeamId, homeTeam);
        if (homeGoals > -1) {
            views.setTextViewText(R.id.homeScoreTextId, homeGoals.toString());
            views.setContentDescription(R.id.homeScoreTextId, homeGoals.toString());
        } else {
            views.setContentDescription(R.id.homeScoreTextId, getString(R.string.NoScoreContentDescrip));
        }
        views.setTextViewText(R.id.awayTeamId, awayTeam);
        if (awayGoals > -1){
            views.setTextViewText(R.id.awayScoreTextId, awayGoals.toString());
            views.setContentDescription(R.id.awayScoreTextId, awayGoals.toString());
        } else {
            views.setContentDescription(R.id.awayScoreTextId, getString(R.string.NoScoreContentDescrip));
        }

        appWidgetManager.updateAppWidget(appWidgetIds, views);

    }
}
