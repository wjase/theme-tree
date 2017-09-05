package au.com.cybernostics.themetree.resource.resolvers;

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
/**
 * <p>TemplateExistenceChecker interface.</p>
 * Provides a method to determine if the template is able to be
 * loaded from the classpath by the view.
 * 
 * @author jason wraxall
 * @version $Id: $Id
 */
public interface TemplateExistenceChecker
{

    /**
     * <p>templateExists.</p>
     *
     * @param withName a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean templateExists(String withName);
}
