package au.com.cybernostics.themetree.config;

/*
 * #%L
 * theme-tree
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
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * <p>SpringTemplateEngineConfigurer interface.</p>
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public interface SpringTemplateEngineConfigurer
{

    /**
     * <p>configure.</p>
     *
     * @param engine a {@link org.thymeleaf.spring4.SpringTemplateEngine} object.
     */
    void configure(SpringTemplateEngine engine);
}
