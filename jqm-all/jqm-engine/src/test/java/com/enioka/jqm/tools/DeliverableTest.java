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
package com.enioka.jqm.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.enioka.jqm.api.JobRequest;
import com.enioka.jqm.api.JqmClientFactory;
import com.enioka.jqm.jpamodel.JobDefParameter;
import com.enioka.jqm.test.helpers.CreationTools;
import com.enioka.jqm.test.helpers.TestHelpers;

public class DeliverableTest extends JqmBaseTest
{
    @Before
    public void before() throws IOException
    {
        File jar = FileUtils.listFiles(new File("../jqm-ws/target/"), new String[] { "war" }, false).iterator().next();
        FileUtils.copyFile(jar, new File("./webapp/jqm-ws.war"));
    }

    /**
     * Retrieve all the files created by a job, with auth, without SSL
     */
    @Test
    public void testGetDeliverables() throws Exception
    {
        Helpers.setSingleParam("disableWsApi", "false", em);
        Helpers.setSingleParam("enableWsApiAuth", "true", em);
        Helpers.setSingleParam("enableWsApiSsl", "false", em);

        ArrayList<JobDefParameter> jdargs = new ArrayList<JobDefParameter>();
        JobDefParameter jdp = CreationTools.createJobDefParameter("filepath", TestHelpers.node.getDlRepo(), em);
        JobDefParameter jdp2 = CreationTools.createJobDefParameter("fileName", "jqm-test-deliverable1.txt", em);
        jdargs.add(jdp);
        jdargs.add(jdp2);

        CreationTools.createJobDef(null, true, "App", jdargs, "jqm-tests/jqm-test-deliverable/target/test.jar", TestHelpers.qVip, 42,
                "getDeliverables", null, "Franquin", "ModuleMachin", "other", "other", false, em);

        JobRequest j = new JobRequest("getDeliverables", "TestUser");
        int id = JqmClientFactory.getClient().enqueue(j);

        addAndStartEngine();
        TestHelpers.waitFor(1, 10000, em);

        List<InputStream> tmp = JqmClientFactory.getClient().getJobDeliverablesContent(id);
        Assert.assertEquals(1, tmp.size());

        // Assert.assertTrue(tmp.get(0).available() > 0);
        String res = IOUtils.toString(tmp.get(0));
        Assert.assertTrue(res.startsWith("Hello World!"));

        tmp.get(0).close();
    }

    /**
     * Retrieve a remote file with authentication, without SSL.
     */
    @Test
    public void testGetOneDeliverableWithAuth() throws Exception
    {
        Helpers.setSingleParam("disableWsApi", "false", em);
        Helpers.setSingleParam("enableWsApiAuth", "true", em);
        Helpers.setSingleParam("enableWsApiSsl", "false", em);

        ArrayList<JobDefParameter> jdargs = new ArrayList<JobDefParameter>();
        JobDefParameter jdp = CreationTools.createJobDefParameter("filepath", TestHelpers.node.getDlRepo(), em);
        JobDefParameter jdp2 = CreationTools.createJobDefParameter("fileName", "jqm-test-deliverable2.txt", em);
        jdargs.add(jdp);
        jdargs.add(jdp2);

        CreationTools.createJobDef(null, true, "App", jdargs, "jqm-tests/jqm-test-deliverable/target/test.jar", TestHelpers.qVip, 42,
                "MarsuApplication", null, "Franquin", "ModuleMachin", "other", "other", false, em);
        int jobId = JobRequest.create("MarsuApplication", "Franquin").submit();

        addAndStartEngine();
        TestHelpers.waitFor(1, 10000, em);

        File f = new File(TestHelpers.node.getDlRepo() + "jqm-test-deliverable2.txt");
        Assert.assertEquals(false, f.exists()); // file should have been moved

        List<com.enioka.jqm.api.Deliverable> files = JqmClientFactory.getClient().getJobDeliverables(jobId);
        Assert.assertEquals(1, files.size());

        InputStream tmp = JqmClientFactory.getClient().getDeliverableContent(files.get(0));

        Assert.assertTrue(tmp.available() > 0);
        String res = IOUtils.toString(tmp);
        Assert.assertTrue(res.startsWith("Hello World!"));

        tmp.close();
    }

