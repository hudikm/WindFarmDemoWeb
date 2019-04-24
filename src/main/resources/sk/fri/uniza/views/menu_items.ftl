<#--https://freemarker.apache.org/docs/ref_builtins_sequence.html-->

<#-- {"title":"","url":"","icon":"","subheader":"","divider":"","roles":""}-->
<#--String USER_READ_ONLY = "USER_READ_ONLY";-->
<#--String ADMIN = "ADMIN";-->


<#assign menu_items = [
{"title":"Fist link With Icon","url":"#","icon":"cloud"},
{"title":"Second Link","url":"#"},
{"title":"Third Link","url":"#","icon":"person","subheader":"Subheader","divider":"true"},
{"title":"Fourth Link","url":"#"},
{"title":"Login","url":"login"},
{"title":"User info","url":"user-info"},
{"title":"Zoznam užívateľov","url":"persons","roles":["ADMIN"]},
{"subheader":"Subheader 2","divider":"true"},
{"title":"ADMIN READ_ONLY","url":"#","roles":["ADMIN","USER_READ_ONLY"]},
{"title":"ADMIN","url":"#","roles":["ADMIN"]},
{"title":"READ_ONLY","url":"#","roles":["USER_READ_ONLY"]}
]
>
