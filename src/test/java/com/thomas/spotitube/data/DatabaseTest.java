package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.properties.SingletonDatabaseProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {
    @Mock
    private Database database;

    @Mock
    private SingletonDatabaseProperties props;

    private String key;

    @Before
    public void setup() {
        key = DatabaseConstants.GET_PLAYLISTS;
    }

    @Test
    public void init() {
        assertNotNull(database);
        assertNotNull(props);
    }

    @Test
    public void TestGetDriverFromProperties() {
        database.loadDriver(props);
    }

    @Test
    public void TestConnectionToDatabase() {
        database.getConnection();
    }

    @Test
    public void TestGetQuery() {
        database.getQuery(key);
    }
}
