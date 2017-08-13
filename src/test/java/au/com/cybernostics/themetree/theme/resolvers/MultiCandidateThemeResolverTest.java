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
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;

/**
 *
 * @author jason wraxall
 */
@RunWith(MockitoJUnit44Runner.class)
public class MultiCandidateThemeResolverTest
{

    @Mock
    HttpServletRequest request;

    public MultiCandidateThemeResolverTest()
    {
    }

    @Test
    public void shouldReturnEmptyThemeWhenNoActiveThemesAndNoDefaultSet()
    {
        MultiCandidateThemeResolver resolver = new MultiCandidateThemeResolver();
        assertThat(resolver.getCurrentThemes(request).count(), is(1L));
        assertThat(resolver.getCurrentThemes(request).findFirst().get(), is(""));
    }

    @Test
    public void shouldReturnSpecifiedDefaultThemeWhenNoActiveThemes()
    {
        MultiCandidateThemeResolver resolver = new MultiCandidateThemeResolver();
        String specifiedDefault = "someValue";
        resolver.setDefault(specifiedDefault);
        assertThat(resolver.getCurrentThemes(request).count(), is(1L));
        assertThat(resolver.getCurrentThemes(request).findFirst().get(), is(specifiedDefault));
    }

    @Test
    public void shouldReturnActiveThemeAndSpecifiedDefaultTheme()
    {
        MultiCandidateThemeResolver resolver = new MultiCandidateThemeResolver();
        String specifiedDefault = "someValue";
        resolver.setDefault(specifiedDefault);
        assertThat(resolver.getCurrentThemes(request).count(), is(1L));
        assertThat(resolver.getCurrentThemes(request).findFirst().get(), is(specifiedDefault));
    }

    @Test
    public void shouldReturnAllActiveThemesInOrder()
    {
        MultiCandidateThemeResolver resolver = new MultiCandidateThemeResolver();
        String specifiedDefault = "someValue";
        resolver.setDefault(specifiedDefault);
        resolver.addTheme(new ConditionalCandidateTheme(10, "theme1", (r) -> true));
        resolver.addTheme(new ConditionalCandidateTheme(20, "theme2", (r) -> false));

        assertThat(resolver.getCurrentThemes(request).collect(toList()), Matchers.contains("theme1", "someValue"));
    }

}
