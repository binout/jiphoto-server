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
package io.github.jiphoto.server.resources;

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

import static io.github.jiphoto.server.resources.ResponseFactory.fromOptional;
import static io.github.jiphoto.server.resources.ResponseFactory.image;
import static io.github.jiphoto.server.resources.ResponseFactory.thumbnail;
import static javax.ws.rs.core.Response.status;

@Path("/library/events")
@Produces("application/json;charset=UTF-8")
public class EventResource {

    private Library library = LibraryHolder.instance();

    @GET
    public List<Album> events() {
        return library.getEvents();
    }

    @GET
    @Path("{eventName}")
    public Response event(@PathParam("eventName") String name) {
        return fromOptional(library.getEvent(name));
    }

    @GET
    @Path("{eventName}/photos")
    public List<Photo> photos(@PathParam("eventName") String name) {
        Optional<Album> event = library.getEvent(name);
        if (event.isPresent()) {
            return event.get().getPhotos();
        }
        return Collections.emptyList();
    }

    @GET
    @Path("{eventName}/photos/{caption}")
    public Response photo(@PathParam("eventName") String name, @PathParam("caption") String caption) {
        Optional<Album> event = library.getEvent(name);
        if (event.isPresent()) {
            Optional<Photo> photo = event.get().getPhoto(caption);
            if (photo.isPresent()) {
                return Response.ok(photo.get()).build();
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{eventName}/photos/{caption}/thumbnail")
    public Response getThumbnail(@PathParam("eventName") String name, @PathParam("caption") String caption) {
        Optional<Album> event = library.getEvent(name);
        if (event.isPresent()) {
            Optional<Photo> photo = event.get().getPhoto(caption);
            if (photo.isPresent()) {
                return thumbnail(photo);
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{eventName}/photos/{caption}/image")
    public Response getImage(@PathParam("eventName") String name, @PathParam("caption") String caption) {
        Optional<Album> event = library.getEvent(name);
        if (event.isPresent()) {
            Optional<Photo> photo = event.get().getPhoto(caption);
            if (photo.isPresent()) {
                return image(photo);
            }
        }
        return status(Response.Status.NOT_FOUND).build();
    }

}
