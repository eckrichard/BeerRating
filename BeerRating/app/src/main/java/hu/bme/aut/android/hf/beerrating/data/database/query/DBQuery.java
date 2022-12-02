package hu.bme.aut.android.hf.beerrating.data.database.query;

import hu.bme.aut.android.hf.beerrating.data.database.dbHelper;

public class DBQuery {
    private dbHelper dbHelper;

    public DBQuery(dbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public dbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(dbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
