package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.interfaces.IPlaylistDao;
import com.thomas.spotitube.domain.Playlist;

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

                Playlist playlist = new Playlist(
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
}
