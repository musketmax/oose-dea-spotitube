package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.interfaces.IPlaylistDao;
import com.thomas.spotitube.domain.Playlist;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class PlaylistDao extends Database implements IPlaylistDao {

    /**
     * Get all playlists
     *
     * @param userId: int
     * @return ArrayList<Playlist>
     */
    @Override
    public ArrayList<Playlist> getPlaylists(int userId) {
        try {
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.GET_PLAYLISTS);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            ArrayList<Playlist> playlists = new ArrayList<Playlist>();

            while (result.next()) {
                boolean isOwner = result.getInt("user_id") == userId;

                Playlist playlist = new Playlist();
                playlist.setProperties(
                        result.getInt("id"),
                        result.getString("name"),
                        isOwner
                );

                playlists.add(playlist);
            }

            preparedStatement.close();
            connection.close();

            return playlists;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * Create a new playlist
     *
     * @param userId:   int
     * @param playlist: JSONObject
     * @return boolean
     */
    @Override
    public boolean addPlaylist(int userId, JSONObject playlist) {
        try {
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.ADD_PLAYLIST);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playlist.get("name").toString());
            preparedStatement.setString(2, playlist.get("user_id").toString());
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Update playlist
     *
     * @param userId:     int
     * @param playlistId: int
     * @param playlist:   Playlist
     * @return boolean
     */
    @Override
    public boolean updatePlaylist(int userId, int playlistId, Playlist playlist) {
        try {
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.UPDATE_PLAYLIST);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlistId);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Delete playlist
     *
     * @param playlistId: int
     * @return boolean
     */
    @Override
    public boolean deletePlaylist(int playlistId) {
        try {
            Connection connection = getConnection();

            String query = getQuery(DatabaseConstants.DELETE_PLAYLIST);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, playlistId);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }
}
