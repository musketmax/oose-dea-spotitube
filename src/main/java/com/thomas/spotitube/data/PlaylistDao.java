package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.interfaces.IPlaylistDao;
import com.thomas.spotitube.domain.Playlist;
import org.json.simple.JSONObject;

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
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getPlaylists);
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
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.addPlaylist);
            preparedStatement.setString(1, playlist.get("name").toString());
            preparedStatement.setString(2, playlist.get("user_id").toString());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Update playlist
     *
     * @param userId: int
     * @return boolean
     */
    @Override
    public boolean updatePlaylist(int userId, Playlist playlist) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.updatePlaylist);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

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
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.deletePlaylist);
            preparedStatement.setInt(1, playlistId);
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }
}
