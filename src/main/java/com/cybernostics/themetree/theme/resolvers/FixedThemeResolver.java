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
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jason
 */
public class FixedThemeResolver implements CascadedThemeResolver
{

    private List<String> themeset;

    @Override
    public Stream<String> getCurrentThemes(HttpServletRequest request)
    {
        return themeset.stream();
    }

    public void setThemeset(List<String> themeset)
    {
        this.themeset = themeset;
    }

    @Override
    public void setDefault(String name)
    {
        throw new UnsupportedOperationException("Not supported.");
    }

}
