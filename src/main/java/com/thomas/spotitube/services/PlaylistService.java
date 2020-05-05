package com.thomas.spotitube.services;

import com.thomas.spotitube.data.constants.HttpMessageConstants;
import com.thomas.spotitube.domain.Playlist;
import com.thomas.spotitube.domain.Track;
import com.thomas.spotitube.domain.User;
import com.thomas.spotitube.exceptions.ServerErrorException;
import com.thomas.spotitube.exceptions.TokenInvalidException;
import com.thomas.spotitube.logic.interfaces.IPlaylistLogic;
import com.thomas.spotitube.logic.interfaces.ITrackLogic;
import com.thomas.spotitube.logic.interfaces.IUserLogic;
import com.thomas.spotitube.services.interfaces.IPlaylistService;
import org.json.simple.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class PlaylistService implements IPlaylistService {
    @Inject
    private IUserLogic userLogic;

    @Inject
    private IPlaylistLogic playlistLogic;

    @Inject
    private ITrackLogic trackLogic;

    @Override
    public Response get(String token) {
        try {
            userLogic.validateToken(token);
            User user = userLogic.getUser(token);

            JSONObject playlists = playlistLogic.getPlaylistsForUser(user.getId());

            return Response
                    .status(Response.Status.OK)
                    .entity(playlists)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        }
    }

    @Override
    public Response create(String token, Playlist playlist) {
        try {
            userLogic.validateToken(token);
            User user = userLogic.getUser(token);

            JSONObject playlists = playlistLogic.addPlaylist(user.getId(), playlist);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(playlists)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        } catch (ServerErrorException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response update(String token, int playlistId, Playlist playlist) {
        try {
            userLogic.validateToken(token);
            User user = userLogic.getUser(token);

            JSONObject playlists = playlistLogic.updatePlaylist(user.getId(), playlistId, playlist);

            return Response
                    .status(Response.Status.OK)
                    .entity(playlists)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        } catch (ServerErrorException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response delete(String token, int playlistId) {
        try {
            userLogic.validateToken(token);
            User user = userLogic.getUser(token);

            JSONObject playlists = playlistLogic.deletePlaylist(user.getId(), playlistId);

            return Response
                    .status(Response.Status.OK)
                    .entity(playlists)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        } catch (ServerErrorException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response tracks(String token, int playlistId) {
        try {
            userLogic.validateToken(token);

            JSONObject tracks = trackLogic.getTracksForPlaylist(playlistId);

            return Response
                    .status(Response.Status.OK)
                    .entity(tracks)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        }
    }

    @Override
    public Response addTrack(String token, int playlistId, Track track) {
        try {
            userLogic.validateToken(token);

            JSONObject tracks = trackLogic.addTrackToPlaylist(playlistId, track);

            return Response
                    .status(Response.Status.OK)
                    .entity(tracks)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        } catch (ServerErrorException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response deleteTrack(String token, int playlistId, int trackId) {
        try {
            userLogic.validateToken(token);

            JSONObject tracks = trackLogic.deleteTrackFromPlaylist(playlistId, trackId);

            return Response
                    .status(Response.Status.OK)
                    .entity(tracks)
                    .build();
        } catch (TokenInvalidException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(HttpMessageConstants.NOT_AUTHORIZED)
                    .build();
        } catch (ServerErrorException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(HttpMessageConstants.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
