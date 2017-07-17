# Spring Theme Tree

## Themeing for Spring - Reimagined

This library contains classes and auto configuration which allows you to

 * have multiple concurrent themes in your web application. 
 * extend themes without having to duplicate all the files
 * switch between them on the fly based on conditions expressed in Spring EL.
 * when coupled with Thymol and Thymeleaf, you can also have designers create, edit and view layouts without requiring a running application. They just double click.
 
 The classic example is the Christmas theme for a web store which changes a handful
 of image and header/footer assets during the Christmas period.

## Why re-imagine Spring themes?

Spring already has some support for loading resources based on a theme name, but
this is fairly basic and awkward, extending the same mechanism used for
externalising message strings, assuming you only want to change css,js and image
assets when switching themes.

In addition, the normal layout for static resources and MVC views dictates that
they live in different places, which is not an easy way to manage files which
naturally belong together.

## What's so good about Thymeleaf?

I'm totally sold on Thymeleaf as the best templating engine I've developed 
against, not just in the Java/Jvm world, but in the world world. I've used 
frameworks in Ruby, PHP and other JVM technologies like freemarker and velocity,
but they all fall short of Thymeleaf as an effective web development tool.

Why?

Because, with Thymeleaf I can double click a template and see what it would 
look like without running any web app.
This means my development cycles are faster. It also means I can do what
template engines have promised but never delivered in the past and share by 
layouts with web designers while I get on with the bulk of the code.

## Don't fragment includes break when you aren't running an app?

Thymeleaf support schlurping in things like shared headers and footers and nav bars
from other files, as do most template engines. You would think this would stop
me being able to just double click to view, and you'd be right - if it weren't 
for Thymol.

Thymol is a javascript dev-only shim which pretends to be Thymeleaf when the file
is launched from the filesystem. It handles all the Thymeleaf tags, includes etc
and even allows you to set test context variables to use in tags which you
would normally need a running controller to provide.

Thymeleaf + Thymol is a killer combination to get a self-contained set of files
you can just drop into the resource folder of an app and just works.

Except it doesn't. At least with Spring.

## Fixing Spring resource layouts

It doesn't work becuase with Spring/MVC the default locations for static 
resources and view templates are not the same.

Resources live in :

    src/main/resources/public 
    or 
    src/main/resources/static 

Whereas, view locations for Thymeleaf default to 

    src/main/resources/templates

In order to fix this, I created some configuration to change this, bringing the 
static asset locations to also live under the templates directory. Now I can
drop a set of files from my designer into the templates folder and I don't have
to copy the resources somewhere else. Moreover, I don't lose the magic
ability to double-click to view I got so excited about earlier.

## You haven't shown me any themes yet?

Well, no. Sorry for the long intro, but I thought it was important to spell out 
the problem so the solution makes more sense.

Once you have a single tree for all your view templates and associated assets,
it's not a huge leap to add another subfolder under the templates directory
to seperate template sets into themes.

I've actually gone with a theme layout that looks like

    /src/main/resources/templates/theme/[themename]

this allows me to still have 'default' resources under the templates folder
because the theme subfolder gets all the files for named themes out of the way.

Having got a set a named themes in subdirectories, the next question was how
to get Spring to show them at the right time. Can I show theme 1 to user X and 
theme 2 to User Y? Yes. Yes you can.

## So how do I use it?

### Prepare your templates and assets for themetree:
 1. Include this library as a dependancy in your spring boot thymeleaf app.
 2. Move your css,js etc asset folders from src/main/resources/public to under 
the src/main/templates folder.
 3. Test that it still works.
 4. Add thymol and configure so you can now double-click to view. (See the example 
   project (TBD Link here))
  
### choose a theme name

 1. Say you wanted to call you new theme 'barry'
 2. Move all the assets in the /src/main/resources tree to a new folder:
    /src/main/resources/templates/theme/barry/
 3. If you run your app you'll get a 404 error, because Spring needs to be
    given a little help in finding your views, fragments and assets.
 4. Add the spring-thymeleaf-themetree to your classpath using maven or gradle.
 5. The themetree classes should autoconfigure themselves into life and provide
    the necessary mapping.
 6. The only bit of config you need to add is a CascadedThemeResolver bean. (If
    you already have a spring ThemeResolver it will get slurped into a special
    single-theme instance of CascadedThemeResolver. If not, you can get started
    with a SingleThemeResolver which will return, well, a single theme.
 7. Why would one user want more than one theme? One word: Christmas. Say you 
    want to decorate your site with a little festive artwork during the 
    lead up to Christmas (or your favourite festival of choice). Themetree 
    allows you to keep your existing site template files, but update a handful
    of templates for things like headers or footers or navbars with a new theme
    name. Themetree will look for templates in a user-configured order eg:
    "christmas","default".
    In this case if there is a header under the christmas folder tree, that will
    override the header in the default location. Same goes for images, css, js 
    etc. 
