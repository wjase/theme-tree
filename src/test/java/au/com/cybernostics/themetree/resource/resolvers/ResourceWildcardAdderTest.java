package au.com.cybernostics.themetree.resource.resolvers;

/*-
 * #%L
 * ThemeTree
 * %%
 * Copyright (C) 1992 - 2017 Cybernostics Pty Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 *
 * @author jason
 */

@RunWith(BlockJUnit4ClassRunner.class)
public class ResourceWildcardAdderTest {
    
    public ResourceWildcardAdderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addVersionWildcard method, of class ResourceWildcardAdder.
     */
    @Test
    public void testAddVersionWildcard() {
        assertThat(ResourceWildcardAdder.addVersionWildcard("css/style.css"), is("css/style*.css"));
        assertThat(ResourceWildcardAdder.addVersionWildcard("css/style.*"), is("css/style*.*"));
        assertThat(ResourceWildcardAdder.addVersionWildcard("css/style*.css"), is("css/style*.css"));
        assertThat(ResourceWildcardAdder.addVersionWildcard("css/app.js"), is("css/app*.js"));
        assertThat(ResourceWildcardAdder.addVersionWildcard("css/*.*"), is("css/*.*"));
    }
    
}
