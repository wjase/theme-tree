package au.com.cybernostics.themetree.theme.persistence;

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
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simply stores the resolved themes in an application wide variable. Note: This
 * means all clients get this theme which doesn't allow you to test new themes
 * on a small selection of users.
 *
 * For that, try CookieThemePersistence or SessionThemePersistence
 *
 * @author jason wraxall
 * @version $Id: $Id
 */
public class ApplicationThemePersistence implements ThemePersistence
{

    Optional<List<String>> themes = Optional.empty();

    /** {@inheritDoc} */
    @Override
    public Optional<Stream<String>> get(HttpServletRequest request)
    {
        return themes.map(l -> l.stream());
    }

    /** {@inheritDoc} */
    @Override
    public void put(String themes,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        this.themes = Optional.of(asList(themes.split(",")));
    }

    /** {@inheritDoc} */
    @Override
    public void clear(HttpServletRequest request, HttpServletResponse response)
    {
        themes = Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public void clearAll()
    {
        themes = Optional.empty();
    }

}
