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
    public int getTotalDurationInSeconds(int[] playlistIds) {
        try {
            // Convert list of playlist ID's to String and remove the array brackets on either side..
            String playlistIdSQLString = Arrays.toString(playlistIds).substring(1, Arrays.toString(playlistIds).length() - 1);

            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getTotalTrackDuration);
            preparedStatement.setString(1, playlistIdSQLString);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return result.getInt("duration");
            }

            return 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return 0;
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
                tracks.add(makeNewTrack(result, true));
            }

            return tracks;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * Get all available tracks for playlist
     *
     * @param playlistId: int
     * @return ArrayList<Track>
     */
    @Override
    public ArrayList<Track> getAvailableTracksForPlaylist(int playlistId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.getAvailableTracksForPlaylist);
            preparedStatement.setInt(1, playlistId);
            ResultSet result = preparedStatement.executeQuery();

            ArrayList<Track> tracks = new ArrayList<Track>();

            while (result.next()) {
                tracks.add(makeNewTrack(result, false));
            }

            return tracks;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return null;
        }
    }

    /**
     * Add a track to playlist
     *
     * @param playlistId: int
     * @param track:      Track
     * @return boolean
     */
    @Override
    public boolean addTrackToPlaylist(int playlistId, Track track) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.addTrackToPlaylist);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, track.getId());
            preparedStatement.setInt(3, track.isOfflineAvailable() ? 1 : 0);
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Delete track from playlist
     *
     * @param playlistId: int
     * @param trackId:    int
     * @return boolean
     */
    @Override
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.deleteTrackFromPlaylist);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "MySQL error: " + singletonDatabaseProperties.connectionString(), e);
            return false;
        }
    }

    /**
     * Create a new Track instance from the ResultSet instance
     *
     * @param result: ResultSet
     * @return Track
     */
    private Track makeNewTrack(ResultSet result, boolean includeOfflineAvailable) throws SQLException {
        Track track = new Track();
        track.setProperties(
                result.getInt("id"),
                result.getString("title"),
                result.getString("performer"),
                result.getInt("duration"),
                result.getString("album"),
                result.getInt("playcount"),
                result.getDate("publicationDate"),
                result.getString("description")
        );

        if (includeOfflineAvailable) {
            track.setOfflineAvailable(result.getInt("offline_available") == 1);
        }

        return track;
    }
}
