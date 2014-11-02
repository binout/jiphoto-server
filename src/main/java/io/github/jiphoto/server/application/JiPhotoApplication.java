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
package io.github.jiphoto.server.application;

import io.github.jiphoto.server.resources.AlbumResource;
import io.github.jiphoto.server.resources.EventResource;
import io.github.jiphoto.server.resources.LibraryResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JiPhotoApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> rrcs = new HashSet<>();
        rrcs.add(AlbumResource.class);
        rrcs.add(EventResource.class);
        rrcs.add(LibraryResource.class);
        return rrcs;
    }
}
