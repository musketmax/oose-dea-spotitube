package com.thomas.spotitube.data;

import com.thomas.spotitube.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    @InjectMocks
    private UserDao userDao;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private UserDao userDaoMock;

    private User user;
    private String query;
    private String token;
    private int userId;

    @Before
    public void setup() throws SQLException {
        query = "Some SQL Query";
        token = "1234-1234-1234-1234";
        userId = 1;

        user = new User(1, "admin", token);
        user.setPassword("password");

        userDaoMock = Mockito.spy(userDao);

        doReturn(query).when(userDaoMock).getQuery(anyString());
        doReturn(c).when(userDaoMock).getConnection();

        when(c.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(userId);
        when(rs.getString("username")).thenReturn(user.getUser());
        when(rs.getString("password")).thenReturn(user.getPassword());
        when(rs.getString("token")).thenReturn(user.getToken());
    }
    
    @Test
    public void init() {
        assertNotNull(userDao);
        assertNotNull(c);
        assertNotNull(stmt);
        assertNotNull(rs);
    }

    @Test
    public void TestGetUserByUsername() throws SQLException {
        doReturn(token).when(userDaoMock).addToken(userId);

        User result = userDaoMock.getUserByUsername(user.getUser());

        verify(userDaoMock).removeToken(userId);
        verify(userDaoMock).addToken(userId);
        verify(rs, times(3)).getInt("id");
        verify(rs).getString("username");
        verify(rs).getString("password");
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUser(), result.getUser());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getToken(), result.getToken());
    }

    @Test
    public void TestGetUserByToken() throws SQLException {
        User result = userDaoMock.getUserByToken(token);

        verify(rs).getInt("id");
        verify(rs).getString("username");
        verify(rs).getString("token");
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUser(), result.getUser());
        assertEquals(user.getToken(), result.getToken());
    }

    @Test
    public void TestDoesTokenExist() throws SQLException {
        boolean doesExist = userDaoMock.doesTokenExist(token);

        verify(rs).next();
        assertTrue(doesExist);
    }

    @Test
    public void TestRemoveToken() throws SQLException {
        userDaoMock.removeToken(userId);

        verify(stmt).executeUpdate();
    }

    @Test
    public void TestAddToken() throws SQLException {
        String result = userDaoMock.addToken(userId);

        verify(stmt).executeUpdate();
        assertNotNull(result);
    }
}
