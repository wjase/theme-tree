package au.com.cybernostics.themetree.theme.resolvers;

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
import au.com.cybernostics.themetree.theme.sources.MutableCandidateThemeSource;
import au.com.cybernostics.themetree.theme.persistence.NoThemePersistence;
import au.com.cybernostics.themetree.theme.persistence.ThemePersistence;
import au.com.cybernostics.themetree.theme.sources.DefaultCandidateThemeSource;
import au.com.cybernostics.themetree.theme.sources.ThemesUpdatedEvent;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.stream.Stream.concat;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;

/**
 * The Multi-candidate theme resolver figures our which themes are active for
 * the current user.
 *
 * @author jason wraxall
 */
public class MultiCandidateThemeResolver implements CascadedThemeResolver, ApplicationListener<ThemesUpdatedEvent>
{

    private MutableCandidateThemeSource candidateThemeSource = new DefaultCandidateThemeSource();
    private List<String> defaultName = asList("");
    private ThemePersistence persistence = new NoThemePersistence();

    @Override
    public Stream<String> getCurrentThemes(HttpServletRequest request)
    {
        Optional<Stream<String>> themes = persistence.get(request);
        if (themes.isPresent())
        {
            return themes.get();
        }

        return concat(candidateThemeSource.getCandidateThemes()
                .filter(theme -> theme.isActive(request))
                .map(r -> r.getName(request)),
                defaultName.stream());
    }

    @Override
    public void onApplicationEvent(ThemesUpdatedEvent e)
    {
        persistence.clearAll();
    }

    public MutableCandidateThemeSource getThemeSource()
    {
        return candidateThemeSource;
    }

    public void setThemeSource(MutableCandidateThemeSource candidateThemes)
    {
        this.candidateThemeSource = candidateThemes;
    }

    public List<String> getDefaultName()
    {
        return defaultName;
    }

    @Override
    public void setDefault(String name)
    {
        this.defaultName = asList(name);
    }

    public boolean addTheme(CandidateTheme theme)
    {
        return candidateThemeSource.add(theme);
    }

    public void setPersistence(ThemePersistence persistence)
    {
        this.persistence = persistence;
    }

}
