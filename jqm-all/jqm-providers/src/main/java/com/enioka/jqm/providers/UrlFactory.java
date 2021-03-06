/**
 * Copyright © 2013 enioka. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.enioka.jqm.providers;

import java.net.URL;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class UrlFactory implements ObjectFactory
{
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception
    {
        String url = null;
        if (obj instanceof Reference)
        {
            Reference resource = (Reference) obj;
            if (resource.get("URL") != null)
            {
                url = (String) resource.get("URL").getContent();
            }
        }
        else if (environment.containsKey("URL"))
        {
            url = (String) environment.get("URL");
        }

        if (url == null)
        {
            throw new NamingException("Resource does not have a valid URL parameter");
        }

        return new URL(url);
    }
}
