<#--https://freemarker.apache.org/docs/ref_builtins_sequence.html-->

<#-- {"title":"","url":"","icon":"","subheader":"","divider":"","roles":""}-->
<#--String USER_READ_ONLY = "USER_READ_ONLY";-->
<#--String ADMIN = "ADMIN";-->


<#assign menu_items = [
{"title":"Dashboard","url":"graphs","icon":"cloud"},
{"title":"Second Link","url":"#"},
{"title":"Fourth Link","url":"#"},
{"title":"Login","url":"login"},
{"subheader":"Info","divider":"true"},
{"title":"User info","icon":"person","url":"persons/user-info"},
{"title":"Nový užívateľ","icon":"person_add","url":"persons/new-user","roles":["ADMIN"]},
{"title":"Zoznam užívateľov","icon":"group","url":"persons","roles":["ADMIN"]},

{"subheader":"Subheader 2","divider":"true"},
{"title":"ADMIN READ_ONLY","url":"#","roles":["ADMIN","USER_READ_ONLY"]},
{"title":"ADMIN","url":"#","roles":["ADMIN"]},
{"title":"READ_ONLY","url":"#","roles":["USER_READ_ONLY"]}
]
>
