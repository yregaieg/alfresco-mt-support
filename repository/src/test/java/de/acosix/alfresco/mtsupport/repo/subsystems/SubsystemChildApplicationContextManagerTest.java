/*
 * Copyright 2016 Acosix GmbH
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
package de.acosix.alfresco.mtsupport.repo.subsystems;

import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Axel Faust, <a href="http://acosix.de">Acosix GmbH</a>
 */
public class SubsystemChildApplicationContextManagerTest
{

    @Test
    public void simplyStartAll()
    {
        try (final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:subsystem-manager-test-context.xml"))
        {
            final SubsystemChildApplicationContextManager manager = ctxt.getBean("SubsystemManagerTest",
                    SubsystemChildApplicationContextManager.class);
            Assert.assertNotNull("manager bean was not found", manager);

            final Collection<String> instanceIds = manager.getInstanceIds();
            instanceIds.forEach(id -> {
                final ApplicationContext childCtxt = manager.getApplicationContext(id);

                Assert.assertNotNull("subsystem " + id + " was not started", childCtxt);
            });
        }
    }

    @Test
    public void correctPropertyPriorities()
    {
        try (final ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("classpath:subsystem-manager-test-context.xml"))
        {
            final SubsystemChildApplicationContextManager manager = ctxt.getBean("SubsystemManagerTest",
                    SubsystemChildApplicationContextManager.class);
            Assert.assertNotNull("manager bean was not found", manager);

            final ApplicationContext inst1Ctxt = manager.getApplicationContext("inst1");
            Assert.assertNotNull("subsystem inst1 was not started", inst1Ctxt);

            final Map<?, ?> inst1Values = inst1Ctxt.getBean("values", Map.class);

            Assert.assertEquals("default value should have been overriden by alfresco-global.properties", "global-value1",
                    inst1Values.get("prop1"));
            Assert.assertEquals("default value should have been left unchanged", "value2", inst1Values.get("prop2"));
            Assert.assertEquals("missing default value should have been provided by alfresco-global.properties", "global-value3",
                    inst1Values.get("prop3"));

            final ApplicationContext inst2Ctxt = manager.getApplicationContext("inst2");
            Assert.assertNotNull("subsystem inst2 was not started", inst2Ctxt);
            final Map<?, ?> inst2Values = inst2Ctxt.getBean("values", Map.class);

            Assert.assertEquals(
                    "default value should have been overriden by alfresco-global.properties which in turn should have been overriden by extension value",
                    "extension-value1", inst2Values.get("prop1"));
            Assert.assertEquals("default value should have been left unchanged", "value2", inst2Values.get("prop2"));
            Assert.assertEquals("missing default value should have been provided by alfresco-global.properties", "global-value3",
                    inst2Values.get("prop3"));
        }
    }
}