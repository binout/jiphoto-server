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

import io.github.binout.jaxrsunit.JaxrsResource;
import io.github.binout.jiphoto.Library;
import io.github.jiphoto.server.application.JiPhotoMockServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.github.jiphoto.server.LibraryMocker.mock;
import static org.fest.assertions.Assertions.assertThat;

public class LibraryResourceTest {

    private static JaxrsResource resource;

    @BeforeClass
    public static void initServer() {
        mock(buildLibrary());
        resource = new JiPhotoMockServer().resource("/library");
    }

    private static Library buildLibrary() {
        Library library = new Library();
        library.setVersion("10");
        return library;
    }

    @Test
    public void getLibrary() throws JSONException {
        String content = resource.get().content();

        assertThat(new JSONObject(content).get("version")).isEqualTo("10");
    }
}
