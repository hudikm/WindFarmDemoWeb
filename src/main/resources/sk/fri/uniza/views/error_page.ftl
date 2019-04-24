<#-- @ftlvariable name="" type="sk.fri.uniza.views.ErrorView" -->
<div class="container">
    <div class="valign-wrapper" style="margin-top: 5%">
        <div style="width: 100%;">
            <i class="large material-icons center-align" style="width: inherit;">
                sentiment_very_dissatisfied
            </i>
            <h2 class="center-align" style="margin-top: 0px;">${getErrorMessage().code!}</h2>
            <h4 class="center-align">${getErrorMessage().message!}</h4>
            <h6 class="center-align">${getErrorMessage().details!}</h6>
        </div>
    </div>
</div>

