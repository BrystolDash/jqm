/**
 * Copyright © 2013 enioka. All rights reserved
 * Authors: Marc-Antoine GOUILLART (marc-antoine.gouillart@enioka.com)
 *          Pierre COPPEE (pierre.coppee@enioka.com)
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

package com.enioka.jqm.old.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * <strong>Not part of any API - this an internal JQM class and may change without notice.</strong> <br>
 * Persistence class for storing the parameters of the JNDI object resources from the {@link JndiObjectResource} table. Parameters are
 * simple key/value String pairs. The meaning of parameters is given by the factory defined in {@link JndiObjectResource#getFactory()}.
 */
public class JndiObjectResourceParameter implements Serializable
{
    private static final long serialVersionUID = -8023896508793524111L;

    private int id;

    private String key;
    private String value;

    private int resource;

    private Calendar lastModified;

    /**
     * A technical ID without any meaning. Generated by the database.
     */
    public int getId()
    {
        return id;
    }

    /**
     * See {@link #getId()}
     */
    void setId(final int id)
    {
        this.id = id;
    }

    /**
     * Name of the parameter. E.g.: QMGR for the queue manager name with MQSeries.
     */
    public String getKey()
    {
        return key;
    }

    /**
     * See {@link #getKey()}
     */
    public void setKey(final String key)
    {
        this.key = key;
    }

    /**
     * Value of the parameter. E.g.: <code>QM.POUET</code> (for a MQSeries parameter which key is QMGR, i.e. the message broker name)
     */
    public String getValue()
    {
        return value;
    }

    /**
     * See {@link #getValue()}
     */
    public void setValue(final String value)
    {
        this.value = value;
    }

    /**
     * The JNDI resource to which this parameter belongs.
     */
    public int getResource()
    {
        return resource;
    }

    /**
     * See {@link #getResource()}
     */
    public void setResource(final int resource)
    {
        this.resource = resource;
    }

    /**
     * When the object was last modified. Read only.
     */
    public Calendar getLastModified()
    {
        return lastModified;
    }

    /**
     * See {@link #getLastModified()}
     */
    protected void setLastModified(Calendar lastModified)
    {
        this.lastModified = lastModified;
    }
}
