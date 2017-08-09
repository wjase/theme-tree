package com.cybernostics.themetree.theme.resolvers;

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
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;

/**
 * This provides the active list of themes to use for locating templates and
 * assets.
 *
 * @author jason wraxall
 */
public interface CascadedThemeResolver
{

    /**
     * Resolve the current theme name set via the given request. Theme names
     * will be used to locate assets in the order provided. First match wins.
     * This allows you to define a small number of theme changes in a new theme,
     * while falling through to an existing theme for everything else.
     *
     * @param request request to be used for resolution
     * @return the current theme name
     */
    Stream<String> getCurrentThemes(HttpServletRequest request);

    /**
     * This theme name will always be appended to the others in case everything
     * else fails.
     *
     * @param name
     */
    void setDefault(String name);

}
