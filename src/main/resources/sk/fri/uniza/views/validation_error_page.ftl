<#-- @ftlvariable name="" type="sk.fri.uniza.views.ValidationErrorView" -->
<div class="container">
    <div class="valign-wrapper" style="margin-top: 5%">
        <div style="width: 100%;">
            <i class="large material-icons center-align" style="width: inherit;">
                sentiment_very_dissatisfied
            </i>
            <h2 class="center-align" style="margin-top: 0px;">Validation errors:</h2>
            <#list getMessage().errors as err>
                <h6 class="center-align">${err!}</h6>
            </#list>

        </div>
    </div>
</div>

