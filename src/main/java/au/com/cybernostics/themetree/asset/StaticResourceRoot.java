package au.com.cybernostics.themetree.asset;

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
 * Encapsulates a root path to a static resource type. eg. css, js, img, font
 * etc
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class StaticResourceRoot
{

    private String rootPath;

    /**
     * <p>Constructor for StaticResourceRoot.</p>
     *
     * @param rootPath a {@link java.lang.String} object.
     */
    public StaticResourceRoot(String rootPath)
    {
        this.rootPath = rootPath;
    }

    /**
     * <p>Getter for the field <code>rootPath</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getRootPath()
    {
        return rootPath;
    }

}
