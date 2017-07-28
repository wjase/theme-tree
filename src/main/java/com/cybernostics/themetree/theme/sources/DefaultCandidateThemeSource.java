package com.cybernostics.themetree.theme.sources;

/*
 * #%L
 * spring-thymeleaf-themetree
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
import com.cybernostics.themetree.config.TemplateResolverAutoConfiguration;
import static com.cybernostics.themetree.config.TemplateResolverAutoConfiguration.APP_PROPERTIES_THEME_PREFIX;
import com.cybernostics.themetree.theme.resolvers.CandidateTheme;
import com.cybernostics.themetree.theme.resolvers.ConditionalELCandidateTheme;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;

/**
 * A List-backed source of Candidate themes. Use it like a list to set add or
 * remove candidate themes.
 *
 * It is also an application event publisher which will notify subscribers when
 * the list has been updated.
 *
 * @author jason
 */
public class DefaultCandidateThemeSource implements MutableCandidateThemeSource
{

    private Set<CandidateTheme> themes = new TreeSet<>(CandidateTheme.sortedTheme);

    private ApplicationEventPublisher eventPublisher;

    public static MutableCandidateThemeSource fromProperties(Environment env, ApplicationContext context)
    {
        final DefaultCandidateThemeSource listCandidateThemeSource = new DefaultCandidateThemeSource();
        String[] themes = env.getProperty(APP_PROPERTIES_THEME_PREFIX)
                .trim()
                .replaceAll(" ", "")
                .split(",");
        int nextUnspecifidOrder = 0;
        for (String theme : themes)
        {
            String prefix = String.format("%s.%s.", APP_PROPERTIES_THEME_PREFIX, theme);
            Integer order = Integer.valueOf(env.getProperty(prefix + "order", Integer.toString(nextUnspecifidOrder++)));
            String activeExpr = env.getProperty(prefix + "active", "true");
            final ConditionalELCandidateTheme conditionalELCandidateTheme = new ConditionalELCandidateTheme(order, theme, activeExpr);
            conditionalELCandidateTheme.setApplicationContext(context);
            listCandidateThemeSource.add(conditionalELCandidateTheme);
            Logger.getLogger(TemplateResolverAutoConfiguration.class.getName()).log(Level.FINE, String.format("", theme, activeExpr));
        }

        return listCandidateThemeSource;
    }

    public DefaultCandidateThemeSource()
    {
    }

    public DefaultCandidateThemeSource(Collection<? extends CandidateTheme> c)
    {
        themes.addAll(c.stream().collect(toList()));
    }

    @Override
    public Stream<CandidateTheme> getCandidateThemes()
    {
        return themes.stream();
    }

    @Override
    public int size()
    {
        return themes.size();
    }

    @Override
    public boolean isEmpty()
    {
        final boolean result = themes.isEmpty();
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean add(CandidateTheme e)
    {
        final boolean result = themes.add(e);
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean remove(CandidateTheme e)
    {
        final boolean result = themes.remove(e);
        notifyEventListeners();
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends CandidateTheme> c)
    {
        final boolean result = themes.addAll(c);
        notifyEventListeners();
        return result;
    }

    @Override
    public void clear()
    {
        themes.clear();
        notifyEventListeners();
    }

    @Autowired
    @Override
    public void setEventPublisher(ApplicationEventPublisher eventPublisher)
    {
        this.eventPublisher = eventPublisher;
        notifyEventListeners();
    }

    private void notifyEventListeners()
    {
        if (eventPublisher != null)
        {
            eventPublisher.publishEvent(new ThemesUpdatedEvent(this));
        }
    }

}