    /**
     * Same as above, except authentication is disabled as well as SSL.
     */
    @Test
    public void testGetOneDeliverableWithoutAuth() throws Exception
    {
        Helpers.setSingleParam("disableWsApi", "false", em);
        Helpers.setSingleParam("enableWsApiAuth", "false", em);
        Helpers.setSingleParam("enableWsApiSsl", "false", em);

        ArrayList<JobDefParameter> jdargs = new ArrayList<JobDefParameter>();
        JobDefParameter jdp = CreationTools.createJobDefParameter("filepath", TestHelpers.node.getDlRepo(), em);
        JobDefParameter jdp2 = CreationTools.createJobDefParameter("fileName", "jqm-test-deliverable3.txt", em);
        jdargs.add(jdp);
        jdargs.add(jdp2);

        CreationTools.createJobDef(null, true, "App", jdargs, "jqm-tests/jqm-test-deliverable/target/test.jar", TestHelpers.qVip, 42,
                "MarsuApplication", null, "Franquin", "ModuleMachin", "other", "other", false, em);
        int jobId = JobRequest.create("MarsuApplication", "Franquin").submit();

        addAndStartEngine();
        TestHelpers.waitFor(1, 10000, em);

        File f = new File(TestHelpers.node.getDlRepo() + "jqm-test-deliverable2.txt");
        Assert.assertEquals(false, f.exists()); // file should have been moved

        List<com.enioka.jqm.api.Deliverable> files = JqmClientFactory.getClient().getJobDeliverables(jobId);
        Assert.assertEquals(1, files.size());

        InputStream tmp = JqmClientFactory.getClient().getDeliverableContent(files.get(0));
        Assert.assertTrue(tmp.available() > 0);
        String res = IOUtils.toString(tmp);
        Assert.assertTrue(res.startsWith("Hello World!"));

        tmp.close();
    }

    /**
     * Retrieve a remote file with authentication, with SSL.
     */
    @Test
    public void testGetOneDeliverableWithAuthWithSsl() throws Exception
    {
        Helpers.setSingleParam("disableWsApi", "false", em);
        Helpers.setSingleParam("enableWsApiAuth", "true", em);
        Helpers.setSingleParam("enableWsApiSsl", "true", em);

        JqmClientFactory.resetClient(null);
        Properties p = new Properties();
        p.put("com.enioka.jqm.ws.truststoreFile", "./conf/trusted.jks");
        p.put("com.enioka.jqm.ws.truststorePass", "SuperPassword");
        JqmClientFactory.setProperties(p);

        ArrayList<JobDefParameter> jdargs = new ArrayList<JobDefParameter>();
        JobDefParameter jdp = CreationTools.createJobDefParameter("filepath", TestHelpers.node.getDlRepo(), em);
        JobDefParameter jdp2 = CreationTools.createJobDefParameter("fileName", "jqm-test-deliverable2.txt", em);
        jdargs.add(jdp);
        jdargs.add(jdp2);

        CreationTools.createJobDef(null, true, "App", jdargs, "jqm-tests/jqm-test-deliverable/target/test.jar", TestHelpers.qVip, 42,
                "MarsuApplication", null, "Franquin", "ModuleMachin", "other", "other", false, em);
        int jobId = JobRequest.create("MarsuApplication", "Franquin").submit();

        addAndStartEngine();
        TestHelpers.waitFor(1, 10000, em);

        File f = new File(TestHelpers.node.getDlRepo() + "jqm-test-deliverable2.txt");
        Assert.assertEquals(false, f.exists()); // file should have been moved

        List<com.enioka.jqm.api.Deliverable> files = JqmClientFactory.getClient().getJobDeliverables(jobId);
        Assert.assertEquals(1, files.size());

        InputStream tmp = JqmClientFactory.getClient().getDeliverableContent(files.get(0));
        Assert.assertTrue(tmp.available() > 0);
        String res = IOUtils.toString(tmp);
        Assert.assertTrue(res.startsWith("Hello World!"));

        tmp.close();
    }

    /**
     * This test is DB only - no simple service use
     */
    @Test
    public void testGetAllDeliverables() throws Exception
    {
        ArrayList<JobDefParameter> jdargs = new ArrayList<JobDefParameter>();
        JobDefParameter jdp = CreationTools.createJobDefParameter("filepath", TestHelpers.node.getDlRepo(), em);
        JobDefParameter jdp2 = CreationTools.createJobDefParameter("fileName", "jqm-test-deliverable4.txt", em);
        jdargs.add(jdp);
        jdargs.add(jdp2);

        CreationTools.createJobDef(null, true, "App", jdargs, "jqm-tests/jqm-test-deliverable/target/test.jar", TestHelpers.qVip, 42,
                "MarsuApplication", null, "Franquin", "ModuleMachin", "other", "other", false, em);
        int jobId = JobRequest.create("MarsuApplication", "Franquin").submit();

        addAndStartEngine();
        TestHelpers.waitFor(1, 10000, em);

        List<com.enioka.jqm.api.Deliverable> tmp = JqmClientFactory.getClient().getJobDeliverables(jobId);
        Assert.assertEquals(1, tmp.size());
    }
}
