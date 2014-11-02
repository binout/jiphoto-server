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

import io.github.binout.jiphoto.Library;
import io.github.binout.jiphoto.Photo;
import io.github.jiphoto.server.LibraryHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static io.github.jiphoto.server.resources.ResponseFactory.*;

@Path("/library")
@Produces("application/json;charset=UTF-8")
public class LibraryResource {

    private Library library = LibraryHolder.instance();

    @GET
    public Library library() {
        return library;
    }

    @GET
    @Path("photos")
    public List<Photo> photos() {
        return library.getPhotos();
    }

    @GET
    @Path("photos/{caption}")
    public Response photo(@PathParam("caption") String caption) {
        return fromOptional(library.getPhoto(caption));
    }

    @GET
    @Path("photos/{caption}/thumbnail")
    public Response getThumbnail(@PathParam("caption") String caption) {
        return thumbnail(library.getPhoto(caption));
    }

    @GET
    @Path("photos/{caption}/image")
    public Response getImage(@PathParam("caption") String caption) {
        return image(library.getPhoto(caption));
    }

}
