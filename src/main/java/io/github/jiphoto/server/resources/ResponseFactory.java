/*
 * Copyright 2014 Benoît Prioux
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

import io.github.binout.jiphoto.Photo;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Optional;

class ResponseFactory {

    static  <T> Response fromOptional(Optional<T> entity) {
        return (entity.isPresent() ? Response.ok(entity.get()) : Response.status(Response.Status.NOT_FOUND)).build();
    }

    static Response thumbnail(Optional<Photo> photo) {
        if (!photo.isPresent()) {
            return Response.noContent().build();
        } else {
            String thumbPath = photo.get().getThumbPath();
            return returnFile(thumbPath);
        }
    }

    static Response image(Optional<Photo> photo) {
        if (!photo.isPresent()) {
            return Response.noContent().build();
        } else {
            String imagePath = photo.get().getImagePath();
            return returnFile(imagePath);
        }
    }

    private static Response returnFile(String path) {
        String mimeType = new MimetypesFileTypeMap().getContentType(path);
        return Response.ok(new File(path), mimeType).build();
    }
}
