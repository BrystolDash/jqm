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

package com.enioka.jqm.osgi.model;

import java.io.Serializable;

import com.enioka.jqm.osgi.jdbc.DbConn;
import com.enioka.jqm.osgi.jdbc.QueryResult;

/**
 * <strong>Not part of any API - this an internal JQM class and may change without notice.</strong> <br>
 * Persistence class for storing short messages that can be created by user code to inform users or systems of its progress. Often, a
 * {@link Message} is created for each big step inside the user code.<br>
 * This is used both by queued {@link JobInstance}s and archived {@link History} elements.
 */
public class Message implements Serializable
{
    private static final long serialVersionUID = 1234454709423602792L;

    private Integer id;

    private String textMessage;

    private int ji;

    /**
     * A technical ID without any meaning. Generated by the database.
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * See {@link #getId()}
     */
    void setId(final Integer id)
    {
        this.id = id;
    }

    /**
     * The actual message.<br>
     * Max length is 1000.
     */
    public String getTextMessage()
    {
        return textMessage;
    }

    /**
     * See {@link #getTextMessage()}
     */
    public void setTextMessage(final String textMessage)
    {
        this.textMessage = textMessage;
    }

    /**
     * The ID of the corresponding JobInstance or History
     */
    public int getJi()
    {
        return ji;
    }

    /**
     * See {@link #getJi()}
     */
    public void setJi(int ji)
    {
        this.ji = ji;
    }

    /**
     * Create a new entry in the database. No commit performed.
     */
    public static int create(DbConn cnx, String textMessage, Integer jobId)
    {
        QueryResult r = cnx.runUpdate("message_insert", jobId, textMessage);

        return r.getGeneratedId();
    }
}
