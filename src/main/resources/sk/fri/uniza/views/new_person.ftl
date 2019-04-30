<#-- @ftlvariable name="" type="sk.fri.uniza.views.NewPersonView" -->
<!-- calls getPersons().getName() and sanitizes it -->
<div class="section no-pad-bot" id="index-banner">
    <form class="container" action="/persons/new-user" method="post">
        <br><br>
        <div class="row">
            <div class="col s12">
                <div class="col s12 m4">
                    <h4>Personálne informacie</h4>
                </div>
                <div class="col s12 m8">
                    <div class="card">
                        <div class="card-title orange lighten-1" style="padding: 10px 10px 0px 10px">
                            <div class="row valign-wrapper">
                                <div class="col s3 m4 l2">
                                    <img src="/assets/img/img_avatar.png" alt="" class="circle responsive-img">
                                    <!-- notice the "circle" class -->
                                </div>
                                <div class="col s9 m8 l10">
                                    <span class="white-text">

                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="card-content">
                            <div class="row">
                              <div class="input-field col s6">
                                    <input id="first_name" name="first_name" type="text" class="validate" required>
                                    <label for="first_name">First Name</label>
                                </div>
                                <div class="input-field col s6">
                                    <input id="last_name" name="last_name" type="text" class="validate" required>
                                    <label for="last_name">Last Name</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                <select name="roles" required>

                                <#list getSystemRoles().getAllRoles() as role>
                                    <option value="${role}" >${role}</option>
                                </#list>

                                </select>
                                <label>Užívateľské oprávnenia</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="password"  name="password" type="password" class="validate" required>
                                    <label for="password">Password</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="username" name="username" type="email" class="validate" required>
                                    <label for="username">Užívateľské meno</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input id="email" name="email" type="email" class="validate" required>
                                    <label for="email">Email</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<#--        <div class="row">-->
<#--            <div class="col s12">-->
<#--                <div class="col s12 m4">-->
<#--                    <h4>Ostatné nastavenia</h4>-->
<#--                </div>-->
<#--                <div class="col s12 m8">-->
<#--                    <div class="card">-->
<#--                        <div class="card-content">-->
<#--                            <div class="row">-->
<#--                                <div class="input-field col s6">-->
<#--                                    <input id="first_name" type="text" class="validate">-->
<#--                                    <label for="first_name">First Name</label>-->
<#--                                </div>-->
<#--                                <div class="input-field col s6">-->
<#--                                    <input id="last_name" type="text" class="validate">-->
<#--                                    <label for="last_name">Last Name</label>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                            <div class="row">-->
<#--                                <div class="input-field col s12">-->
<#--                                    <input id="disabled" type="text" class="validate">-->
<#--                                    <label for="disabled">Disabled</label>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                            <div class="row">-->
<#--                                <div class="input-field col s12">-->
<#--                                    <input id="password" type="password" class="validate">-->
<#--                                    <label for="password">Password</label>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                            <div class="row">-->
<#--                                <div class="input-field col s12">-->
<#--                                    <input id="email" type="email" class="validate">-->
<#--                                    <label for="email">Email</label>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
        <div class="row">
            <div class="col s8 offset-s4 center-align">
                <button class="btn waves-effect waves-light orange" type="submit" name="action">Uložiť
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </div>
        <#if toastMsg??>
        <script>
            // A $( document ).ready() block.
            $( document ).ready(function() {
                M.toast({html: '${toastMsg?no_esc}'});
            });

        </script>
        </#if>