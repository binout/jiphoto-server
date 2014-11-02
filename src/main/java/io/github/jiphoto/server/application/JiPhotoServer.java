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

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class JiPhotoServer extends Component {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        if (args != null && args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        new JiPhotoServer(port).start();
    }

    public JiPhotoServer(int port) {
        Server server = new Server(Protocol.HTTP, port);
        getServers().add(server);

        JaxRsApplication jaxRsApplication = new JaxRsApplication(getContext());
        jaxRsApplication.add(new JiPhotoApplication());
        getDefaultHost().attach("/jiphoto-server", jaxRsApplication);
    }
}
