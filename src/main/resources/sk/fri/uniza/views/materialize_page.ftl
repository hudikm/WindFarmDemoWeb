<#ftl encoding="utf-8">
<#-- @ftlvariable name="" type="sk.fri.uniza.views.MaterializePage" -->
<!DOCTYPE html>
<html lang="sk">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
    <title>WindFarm Demo</title>
    <link rel="shortcut icon" type="image/png" href="/assets/img/favicon.png"/>

    <!-- CSS  -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="/assets/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="/assets/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>

    <#list (getCssFiles())! as cssfile>
        <link href="${cssfile}" type="text/css" rel="stylesheet">
    </#list>
    <!-- scripts-->
    <script src="/assets/js/jquery-3.4.0.min.js"></script>
    <script src="/assets/js/Chart.min.js"></script>
</head>

<body>
<#include getHeader().getTemplateName()>
<main>
    <#include getContentTemplate()>
</main>
<#include getFooter().getTemplateName()>
<!-- Other Scripts-->
<script src="/assets/js/materialize.js"></script>
<script src="/assets/js/init.js"></script>
<#list (getJsFiles())! as jsfile>
    <script src="${jsfile}"></script>
</#list>
</body>
</html>
