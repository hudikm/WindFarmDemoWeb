<#-- @ftlvariable name="" type="sk.fri.uniza.views.MaterializePage<sk.fri.uniza.views.MaterializeHeader,sk.fri.uniza.views.MaterializeFooter>" -->
<#import "menu_items.ftl" as menu>
<header>
    <nav class="light-blue lighten-1 nav-extended" role="navigation">
        <div class="nav-wrapper container">
            <a id="logo-container" href="/" class="brand-logo left logo">
                <img class="responsive-img" src="/assets/img/logo_small.png" style="max-height:50px;">
                <span class="hide-on-large-only show-on-medium-and-down " style="margin-left: 60px; font-size: large">WindFarm Demo</span>
                <span class="hide-on-med-and-down" style="margin-left: 60px; font-size: xx-large">WindFarm Demo</span>
            </a>

            <#--Top nav pannel-->
            <ul class="right hide-on-med-and-down">
                <#if (getHeader().getUser())??>
                    <li><a href="/login/logout"><i
                                    class="material-icons left">exit_to_app</i>Odhlasiť: ${(getHeader().getUser().name)!}
                        </a></li>
                </#if>
            </ul>

            <#if !getHeader().getShowSideMenu()>
            <#--Top nav pannel on mobile device change to side panel-->
                <ul id="nav-mobile" class="sidenav">
                    <#if (getHeader().getUser())??>
                        <#assign show_burger_menu_icon = true>
                        <li><a href="/login/logout">Odhlasiť</a></li>
                    </#if>
                </ul>
                <#if show_burger_menu_icon??>
                    <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
                </#if>
            <#else>
            <#-- LeftSide menu-->
                <ul id="slide-out" class="sidenav sidenav-fixed">
                    <li>
                        <div class="user-view">
                            <div class="background orange lighten-1">
                                <#--                                <img src="/assets/img/windmill_small_.jpg">-->
                            </div>
                            <a href="#user"><img class="circle" src="/assets/img/img_avatar.png"></a>
                            <a href="#name"><span class="white-text name">${getHeader().getUser().getName()}</span></a>
                            <a href="#email"><span class="white-text email">${getHeader().getUser().getName()}</span></a>
                        </div>
                    </li>

                    <#list menu.menu_items as item>
                    <#-- {"title":"Fist link With Icon","url":"#","icon":"clud","subheader":"","divider":""}-->
                        <#if !item.roles?has_content || (getHeader().getUser()?? && getHeader().getUser().isPresentInSomeRole(item.roles))>
                            <#if item.divider?has_content>
                                <li>
                                    <div class="divider"></div>
                                </li>
                            </#if>
                            <#if item.subheader?has_content>
                                <li><a class="subheader">${item.subheader}</a></li>
                            </#if>

                            <#if item.title?has_content>
                                <li ${(getCurrentUrl()?has_content && getCurrentUrl() == item.url)?then("class=\"active\""?no_esc,"")}>
                                    <a href="/${item.url!?no_esc}"
                                       class="waves-effect">
                                        <#if item.icon?has_content>
                                            <i class="material-icons left">${item.icon!}</i>
                                        </#if>
                                        ${item.title!}
                                    </a>
                                </li>
                            </#if>
                        </#if>




                    </#list>

                    <#--                    <li><a href="#!"><i class="material-icons left">cloud</i>First Link With Icon</a></li>-->
                    <#--                    <li><a href="#!">Second Link</a></li>-->
                    <#--                    <li>-->
                    <#--                        <div class="divider"></div>-->
                    <#--                    </li>-->
                    <#--                    <li><a class="subheader">Subheader</a></li>-->
                    <#--                    <li><a class="waves-effect" href="#!">Third Link With Waves</a></li>-->

                    <#if (getHeader().getUser())??>
                        <li><a class="hide-on-large-only show-on-medium-and-down waves-effect"
                               href="/login/logout"><i
                                        class="material-icons">exit_to_app</i>Odhlasiť: ${(getHeader().getUser().name)!}
                            </a>
                        </li>
                    </#if>


                </ul>
                <a href="#" data-target="slide-out" class="sidenav-trigger"><i class="material-icons">menu</i></a>
            </#if>
        </div>
        <#--  Display title-->
        <#if (getHeader().getTitle())??>
            <div class="nav-content container">
                <span class="nav-title">${getHeader().getTitle()}</span>
            </div>
        </#if>
    </nav>
</header>

