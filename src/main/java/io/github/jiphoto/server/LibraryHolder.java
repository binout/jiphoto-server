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
package io.github.jiphoto.server;

import io.github.binout.jiphoto.Library;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class LibraryHolder {

    private static final String IPHOTO_LIBRARY_PATH = "iphoto.library.path";

    private static Library library;

    public static Library instance() {
        if (library == null) {
            library = loadLibrary();
        }
        return library;
    }

    private static Library loadLibrary() {
        String path = fromSystemProperty().orElse(fromUserHome().get());
        try {
            return Library.fromPath(path);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Optional<String> fromUserHome() {
        String configFile = System.getProperty("user.home") + "/.jiphoto-server/config.properties";
        final Properties properties = new Properties();
        try (InputStream is = new FileInputStream(configFile) ){
            properties.load(is);
            return Optional.ofNullable(properties.getProperty(IPHOTO_LIBRARY_PATH));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private static Optional<String> fromSystemProperty() {
        return Optional.ofNullable(System.getProperty(IPHOTO_LIBRARY_PATH));
    }
}
