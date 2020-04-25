package com.thomas.spotitube.data;

import com.thomas.spotitube.data.constants.DatabaseConstants;
import com.thomas.spotitube.data.interfaces.ITrackDao;
import com.thomas.spotitube.domain.Track;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class TrackDao extends Database implements ITrackDao {

    /**
     * Get all tracks which belong to playlists
     *
     * @param playlistIds: int[]
     * @return ArrayList<Track>
     */
    @Override
    public ArrayList<Track> getTracksForPlaylists(int[] playlistIds) {
        try {
            // Convert list of playlist ID's to String and remove the array brackets on either side..
            String playlistIdSQLString = Arrays.toString(playlistIds).substring(1, Arrays.toString(playlistIds).length() - 1);

            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getTracksForPlaylists);
            preparedStatement.setString(1, playlistIdSQLString);
            ResultSet result = preparedStatement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<Track>();

            while (result.next()) {
                tracks.add(new Track(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("performer"),
                        result.getInt("duration"),
                        result.getString("album"),
                        result.getInt("playcount"),
                        result.getDate("publicationDate"),
                        result.getString("description"),
                        result.getInt("offlineAvailable") == 1
                ));
            }

            return tracks;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * Get all tracks which belong to one playlist
     *
     * @param playlistId: int
     * @return ArrayList<Track>
     */
    @Override
    public ArrayList<Track> getTracksForPlaylist(int playlistId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getTracksForPlaylist);
            preparedStatement.setInt(1, playlistId);
            ResultSet result = preparedStatement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<Track>();

            while (result.next()) {
                tracks.add(new Track(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("performer"),
                        result.getInt("duration"),
                        result.getString("album"),
                        result.getInt("playcount"),
                        result.getDate("publicationDate"),
                        result.getString("description"),
                        result.getInt("offlineAvailable") == 1
                ));

                logger.log(Level.INFO, tracks.get(0).getPublicationDate());
            }

            return tracks;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }
}
