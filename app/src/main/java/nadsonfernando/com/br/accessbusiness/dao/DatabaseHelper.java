package nadsonfernando.com.br.accessbusiness.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import nadsonfernando.com.br.accessbusiness.model.Cliente;
import nadsonfernando.com.br.accessbusiness.model.Venda;

/**
 * Created by nadsonfernando on 21/10/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "acessbusinnes.db";
    private static final int databaseVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cliente.class);
            TableUtils.createTable(connectionSource, Venda.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Cliente.class, true);
            TableUtils.dropTable(connectionSource, Venda.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
