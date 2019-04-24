<#-- @ftlvariable name="loginView" type="sk.fri.uniza.views.LoginView" -->
<div class="section no-pad-bot" id="index-banner">
    <div class="container">
        <br><br>
        <h1 class="header center orange-text">Prihlásenie do aplikácie WindFarm demo</h1>
        <div class="row center-align">
            <a class="waves-effect waves-light btn-large orange" onclick="onBtnClick()">Prihlásenie cez OAuth 2.0</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    function onBtnClick() {
        let top = ($(window).height() / 2 - 640 / 2) + "px"
        let left = ($(window).width() / 2 - 480 / 2) + "px"

        let params = 'scrollbars=no,resizable=no,status=no,location=no,toolbar=no,menubar=no,width=480,height=640,left=' + left + ',top=' + top
        window.open('${(getLoginOAuthUrl()?no_esc)!}', "Login page", params)

    }

    window.logindone = function () {
        window.location.assign('${getRedirectUrl()!}')
    }
</script>