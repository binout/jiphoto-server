/*
 * Copyright 2013 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.jiphoto.server.rest;

import io.github.binout.jiphoto.Album;
import io.github.binout.jiphoto.Library;
import io.github.binout.jiphoto.Photo;
import io.github.jiphoto.server.LibraryHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.github.jiphoto.server.rest.ResponseFactory.fromOptional;
import static io.github.jiphoto.server.rest.ResponseFactory.image;
import static io.github.jiphoto.server.rest.ResponseFactory.thumbnail;
import static javax.ws.rs.core.Response.status;

@Path("albums")
@Produces("application/json;charset=UTF-8")
public class AlbumResource {

    private Library library = LibraryHolder.instance();

    @GET
    public List<Album> albums() {
        return library.getAlbums();
    }

    @GET
    @Path("{albumName}")
    public Response album(@PathParam("albumName") String name) {
        return fromOptional(library.getAlbum(name));
    }

    @GET
    @Path("{albumName}/photos")
    public List<Photo> photos(@PathParam("albumName") String name) {
        Optional<Album> album = library.getEvent(name);
        return album.isPresent() ? album.get().getPhotos() : Collections.<Photo>emptyList();
    }

    @GET
    @Path("{albumName}/photos/{caption}")
    public Response photo(@PathParam("albumName") String name, @PathParam("caption") String caption) {
        Optional<Album> album = library.getAlbum(name);
        if (album.isPresent()) {
            Optional<Photo> photo = album.get().getPhoto(caption);
            if (photo.isPresent()) {
                return Response.ok(photo.get()).build();
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{albumName}/photos/{caption}/thumbnail")
    public Response getThumbnail(@PathParam("albumName") String name, @PathParam("caption") String caption) {
        Optional<Album> album = library.getAlbum(name);
        if (album.isPresent()) {
            Optional<Photo> photo = album.get().getPhoto(caption);
            if (photo.isPresent()) {
                return thumbnail(photo);
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{albumName}/photos/{caption}/image")
    public Response getImage(@PathParam("albumName") String name, @PathParam("caption") String caption) {
        Optional<Album> album = library.getAlbum(name);
        if (album.isPresent()) {
            Optional<Photo> photo = album.get().getPhoto(caption);
            if (photo.isPresent()) {
                return image(photo);
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

}
